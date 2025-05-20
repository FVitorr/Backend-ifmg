package com.example.demo.service;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.dtos.RoleDTO;
import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserInsertDTO;
import com.example.demo.entities.Produto;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repository.ProdutoRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.resources.ProdutoResources;
import com.example.demo.service.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Service
public class UserService {
    
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


}