package com.init.JocDausM.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@SpringBootApplication
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

		List<Jogador> jogadors =serviceController.llistaJogadors();
		return ResponseEntity.ok(jogadors);
	}

	// 2 POST: /players : crea un jugador
	@PostMapping(value = "/players")
	public ResponseEntity<String> createJogador(@RequestBody Jogador jogador) {
		List<Jogador> jogadors =jogadorDao.findAll();
		if (jogador.getNom().equalsIgnoreCase("")) {
			jogador.setNom("Anonimo");
		}

		for (Jogador nombre : jogadors) {
			if (nombre.getNom().equalsIgnoreCase(jogador.getNom())) {
				jogador.setNom(jogador.getNom() + "1");
			}
		}
		jogador=serviceController.creaJogador(jogador);
		//serviceController.creaJogador(jogador);

		return ResponseEntity.ok("jogador creat correctament");
	}

	// 3 PUT /players : modifica el nom del jugador
	@PutMapping(value = "/players/{id}")
	public ResponseEntity<String> ModificaNomJogador(@PathVariable long id, @RequestBody String nom) {
		Jogador jogador=serviceController.canviaNomJogador(id, nom);
		if (jogador==null) return ResponseEntity.ok("el jugador con id " + id + " no existe");
		else return ResponseEntity.ok(jogador.toString());
	}

	// 4 POST /players/{id}/games/ : un jugador específic realitza una tirada dels
	// daus.
	@PostMapping(value = "/players/{id}/games")
	public ResponseEntity<String> tirarDaus(@PathVariable(name = "id") long id) {
		
		String tirada=serviceController.jogadorTiraDaus(id);
		return ResponseEntity.ok(tirada);
	}

	// 5 DELETE /players/{id}/games: elimina les tirades del jugador
	@Transactional
	@DeleteMapping(value = "players/{id_jogador}/games")
	public ResponseEntity<String> borrarTiradas(@PathVariable(name = "id_jogador") long id) {
		
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);

		String resposta=serviceController.eliminaTiradesJogador(optionalJogador);
		return ResponseEntity.ok(resposta);
	}

	// 6 GET /players/: retorna el llistat de tots els jugadors del sistema amb el
	// seu percentatge mig d’èxits
	@GetMapping(value = "/players/porcentatge")
	public Map<String, Double> llistatPercentatges() {
		
		Map<String, Double> mapjogadorPercentatge = serviceController.llistatnomIPercentatges();
		return mapjogadorPercentatge;
	}

	// 7 GET /players/{id}/games: retorna el llistat de jugades per un jugador.
	@GetMapping(value = "/players/{id}/games")
	public ResponseEntity<List<Tirada>> llistarTirades(@PathVariable long id) {
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);

		if (optionalJogador.isPresent()) {
			List<Tirada> tirades=tiradesDao.findAll();
			List<Tirada> tiradesJogador = serviceController.buscartiradesJogador(tirades,id);
			return ResponseEntity.ok(tiradesJogador);

		} else {
			List<Tirada> empty = new ArrayList<Tirada>();
			return ResponseEntity.ok(empty);
		}

	}

	// 7 GET /players/ranking: retorna el ranking mig de tots els jugadors del
	// sistema . És a dir, el percentatge mig d’èxits.
	@GetMapping(value = "/players/ranking")
	public double rankingMig() {
		List<Tirada> tirades=tiradesDao.findAll();
		double rankingMig =serviceController.DonaRankingMig(tirades);
		return rankingMig;
	}

	//8 GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit
	@GetMapping(value = "/players/ranking/loser")
	public Jogador rankingloser() {
		List<Jogador> jogadors = jogadorDao.findAll();
		Jogador jogadorPitjor = serviceController.DonaPitjorRanking(jogadors);
		return jogadorPitjor;
	}

	// 9 GET /players/ranking/winner: retorna el jugador amb millor percentatge d’èxit
	@GetMapping(value = "/players/ranking/winner")
	public Jogador rankingwinner() {

		List<Jogador> jogadors = jogadorDao.findAll();
		Jogador jogadorMillor = serviceController.DonaMillorRanking(jogadors);
		return jogadorMillor;

	}
}
