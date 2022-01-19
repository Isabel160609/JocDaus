package com.init.JocDausM.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;
import com.init.JocDausM.dao.JogadorDao;
import com.init.JocDausM.dao.TiradesDao;


@Service
public class ServiceController {
	
	@Autowired
	private JogadorDao jogadorDao;

	@Autowired
	private TiradesDao tiradesDao;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	
	//1 Llistar jogadors: retorna la llista de jogadors
	public List<Jogador> llistaJogadors(){
		List<Jogador> jogadors = jogadorDao.findAll();
		return jogadors;
	}
	
	// 2 crea un jugador
	public Jogador nouJogador(Jogador jogador) {
		
		List<Jogador> jogadors = jogadorDao.findAll();
		if (jogador.getNom().equalsIgnoreCase("")) {
			jogador.setNom("Anonimo");
		}

		for (Jogador nombre : jogadors) {
			if (nombre.getNom().equalsIgnoreCase(jogador.getNom())) {
				jogador.setNom(jogador.getNom() + "1");
			}
		}
		
		jogador.setId(sequenceGeneratorService.generateSequence(Jogador.SEQUENCE_NAME));
		jogadorDao.save(jogador);
		
		return jogador;
		
	}
	
	// 3 modifica el nom del jugador
	public String canviaNomJogador(long id,String nom) {
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);
		String respuesta;
		if (optionalJogador.isPresent()) {
			Jogador updateJogador = optionalJogador.get();
			updateJogador.setNom(nom);
			jogadorDao.save(updateJogador);
			respuesta="nombre cambiado correctamente "+ updateJogador.toString();

		}else {
			respuesta="el jugador con id " + id + " no existe";
		}

		return respuesta;

	}
	
	// 4 un jugador específic realitza una tirada dels daus.
		public String jogadorTiraDaus(long id) {
			Optional<Jogador> optionalJogador = jogadorDao.findById(id);
			String respuesta;
			if (optionalJogador.isPresent()) {
				Jogador updateJogador = optionalJogador.get();
				Tirada tirada = new Tirada( updateJogador);
				tirada.setId(sequenceGeneratorService.generateSequence(Tirada.SEQUENCE_NAME));
				updateJogador.addTirada(tirada);
				jogadorDao.save(updateJogador);
				tiradesDao.save(tirada);
				respuesta="la tirada ha sido añadida" + tirada.toString();
				
			}else {
				respuesta="el jugador con id " + id + " no existe";
			}
			return respuesta;
			
		}
		// 5 DELETE /players/{id}/games: elimina les tirades del jugador
		public String borraTitades( long id) {
			String resposta;
				Optional<Jogador> jogador = jogadorDao.findById(id);

				if (!jogador.isEmpty()) {
					tiradesDao.deleteAll(jogador.get().getTirades());

					resposta="totes les tirades del jogador amb id " + id + " han sigut eliminades";
				}else {
					resposta="el jogador amb id " + id + " no existeix";
				}
					
				return resposta;
				
		}
		// 6 retorna el llistat de tots els jugadors del sistema amb el
		// seu percentatge mig d’èxits
	public Map<String, Double> llistatnomIPercentatges(List<Jogador> jogadors ){
		
 Map<String, Double> mapjogadorPercentatge = new HashMap<String, Double>();
		 
		 if (jogadors!=null && jogadors.size()>0) {
			 String key="";
			 Double value=null;
			 for(int i=0;i<jogadors.size();i++) { 
				 String Key=jogadors.get(i).getNom();
				 if (jogadors.get(i).getTirades().size()>0 &&jogadors.get(i).getTirades()!=null) {
					 value=jogadors.get(i).calcularPorcentaje();
				 }else {
					 value=null;
				 }
				 mapjogadorPercentatge.put(Key, value);
		 }
		
		}
		return mapjogadorPercentatge;
	}
	
	// 6 GET  retorna el llistat de jugades per un jugador.
	public List<Tirada> donaTiradesJogador(long id){
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);
		List<Tirada> tirades;
		if (optionalJogador.isPresent()) {
			Jogador jogador = optionalJogador.get();
			 tirades = jogador.getTirades();
			

		} else {
			tirades=null;
		}
		return tirades;
	}
	

	// 7 retorna el ranking mig de tots els jugadors del sistema . És a dir, el percentatge mig d’èxits.
	 public double DonaRankingMig(List<Jogador> jogadors) {
		double rankingMig=0.0;
		//List<Jogador> jogadors = jogadorDao.findAll();
		double sumatoriPorcentatge=0.0;
		for(int i=0;i<jogadors.size();i++) {
			 if (jogadors.get(i).getTirades()!=null && jogadors.get(i).getTirades().size()>0) {
				 sumatoriPorcentatge+=jogadors.get(i).calcularPorcentaje();
			 }
		}
		rankingMig=sumatoriPorcentatge/jogadors.size();
		return rankingMig;
	}
	//llista els jogadors que tenen tirades
	 public List<Jogador> llistatJogadorsAmbTirades(List<Jogador> jogadors){
		 List<Jogador> llistatJogadorsAmbTirades=new ArrayList<Jogador>();
		 
		 for(Jogador jogador:jogadors) {
			 if (jogador.getTirades()!=null && jogador.getTirades().size()>0) {
				 llistatJogadorsAmbTirades.add(jogador);
			 }
			 
		 }
		 return llistatJogadorsAmbTirades;
	 }
	//8 retorna el jugador  amb pitjor percentatge d’èxit
	 public Jogador DonaPitjorRanking(List<Jogador> jogadors) {
		 List<Jogador> jogadorsAmbTirades= llistatJogadorsAmbTirades(jogadors);
		 jogadorsAmbTirades.sort(Comparator.comparing(Jogador::calcularPorcentaje));

			return jogadorsAmbTirades.get(0);
	 }
	 
	//9 retorna el jugador amb millor percentatge d’èxit
	 public Jogador DonaMillorRanking(List<Jogador> jogadors) {
		 List<Jogador> jogadorsAmbTirades= llistatJogadorsAmbTirades(jogadors);
		 jogadorsAmbTirades.sort(Comparator.comparing(Jogador::calcularPorcentaje).reversed());

			return jogadorsAmbTirades.get(0);
	 }
	 
	 
  }
