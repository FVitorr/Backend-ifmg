package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.Categoria;
import com.example.demo.entities.Produto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.service.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

import com.example.demo.repository.ProdutoRepository;
import com.example.demo.dtos.CategoriaDTO;
import com.example.demo.dtos.ProdutoDTO;


@Service
public class ProdutoService {
    
    @Autowired
    public ProdutoRepository ProdutoRepository;

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(Pageable pageable){
        Page<Produto> produtos = ProdutoRepository.findAll(pageable);

        return produtos.map(p -> new ProdutoDTO(p));
    }


    @Transactional(readOnly = true)
    public ProdutoDTO findById(Long id){
        Optional<Produto> obj = ProdutoRepository.findById(id);
        Produto entity = obj.orElseThrow(() -> new ResourceNotFound("Produto não encontrado"));
        return new ProdutoDTO(entity);
    }
    
    @Transactional
    public ProdutoDTO insertProduto(ProdutoDTO dto){
        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);

        entity = ProdutoRepository.save(entity);

        return new ProdutoDTO(entity);
    }


    @Transactional
    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        try{
            Produto entity = ProdutoRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return new ProdutoDTO(ProdutoRepository.save(entity));
        }catch (EntityNotFoundException e){
            throw new ResourceNotFound("Categoria não encontrada");
        }

    }


    @Transactional
    public void delete(Long id){
        if (!ProdutoRepository.existsById(id)){
            throw new ResourceNotFound("Produto não encontrada");
        }
        try{
            ProdutoRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new ResourceNotFound("Integration violation");
        }
    }



    private void copyDtoToEntity(ProdutoDTO dto, Produto entity){
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setPrice(dto.getPrice());
        entity.setImageUrl(dto.getImageUrl());
    }

}
