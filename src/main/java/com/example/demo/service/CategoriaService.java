package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.service.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Categoria;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.service.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

import com.example.demo.dtos.CategoriaDTO;


@Service
public class CategoriaService {
    
    @Autowired
    public CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAll(){
        List<Categoria> list = categoriaRepository.findAll();
        return list.stream().map(x-> new CategoriaDTO(x)).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAll(Pageable pageable){
        Page<Categoria> list = categoriaRepository.findAll(pageable);
        return list.map(x-> new CategoriaDTO(x));
    }



    @Transactional(readOnly = true)
    public CategoriaDTO findById(Long id){
        Optional<Categoria> obj = categoriaRepository.findById(id);
        Categoria categoria = obj.orElseThrow(() -> new ResourceNotFound("Categoria não encontrada"));
        return new CategoriaDTO(categoria);
    }

    @Transactional
    public CategoriaDTO insert(CategoriaDTO dto) {
        Categoria entity = new Categoria();
        entity.setName(dto.getName());
        return new CategoriaDTO(categoriaRepository.save(entity));
    }

    @Transactional
    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        try{
            Categoria entity = categoriaRepository.getReferenceById(id);
            entity.setName(dto.getName());
            return new CategoriaDTO(categoriaRepository.save(entity));
        }catch (EntityNotFoundException e){
            throw new ResourceNotFound("Categoria não encontrada");
        }

    }

    @Transactional
    public void delete(Long id){
        if (!categoriaRepository.existsById(id)){
            throw new ResourceNotFound("Categoria não encontrada");
        }
        try{
            categoriaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integration violation");
        }
    }
}
