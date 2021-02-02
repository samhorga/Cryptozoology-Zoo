package com.example.CryptozoologyZoo.service;

import com.example.CryptozoologyZoo.model.*;
import com.example.CryptozoologyZoo.repository.AnimalRepository;
import com.example.CryptozoologyZoo.repository.ZooRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ZooServiceTest {

    @Mock
    ZooRepository zooRepository;

    @Mock
    AnimalRepository animalRepository;

    @InjectMocks
    ZooService zooService;


    @Test
    public void addAnimal() {
        Animal animal = new Animal("TIGER", AnimalType.WALKING, AnimalMood.UNHAPPY);
        Zoo zoo = new Zoo();
        zoo.setAnimalList(Collections.singletonList(animal));

        when(zooRepository.save(zoo)).thenReturn(zoo);

        Zoo actualZoo = zooService.addAnimal(animal);
        verify(zooRepository).save(zoo);

        assertEquals(animal, actualZoo.getAnimalList().get(0));
    }

    @Test
    public void getAnimals() {
        Animal tiger = new Animal("TIGER", AnimalType.WALKING, AnimalMood.UNHAPPY);
        Animal lion = new Animal("LION", AnimalType.WALKING, AnimalMood.UNHAPPY);
        Animal bird = new Animal("BIRD", AnimalType.FLYING, AnimalMood.UNHAPPY);

        Zoo expectedZoo = new Zoo();
        expectedZoo.setAnimalList(Arrays.asList(tiger, lion, bird));

        List<Zoo> zooList = Collections.singletonList(expectedZoo);

        when(zooRepository.findAll()).thenReturn(zooList);

        Zoo actualZoo = zooService.getAnimals();

        verify(zooRepository).findAll();
        assertEquals(expectedZoo.getAnimalList().toString(), actualZoo.getAnimalList().toString());
    }

    @Test
    public void feedAnimals() {
        Animal tiger = new Animal("TIGER", AnimalType.WALKING, AnimalMood.UNHAPPY);

        when(animalRepository.save(tiger)).thenReturn(tiger);
        when(animalRepository.findById(tiger.getId())).thenReturn(Optional.of(tiger));

        Animal animalTreatedReceived = zooService.feedAnimal(tiger.getId());

        verify(animalRepository).save(tiger);
        assertEquals(AnimalMood.HAPPY, animalTreatedReceived.getAnimalMood());
    }
}
