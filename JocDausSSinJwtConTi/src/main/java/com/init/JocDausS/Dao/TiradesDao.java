package com.init.JocDausS.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.init.JocDausS.Entity.Jogador;
import com.init.JocDausS.Entity.Tirada;

public interface TiradesDao extends JpaRepository<Tirada, Integer> {
	
	int deleteAllByJogador(Jogador jogador);
}
