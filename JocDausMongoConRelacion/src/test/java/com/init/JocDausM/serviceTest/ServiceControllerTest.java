package com.init.JocDausM.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;
import com.init.JocDausM.Service.SequenceGeneratorService;
import com.init.JocDausM.Service.ServiceController;
import com.init.JocDausM.dao.JogadorDao;
import com.init.JocDausM.dao.TiradesDao;





@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes=ServiceControllerTest.class)
public class ServiceControllerTest {

	@Mock
	private JogadorDao jogadorDao;
	
	
	@Mock
	private TiradesDao tiradesDao;
	
	@Mock
	private SequenceGeneratorService sequenceGeneratorService;
	

	@InjectMocks
	ServiceController serviceController;
	
	List<Jogador>elsJogadors;
	
	Jogador jogador1;
	Jogador jogador2;
	Jogador jogador3;
	
	Tirada tirada1;
	Tirada tirada2;
	Tirada tirada3;
	Tirada tirada4;
	Tirada tirada5;
	
	@Test @Order(1)
	public void test_llistaJogadors() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		jogador2=new Jogador(2,"Josep",new java.util.Date(7/1/2022));
		
		List<Jogador>elsJogadors=new ArrayList<Jogador>();
		elsJogadors.add(jogador1);
		elsJogadors.add(jogador2);
		
		when(jogadorDao.findAll()).thenReturn(elsJogadors);
		assertEquals(2,serviceController.llistaJogadors().size());
		assertEquals("Isa",serviceController.llistaJogadors().get(0).getNom());
		
	}
	
	@Test @Order(2)
	public void test_creaJogador() {
		Jogador jogador=new Jogador(3,"Consuelo",new java.util.Date(7/1/2022));
		
		when(jogadorDao.save(jogador)).thenReturn(jogador);
		assertEquals(jogador, serviceController.nouJogador(jogador));
	}
	
	@Test @Order(3)
	public void test_updateJogadorFound() {
		Jogador jogador=new Jogador(3,"Consuelo",new java.util.Date(7/1/2022));
		String nom="Ana";
		long id=3;
		Jogador jogadorUpdate=new Jogador(3,"Ana",new java.util.Date(7/1/2022));
		when(jogadorDao.findById(id)).thenReturn(Optional.of(jogador));
		when(jogadorDao.save(jogador)).thenReturn(jogadorUpdate);
		assertEquals(("nombre cambiado correctamente "+ jogadorUpdate.toString()), serviceController.canviaNomJogador(id,nom));
	}
	@Test @Order(3)
	public void test_updateJogadorNotFound() {
		String nom="ana";
		long id=1;
		
		when(jogadorDao.findById(id)).thenReturn(Optional.empty());
		
		assertEquals(("el jugador con id " + id + " no existe"), serviceController.canviaNomJogador(id,nom));
	}
	
	@Test @Order(4)
	public void test_jogadorTiraDausFound() {
		Jogador jogador=new Jogador(3,"Consuelo",new java.util.Date(7/1/2022));
		long id=3;
		tirada1=new Tirada(1, 2, 5, true, jogador);
		jogador.addTirada(tirada1);
		
		when(jogadorDao.findById(id)).thenReturn(Optional.of(jogador));
		when(jogadorDao.save(jogador)).thenReturn(jogador);
		when(tiradesDao.save(tirada1)).thenReturn(tirada1);
		assertEquals(("la tirada ha sido añadida" ),serviceController.jogadorTiraDaus(id));
		
	}
	@Test @Order(4)
	public void test_jogadorTiraDausNotFound() {
		long id=3;
		when(jogadorDao.findById(id)).thenReturn(Optional.empty());
	
		assertEquals(("el jugador con id " + id + " no existe"),serviceController.jogadorTiraDaus(id));
		
	}
	
	@Test @Order(5)
	public void test_borraTitadesFound() {
		Jogador jogador=new Jogador(3,"Consuelo",new java.util.Date(7/1/2022));
		long id=3;
		tirada1=new Tirada(1, 2, 5, true, jogador);
		jogador.addTirada(tirada1);
		
		when(jogadorDao.findById(id)).thenReturn(Optional.of(jogador));
		assertEquals(("totes les tirades del jogador amb id " + id + " han sigut eliminades"),serviceController.borraTitades(id));
	}
	
	@Test @Order(5)
	public void test_borraTitadesNotFound() {
		long id=3;
		when(jogadorDao.findById(id)).thenReturn(Optional.empty());
		
		assertEquals(("el jogador amb id " + id + " no existeix"),serviceController.borraTitades(id));
	}
	
	@Test @Order(6)
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
		
		Map<String, Double>llistacreada=serviceController.llistatnomIPercentatges(elsJogadors);
		assertEquals(elsJogadors.get(0).calcularPorcentaje(),llistacreada.get("Isa"));
		assertEquals(elsJogadors.get(1).calcularPorcentaje(),llistacreada.get("Josep"));
		assertEquals(elsJogadors.get(2).calcularPorcentaje(),llistacreada.get("Ana"));
		assertEquals(3,llistacreada.size());
	}
	
	@Test @Order(7)
	public void test_llistaTiradesJogadorFound() {
		jogador1=new Jogador(1,"Isa",new java.util.Date(7/1/2022));
		tirada1=new Tirada(1, 2, 5, true, jogador1);
		tirada2=new Tirada(2, 2, 4, false, jogador1);
		jogador1.addTirada(tirada1);
		jogador1.addTirada(tirada2);
		long id=1;
		when(jogadorDao.findById(id)).thenReturn(Optional.of(jogador1));
		List<Tirada> tirades=serviceController.donaTiradesJogador(id);
		assertEquals(jogador1.getTirades(),tirades);
	}
	
	@Test @Order(7)
	public void test_llistaTiradesJogadorNotFound() {
		List<Tirada>listaEsperada=null;
		long id=1;
		when(jogadorDao.findById(id)).thenReturn(Optional.empty());
		List<Tirada> tirades=serviceController.donaTiradesJogador(id);
		assertEquals(listaEsperada,tirades);
	}
	
	@Test @Order(8)
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
		double porcentatge= serviceController.DonaRankingMig(elsJogadors);
		assertEquals(porcentatgeReal,porcentatge);
	}
	
	

	@Test @Order(9)
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
		
		List<Jogador>jogadorsAmbTirades=serviceController.llistatJogadorsAmbTirades(elsJogadors);

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
		
		Jogador jogadorPitjor=serviceController.DonaPitjorRanking(elsJogadors);
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
		
		Jogador jogadorPitjor=serviceController.DonaMillorRanking(elsJogadors);
		assertEquals(elsJogadors.get(0),jogadorPitjor);
	}
}
