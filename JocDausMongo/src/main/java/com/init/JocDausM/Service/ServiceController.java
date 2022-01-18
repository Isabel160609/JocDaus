package com.init.JocDausM.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;
import com.init.JocDausM.dao.JogadorDao;
import com.init.JocDausM.dao.TiradesDao;



public class ServiceController {
	
	@Autowired
	private JogadorDao jogadorDao;

	@Autowired
	private TiradesDao tiradesDao;
	
	public Map<String, Double> llistatnomIPercentatges(List<Jogador> jogadors,List<Tirada> tirades ){
		
 Map<String, Double> mapjogadorPercentatge = new HashMap<String, Double>();
		List<Tirada> tiradesJogador;
		 if (jogadors!=null && jogadors.size()>0) {
			 String key="";
			 Double value=null;
			 for(int i=0;i<jogadors.size();i++) { 
				 String Key=jogadors.get(i).getNom();
				 tiradesJogador=buscartiradesJogador(tirades,jogadors.get(i).getId());
				 if(!tiradesJogador.isEmpty()) {
					 
					 value=calcularPorcentaje(tiradesJogador);
				 }else {
					 value=null;
				 }
				 mapjogadorPercentatge.put(Key, value);
		 }
		
		}
		return mapjogadorPercentatge;
	}
	
	public List<Tirada> buscartiradesJogador(List<Tirada> tirades, String jogador_id){
		List<Tirada> tiradesJogador=new ArrayList<Tirada>();
		for(Tirada tirada:tirades) {
			if (tirada.getId_jogador().equalsIgnoreCase(jogador_id)){
				tiradesJogador.add(tirada);
			}
		}
			return tiradesJogador;
		
	}
	
	public double calcularPorcentaje(List<Tirada>llistaTirades) {
		int contador=0;
		for(int i=0;i<llistaTirades.size();i++) {
			if(llistaTirades.get(i).isGuanyar()) {
				contador+=1;
			}
		}
		double porcentaje=pasaAPorcentaje(llistaTirades.size(),contador);
		return porcentaje;
	}
	
	public double pasaAPorcentaje(int total, int totalTrue) {
		double porcentaje=(totalTrue*100)/total;
		return porcentaje;
	}
	
	 public double DonaRankingMig(List<Tirada> tirades) {
		double rankingMig=0.0;
		 if (tirades!=null && tirades.size()>0) {
			 rankingMig=calcularPorcentaje(tirades);
		 }
		return rankingMig;
	}
	 
//	 public List<Jogador> llistatJogadorsAmbTirades(List<Jogador> jogadors){
//		 List<Jogador> llistatJogadorsAmbTirades=new ArrayList<Jogador>();
//		 
//		 for(Jogador jogador:jogadors) {
//			 if (jogador.getTirades()!=null && jogador.getTirades().size()>0) {
//				 llistatJogadorsAmbTirades.add(jogador);
//			 }
//			 
//		 }
//		 return llistatJogadorsAmbTirades;
//	 }
	 public Jogador DonaPitjorRanking(List<Jogador> jogadors) {
		
		 jogadors.sort(Comparator.comparing(Jogador::getPorcentatgeExit));

			return jogadors.get(0);
	 }
	 
	 public Jogador DonaMillorRanking(List<Jogador> jogadors) {
		 jogadors.sort(Comparator.comparing(Jogador::getPorcentatgeExit).reversed());

			return jogadors.get(0);
	 }
	 
	 
  }
