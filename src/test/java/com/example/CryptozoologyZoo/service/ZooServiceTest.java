package com.example.CryptozoologyZoo.service;

import com.example.CryptozoologyZoo.model.Animal;
import com.example.CryptozoologyZoo.model.AnimalType;
import com.example.CryptozoologyZoo.model.Zoo;
import com.example.CryptozoologyZoo.repository.ZooRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ZooServiceTest {

    @Mock
    ZooRepository zooRepository;

    @InjectMocks
    ZooService zooService;



    @Test
    public void addAnimal() {
        Animal animal = new Animal("TIGER", AnimalType.WALKING);
        Zoo zoo = new Zoo();
        zoo.setAnimalList(Collections.singletonList(animal));

        when(zooRepository.save(zoo)).thenReturn(zoo);

        Zoo actualZoo = zooService.addAnimal(animal);
        verify(zooRepository).save(zoo);

        assertEquals(animal, actualZoo.getAnimalList().get(0));
    }
}