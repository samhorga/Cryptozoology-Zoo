package com.example.CryptozoologyZoo.controller;

import com.example.CryptozoologyZoo.model.Animal;
import com.example.CryptozoologyZoo.model.Habitat;
import com.example.CryptozoologyZoo.model.Zoo;
import com.example.CryptozoologyZoo.service.ZooService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zoo")
public class ZooController {

    private ZooService zooService;

    public ZooController(ZooService zooService) {
        this.zooService = zooService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Zoo addAnimal(@RequestBody Animal animal) {
        return zooService.addAnimal(animal);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Zoo getAnimals() {
        return zooService.getAnimals();
    }

    @PutMapping("/{id}/feed")
    @ResponseStatus(HttpStatus.CREATED)
    public Animal updateAnimal(@PathVariable Long id) {
        return zooService.feedAnimal(id);
    }

    @PutMapping("/{animalId}/habitat")
    @ResponseStatus(HttpStatus.CREATED)
    public Animal checkAnimalHabitat(@PathVariable Long animalId, @RequestBody Habitat habitat) {
        return zooService.checkAnimalHabitat(animalId, habitat);
    }
}
