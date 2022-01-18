package com.init.JocDausS.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.init.JocDausS.Entity.Jogador;
import com.init.JocDausS.Entity.Tirada;

public interface IServiceController {
	
	List<Jogador> llistaJogadors();
	Jogador creaJogador(Jogador jogador);
	Jogador updateJogador(Jogador jogador);
	Optional<Jogador> BuscaJogador(int id);
	Jogador deleteTiradesJogador(Jogador jogador);
	Map<String, Double> llistatnomIPercentatges(List<Jogador> jogadors);
	List<Tirada> llistaTiradesJogador(Jogador jogador);
	double DonaRankingMig(List<Jogador> jogadors);
	List<Jogador> llistatJogadorsAmbTirades(List<Jogador> jogadors);
	Jogador DonaPitjorRanking(List<Jogador> jogadors);
	Jogador DonaMillorRanking(List<Jogador> jogadors);
	
	
}
