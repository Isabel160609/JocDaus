package com.init.JocDausM.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;


public interface TiradesDao extends MongoRepository<Tirada,String>{

	//int deleteAllById_jogador(String id_jogador);
	//List<Tirada> findAllById_jogador(String id_jogador);
}
