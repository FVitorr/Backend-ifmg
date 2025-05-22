package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.RoleDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserInsertDTO;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.projections.UserDetailsProjection;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    public UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable){
        Page<User> produtos = userRepository.findAll(pageable);

        return produtos.map(u -> new UserDTO(u));
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        Optional<User> opt = userRepository.findById(id);
        User user = opt.orElseThrow( () -> new ResourceNotFound("User not found"));

        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto){
        User entity = new User();
        copyDtoToEntity(dto, entity);
        User nv = userRepository.save(entity);
        return new UserDTO(nv);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {

        try{
            User entity = userRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFound("Usuario não encontrado" + id);
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDTO role: dto.getRoles()){
            Role r =  roleRepository.getReferenceById(role.getId());
            entity.getRoles().add(r);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRoleByEmail(email);

        if (result.size() == 0){
            throw new UsernameNotFoundException("Usuario not found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection p: result){
            user.addRole(new Role(p.getRoleId(),p.getAuthority()));
        }

        return user;
    }


}