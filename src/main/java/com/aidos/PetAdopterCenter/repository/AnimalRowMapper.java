package com.aidos.PetAdopterCenter.repository;

import com.aidos.PetAdopterCenter.model.AnimalBase;
import com.aidos.PetAdopterCenter.model.Cat;
import com.aidos.PetAdopterCenter.model.Dog;
import com.aidos.PetAdopterCenter.model.Shelter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalRowMapper implements RowMapper<AnimalBase> {

    @Override
    public AnimalBase mapRow(ResultSet rs, int rowNum) throws SQLException {

        Shelter shelter = new Shelter(rs.getInt("shelter_id"), rs.getString("shelter_name"));

        String type = rs.getString("animal_type");
        int id = rs.getInt("id");

        String name = rs.getString("name");
        String breed = rs.getString("breed");
        int age = rs.getInt("age");

        if ("Dog".equals(type)) {
            double weight = rs.getDouble("weight");
            return new Dog(id, name, breed, shelter, age, weight);
        }

        if ("Cat".equals(type)) {
            boolean indoor = rs.getBoolean("indoor");
            return new Cat(id, name, breed, shelter, age, indoor);
        }

        throw new IllegalArgumentException("Unknown animal_type: " + type);
    }
}
