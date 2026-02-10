package com.aidos.PetAdopterCenter.utils;

import com.aidos.PetAdopterCenter.dto.AnimalResponse;
import com.aidos.PetAdopterCenter.model.AnimalBase;
import com.aidos.PetAdopterCenter.model.AnimalType;
import com.aidos.PetAdopterCenter.model.Cat;
import com.aidos.PetAdopterCenter.model.Dog;

public class AnimalMapper {

    public static AnimalResponse toResponse(AnimalBase animal) {
        AnimalResponse res = new AnimalResponse();

        res.setId(animal.getId());
        res.setName(animal.getName());
        res.setBreed(animal.getBreed());
        res.setAge(animal.getAge());

        res.setShelterId(animal.getShelter().getId());
        res.setShelterName(animal.getShelter().getName());

        if (animal instanceof Dog dog) {
            res.setType(AnimalType.Dog);
            res.setWeight(dog.getWeight());
            res.setIndoor(null);

        } else if (animal instanceof Cat cat) {
            res.setType(AnimalType.Cat);
            res.setIndoor(cat.isIndoor());
            res.setWeight(null);
        }

        return res;
    }
}
