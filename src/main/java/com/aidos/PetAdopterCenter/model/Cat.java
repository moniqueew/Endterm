package com.aidos.PetAdopterCenter.model;

public class Cat extends AnimalBase {
    private boolean indoor;

    public Cat(int id, String name, String breed, Shelter shelter, int age, boolean indoor) {
        super(id, name, breed, shelter, age);
        this.indoor = indoor;
    }

    @Override
    public String getType() {
        return AnimalType.Cat.name();
    }

    @Override
    public String getDetails() {
        return getName() + " " + (indoor ? "Indoor" : "Outdoor");
    }

    public boolean isIndoor() { return indoor; }

    public void setIndoor(boolean indoor) { this.indoor = indoor; }
}
