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
import com.init.JocDausM.Service.ServiceController;


//@CrossOrigin(origins = "*", methods= { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RestController
@RequestMapping("/")
public class ControllerJogador {

	@Autowired
	private JogadorDao jogadorDao;

	@Autowired
	private TiradesDao tiradesDao;

	ServiceController ServiceController = new ServiceController();

	// Llistar jogadors: retorna la llista de jogadors
	// (GET /players/).
	@GetMapping(value = "/players")
	public ResponseEntity<List<Jogador>> getJogadors() {

		List<Jogador> jogadors = jogadorDao.findAll();
		return ResponseEntity.ok(jogadors);
	}

	// 1 POST: /players : crea un jugador
	@PostMapping(value = "/players")
	public ResponseEntity<String> createJogador(@RequestBody Jogador jogador) {
		List<Jogador> jogadors = jogadorDao.findAll();
		if (jogador.getNom().equalsIgnoreCase("")) {
			jogador.setNom("Anonimo");
		}

		for (Jogador nombre : jogadors) {
			if (nombre.getNom().equalsIgnoreCase(jogador.getNom())) {
				jogador.setNom(jogador.getNom() + "1");
			}
		}

		jogadorDao.save(jogador);

		return ResponseEntity.ok("jogador creat correctament");
	}

	// 2 PUT /players : modifica el nom del jugador
	@PutMapping(value = "/players/{id}")
	public ResponseEntity<String> ModificaNomJogador(@PathVariable String id, @RequestBody String nom) {
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);

		if (optionalJogador.isPresent()) {
			Jogador updateJogador = optionalJogador.get();
			updateJogador.setNom(nom);
			jogadorDao.save(updateJogador);
			return ResponseEntity.ok("nombre cambiado correctamente");

		}

		return ResponseEntity.ok("el jugador con id " + id + " no existe");

	}

	// 3 POST /players/{id}/games/ : un jugador específic realitza una tirada dels
	// daus.
	@PostMapping(value = "/players/{id}/games")
	public ResponseEntity<String> tirarDaus(@PathVariable(name = "id") String id,@RequestBody String id_tirada ) {
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);

		if (optionalJogador.isPresent()) {
			Jogador updateJogador = optionalJogador.get();
			Tirada tirada = new Tirada(id_tirada, updateJogador.getId());
			//updateJogador.addTirada(tirada);
			tiradesDao.save(tirada);
			List<Tirada> tirades=tiradesDao.findAll();
			List<Tirada> tiradesjogador=ServiceController.buscartiradesJogador(tirades, id);
			double porcentatge=ServiceController.calcularPorcentaje(tiradesjogador);
			updateJogador.setPorcentatgeExit(porcentatge);
			jogadorDao.save(updateJogador);
			return ResponseEntity.ok("la tirada ha sido añadida");
		}
		return ResponseEntity.ok("el jugador con id " + id + " no existe");
	}

	// 4 DELETE /players/{id}/games: elimina les tirades del jugador
	@Transactional
	@DeleteMapping(value = "players/{id_jogador}/games")
	public ResponseEntity<String> borrarTiradas(@PathVariable(name = "id_jogador") String id) {
		
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);

		if (optionalJogador.isPresent()) {
			List<Tirada> tirades=tiradesDao.findAll();
			for(Tirada tirada:tirades) {
				if (tirada.getId_jogador().equalsIgnoreCase(id)) {
					 tiradesDao.delete(tirada);
				}
			}
			return ResponseEntity.ok("tirades eliminades");
		}
		return ResponseEntity.ok("el jogador amb id " + id + " no existeix");
	}

	// 5 GET /players/: retorna el llistat de tots els jugadors del sistema amb el
	// seu percentatge mig d’èxits
	@GetMapping(value = "/players/porcentatge")
	public Map<String, Double> llistatPercentatges() {
		List<Jogador> jogadors = jogadorDao.findAll();
		List<Tirada> tirades=tiradesDao.findAll();
		Map<String, Double> mapjogadorPercentatge = ServiceController.llistatnomIPercentatges(jogadors,tirades);
		return mapjogadorPercentatge;
	}

	// 6 GET /players/{id}/games: retorna el llistat de jugades per un jugador.
	@GetMapping(value = "/players/{id}/games")
	public ResponseEntity<List<Tirada>> llistarTirades(@PathVariable String id) {
		Optional<Jogador> optionalJogador = jogadorDao.findById(id);

		if (optionalJogador.isPresent()) {
			List<Tirada> tirades=tiradesDao.findAll();
			List<Tirada> tiradesJogador = ServiceController.buscartiradesJogador(tirades,id);
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
		double rankingMig =ServiceController.DonaRankingMig(tirades);
		return rankingMig;
	}

//		//GET /players/ranking/loser: retorna el jugador  amb pitjor percentatge d’èxit
	@GetMapping(value = "/players/ranking/loser")
	public Jogador rankingloser() {
		List<Jogador> jogadors = jogadorDao.findAll();
		Jogador jogadorPitjor = ServiceController.DonaPitjorRanking(jogadors);
		return jogadorPitjor;
	}

	// GET /players/ranking/winner: retorna el jugador amb millor percentatge d’èxit
	@GetMapping(value = "/players/ranking/winner")
	public Jogador rankingwinner() {

		List<Jogador> jogadors = jogadorDao.findAll();
		Jogador jogadorMillor = ServiceController.DonaMillorRanking(jogadors);
		return jogadorMillor;

	}
}
