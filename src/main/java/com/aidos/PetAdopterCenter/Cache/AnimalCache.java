package com.aidos.PetAdopterCenter.Cache;

import com.aidos.PetAdopterCenter.model.AnimalBase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AnimalCache {

    private static AnimalCache instance;

    private final Map<Integer, AnimalBase> animalByIdCache;
    private List<AnimalBase> allAnimalsCache;
    private volatile boolean allAnimalsCacheValid;

    private AnimalCache() {
        this.animalByIdCache = new ConcurrentHashMap<>();
        this.allAnimalsCache = null;
        this.allAnimalsCacheValid = false;
    }

    public static synchronized AnimalCache getInstance() {
        if (instance == null) {
            instance = new AnimalCache();
        }
        return instance;
    }

    public Optional<List<AnimalBase>> getAllAnimals() {
        if (allAnimalsCacheValid && allAnimalsCache != null) {
            System.out.println("Cache hit");
            return Optional.of(allAnimalsCache);
        }
        System.out.println("Cache miss");
        return Optional.empty();
    }

    public void cacheAllAnimals(List<AnimalBase> animals) {
        this.allAnimalsCache = animals;
        this.allAnimalsCacheValid = true;
        System.out.println("Cached: " + animals.size() + " animals");
    }

    public Optional<AnimalBase> getAnimalById(int id) {
        AnimalBase animal = animalByIdCache.get(id);
        if (animal != null) {
            System.out.println("Cache hit (" + id + ")");
            return Optional.of(animal);
        }
        System.out.println("Cache miss (" + id + ")");
        return Optional.empty();
    }

    public void cacheAnimal(AnimalBase animal) {
        animalByIdCache.put(animal.getId(), animal);
        System.out.println("Cached animal: " + animal.getId() + " - " + animal.getName());
    }

    public void clearCache() {
        animalByIdCache.clear();
        allAnimalsCache = null;
        allAnimalsCacheValid = false;
        System.out.println("Cache cleared");
    }

    public void invalidateAnimal(int id) {
        animalByIdCache.remove(id);
        allAnimalsCacheValid = false;
        System.out.println("Invalidated cache for animal ID: " + id);
    }

    public void invalidateAllAnimals() {
        allAnimalsCacheValid = false;
        System.out.println("Invalidated getAllAnimals() cache");
    }
}