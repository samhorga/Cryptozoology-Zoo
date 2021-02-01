package com.example.CryptozoologyZoo.service;


import com.example.CryptozoologyZoo.model.Animal;
import com.example.CryptozoologyZoo.model.Zoo;
import com.example.CryptozoologyZoo.repository.ZooRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ZooService {

    private ZooRepository zooRepository;

    public ZooService(ZooRepository zooRepository) {
        this.zooRepository = zooRepository;
    }

    public Zoo addAnimal(Animal animal) {
        Zoo zoo = new Zoo();
        zoo.setAnimalList(Collections.singletonList(animal));
        return zooRepository.save(zoo);
    }

    public Zoo getAnimals() {
        return zooRepository.findAll().stream().findFirst().get();
    }
}
