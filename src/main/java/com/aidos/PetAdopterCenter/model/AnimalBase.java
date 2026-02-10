package com.aidos.PetAdopterCenter.model;

public abstract class AnimalBase extends BaseEntity {

    private String breed;
    private Shelter shelter;
    private int age;

    protected AnimalBase(int id, String name, String breed, Shelter shelter, int age) {
        super(id, name);
        setBreed(breed);
        setShelter(shelter);
        setAge(age);
    }

    public void validate() {
        if (getName() == null || getName().isBlank()) throw new IllegalArgumentException("name cannot be empty");
        if (breed == null || breed.isBlank()) throw new IllegalArgumentException("breed cannot be empty");
        if (shelter == null) throw new IllegalArgumentException("shelter is required");
        if (age < 0) throw new IllegalArgumentException("age must be >= 0");
    }

    public String getBreed() { return breed; }

    public void setBreed(String breed) {
        if (breed == null || breed.isBlank()) throw new IllegalArgumentException("breed cannot be empty");
        this.breed = breed;
    }

    public Shelter getShelter() { return shelter; }

    public void setShelter(Shelter shelter) {
        if (shelter == null) throw new IllegalArgumentException("shelter is required");
        this.shelter = shelter;
    }

    public int getAge() { return age; }

    public void setAge(int age) {
        if (age < 0) throw new IllegalArgumentException("age must be >= 0");
        this.age = age;
    }
}
