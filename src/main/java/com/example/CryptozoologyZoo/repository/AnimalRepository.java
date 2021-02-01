package com.example.CryptozoologyZoo.repository;

import com.example.CryptozoologyZoo.model.Animal;
import com.example.CryptozoologyZoo.model.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
