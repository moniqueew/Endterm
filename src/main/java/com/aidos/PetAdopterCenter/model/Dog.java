package com.aidos.PetAdopterCenter.model;

public class Dog extends AnimalBase {

    private double weight;

    public Dog(int id, String name, String breed, Shelter shelter, int age, double weight) {
        super(id, name, breed, shelter, age);
        setWeight(weight);
    }

    @Override
    public String getType() {
        return AnimalType.Dog.name();
    }

    @Override
    public String getDetails() {
        return getName() + " " + weight + " kg";
    }

    public double getWeight() { return weight; }

    public void setWeight(double weight) {
        if (weight <= 0) throw new IllegalArgumentException("weight must be > 0");
        this.weight = weight;
    }
}
