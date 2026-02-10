package com.aidos.PetAdopterCenter.model;

public class Shelter {
    private int id;
    private String name;

    public Shelter(int id, String name) {
        if (id < 0) throw new IllegalArgumentException("shelter id must be >= 0");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("shelter name cannot be empty");
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("shelter id must be >= 0");
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("shelter name cannot be empty");
        this.name = name;
    }
}
