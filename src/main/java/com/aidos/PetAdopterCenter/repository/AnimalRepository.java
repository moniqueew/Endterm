package com.aidos.PetAdopterCenter.repository;

import com.aidos.PetAdopterCenter.model.AnimalBase;
import com.aidos.PetAdopterCenter.model.Cat;
import com.aidos.PetAdopterCenter.model.Dog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AnimalRepository {
    private final JdbcTemplate jdbc;
    private final AnimalRowMapper mapper = new AnimalRowMapper();

    public AnimalRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int create(AnimalBase animal) {

        String insertAnimalSql = """
            INSERT INTO animals (name, breed, age, shelter_id, animal_type)
            VALUES (?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertAnimalSql, new String[]{"id"});
            ps.setString(1, animal.getName());
            ps.setString(2, animal.getBreed());
            ps.setInt(3, animal.getAge());
            ps.setInt(4, animal.getShelter().getId());
            ps.setString(5, animal.getType());
            return ps;
        }, keyHolder);

        Integer newId = null;

        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null && keys.get("id") != null) {
            newId = ((Number) keys.get("id")).intValue();
        } else if (keyHolder.getKey() != null) {
            newId = keyHolder.getKey().intValue();
        }

        if (newId == null) throw new IllegalStateException("No generated id returned");

        if (animal instanceof Dog dog) {
            jdbc.update("INSERT INTO dogs (animal_id, weight) VALUES (?, ?)", newId, dog.getWeight());
        } else if (animal instanceof Cat cat) {
            jdbc.update("INSERT INTO cats (animal_id, indoor) VALUES (?, ?)", newId, cat.isIndoor());
        } else {
            throw new IllegalArgumentException("Unknown subclass of AnimalBase");
        }

        return newId;
    }

    public List<AnimalBase> getAll() {
        String sql = """
            SELECT a.id, a.name, a.breed, a.age, a.animal_type,
                   s.id AS shelter_id, s.name AS shelter_name,
                   d.weight, c.indoor
            FROM animals a
            JOIN shelters s ON s.id = a.shelter_id
            LEFT JOIN dogs d ON d.animal_id = a.id
            LEFT JOIN cats c ON c.animal_id = a.id
            ORDER BY a.id
        """;
        return jdbc.query(sql, mapper);
    }

    public Optional<AnimalBase> getById(int id) {
        String sql = """
            SELECT a.id, a.name, a.breed, a.age, a.animal_type,
                   s.id AS shelter_id, s.name AS shelter_name,
                   d.weight, c.indoor
            FROM animals a
            JOIN shelters s ON s.id = a.shelter_id
            LEFT JOIN dogs d ON d.animal_id = a.id
            LEFT JOIN cats c ON c.animal_id = a.id
            WHERE a.id = ?
        """;

        List<AnimalBase> list = jdbc.query(sql, mapper, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public int update(int id, AnimalBase animal) {

        String updateAnimalSql = """
            UPDATE animals
            SET name = ?, breed = ?, age = ?, shelter_id = ?
            WHERE id = ?
        """;

        int affected = jdbc.update(
                updateAnimalSql,
                animal.getName(),
                animal.getBreed(),
                animal.getAge(),
                animal.getShelter().getId(),
                id
        );

        if (affected == 0) return 0;

        if (animal instanceof Dog dog) {
            jdbc.update("DELETE FROM cats WHERE animal_id = ?", id);
            jdbc.update("INSERT INTO dogs (animal_id, weight) VALUES (?, ?) " +
                    "ON CONFLICT (animal_id) DO UPDATE SET weight = EXCLUDED.weight", id, dog.getWeight());
        } else if (animal instanceof Cat cat) {
            jdbc.update("DELETE FROM dogs WHERE animal_id = ?", id);
            jdbc.update("INSERT INTO cats (animal_id, indoor) VALUES (?, ?) " +
                    "ON CONFLICT (animal_id) DO UPDATE SET indoor = EXCLUDED.indoor", id, cat.isIndoor());
        }

        return affected;
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM animals WHERE id = ?", id);
    }

    public boolean existsByNameAndBreed(String name, String breed) {
        Integer filtered = jdbc.queryForObject(
                "SELECT COUNT(*) FROM animals WHERE name = ? AND breed = ?",
                Integer.class,
                name, breed
        );

        return filtered != null && filtered > 0;
    }
}
