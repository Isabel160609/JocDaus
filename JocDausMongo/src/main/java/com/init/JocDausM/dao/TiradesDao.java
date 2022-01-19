package com.init.JocDausM.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.init.JocDausM.Model.Jogador;
import com.init.JocDausM.Model.Tirada;

@Repository
public interface TiradesDao extends MongoRepository<Tirada,Long>{

	//int deleteAllById_jogador(String id_jogador);
	//List<Tirada> findAllById_jogador(String id_jogador);
}
