package com.init.JocDausS.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.type.TrueFalseType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.init.JocDausS.Model.JogadorModel;
import com.init.JocDausS.Model.TiradaModel;
import com.init.JocDausS.document.Tirada;
import com.init.JocDausS.entity.Jogador;
import com.init.JocDausS.repository.JogadorRepository;
import com.init.JocDausS.repository.TiradaRepository;

@Service
public class ServiceJogador {

	@Autowired
	JogadorRepository jogadorRepository;

	@Autowired
	TiradaRepository tiradaRepository;
	// 1 Llistar jogadors: retorna la llista de jogadors
	public List<JogadorModel> llistaJogadors() {
		List<JogadorModel> jogadors = new ArrayList<JogadorModel>();
		List<Jogador> jogadorsLlista = new ArrayList<Jogador>();
		try {
			jogadorsLlista = jogadorRepository.findAll();
		} catch (Exception e) {
			throw e;
		}
		if (jogadorsLlista.size() > 0) {
			jogadorsLlista.stream().forEach(j -> {
				JogadorModel jogadorModel = new JogadorModel();
				jogadorModel.setId(j.getId());
				jogadorModel.setNom(j.getNom());
				jogadorModel.setData(j.getData());
				jogadorModel.setPorcentatgeExit(j.getPorcentatgeExit());
				List<Tirada> tiradesLlista = new ArrayList<Tirada>();
				List<TiradaModel> tirades = new ArrayList<TiradaModel>();
				try {
					tiradesLlista = tiradaRepository.findTiradaByIdjogador(jogadorModel.getId());
				} catch (Exception e) {
					throw e;
				}
				if (tiradesLlista.size() > 0) {
					tiradesLlista.stream().forEach(t -> {
						TiradaModel tiradaModel = new TiradaModel();
						BeanUtils.copyProperties(t, tiradaModel);
						tirades.add(tiradaModel);
					});

				}
				jogadorModel.setTirades(tirades);
				jogadors.add(jogadorModel);
			});

		}
		return jogadors;
	}

	// 2 crea jogador nou
	@Transactional
	public String creaJogador(JogadorModel jogadorModel) {
		if (!jogadorRepository.existsById(jogadorModel.getId())) {
			Jogador jogador = new Jogador();
			BeanUtils.copyProperties(jogadorModel, jogador);
			try {
				jogadorRepository.save(jogador);
				jogadorModel.getTirades().stream().forEach(t -> {
					Tirada tirada = new Tirada();
					t.setId_jogador(jogadorModel.getId());
					BeanUtils.copyProperties(t, tirada);
					try {
						tiradaRepository.save(tirada);

					} catch (Exception e) {
						throw e;
					}
				});

			} catch (Exception e) {
				throw e;
			}
		}
		return "jogador creat correctament";

	}

	// 3 edita un jugador
	@Transactional
	public String updateNomJogador(int id, String nom) {
		if (jogadorRepository.existsById(id)) {
			Jogador jogador = jogadorRepository.findById(id);
			jogador.setNom(nom);
			jogadorRepository.save(jogador);
			return "nom canviat correctament";

		}
		return "aquest jogador no existeix";
	}

	// 4 afagir tirada
	@Transactional
	public String afegirTirada(int id, String idTirada) {
		if (jogadorRepository.existsById(id)) {
			Tirada tirada = new Tirada(idTirada, id);
			tiradaRepository.save(tirada);
			List<Tirada>tirades=tiradaRepository.findTiradaByIdjogador(id);
			int tiradesTotales=tirades.size();
			int tiradesTrue=(int) tirades.stream().filter(t->t.isGuanyar()==true).count();
			double porcentatge=(tiradesTrue*100)/tiradesTotales;
			Jogador jogador=jogadorRepository.findById(id);
			jogador.setPorcentatgeExit(porcentatge);
			jogadorRepository.save(jogador);
			return "tirada afegida correctament";
		}
		return "Aquest jogador no existeix";

	}

	// 5 esborra tirades d'un jugador
	@Transactional
	public String deleteTiradesJogador(int id) {
		if (jogadorRepository.existsById(id)) {
			tiradaRepository.deleteAllByIdjogador(id);
			return "les tirades han sigut esborrades";
		}
		return "Aquest jogador no existeix";
	}

	// 6 llista el nom i percentatge del jogadors
	public Map<String, Double> llistatnomIPercentatges() {

		List<JogadorModel> jogadors = llistaJogadors();
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

	//7 llista tirades d'un jugador
		public List<Tirada> llistaTiradesJogador(int id_jogador){
			List <Tirada> tirades= tiradaRepository.findTiradaByIdjogador(id_jogador);
			return tirades;
		}
		
		//8 dona el ranking mig dels jogadors
		public double DonaRankingMig() {
			List<Jogador> jogadors=jogadorRepository.findAll();
			double rankingMig = 0.0;
			double sumatoriPorcentatge = 0.0;
			for (int i = 0; i < jogadors.size(); i++) {
				sumatoriPorcentatge += jogadors.get(i).getPorcentatgeExit();
				
			}
			rankingMig = sumatoriPorcentatge / jogadors.size();
			double rankingMiground=Math.round(rankingMig * 100.0) / 100.0;
			return rankingMiground;
		}
	
		//9 llista nomes els jogadors que tenen tirades
		public List<Jogador> llistatJogadorsAmbTirades() {
			List<Jogador> jogadors=jogadorRepository.findAll();
			List<Jogador> jogadorsAmbTirades=new ArrayList <Jogador>();
			for (Jogador jogador : jogadors) {
				if(jogador.getPorcentatgeExit()!=null) {
					jogadorsAmbTirades.add(jogador);
					
				}
			}
			return jogadorsAmbTirades;
		}
		
		//10 retorna el pitjor jogar
		public Jogador DonaPitjorRanking() {

			List<Jogador> jogadorsAmbTirades=llistatJogadorsAmbTirades();
			if (jogadorsAmbTirades.size()>0 &&jogadorsAmbTirades!=null) {
				jogadorsAmbTirades.sort(Comparator.comparing(Jogador::getPorcentatgeExit));
				return jogadorsAmbTirades.get(0);
			}
			return null;
		}
		
		//11 retorna el pitjor millor
		public Jogador DonaMillorRanking() {

			List<Jogador> jogadorsAmbTirades=llistatJogadorsAmbTirades();
			if (jogadorsAmbTirades.size()>0 &&jogadorsAmbTirades!=null) {
				jogadorsAmbTirades.sort(Comparator.comparing(Jogador::getPorcentatgeExit).reversed());
				return jogadorsAmbTirades.get(0);
			}
			return null;
		}
		
}
