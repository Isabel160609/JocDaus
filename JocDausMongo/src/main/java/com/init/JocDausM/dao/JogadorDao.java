package com.init.JocDausM.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.init.JocDausM.Model.Jogador;

public interface JogadorDao extends MongoRepository<Jogador,String>{

	//Optional<Jogador> findById(String id);
}
