package com.init.JocDausS.Controller;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.init.JocDausS.Model.JogadorModel;
import com.init.JocDausS.document.Tirada;
import com.init.JocDausS.entity.Jogador;
import com.init.JocDausS.service.ServiceJogador;

@RestController
public class Controller {

	@Autowired
	ServiceJogador serviceJogador;

	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String info() {
		return "The application is up...";
	}

	// 1 Llistar jogadors: retorna la llista de jogadors
	// (GET /players/).
	@GetMapping(value = "/players")
	public List<JogadorModel> getJogadors() {
		return serviceJogador.llistaJogadors();
	}

	//// 2 POST: /players : crea un jugador
	@PostMapping(value = "players")
	public String createJogador(@RequestBody JogadorModel jogadorModel) {
		return serviceJogador.creaJogador(jogadorModel);
	}

	// 3 PUT /players : modifica el nom del jugador
	@PutMapping(value = "/players/{id}")
	public String ModificaNomJogador(@PathVariable int id, @RequestBody String nom) {
		return serviceJogador.updateNomJogador(id, nom);
	}

	// 4 POST /players/{id}/games/ : un jugador específic realitza una tirada dels
	// daus.
	@PostMapping(value = "/players/{id}/games/")
	public String tirarDaus(@PathVariable int id, @RequestBody String idtirada) {
		return serviceJogador.afegirTirada(id, idtirada);
	}

	// 5 DELETE /players/{id}/games: elimina les tirades del jugador
	@Transactional
	@DeleteMapping(value = "players/{id_jogador}/games")
	public String borrarTiradas(@PathVariable(name = "id_jogador") int id) {
		return serviceJogador.deleteTiradesJogador(id);
	}

	// 6 GET /players/: retorna el llistat de tots els jugadors del sistema amb el
	// seu percentatge mig d’èxits
	@GetMapping(value = "/players/porcentatge")
	public ResponseEntity<Map<String, Double>> llistatPercentatges() {
		Map<String, Double> porcentatge = serviceJogador.llistatnomIPercentatges();
		return new ResponseEntity<Map<String, Double>>(porcentatge, HttpStatus.OK);
	}

	// 7 GET /players/{id}/games: retorna el llistat de jugades per un jugador.
	@GetMapping(value = "/players/{id}/games")
		public ResponseEntity<List<Tirada>> llistarTirades(@PathVariable int id) {
		List<Tirada> tirades = serviceJogador.llistaTiradesJogador(id);
		return new ResponseEntity<List<Tirada>>(tirades, HttpStatus.OK);
	}
	

	// 8 GET /players/ranking: retorna el ranking mig de tots els jugadors del
	// sistema . És a dir, el percentatge mig d’èxits.
	@GetMapping(value = "/players/ranking")
	public ResponseEntity<Double> rankingMig() {
		double rankingMig=serviceJogador.DonaRankingMig();
		return new ResponseEntity<Double>(rankingMig,  HttpStatus.OK);
	}
	
	//9 GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit
	@GetMapping(value = "/players/ranking/loser")
	public ResponseEntity<Jogador> rankingloser() {
		Jogador jogador=serviceJogador.DonaPitjorRanking();
		return new ResponseEntity<Jogador>(jogador,  HttpStatus.OK);
	}
	
	// 10 GET /players/ranking/winner: retorna el jugador amb millor percentatge
		// d’èxit
		@GetMapping(value = "/players/ranking/winner")
		public ResponseEntity<Jogador> rankingwinner() {
			Jogador jogador=serviceJogador.DonaMillorRanking();
			return new ResponseEntity<Jogador>(jogador,  HttpStatus.OK);
		}

}
