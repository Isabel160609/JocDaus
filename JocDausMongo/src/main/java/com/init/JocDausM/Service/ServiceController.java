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

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;
import com.init.JocDausM.dao.JogadorDao;
import com.init.JocDausM.dao.TiradesDao;


@Service
public class ServiceController implements IServiceController{

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
	
	// 2 POST: /players : crea un jugador
	public Jogador creaJogador(Jogador jogador) {
		jogador.setId(sequenceGeneratorService.generateSequence(Jogador.SEQUENCE_NAME));
		jogadorDao.save(jogador);
		return jogador;
	}
	
	// 3 PUT /players : modifica el nom del jugador
	public Jogador canviaNomJogador(long id, String nom) {
		
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);
		
		if (optionalJogador.isPresent()) {
			Jogador updateJogador = optionalJogador.get();
			updateJogador.setNom(nom);
			jogadorDao.save(updateJogador);
			return updateJogador;

		}else {
			Jogador updateJogador= optionalJogador.get();
			return updateJogador;
		}
	}
	// 4 POST un jugador específic realitza una tirada dels daus.
	public String jogadorTiraDaus(long id){
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);
		String resposta;
		if (optionalJogador.isPresent()) {
			Jogador updateJogador = optionalJogador.get();
			
			Tirada tirada = new Tirada(updateJogador.getId());
			//updateJogador.addTirada(tirada);
			tirada.setId(sequenceGeneratorService.generateSequence(Tirada.SEQUENCE_NAME));
			tiradesDao.save(tirada);
			List<Tirada> tirades=tiradesDao.findAll();
			List<Tirada> tiradesjogador=buscartiradesJogador(tirades, id);
			double porcentatge=calcularPorcentaje(tiradesjogador);
			updateJogador.setPorcentatgeExit(porcentatge);
			jogadorDao.save(updateJogador);
			resposta=tirada.toString();
		}else resposta="aquest jogador no existeix";
		
		return resposta;
	}

	// 5 DELETE /players/{id}/games: elimina les tirades del jugador
	public String eliminaTiradesJogador(Optional<Jogador> optionalJogador) {
		if (optionalJogador.isPresent()) {
			List<Tirada> tirades=tiradesDao.findAll();
			for(Tirada tirada:tirades) {
				if (tirada.getId_jogador()==optionalJogador.get().getId()) {
					 tiradesDao.delete(tirada);
				}
			}
			return "tirades eliminades";
		}
		return "el jogador amb id " + optionalJogador.get().getId() + " no existeix";
		
	}
	
	
	// 6 GET /players/: retorna el llistat de tots els jugadors del sistema amb el
		// seu percentatge mig d’èxits
	public Map<String, Double> llistatnomIPercentatges() {
		List<Jogador> jogadors = jogadorDao.findAll();
		List<Tirada> tirades=tiradesDao.findAll();
		
		Map<String, Double> mapjogadorPercentatge = new HashMap<String, Double>();
		List<Tirada> tiradesJogador;
		if (jogadors != null && jogadors.size() > 0) {
			String key = "";
			Double value = null;
			for (int i = 0; i < jogadors.size(); i++) {
				String Key = jogadors.get(i).getNom();
				tiradesJogador = buscartiradesJogador(tirades, jogadors.get(i).getId());
				if (!tiradesJogador.isEmpty()) {

					value = calcularPorcentaje(tiradesJogador);
				} else {
					value = null;
				}
				mapjogadorPercentatge.put(Key, value);
			}

		}
		return mapjogadorPercentatge;
	}
	//7 busca tirades d'un jogador
	public List<Tirada> buscartiradesJogador(List<Tirada> tirades, long jogador_id) {
		List<Tirada> tiradesJogador = new ArrayList<Tirada>();
		for (Tirada tirada : tirades) {
			if (tirada.getId_jogador() == jogador_id) {
				tiradesJogador.add(tirada);
			}
		}
		return tiradesJogador;

	}
	//calcula porcentatge d'un jogador
	public double calcularPorcentaje(List<Tirada> llistaTirades) {
		int contador = 0;
		for (int i = 0; i < llistaTirades.size(); i++) {
			if (llistaTirades.get(i).isGuanyar()) {
				contador += 1;
			}
		}
		double porcentaje = pasaAPorcentaje(llistaTirades.size(), contador);
		return porcentaje;
	}
	//calcula %
	public double pasaAPorcentaje(int total, int totalTrue) {
		double porcentaje = (totalTrue * 100) / total;
		return porcentaje;
	}
	// 7retorna el ranking mig de tots els jugadors del sistema . És a dir, el percentatge mig d’èxits.
	public double DonaRankingMig(List<Tirada> tirades) {
		double rankingMig = 0.0;
		if (tirades != null && tirades.size() > 0) {
			rankingMig = calcularPorcentaje(tirades);
		}
		return rankingMig;
	}

	//8  retorna el jugador  amb pitjor percentatge d’èxit
	public Jogador DonaPitjorRanking(List<Jogador> jogadors) {

		jogadors.sort(Comparator.comparing(Jogador::getPorcentatgeExit));

		return jogadors.get(0);
	}

	// 9  retorna el jugador amb millor percentatge d’èxit
	public Jogador DonaMillorRanking(List<Jogador> jogadors) {
		jogadors.sort(Comparator.comparing(Jogador::getPorcentatgeExit).reversed());

		return jogadors.get(0);
	}

}
