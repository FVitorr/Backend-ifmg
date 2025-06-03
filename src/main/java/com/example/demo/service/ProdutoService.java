package com.example.demo.service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import com.example.demo.entities.Categoria;
import com.example.demo.entities.Produto;
import com.example.demo.service.exceptions.ResourceNotFound;

import jakarta.persistence.EntityNotFoundException;

import com.example.demo.repository.ProdutoRepository;
import com.example.demo.resources.ProdutoResources;
import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.dtos.ProdutoListaDTO;


@Service
public class ProdutoService {
    
    @Autowired
    public ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(Pageable pageable){
        Page<Produto> produtos = produtoRepository.findAll(pageable);

        return produtos.map(product -> new ProdutoDTO(product)
        .add(linkTo(methodOn(ProdutoResources.class).findAll(null)).withSelfRel())
        .add(linkTo(methodOn(ProdutoResources.class).findById(product.getId())).withRel("Get a product")));
    }


    @Transactional(readOnly = true)
    public ProdutoDTO findById(Long id){
        Optional<Produto> obj = produtoRepository.findById(id);
        Produto entity = obj.orElseThrow(() -> new ResourceNotFound("Produto não encontrado"));
        return new ProdutoDTO(entity)
             .add( linkTo(methodOn(ProdutoResources.class).findById(entity.getId())).withSelfRel())
             .add( linkTo(methodOn(ProdutoResources.class).findAll(null)).withRel("All products"))
             .add( linkTo(methodOn(ProdutoResources.class).update(entity.getId(),null)).withRel("Update product"))
             .add( linkTo(methodOn(ProdutoResources.class).delete(entity.getId())).withRel("Delete product"));
    }
    
    @Transactional
    public ProdutoDTO insertProduto(ProdutoDTO dto){
        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);

        entity = produtoRepository.save(entity);

        return new ProdutoDTO(entity)
             .add( linkTo(methodOn(ProdutoResources.class).findById(entity.getId())).withRel("Get a product"))
             .add( linkTo(methodOn(ProdutoResources.class).findAll(null)).withRel("All products"))
             .add( linkTo(methodOn(ProdutoResources.class).update(entity.getId(),null)).withRel("Update product"))
             .add( linkTo(methodOn(ProdutoResources.class).delete(entity.getId())).withRel("Delete product"));
    }


    @Transactional
    public ProdutoDTO update(Long id, ProdutoDTO dto) {
        try{
            Produto entity = produtoRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = produtoRepository.save(entity);

            return new ProdutoDTO(entity)
                .add( linkTo(methodOn(ProdutoResources.class).findById(entity.getId())).withRel("Get a product"))
                .add( linkTo(methodOn(ProdutoResources.class).findAll(null)).withRel("All products"))
                .add( linkTo(methodOn(ProdutoResources.class).update(entity.getId(),null)).withRel("Update product"))
                .add( linkTo(methodOn(ProdutoResources.class).delete(entity.getId())).withRel("Delete product"));
        }catch (EntityNotFoundException e){
            throw new ResourceNotFound("Categoria não encontrada");
        }

    }


    @Transactional
    public void delete(Long id){
        if (!produtoRepository.existsById(id)){
            throw new ResourceNotFound("Produto não encontrada");
        }
        try{
            produtoRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new ResourceNotFound("Integration violation");
        }
    }



    private void copyDtoToEntity(ProdutoDTO dto, Produto entity){
        entity.setName(dto.getNome());
        entity.setDescription(dto.getDescricao());
        entity.setPrice(dto.getPrice());
        entity.setImageUrl(dto.getImageUrl());
        dto.getCategorias().forEach(c->entity.getCategorias().add(new Categoria(c)));
    }


    public Page<ProdutoListaDTO> findAllPaged(String name, String categpryId, Pageable pageable) {
        List<Long> categoriasId = null;

        if (!categpryId.equals(0)){
            categoriasId = Arrays.stream(categpryId.split(",")).map(id -> Long.parseLong(id)).toList();
        }

        Page<ProdutoListaDTO> page = produtoRepository.searchProducts(categoriasId, name, pageable);

        Page<ProdutoListaDTO> dtos = page.stream().map(p -> new ProdutoListaDTO(p)).toList();

        return new PageImpl<>(dtos, pageable, page.getTotalElements());

    }

}
