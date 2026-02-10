package com.aidos.PetAdopterCenter.dto;
import com.aidos.PetAdopterCenter.model.AnimalType;
public class AnimalRequest {
    private String name;
    private String breed;
    private int age;
    private int shelterId;

    private AnimalType type;
    private Double weight;
    private Boolean indoor;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getShelterId() { return shelterId; }
    public void setShelterId(int shelterId) { this.shelterId = shelterId; }

    public AnimalType getType() { return type; }
    public void setType(AnimalType type) { this.type = type; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Boolean getIndoor() { return indoor; }
    public void setIndoor(Boolean indoor) { this.indoor = indoor; }
}
