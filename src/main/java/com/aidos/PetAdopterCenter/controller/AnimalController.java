package com.aidos.PetAdopterCenter.controller;

import com.aidos.PetAdopterCenter.dto.AnimalRequest;
import com.aidos.PetAdopterCenter.dto.AnimalResponse;
import com.aidos.PetAdopterCenter.dto.AnimalUpdate;
import com.aidos.PetAdopterCenter.model.AnimalBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aidos.PetAdopterCenter.service.AnimalService;
import com.aidos.PetAdopterCenter.utils.AnimalMapper;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalService service;

    public AnimalController(AnimalService service) {
        this.service = service;
    }

    @GetMapping
    public List<AnimalResponse> getAll() {
        return service.getAllAnimals().stream().map(AnimalMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public AnimalResponse getById(@PathVariable int id) {
        AnimalBase animal = service.getAnimalById(id);
        return AnimalMapper.toResponse(animal);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AnimalRequest req) {
        int newId = service.addAnimal(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(newId);
    }

    @PutMapping("/{id}")
    public AnimalResponse update(@PathVariable int id, @RequestBody AnimalUpdate req) {
        service.updateAnimal(id, req);
        return AnimalMapper.toResponse(service.getAnimalById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        service.removeAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
