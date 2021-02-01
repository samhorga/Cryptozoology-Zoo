package com.example.CryptozoologyZoo.controller;

import com.example.CryptozoologyZoo.model.Animal;
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
}
