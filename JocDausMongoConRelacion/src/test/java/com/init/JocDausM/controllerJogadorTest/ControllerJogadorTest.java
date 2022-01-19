package com.init.JocDausM.controllerJogadorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.init.JocDausM.Controller.ControllerJogador;
import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;
import com.init.JocDausM.Service.ServiceController;


@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {ControllerJogadorTest.class})
public class ControllerJogadorTest {

	
	@Mock
	ServiceController serviceController;
	
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

	
	@Test
	@Order(1)
	public void test_getJogadors() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		when(serviceController.llistaJogadors()).thenReturn(elsJogadors);
		ResponseEntity<List<Jogador>> res=controllerJogador.getJogadors();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(2,res.getBody().size());
	}
	
	@Test
	@Order(2)
	public void test_createJogador(){
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		
		when(serviceController.nouJogador(jogador1)).thenReturn(jogador1);
		ResponseEntity<String> res=controllerJogador.createJogador(jogador1);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(jogador1.toString(),res.getBody());
	}
	
	@Test
	@Order(3)
	public void test_ModificaNomJogador() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogadorModificado=new Jogador(1,"Ana",new java.util.Date(7/1/2022));
		int  jogadorId=1;
		String nom="Ana";
		when(serviceController.canviaNomJogador(jogadorId, nom)).thenReturn("nombre cambiado correctamente "+ jogadorModificado.toString());
		ResponseEntity<String> res=controllerJogador.ModificaNomJogador(jogadorId, "Ana");
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(("nombre cambiado correctamente "+ jogadorModificado.toString()),res.getBody());
	}
	
	@Test
	@Order(4)
	public void test_tirarDaus() {
		int  jogadorId=1;
		when(serviceController.jogadorTiraDaus(jogadorId)).thenReturn("la tirada ha sido añadida" );
		ResponseEntity<String> res=controllerJogador.tirarDaus(jogadorId);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals("la tirada ha sido añadida",res.getBody());
		
	}
	@Test
	@Order(5)
	public void test_borrarTiradas() {
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		jogador1.addTirada(tirada1);
		jogadorModificado=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		int  jogadorId=1;
		
		
		when(serviceController.borraTitades(jogadorId)).thenReturn("totes les tirades del jogador amb id " + jogadorId + " han sigut eliminades");
		ResponseEntity<String> res=controllerJogador.borrarTiradas(jogadorId);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(("totes les tirades del jogador amb id " + jogadorId + " han sigut eliminades"),res.getBody());
	}
	@Test
	@Order(6)
	public void test_llistatPercentatges(){
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
		when(serviceController.llistaJogadors()).thenReturn(elsJogadors);
		when(serviceController.llistatnomIPercentatges(elsJogadors)).thenReturn(ranking);
		
		ResponseEntity<Map<String, Double>> res=controllerJogador.llistatPercentatges();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(ranking,res.getBody());
	}
	@Test
	@Order(8)
	public void test_rankingMig() {
		
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
		when(serviceController.llistaJogadors()).thenReturn(elsJogadors);
		when(serviceController.DonaRankingMig(elsJogadors)).thenReturn(rankingMig);
		
		ResponseEntity<Double> res=controllerJogador.rankingMig();
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(75,res.getBody());
		
	}
	@Test
	@Order(9)
	public void test_rankingloser() {
		
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
		
		when(serviceController.llistaJogadors()).thenReturn(elsJogadors);
		when(serviceController.DonaPitjorRanking(elsJogadors)).thenReturn(jogador1);
		
		ResponseEntity<Jogador> res=controllerJogador.rankingloser();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(jogador1,res.getBody());
	}
	
	@Test
	@Order(10)
	public void test_rankingwinner() {
		
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
		
		when(serviceController.llistaJogadors()).thenReturn(elsJogadors);
		when(serviceController.DonaMillorRanking(elsJogadors)).thenReturn(jogador2);
		
		ResponseEntity<Jogador> res=controllerJogador.rankingwinner();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(jogador2,res.getBody());
	}
	
}
