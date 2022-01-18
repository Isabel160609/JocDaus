package com.init.JocDausS;

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

import com.init.JocDausS.Controller.ControllerJogador;
import com.init.JocDausS.Entity.Jogador;
import com.init.JocDausS.Entity.Tirada;
import com.init.JocDausS.Service.ServiceController;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {ControllerTest.class})
public class ControllerTest {
	
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

	
	@Test
	@Order(1)
	public void test_getJogadors() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		ResponseEntity<List<Jogador>> res=controllerJogador.getJogadors();
		assertEquals(HttpStatus.FOUND,res.getStatusCode());
		assertEquals(2,res.getBody().size());
	}
	
	@Test
	@Order(1)
	public void test_getJogadorsNotFound() {

		when(ServiceController.llistaJogadors()).thenThrow();
		ResponseEntity<List<Jogador>> res=controllerJogador.getJogadors();
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());

	}
	
	@Test
	@Order(2)
	public void test_createJogador(){
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		
		when(ServiceController.creaJogador(jogador1)).thenReturn(jogador1);
		ResponseEntity<Jogador> res=controllerJogador.createJogador(jogador1);
		assertEquals(HttpStatus.CREATED,res.getStatusCode());
		assertEquals(jogador1,res.getBody());
	}
	
	@Test
	@Order(2)
	public void test_createJogadorConflict(){
	
		when(ServiceController.creaJogador(null)).thenThrow();
		ResponseEntity<Jogador> res=controllerJogador.createJogador(null);
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());
		
	}
	@Test
	@Order(3)
	public void test_ModificaNomJogador() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogadorModificado=new Jogador(1,"Ana",new java.util.Date(7/1/2022));
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.updateJogador(jogador1)).thenReturn(jogadorModificado);
		ResponseEntity<Jogador> res=controllerJogador.ModificaNomJogador(jogadorId, "Ana");
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(1,res.getBody().getId());
		assertEquals("Ana",res.getBody().getNom());
	}
	@Test
	@Order(3)
	public void test_ModificaNomJogadorNotFound() {
		int  jogadorId=4;
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.empty());
		ResponseEntity<Jogador> res=controllerJogador.ModificaNomJogador(jogadorId, "Ana");
		
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());
		
	}
	@Test
	@Order(3)
	public void test_ModificaNomJogadorComflict() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.updateJogador(jogador1)).thenThrow();
		ResponseEntity<Jogador> res=controllerJogador.ModificaNomJogador(jogadorId, "Ana");
		
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());
		
	}
	@Test
	@Order(4)
	public void test_tirarDaus() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		int  jogadorId=1;
		jogadorModificado=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		jogadorModificado.addTirada(tirada1);
		
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.updateJogador(jogador1)).thenReturn(jogadorModificado);
		
		ResponseEntity<Jogador> res=controllerJogador.tirarDaus(jogadorId);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(1,res.getBody().getTirades().size());
		
	}
	
	@Test
	@Order(4)
	public void test_tirarDausNotFound() {
		int  jogadorId=4;

		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.empty());
		
		ResponseEntity<Jogador> res=controllerJogador.tirarDaus(jogadorId);
		
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());

	}
	
	@Test
	@Order(4)
	public void test_tirarDausComflict() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		int  jogadorId=1;

		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.updateJogador(jogador1)).thenThrow();
		
		ResponseEntity<Jogador> res=controllerJogador.tirarDaus(jogadorId);
		
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());	
	}
	
	@Test
	@Order(5)
	public void test_borrarTiradas() {
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		jogador1.addTirada(tirada1);
		jogadorModificado=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.deleteTiradesJogador(jogador1)).thenReturn(jogadorModificado);
		ResponseEntity<Jogador> res=controllerJogador.borrarTiradas(jogadorId);
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		
	}
	@Test
	@Order(5)
	public void test_borrarTiradasNotFound() {
		
		int  jogadorId=4;

		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.empty());
		
		ResponseEntity<Jogador> res=controllerJogador.borrarTiradas(jogadorId);
		
		assertEquals(HttpStatus.NOT_FOUND,res.getStatusCode());

	}
	@Test
	@Order(5)
	public void test_borrarTiradasConflict() {
		
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		int  jogadorId=1;

		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.deleteTiradesJogador(jogador1)).thenThrow();
		ResponseEntity<Jogador> res=controllerJogador.borrarTiradas(jogadorId);
		
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());

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
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		when(ServiceController.llistatnomIPercentatges(elsJogadors)).thenReturn(ranking);
		ResponseEntity<Map<String, Double>> res=controllerJogador.llistatPercentatges();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(100,res.getBody().get("Isa"));
	}
	
	@Test
	@Order(6)
	public void test_llistatPercentatgesComflict(){
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
		when(ServiceController.llistatnomIPercentatges(elsJogadors)).thenThrow();
		ResponseEntity<Map<String, Double>> res=controllerJogador.llistatPercentatges();
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());
		
	}
	@Test
	@Order(7)
	public void test_llistarTirades() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador1);
		jogador1.addTirada(tirada1);
		jogador1.addTirada(tirada2);
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.llistaTiradesJogador(jogador1)).thenReturn(jogador1.getTirades());
		ResponseEntity <List<Tirada>> res=controllerJogador.llistarTirades(jogadorId);
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(tirada1,res.getBody().get(0));
		assertEquals(2,res.getBody().size());
		
	}
	
	@Test
	@Order(7)
	public void test_llistarTiradesNoContent() {
		
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.empty());
		
		ResponseEntity <List<Tirada>> res=controllerJogador.llistarTirades(jogadorId);
		assertEquals(HttpStatus.NO_CONTENT,res.getStatusCode());
		
	}
	
	@Test
	@Order(7)
	public void test_llistarTiradesConflict() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador1);
		jogador1.addTirada(tirada1);
		jogador1.addTirada(tirada2);
		int  jogadorId=1;
		
		when(ServiceController.BuscaJogador(jogadorId)).thenReturn(Optional.of(jogador1));
		when(ServiceController.llistaTiradesJogador(jogador1)).thenThrow();
		ResponseEntity <List<Tirada>> res=controllerJogador.llistarTirades(jogadorId);
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());
		
		
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
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		when(ServiceController.DonaRankingMig(elsJogadors)).thenReturn(rankingMig);
		
		ResponseEntity<Double> res=controllerJogador.rankingMig();
		
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(75,res.getBody());
		
	}
	
	@Test
	@Order(8)
	public void test_rankingMigConflict() {
		
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
		when(ServiceController.DonaRankingMig(elsJogadors)).thenThrow();
		
		ResponseEntity<Double> res=controllerJogador.rankingMig();
		
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());
	
		
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
		
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		when(ServiceController.DonaPitjorRanking(elsJogadors)).thenReturn(jogador1);
		
		ResponseEntity<Jogador> res=controllerJogador.rankingloser();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(jogador1,res.getBody());
	}
	
	@Test
	@Order(9)
	public void test_rankingloserConflict() {
		
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
		when(ServiceController.DonaPitjorRanking(elsJogadors)).thenThrow();
		
		ResponseEntity<Jogador> res=controllerJogador.rankingloser();
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());
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
		
		when(ServiceController.llistaJogadors()).thenReturn(elsJogadors);
		when(ServiceController.DonaMillorRanking(elsJogadors)).thenReturn(jogador2);
		
		ResponseEntity<Jogador> res=controllerJogador.rankingwinner();
		assertEquals(HttpStatus.OK,res.getStatusCode());
		assertEquals(jogador2,res.getBody());
	}
	
	@Test
	@Order(10)
	public void test_rankingwinnerConflict() {
		
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
		when(ServiceController.DonaMillorRanking(elsJogadors)).thenThrow();
		
		ResponseEntity<Jogador> res=controllerJogador.rankingwinner();
		assertEquals(HttpStatus.CONFLICT,res.getStatusCode());

	}
}
