package com.example.CryptozoologyZoo;

import com.example.CryptozoologyZoo.model.*;
import com.example.CryptozoologyZoo.repository.AnimalRepository;
import com.example.CryptozoologyZoo.repository.HabitatRepository;
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

import static org.junit.jupiter.api.Assertions.*;
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

	@Autowired
	HabitatRepository habitatRepository;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
		zooRepository.deleteAll();
		animalRepository.deleteAll();
		habitatRepository.deleteAll();
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
	public void feedAnimals() throws Exception {
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
		Habitat habitat = new Habitat(HabitatEnum.FOREST, false, null);
		Animal tigerSaved = animalRepository.save(tiger);
		Habitat habitatSaved = habitatRepository.save(habitat);

		String requestJson = objectMapper.writeValueAsString(habitatSaved);

		MvcResult mvcResult = mockMvc.perform(put("/zoo/" + tigerSaved.getId() + "/habitat").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isCreated()).andReturn();

		Animal actualAnimal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Animal.class);

		assertEquals(HabitatEnum.FOREST, actualAnimal.getHabitat().getHabitatEnum());
	}

	@Test
	public void inCompatibleHabitat() throws Exception {
		Animal tiger = new Animal("TIGER", AnimalType.WALKING, AnimalMood.HAPPY);
		Habitat habitat = new Habitat(HabitatEnum.NEST, false, null);
		Animal tigerSaved = animalRepository.save(tiger);
		Habitat habitatSaved = habitatRepository.save(habitat);

		String requestJson = objectMapper.writeValueAsString(habitatSaved);

		MvcResult mvcResult = mockMvc.perform(put("/zoo/" + tigerSaved.getId() + "/habitat").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isCreated()).andReturn();

		Animal actualAnimal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Animal.class);

		assertNotEquals(HabitatEnum.FOREST, actualAnimal.getHabitat().getHabitatEnum());
		assertEquals(AnimalMood.UNHAPPY, actualAnimal.getAnimalMood());
	}

	@Test
	public void occupiedHabitat() throws Exception {
		Animal tiger = new Animal("TIGER", AnimalType.WALKING, AnimalMood.HAPPY);
		Habitat habitat = new Habitat(HabitatEnum.FOREST, true, null);
		Animal tigerSaved = animalRepository.save(tiger);
		Habitat habitatSaved = habitatRepository.save(habitat);

		String requestJson = objectMapper.writeValueAsString(habitatSaved);

		MvcResult mvcResult = mockMvc.perform(put("/zoo/" + tigerSaved.getId() + "/habitat").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isCreated()).andReturn();

		Animal actualAnimal = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Animal.class);

		assertEquals(HabitatEnum.FOREST, actualAnimal.getHabitat().getHabitatEnum());
		assertEquals(AnimalMood.HAPPY, actualAnimal.getAnimalMood());
	}

}
