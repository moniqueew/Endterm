package com.aidos.PetAdopterCenter.patterns;

import com.aidos.PetAdopterCenter.dto.AnimalRequest;
import com.aidos.PetAdopterCenter.model.AnimalBase;
import com.aidos.PetAdopterCenter.model.Cat;
import com.aidos.PetAdopterCenter.model.Dog;
import com.aidos.PetAdopterCenter.model.Shelter;


public class AnimalFactory {

    public static AnimalBase create(AnimalRequest req, Shelter shelter) {

        if (req.getType() == null) {
            throw new IllegalArgumentException("type is required");
        }

        return switch (req.getType()) {
            case Dog -> new Dog(
                    0,
                    req.getName(),
                    req.getBreed(),
                    shelter,
                    req.getAge(),
                    req.getWeight()
            );
            case Cat -> new Cat(
                    0,
                    req.getName(),
                    req.getBreed(),
                    shelter,
                    req.getAge(),
                    req.getIndoor()
            );
        };
    }
}
