package com.example.demo.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserInsertDTO;
import com.example.demo.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "user", description = "Controller/Resource for user")
public class UserResources {

    @Autowired 
    private UserService userService;

    @GetMapping(produces = "application/json")
    @Operation(
        description = "Get all products",
        summary = "Get all products",
        responses = {
            @ApiResponse(description = "ok", responseCode = "200")
        }
    )
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok( userService.findAll(pageable));
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
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok( userService.findById(id));
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
    public ResponseEntity<UserDTO> insert (@Valid @RequestBody UserInsertDTO dto){
        UserDTO user = userService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
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
    public ResponseEntity<UserDTO> update(@Valid @PathVariable Long id, 
    @RequestBody UserDTO dto ){
        dto = userService.update(id, dto);
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
    public ResponseEntity<Void> delete (@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();

    }
}