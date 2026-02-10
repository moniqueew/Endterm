package com.aidos.PetAdopterCenter.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShelterRepository {
    private final JdbcTemplate jdbc;

    public ShelterRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean existsById(int id) {
        Integer v = jdbc.queryForObject("SELECT 1 FROM shelters WHERE id = ?", Integer.class, id);
        return v != null;
    }

    public String findNameById(int id) {
        return jdbc.queryForObject("SELECT name FROM shelters WHERE id = ?", String.class, id);
    }
}
