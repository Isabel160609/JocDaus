package com.init.JocDausS.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.init.JocDausS.Dao.JogadorDao;
import com.init.JocDausS.Dao.TiradesDao;
import com.init.JocDausS.Entity.Jogador;
import com.init.JocDausS.Entity.Tirada;

@Service
public class ServiceController implements IServiceController  {

	@Autowired
	private JogadorDao jogadorDao;

	@Autowired
	private TiradesDao tiradesDao;
	//1 llistar jogadors
	public List<Jogador> llistaJogadors() {

		List<Jogador> jogadors = jogadorDao.findAll();
		return jogadors;
	}

	//2 crea jogador nou
	public Jogador creaJogador(Jogador jogador) {
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

		return jogador;
	}
	//3 edita un jugador
	public Jogador updateJogador(Jogador jogador) {
		jogadorDao.save(jogador);

		return jogador;

	}
	
	//4 busca un jogador per id
	//5 test per si no troba
	public Optional<Jogador> BuscaJogador(int id) {

		Optional<Jogador> jogador = jogadorDao.findById(id);

		return jogador;
	}

	//6 esborra tirades d'un jugador
	public Jogador deleteTiradesJogador(Jogador jogador) {
		tiradesDao.deleteAllByJogador(jogador);
		Optional<Jogador> jugador=jogadorDao.findById(jogador.getId());
		Jogador jogador1=null;
		if (!jugador.isEmpty()) {
			jogador1=jugador.get();
		}
		//tiradesDao.deleteAllByJogador(jogador);
		return jogador1;

	}
//	public void deleteTiradesJogador2(Jogador jogador) {
//		tiradesDao.deleteAllByJogador(jogador);
//		
//	}
	
	//7 llista el nom i percentatge del jogadors
	public Map<String, Double> llistatnomIPercentatges(List<Jogador> jogadors) {

		Map<String, Double> mapjogadorPercentatge = new HashMap<String, Double>();

		if (jogadors != null && jogadors.size() > 0) {
			String key = "";
			Double value = null;
			for (int i = 0; i < jogadors.size(); i++) {
				key = jogadors.get(i).getNom();
				if (jogadors.get(i).getTirades().size() > 0 && jogadors.get(i).getTirades() != null) {
					value = jogadors.get(i).calcularPorcentaje();
				} else {
					value = null;
				}
				mapjogadorPercentatge.put(key, value);
			}
		}
		return mapjogadorPercentatge;
	}
	
	//8 llista tirades d'un jugador
	public List<Tirada> llistaTiradesJogador(Jogador jogador){
		List<Tirada> tirades = jogador.getTirades();
		return tirades;
	}

	//9 dona el ranking mig dels jogadors
	public double DonaRankingMig(List<Jogador> jogadors) {
		double rankingMig = 0.0;
		// List<Jogador> jogadors = jogadorDao.findAll();
		double sumatoriPorcentatge = 0.0;
		for (int i = 0; i < jogadors.size(); i++) {
			if (jogadors.get(i).getTirades() != null && jogadors.get(i).getTirades().size() > 0) {
				sumatoriPorcentatge += jogadors.get(i).calcularPorcentaje();
			}
		}
		rankingMig = sumatoriPorcentatge / jogadors.size();
		return rankingMig;
	}

	//10 llista nomes els jogadors que tenen tirades
	public List<Jogador> llistatJogadorsAmbTirades(List<Jogador> jogadors) {
		List<Jogador> llistatJogadorsAmbTirades = new ArrayList<Jogador>();

		for (Jogador jogador : jogadors) {
			if (jogador.getTirades() != null && jogador.getTirades().size() > 0) {
				llistatJogadorsAmbTirades.add(jogador);
			}

		}
		return llistatJogadorsAmbTirades;
	}

	//11 retorna el pitjor jogar
	public Jogador DonaPitjorRanking(List<Jogador> jogadors) {
		List<Jogador> jogadorsAmbTirades = llistatJogadorsAmbTirades(jogadors);
		jogadorsAmbTirades.sort(Comparator.comparing(Jogador::calcularPorcentaje));

		return jogadorsAmbTirades.get(0);

	}

	//12 retorna el millor jogar
	public Jogador DonaMillorRanking(List<Jogador> jogadors) {
		List<Jogador> jogadorsAmbTirades = llistatJogadorsAmbTirades(jogadors);
		jogadorsAmbTirades.sort(Comparator.comparing(Jogador::calcularPorcentaje).reversed());

		return jogadorsAmbTirades.get(0);
	}

}
