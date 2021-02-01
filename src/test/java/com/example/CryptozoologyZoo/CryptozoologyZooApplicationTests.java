package com.example.CryptozoologyZoo;

import com.example.CryptozoologyZoo.model.*;
import com.example.CryptozoologyZoo.repository.AnimalRepository;
import com.example.CryptozoologyZoo.repository.ZooRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CryptozoologyZooApplicationTests {

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper;

	@Autowired
	ZooRepository zooRepository;

	@Autowired
	AnimalRepository animalRepository;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void addAnimal() throws Exception {
		Animal animal = new Animal("TIGER", AnimalType.WALKING, AnimalMood.UNHAPPY);
		Zoo zoo = new Zoo();
		zoo.setAnimalList(Collections.singletonList(animal));

		String requestJson = objectMapper.writeValueAsString(animal);

		mockMvc.perform(post("/zoo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.animalList[0].name").value("TIGER"))
				.andExpect(jsonPath("$.animalList[0].type").value("WALKING"));

	}

	@Test
	public void getAnimals() throws Exception {
		Animal tiger = new Animal("TIGER", AnimalType.WALKING, AnimalMood.UNHAPPY);
		Animal lion = new Animal("LION", AnimalType.WALKING, AnimalMood.UNHAPPY);
		Animal bird = new Animal("BIRD", AnimalType.FLYING, AnimalMood.UNHAPPY);

		Zoo expectedZoo = new Zoo();
		expectedZoo.setAnimalList(Arrays.asList(tiger, lion, bird));

		zooRepository.save(expectedZoo);

		MvcResult mvcResult = mockMvc.perform(get("/zoo"))
				.andExpect(status().isOk()).andReturn();

		Zoo actualZoo = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Zoo.class);

		assertEquals(expectedZoo.getAnimalList().toString(), actualZoo.getAnimalList().toString());
	}

	@Test
	public void feedAnimalsHappyCase() throws Exception {
		Animal tiger = new Animal("TIGER", AnimalType.WALKING, AnimalMood.UNHAPPY);
		Zoo zoo = new Zoo();
		zoo.setAnimalList(Collections.singletonList(tiger));

		Animal animalAdded = zooRepository.save(zoo).getAnimalList().get(0);

		MvcResult mvcResult = mockMvc.perform(put("/zoo/" + animalAdded.getId() + "/feed"))
				.andExpect(status().isCreated()).andReturn();

		Animal actualAnimal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Animal.class);

		assertEquals(AnimalMood.HAPPY, actualAnimal.getAnimalMood());
	}

	@Test
	public void compatibleHabitat() throws Exception {
		Animal tiger = new Animal("TIGER", AnimalType.WALKING, AnimalMood.UNHAPPY);

		Animal tigerSaved = animalRepository.save(tiger);

		MvcResult mvcResult = mockMvc.perform(put("/zoo/" + tigerSaved.getId() + "/habitat"))
				.andExpect(status().isCreated()).andReturn();

		Animal actualAnimal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Animal.class);

		assertEquals(AnimalMood.HAPPY, actualAnimal.getAnimalMood());
		assertEquals(AnimalHabitat.FOREST, actualAnimal.getAnimalHabitat());

	}

}
