package com.example.CryptozoologyZoo.repository;

import com.example.CryptozoologyZoo.model.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZooRepository extends JpaRepository<Zoo, Long> {
}
