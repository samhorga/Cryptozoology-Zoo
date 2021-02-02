package com.example.CryptozoologyZoo.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Animal {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String name;
    private AnimalType type;
    private AnimalMood animalMood;

    @OneToOne(cascade = CascadeType.ALL)
    private Habitat habitat;

    public Animal() {
    }

    public Animal(String name, AnimalType type, AnimalMood animalMood) {
        this.name = name;
        this.type = type;
        this.animalMood = animalMood;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(name, animal.name) && type == animal.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    public AnimalMood getAnimalMood() {
        return animalMood;
    }

    public void setAnimalMood(AnimalMood animalMood) {
        this.animalMood = animalMood;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }
}
