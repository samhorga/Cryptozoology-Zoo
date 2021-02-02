package com.example.CryptozoologyZoo.service;


import com.example.CryptozoologyZoo.model.*;
import com.example.CryptozoologyZoo.repository.AnimalRepository;
import com.example.CryptozoologyZoo.repository.HabitatRepository;
import com.example.CryptozoologyZoo.repository.ZooRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class ZooService {

    private ZooRepository zooRepository;
    private AnimalRepository animalRepository;
    private HabitatRepository habitatRepository;

    public ZooService(ZooRepository zooRepository, AnimalRepository animalRepository, HabitatRepository habitatRepository) {
        this.zooRepository = zooRepository;
        this.animalRepository = animalRepository;
        this.habitatRepository = habitatRepository;
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

    public Animal checkAnimalHabitat(Long animalId, Habitat habitat) {
        Animal animalFounded = animalRepository.findById(animalId).get();
        Habitat habitatFounded = habitatRepository.findById(habitat.getId()).get();
        animalFounded.setHabitat(habitatFounded);


        if(!habitat.isOccupied()) {
            if (animalFounded.getType() == AnimalType.FLYING && animalFounded.getHabitat().getHabitatEnum() == HabitatEnum.NEST
                    || animalFounded.getType() == AnimalType.SWIMMING && animalFounded.getHabitat().getHabitatEnum() == HabitatEnum.OCEAN
                    || animalFounded.getType() == AnimalType.WALKING && animalFounded.getHabitat().getHabitatEnum() == HabitatEnum.FOREST) {
                animalFounded.setAnimalMood(AnimalMood.HAPPY);
            } else {
                animalFounded.setAnimalMood(AnimalMood.UNHAPPY);
            }
        }

        animalRepository.save(animalFounded);

        return animalFounded;

    }
}
