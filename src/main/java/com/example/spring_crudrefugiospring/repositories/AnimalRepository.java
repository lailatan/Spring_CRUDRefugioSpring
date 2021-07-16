package com.example.spring_crudrefugiospring.repositories;

import com.example.spring_crudrefugiospring.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    public List<Animal> findAnimalesByNombreContaining(String nombre);

    public List<Animal> findAnimalesByColorEquals(String color);

    public List<Animal> findAnimalesByEdadBetween(Integer edadDesde, Integer edadHasta);

    @Query("SELECT DISTINCT animal.color FROM Animal animal")
    public List<String> findDistinctByColor();
}
