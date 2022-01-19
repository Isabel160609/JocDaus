package com.init.JocDausM.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;


public interface TiradesDao extends MongoRepository<Tirada,Long>{

	int deleteAllByJogador(Jogador jogador);
}
