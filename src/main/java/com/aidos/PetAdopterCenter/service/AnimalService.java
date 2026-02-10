package com.aidos.PetAdopterCenter.service;

import com.aidos.PetAdopterCenter.dto.AnimalRequest;
import com.aidos.PetAdopterCenter.dto.AnimalUpdate;
import com.aidos.PetAdopterCenter.exception.DatabaseOperationException;
import com.aidos.PetAdopterCenter.exception.DuplicateResourceException;
import com.aidos.PetAdopterCenter.exception.InvalidInputException;
import com.aidos.PetAdopterCenter.exception.ResourceNotFoundException;
import com.aidos.PetAdopterCenter.model.*;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aidos.PetAdopterCenter.patterns.AnimalFactory;
import com.aidos.PetAdopterCenter.repository.AnimalRepository;
import com.aidos.PetAdopterCenter.repository.ShelterRepository;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepo;
    private final ShelterRepository shelterRepo;

    public AnimalService(AnimalRepository animalRepo, ShelterRepository shelterRepo) {
        this.animalRepo = animalRepo;
        this.shelterRepo = shelterRepo;
    }

    public List<AnimalBase> getAllAnimals() {
        try {
            return animalRepo.getAll();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database can't access", e);
        }
    }

    public AnimalBase getAnimalById(int id) {
        if (id <= 0) throw new InvalidInputException("id must be positive");

        try {
            return animalRepo.getById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Animal " + id + " not found"));
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database can't access", e);
        }
    }

    @Transactional
    public int addAnimal(AnimalRequest req) {
        try {
            validateRequest(req);

            if (!shelterRepo.existsById(req.getShelterId())) {
                throw new ResourceNotFoundException("Shelter with id " + req.getShelterId() + " not found");
            }

            if (animalRepo.existsByNameAndBreed(req.getName(), req.getBreed())) {
                throw new DuplicateResourceException("Animal already exists (same name and breed)");
            }

            String shelterName = shelterRepo.findNameById(req.getShelterId());

            Shelter shelter = new Shelter(req.getShelterId(), shelterName);

            AnimalBase animal = AnimalFactory.create(req, shelter);

            animal.validate();

            return animalRepo.create(animal);

        } catch (InvalidInputException | ResourceNotFoundException | DuplicateResourceException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error while creating animal", e);
        } catch (Exception e) {
            throw new DatabaseOperationException("Unexpected error while creating animal", e);
        }
    }

    @Transactional
    public void updateAnimal(int id, AnimalUpdate req) {

        if (id <= 0) throw new InvalidInputException("id must be positive");
        if (req == null) throw new InvalidInputException("Request body is required");

        AnimalBase existing = getAnimalById(id);

        if (req.getShelterId() != null) {
            if (req.getShelterId() <= 0) throw new InvalidInputException("shelterId must be positive");
            if (!shelterRepo.existsById(req.getShelterId())) {
                throw new ResourceNotFoundException("Shelter with id " + req.getShelterId() + " not found");
            }
            String shelterName = shelterRepo.findNameById(req.getShelterId());
            existing.setShelter(new Shelter(req.getShelterId(), shelterName));
        }

        if (req.getName() != null) existing.setName(req.getName());
        if (req.getBreed() != null) existing.setBreed(req.getBreed());
        if (req.getAge() != null) existing.setAge(req.getAge());

        if (existing instanceof Dog dog) {
            if (req.getWeight() != null) dog.setWeight(req.getWeight());
            if (req.getIndoor() != null) {
                throw new InvalidInputException("Trying to put indoor in Dog");
            }
        } else if (existing instanceof Cat cat) {
            if (req.getIndoor() != null) cat.setIndoor(req.getIndoor());
            if (req.getWeight() != null) {
                throw new InvalidInputException("Trying to put weight in Cat");
            }
        }

        existing.validate();

        int affected = animalRepo.update(id, existing);
        if (affected == 0) {
            throw new ResourceNotFoundException("Animal not found id: " + id);
        }
    }

    @Transactional
    public void removeAnimal(int id) {
        if (id <= 0) throw new InvalidInputException("id must be positive");

        try {
            int affected = animalRepo.delete(id);
            if (affected == 0) {
                throw new ResourceNotFoundException("Animal not found id:" + id);
            }
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error while deleting animal", e);
        }
    }

    private void validateRequest(AnimalRequest req) {
        if (req == null) throw new InvalidInputException("Request body is required");

        if (req.getName() == null || req.getName().isBlank())
            throw new InvalidInputException("name is required");

        if (req.getBreed() == null || req.getBreed().isBlank())
            throw new InvalidInputException("breed is required");

        if (req.getAge() < 0)
            throw new InvalidInputException("age must be >= 0");

        if (req.getShelterId() <= 0)
            throw new InvalidInputException("shelterId must be positive");

        if (req.getType() == null)
            throw new InvalidInputException("type is required");

        if (req.getType() == AnimalType.Dog) {
            if (req.getWeight() == null || req.getWeight() <= 0) {
                throw new InvalidInputException("[Dog] weight must be > 0");
            }
        }

        if (req.getType() == AnimalType.Cat) {
            if (req.getIndoor() == null) {
                throw new InvalidInputException("[Cat] indoor is required");
            }
        }
    }
}
