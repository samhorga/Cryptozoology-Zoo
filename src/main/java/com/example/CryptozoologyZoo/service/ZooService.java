package com.example.CryptozoologyZoo.service;


import com.example.CryptozoologyZoo.model.Animal;
import com.example.CryptozoologyZoo.model.AnimalMood;
import com.example.CryptozoologyZoo.model.Zoo;
import com.example.CryptozoologyZoo.repository.AnimalRepository;
import com.example.CryptozoologyZoo.repository.ZooRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ZooService {

    private ZooRepository zooRepository;
    private AnimalRepository animalRepository;

    public ZooService(ZooRepository zooRepository, AnimalRepository animalRepository) {
        this.zooRepository = zooRepository;
        this.animalRepository = animalRepository;
    }

    public Zoo addAnimal(Animal animal) {
        Zoo zoo = new Zoo();
        zoo.setAnimalList(Collections.singletonList(animal));
        return zooRepository.save(zoo);
    }

    public Zoo getAnimals() {
        return zooRepository.findAll().stream().findFirst().get();
    }

    public Animal feedAnimal(Long id) {
        Animal animalFounded = animalRepository.findById(id).get();
        animalFounded.setAnimalMood(AnimalMood.HAPPY);
        return animalRepository.save(animalFounded);
    }
}
