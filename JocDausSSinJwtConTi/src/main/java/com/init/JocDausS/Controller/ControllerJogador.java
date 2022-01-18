package com.init.JocDausS.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.init.JocDausS.Dao.JogadorDao;
import com.init.JocDausS.Dao.TiradesDao;
import com.init.JocDausS.Entity.Jogador;
import com.init.JocDausS.Entity.Tirada;
import com.init.JocDausS.Service.ServiceController;

@RestController
@RequestMapping("/")
public class ControllerJogador {

	@Autowired
	private JogadorDao jogadorDao;

	@Autowired
	private TiradesDao tiradesDao;

	@Autowired
	ServiceController ServiceController = new ServiceController();

	// 1 Llistar jogadors: retorna la llista de jogadors
	// (GET /players/).
	@GetMapping(value = "/players")
	public ResponseEntity<List<Jogador>> getJogadors() {
		try {
			List<Jogador> jogadors = ServiceController.llistaJogadors();
			return new ResponseEntity<List<Jogador>>(jogadors, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// 2 POST: /players : crea un jugador
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/players")
	public ResponseEntity<Jogador> createJogador(@RequestBody Jogador jogador) {
		try {
			Jogador jogadorCreat = ServiceController.creaJogador(jogador);
			return new ResponseEntity<Jogador>(jogadorCreat, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	// 3 PUT /players : modifica el nom del jugador
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value = "/players/{id}")
	public ResponseEntity<Jogador> ModificaNomJogador(@PathVariable int id, @RequestBody String nom) {
		try {
			Optional<Jogador> optionalJogador = ServiceController.BuscaJogador(id);

			if (optionalJogador.isPresent()) {
				Jogador updateJogador = optionalJogador.get();
				updateJogador.setNom(nom);
				ServiceController.updateJogador(updateJogador);
				return new ResponseEntity<Jogador>(updateJogador, HttpStatus.OK);

			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	// 4 POST /players/{id}/games/ : un jugador específic realitza una tirada dels
	// daus.
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/players/{id}/games/")
	public ResponseEntity<Jogador> tirarDaus(@PathVariable(name = "id") int id) {
		try {
			Optional<Jogador> optionalJogador = ServiceController.BuscaJogador(id);

			if (optionalJogador.isPresent()) {
				Jogador updateJogador = optionalJogador.get();
				Tirada tirada = new Tirada(updateJogador);
				updateJogador.addTirada(tirada);
				ServiceController.updateJogador(updateJogador);
				// jogadorDao.save(updateJogador);
				return new ResponseEntity<Jogador>(updateJogador, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	// 5 DELETE /players/{id}/games: elimina les tirades del jugador
	//@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	@DeleteMapping(value = "players/{id_jogador}/games")
	public ResponseEntity<Jogador> borrarTiradas(@PathVariable(name = "id_jogador") int id) {
		try {
			Optional<Jogador> jogadorOptional = ServiceController.BuscaJogador(id);
			Jogador Jogador = jogadorOptional.get();

			if (!jogadorOptional.isEmpty()) {

				Jogador JogadorSenseTirades = ServiceController.deleteTiradesJogador(Jogador);

				return new ResponseEntity<Jogador>(JogadorSenseTirades, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	// 6 GET /players/: retorna el llistat de tots els jugadors del sistema amb el
	// seu percentatge mig d’èxits
	@GetMapping(value = "/players/porcentatge")
	public ResponseEntity<Map<String, Double>> llistatPercentatges() {
		try {
			List<Jogador> jogadors = ServiceController.llistaJogadors();
			Map<String, Double> mapjogadorPercentatge = ServiceController.llistatnomIPercentatges(jogadors);
			return new ResponseEntity<Map<String, Double>>(mapjogadorPercentatge, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	// 7 GET /players/{id}/games: retorna el llistat de jugades per un jugador.
	@GetMapping(value = "/players/{id}/games")
	public ResponseEntity<List<Tirada>> llistarTirades(@PathVariable int id) {
		try {
			Optional<Jogador> optionalJogador = ServiceController.BuscaJogador(id);

			if (optionalJogador.isPresent()) {
				Jogador jogador = optionalJogador.get();
				List<Tirada> tirades = ServiceController.llistaTiradesJogador(jogador);
				return new ResponseEntity<List<Tirada>>(tirades, HttpStatus.OK);

			} else {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	// 8 GET /players/ranking: retorna el ranking mig de tots els jugadors del
	// sistema . És a dir, el percentatge mig d’èxits.
	@GetMapping(value = "/players/ranking")
	public ResponseEntity<Double> rankingMig() {
		try {
			double rankingMig = 0.0;
			List<Jogador> jogadors = ServiceController.llistaJogadors();
			rankingMig = ServiceController.DonaRankingMig(jogadors);
			return new ResponseEntity<Double>(rankingMig, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

//		//9 GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit
	@GetMapping(value = "/players/ranking/loser")
	public ResponseEntity<Jogador> rankingloser() {
		try {
			double rankingMig = 0.0;
			List<Jogador> jogadors = ServiceController.llistaJogadors();
			Jogador jogadorPitjor = ServiceController.DonaPitjorRanking(jogadors);
			return new ResponseEntity<Jogador>(jogadorPitjor, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	// 10 GET /players/ranking/winner: retorna el jugador amb millor percentatge
	// d’èxit
	@GetMapping(value = "/players/ranking/winner")
	public ResponseEntity<Jogador> rankingwinner() {
		try {
			List<Jogador> jogadors = ServiceController.llistaJogadors();
			Jogador jogadorMillor = ServiceController.DonaMillorRanking(jogadors);
			return new ResponseEntity<Jogador>(jogadorMillor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}
}
