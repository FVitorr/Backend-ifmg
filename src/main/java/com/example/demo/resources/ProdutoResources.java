package com.example.demo.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dtos.ProdutoDTO;
import com.example.demo.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/produto")
@Tag(name = "Product", description = "Controller/Resource for products")
public class ProdutoResources {

    @Autowired 
    private ProdutoService produtoService;

    @GetMapping(produces = "application/json")
    @Operation(
        description = "Get all products",
        summary = "Get all products",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200")
        }
    )
    public ResponseEntity<Page<ProdutoDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok( produtoService.findAll(pageable));
    }


    @GetMapping(value = "/{id}")
    @Operation(
        description = "Get a product",
        summary = "Get a product",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200"),
            @ApiResponse(description = "not found", responseCode = "404")
        }
    )
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok( produtoService.findById(id));
    }

    @PostMapping
    @Operation(
        description = "Create a product",
        summary = "Create  a product",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200"),
            @ApiResponse(description = "bad resquest", responseCode = "400"),
            @ApiResponse(description = "unautorized", responseCode = "401"),
            @ApiResponse(description = "forbiden", responseCode = "403"),
            @ApiResponse(description = "not found", responseCode = "404"),
        }
    )
    @PreAuthorize("hasAnyAuthority('ROLES_ADMIN', 'ROLE_OPERATOR')")
    public ResponseEntity<ProdutoDTO> insert (@Valid @RequestBody ProdutoDTO dto){
        dto = produtoService.insertProduto(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    @Operation(
        description = "update a product",
        summary = "update a product",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200"),
            @ApiResponse(description = "bad resquest", responseCode = "400"),
            @ApiResponse(description = "unautorized", responseCode = "401"),
            @ApiResponse(description = "forbiden", responseCode = "403"),
            @ApiResponse(description = "not found", responseCode = "404"),
        }
    )
    @PreAuthorize("hasAnyAuthority('ROLES_ADMIN', 'ROLE_OPERATOR')")
    public ResponseEntity<ProdutoDTO> update(@Valid @PathVariable Long id, 
    @RequestBody ProdutoDTO dto ){
        dto = produtoService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }


    @DeleteMapping(value = "/{id}")
    @Operation(
        description = "delete a product",
        summary = "delete a product",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200"),
            @ApiResponse(description = "bad resquest", responseCode = "400"),
            @ApiResponse(description = "unautorized", responseCode = "401"),
            @ApiResponse(description = "forbiden", responseCode = "403"),
            @ApiResponse(description = "not found", responseCode = "404"),
        }
    )
    @PreAuthorize("hasAnyAuthority('ROLES_ADMIN', 'ROLE_OPERATOR')")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.noContent().build();

    }
}