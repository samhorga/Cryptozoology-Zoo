package com.example.CryptozoologyZoo;

import com.example.CryptozoologyZoo.model.Animal;
import com.example.CryptozoologyZoo.model.AnimalType;
import com.example.CryptozoologyZoo.model.Zoo;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CryptozoologyZooApplicationTests {

	@Autowired
	MockMvc mockMvc;

	ObjectMapper objectMapper;

//	@Autowired
//	AnimalRepository animalRepository;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void addAnimal() throws Exception {
		Animal animal = new Animal("TIGER", AnimalType.WALKING);
		Zoo zoo = new Zoo();
		zoo.setAnimalList(Collections.singletonList(animal));

		String requestJson = objectMapper.writeValueAsString(animal);

		mockMvc.perform(post("/zoo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.animalList[0].name").value("TIGER"));
//				.andExpect(jsonPath("$.type").value("WALKING"));

	}
}
