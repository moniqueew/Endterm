package com.aidos.PetAdopterCenter.dto;

public class AnimalUpdate {
    private String name;
    private String breed;
    private Integer age;
    private Integer shelterId;

    private Double weight;
    private Boolean indoor;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getShelterId() { return shelterId; }
    public void setShelterId(Integer shelterId) { this.shelterId = shelterId; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Boolean getIndoor() { return indoor; }
    public void setIndoor(Boolean indoor) { this.indoor = indoor; }
}
