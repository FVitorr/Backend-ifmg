package com.example.demo.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dtos.CategoriaDTO;
import com.example.demo.service.CategoriaService;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaResources {
    
    @Autowired
    private CategoriaService categoriaService;

    // @GetMapping
    // public ResponseEntity<List<CategoriaDTO>> findAll(){
    //     return ResponseEntity.ok().body(categoriaService.findAll());
    // }

    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> findAll(  @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                        @RequestParam(value = "orderBy", defaultValue = "id") String[] orderBy){

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok().body(categoriaService.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(categoriaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> insert (@RequestBody CategoriaDTO dto){
        dto = categoriaService.insert(dto);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> update (@PathVariable Long id,
                                                @RequestBody CategoriaDTO dto){
        dto = categoriaService.update(id,dto);
        return ResponseEntity.ok().body(dto);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    } 
        
}
