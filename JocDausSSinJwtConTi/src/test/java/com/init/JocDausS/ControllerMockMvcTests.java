package com.init.JocDausS;

import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.init.JocDausS.Controller.ControllerJogador;
import com.init.JocDausS.Entity.Jogador;
import com.init.JocDausS.Entity.Tirada;
import com.init.JocDausS.Service.ServiceController;


@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages="com.init.JocDausS")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes= {ControllerMockMvcTests.class})
public class ControllerMockMvcTests {

	@Autowired
	MockMvc	mockMvc	;
	
	@Mock
	ServiceController ServiceController;
	
	@InjectMocks
	ControllerJogador controllerJogador;
	
	List<Jogador>elsJogadors;
	
	Jogador jogador1;
	Jogador jogador2;
	Jogador jogador3;
	Jogador jogadorModificado;
	
	Tirada tirada1;
	Tirada tirada2;
	Tirada tirada3;
	
	@BeforeEach
	public void setUp(){
		
		mockMvc=MockMvcBuilders.standaloneSetup(controllerJogador).build();
	}
	

	@Test
	@Order(1)
	public void test_getJogadors() throws Exception {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		
		this.mockMvc.perform(get("/players"))
			.andExpect(status().isFound())
			.andDo(print());
	}
	

	@Test
	@Order(2)
	public void test_createJogador() throws Exception{
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		
		when(ServiceController.creaJogador(jogador1)).thenReturn(jogador1);
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogador1);
		
		this.mockMvc.perform(post("/players")
							.content(jsonBody)
							.contentType(MediaType.APPLICATION_JSON)
							)
					.andExpect(status().isCreated())
					.andDo(print());
		
	}
	
	@Test
	@Order(3)
	public void test_ModificaNomJogador() throws Exception {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogadorModificado=new Jogador(1,"Ana",new java.util.Date(7/1/2022));
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.updateJogador(jogador1)).thenReturn(jogadorModificado);
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogadorModificado);
		
		this.mockMvc.perform(put("/players/{id}",jogadorId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
		.andExpect(jsonPath("$.nom", containsString("Ana")))
		.andDo(print());
	}
	

	@Test
	@Order(4)
	public void test_tirarDaus() throws Exception {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		int  jogadorId=1;
		jogadorModificado=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		jogadorModificado.addTirada(tirada1);
		
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.updateJogador(jogador1)).thenReturn(jogadorModificado);
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogadorModificado);
		
		this.mockMvc.perform(post("/players/{id}/games/",jogadorId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", org.hamcrest.Matchers.is(1)))
		.andExpect(MockMvcResultMatchers.jsonPath(".nom").value("Isa"))
		.andExpect(MockMvcResultMatchers.jsonPath(".tirades").isArray())
		.andDo(print());
		
	}
	
	@Test
	@Order(5)
	public void test_borrarTiradas() throws Exception{

		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		jogador1.addTirada(tirada1);
		jogadorModificado=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		List<Tirada>tirades=new ArrayList<Tirada>();
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.deleteTiradesJogador(jogador1)).thenReturn(jogadorModificado);
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogadorModificado);
		
		this.mockMvc.perform(delete("/players/{id_jogador}/games",jogadorId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath(".nom").value("Isa"))
		.andExpect(MockMvcResultMatchers.jsonPath(".tirades").isArray())
		.andDo(print());
	}
	
	@Test
	@Order(6)
	public void test_llistatPercentatges() throws Exception{
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador2);
		tirada3=new Tirada(3, 2, 5, true, jogador2);
		jogador1.addTirada(tirada1);
		jogador2.addTirada(tirada2);
		jogador2.addTirada(tirada2);
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		Map<String, Double> ranking=new HashMap<String, Double>();
		ranking.put("Isa", 100.0);
		ranking.put("Josep", 50.0);
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		when(ServiceController.llistatnomIPercentatges(elsJogadors)).thenReturn(ranking);
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogadorModificado);
		
		this.mockMvc.perform(get( "/players/porcentatge")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".Isa").value(100.0))
		.andExpect(MockMvcResultMatchers.jsonPath(".Josep").value(50.0))
		.andDo(print());
	
	}
	
	
	@Test
	@Order(7)
	public void test_llistarTirades() throws Exception {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador1);
		jogador1.addTirada(tirada1);
		jogador1.addTirada(tirada2);
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.llistaTiradesJogador(jogador1)).thenReturn(jogador1.getTirades());
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogadorModificado);
		
		this.mockMvc.perform(get("/players/{id}/games",jogadorId )
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").isArray())
		.andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
		.andExpect(jsonPath("$[*].id", org.hamcrest.Matchers.containsInAnyOrder(1,2)))
		.andDo(print());
	
	}
	
	@Test
	@Order(8)
	public void test_rankingMig() throws Exception {
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador1);
		tirada3=new Tirada(3, 2, 5, true, jogador2);
		jogador1.addTirada(tirada1);
		jogador1.addTirada(tirada2);
		jogador2.addTirada(tirada3);
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		Double rankingMig=75.0;
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		when(ServiceController.DonaRankingMig(elsJogadors)).thenReturn(rankingMig);
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogadorModificado);
		
		this.mockMvc.perform(get("/players/ranking" )
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", org.hamcrest.Matchers.is(75.0)))
		.andDo(print());
	}
	
	@Test
	@Order(9)
	public void test_rankingloser() throws Exception {
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador1);
		tirada3=new Tirada(3, 2, 5, true, jogador2);
		jogador1.addTirada(tirada1);
		jogador1.addTirada(tirada2);
		jogador2.addTirada(tirada3);
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		when(ServiceController.DonaPitjorRanking(elsJogadors)).thenReturn(jogador1);
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogadorModificado);
		
		this.mockMvc.perform(get("/players/ranking/loser" )
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".nom").value("Isa"))
		.andDo(print());
	}
	
	@Test
	@Order(9)
	public void test_rankingwinner() throws Exception {
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador1);
		tirada3=new Tirada(3, 2, 5, true, jogador2);
		jogador1.addTirada(tirada1);
		jogador1.addTirada(tirada2);
		jogador2.addTirada(tirada3);
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		when(ServiceController.DonaMillorRanking(elsJogadors)).thenReturn(jogador2);
		
		ObjectMapper mapper=new ObjectMapper() ;
		String jsonBody=mapper.writeValueAsString(jogadorModificado);
		
		this.mockMvc.perform(get("/players/ranking/winner" )
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".nom").value("Josep"))
		.andDo(print());
	}
}
