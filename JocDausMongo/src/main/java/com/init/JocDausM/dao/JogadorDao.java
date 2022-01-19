package com.init.JocDausM.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.init.JocDausM.Model.Jogador;
@Component
@Repository
public interface JogadorDao extends MongoRepository<Jogador,Long>{

	//Optional<Jogador> findById(long id);
	//List<Jogador> findAll();
}
