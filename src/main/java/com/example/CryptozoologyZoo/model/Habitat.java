package com.example.CryptozoologyZoo.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean isOccupied;

    private HabitatEnum habitatEnum;

    @OneToOne(cascade = CascadeType.ALL)
    private Animal animal;

    public Habitat(HabitatEnum habitatEnum, boolean isOccupied, Animal animal) {
        this.habitatEnum = habitatEnum;
        this.isOccupied = isOccupied;
        this.animal = animal;
    }

    public Habitat() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public HabitatEnum getHabitatEnum() {
        return habitatEnum;
    }

    public void setHabitatEnum(HabitatEnum habitatEnum) {
        this.habitatEnum = habitatEnum;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }


}
