package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Categoria;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.service.exceptions.ResourceNotFound;
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
    public CategoriaDTO findById(Long id){
        Optional<Categoria> obj = categoriaRepository.findById(id);
        Categoria categoria = obj.orElseThrow(() -> new ResourceNotFound("Dados não encontrado"));
        return new CategoriaDTO(categoria);
    }
}
