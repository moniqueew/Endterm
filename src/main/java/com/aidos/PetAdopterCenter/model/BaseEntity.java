package com.aidos.PetAdopterCenter.model;

public abstract class BaseEntity {
    private int id;
    private String name;

    protected BaseEntity(int id, String name) {
        this.id = id;
        setName(name);
    }

    public abstract String getType();

    public abstract String getDetails();

    public String getInfo() {
        return id + " - " + name;
    }

    public int getId() { return id; }

    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("id must be >= 0");
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        this.name = name;
    }
}
