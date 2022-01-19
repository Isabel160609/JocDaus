package com.init.JocDausM.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;
import com.init.JocDausM.dao.JogadorDao;
import com.init.JocDausM.dao.TiradesDao;
import com.init.JocDausM.Service.SequenceGeneratorService;
import com.init.JocDausM.Service.ServiceController;


//@CrossOrigin(origins = "*", methods= { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RestController
@RequestMapping("/")
public class ControllerJogador {

	@Autowired
	private JogadorDao jogadorDao;

	@Autowired
	private TiradesDao tiradesDao;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;


	@Autowired
	ServiceController serviceController = new ServiceController();

	//1 Llistar jogadors: retorna la llista de jogadors
	// (GET /players/).
	@GetMapping(value = "/players")
	public ResponseEntity<List<Jogador>> getJogadors() {

		List<Jogador> jogadors =  serviceController.llistaJogadors();
		return ResponseEntity.ok(jogadors);
	}

	// 2 POST: /players : crea un jugador
	@PostMapping(value = "/players")
	public ResponseEntity<String> createJogador(@RequestBody Jogador jogador) {
	Jogador jogadorcreat=serviceController.nouJogador(jogador);

		return ResponseEntity.ok(jogadorcreat.toString());
	}

	// 3 PUT /players : modifica el nom del jugador
	@PutMapping(value = "/players/{id}")
	public ResponseEntity<String> ModificaNomJogador(@PathVariable long id, @RequestBody String nom) {
		String respuesta=serviceController.canviaNomJogador(id, nom);
		return ResponseEntity.ok(respuesta);

	}

	// 4 POST /players/{id}/games/ : un jugador específic realitza una tirada dels
	// daus.
	@PostMapping(value = "/players/{id}/games")
	public ResponseEntity<String> tirarDaus(@PathVariable(name = "id") long id ) {
		String respuesta=serviceController.jogadorTiraDaus(id);
		return ResponseEntity.ok(respuesta);
	}

	// 5 DELETE /players/{id}/games: elimina les tirades del jugador
	@Transactional
	@DeleteMapping(value = "players/{id_jogador}/games")
	public ResponseEntity<String> borrarTiradas(@PathVariable(name = "id_jogador") long id) {
		String resposta=serviceController.borraTitades(id);
		return ResponseEntity.ok(resposta);
	}

	// 6 GET /players/: retorna el llistat de tots els jugadors del sistema amb el
	// seu percentatge mig d’èxits
	@GetMapping(value = "/players/porcentatge")
	public ResponseEntity<Map<String, Double>> llistatPercentatges() {
		List<Jogador> jogadors =serviceController.llistaJogadors();
		Map<String, Double> mapjogadorPercentatge = serviceController.llistatnomIPercentatges(jogadors);
		return ResponseEntity.ok(mapjogadorPercentatge);
	}

	// 6 GET /players/{id}/games: retorna el llistat de jugades per un jugador.
	@GetMapping(value = "/players/{id}/games")
	public ResponseEntity<List<Tirada>> llistarTirades(@PathVariable long id) {
		
			List<Tirada> tirades = serviceController.donaTiradesJogador(id);
			
			return ResponseEntity.ok(tirades);
	}

	// 7 GET /players/ranking: retorna el ranking mig de tots els jugadors del
	// sistema . És a dir, el percentatge mig d’èxits.
	@GetMapping(value = "/players/ranking")
	public ResponseEntity<Double> rankingMig() {
		double rankingMig = 0.0;
		List<Jogador> jogadors = serviceController.llistaJogadors();
		rankingMig = serviceController.DonaRankingMig(jogadors);
		return ResponseEntity.ok(rankingMig);
	}

	//8 GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit
	@GetMapping(value = "/players/ranking/loser")
	public ResponseEntity<Jogador> rankingloser() {
		double rankingMig = 0.0;
		List<Jogador> jogadors = serviceController.llistaJogadors();;
		Jogador jogadorPitjor = serviceController.DonaPitjorRanking(jogadors);
		return ResponseEntity.ok(jogadorPitjor);
	}

	//9 GET /players/ranking/winner: retorna el jugador amb millor percentatge d’èxit
	@GetMapping(value = "/players/ranking/winner")
	public ResponseEntity<Jogador> rankingwinner() {

		List<Jogador> jogadors = serviceController.llistaJogadors();;
		Jogador jogadorMillor = serviceController.DonaMillorRanking(jogadors);
		return ResponseEntity.ok(jogadorMillor);

	}
}
