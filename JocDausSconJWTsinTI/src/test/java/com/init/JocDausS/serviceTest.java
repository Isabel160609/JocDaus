package com.init.JocDausS;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Map;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.init.JocDausS.Dao.JogadorDao;
import com.init.JocDausS.Dao.TiradesDao;
import com.init.JocDausS.Entity.Jogador;
import com.init.JocDausS.Entity.Tirada;
import com.init.JocDausS.Service.ServiceController;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes=serviceTest.class)
public class serviceTest {

	
	@Mock
	private JogadorDao jogadorDao;
	
	@InjectMocks
	ServiceController ServiceController;
	
	@Mock
	private TiradesDao tiradesDao;
	
	List<Jogador>elsJogadors;
	
	Jogador jogador1;
	Jogador jogador2;
	Jogador jogador3;
	
	Tirada tirada1;
	Tirada tirada2;
	Tirada tirada3;
	Tirada tirada4;
	Tirada tirada5;
	
//	
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
	
	
//		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
//		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
//		tirada1=new Tirada(jogador1);
//		tirada2=new Tirada(jogador2);
//		jogador1.addTirada(tirada1);
//		jogador2.addTirada(tirada2);
//		List<Jogador>elsJogadors=new ArrayList<Jogador>();
//		elsJogadors.add(jogador1);
//		elsJogadors.add(jogador2);
//	}
	@Test @Order(1)
	public void test_llistaJogadors() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		when(jogadorDao.findAll()).thenReturn(elsJogadors);
		assertEquals(2,ServiceController.llistaJogadors().size());
		assertEquals("Isa",ServiceController.llistaJogadors().get(0).getNom());
		
	}

	
	@Test @Order(2)
	public void test_creaJogador() {
		Jogador jogador=new Jogador(3,"Consuelo",new java.util.Date(7/1/2022));
		
		when(jogadorDao.save(jogador)).thenReturn(jogador);
		assertEquals(jogador, ServiceController.creaJogador(jogador));
	}
	
	@Test @Order(3)
	public void test_updateJogador() {
		Jogador jogador=new Jogador(3,"Consuelo",new java.util.Date(7/1/2022));
		
		when(jogadorDao.save(jogador)).thenReturn(jogador);
		assertEquals(jogador, ServiceController.updateJogador(jogador));
	}
	
	
	@Test @Order(4)
	public void test_BuscaJogadorfound() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		int jogadorId=1;
		
		when(jogadorDao.findById(1)).thenReturn(Optional.of(jogador1));
	
			assertEquals(jogadorId,ServiceController.BuscaJogador(jogadorId).get().getId());

	}
	
	@Test @Order(5)
	public void test_BuscaJogadornotFound() {
		

		int jogadorId=5;
		
		when(jogadorDao.findById(5)).thenReturn(Optional.empty());
	
			assertEquals(true,ServiceController.BuscaJogador(jogadorId).isEmpty());

	}
	
	@Test @Order(6)
	public void test_deleteTiradesJogador() {
		
		jogador3=new Jogador(3,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(jogador3);
		tirada2=new Tirada(jogador3);
		jogador3.addTirada(tirada1);
		jogador3.addTirada(tirada2);
		Jogador jogadorSenseTirades=new Jogador(3,"Isa",new java.util.Date(7/1/2022));
		int id=3;
		when(jogadorDao.findById(id)).thenReturn(Optional.of(jogador3));//Mocking
		
		tiradesDao.deleteAllByJogador(jogador3);
        verify(tiradesDao,times(1)).deleteAllByJogador(jogador3);
		
		when(tiradesDao.deleteAllByJogador(jogador3)).thenReturn(jogadorSenseTirades.getId());
		when(jogadorDao.findById(3)).thenReturn(Optional.of(jogadorSenseTirades)); 

		assertEquals(null,ServiceController.deleteTiradesJogador(jogador3).getTirades());
		assertEquals(3,ServiceController.deleteTiradesJogador(jogador3).getId());

	}
	
	@Test @Order(7)
	public void test_llistatnomIPercentatges() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		jogador3=new Jogador(3,"Ana",new java.util.Date(7/1/2022));
		tirada1=new Tirada(jogador1);
		tirada2=new Tirada(jogador2);
		tirada3=new Tirada(jogador3);
		jogador1.addTirada(tirada1);
		jogador2.addTirada(tirada2);
		jogador3.addTirada(tirada3);
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		elsJogadors.add(jogador3);
		
		Map<String, Double>llistacreada=ServiceController.llistatnomIPercentatges(elsJogadors);
		assertEquals(elsJogadors.get(0).calcularPorcentaje(),llistacreada.get("Isa"));
		assertEquals(elsJogadors.get(1).calcularPorcentaje(),llistacreada.get("Josep"));
		assertEquals(elsJogadors.get(2).calcularPorcentaje(),llistacreada.get("Ana"));
		assertEquals(3,llistacreada.size());
	}

	@Test @Order(8)
	public void test_llistaTiradesJogador() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador1);
		jogador1.addTirada(tirada1);
		jogador1.addTirada(tirada2);
		List<Tirada> tirades=ServiceController.llistaTiradesJogador(jogador1);
		assertEquals(jogador1.getTirades(),tirades);
	}
	
	
	@Test @Order(9)
	public void test_DonaRankingMig(){
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		jogador3=new Jogador(3,"Ana",new java.util.Date(7/1/2022));
		tirada1=new Tirada(jogador1);
		tirada2=new Tirada(jogador2);
		tirada3=new Tirada(jogador3);
		jogador1.addTirada(tirada1);
		jogador2.addTirada(tirada2);
		jogador2.addTirada(tirada3);
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		elsJogadors.add(jogador3);
		
		double porcentatgeReal=(jogador1.calcularPorcentaje()+jogador2.calcularPorcentaje())/3;
		double porcentatge= ServiceController.DonaRankingMig(elsJogadors);
		assertEquals(porcentatgeReal,porcentatge);
	}
	
	@Test @Order(10)
	public void test_llistatJogadorsAmbTirades(){
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		jogador3=new Jogador(3,"Ana",new java.util.Date(7/1/2022));
		tirada1=new Tirada(jogador1);
		tirada2=new Tirada(jogador2);
		tirada3=new Tirada(jogador3);
		jogador1.addTirada(tirada1);
		jogador2.addTirada(tirada2);
		jogador2.addTirada(tirada3);
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		elsJogadors.add(jogador3);
		
		List<Jogador>jogadorsAmbTirades=ServiceController.llistatJogadorsAmbTirades(elsJogadors);

		assertEquals(2,jogadorsAmbTirades.size());
	}
	
	@Test @Order(11)
	public void test_DonaPitjorRanking() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		jogador3=new Jogador(3,"Ana",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador2);
		tirada3=new Tirada(3, 2, 5, true, jogador2);
		jogador1.addTirada(tirada1);
		jogador2.addTirada(tirada2);
		jogador2.addTirada(tirada3);
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		elsJogadors.add(jogador3);
		
		Jogador jogadorPitjor=ServiceController.DonaPitjorRanking(elsJogadors);
		assertEquals(elsJogadors.get(1),jogadorPitjor);
	}
	@Test @Order(12)
	public void test_DonaMillorRanking() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		jogador3=new Jogador(3,"Ana",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador2);
		tirada3=new Tirada(3, 2, 5, true, jogador2);
		jogador1.addTirada(tirada1);
		jogador2.addTirada(tirada2);
		jogador2.addTirada(tirada3);
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		elsJogadors.add(jogador3);
		
		Jogador jogadorPitjor=ServiceController.DonaMillorRanking(elsJogadors);
		assertEquals(elsJogadors.get(0),jogadorPitjor);
	}
}
