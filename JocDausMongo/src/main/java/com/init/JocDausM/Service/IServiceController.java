package com.init.JocDausM.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;

public interface IServiceController {
	List<Jogador> llistaJogadors();

	Jogador creaJogador(Jogador jogador);

	Jogador canviaNomJogador(long id, String nom);

	String jogadorTiraDaus(long id);

	String eliminaTiradesJogador(Optional<Jogador> optionalJogador);

	public Map<String, Double> llistatnomIPercentatges();

	List<Tirada> buscartiradesJogador(List<Tirada> tirades, long jogador_id);

	double calcularPorcentaje(List<Tirada> llistaTirades);

	double pasaAPorcentaje(int total, int totalTrue);

	double DonaRankingMig(List<Tirada> tirades);

	Jogador DonaPitjorRanking(List<Jogador> jogadors);

	Jogador DonaMillorRanking(List<Jogador> jogadors);

}
