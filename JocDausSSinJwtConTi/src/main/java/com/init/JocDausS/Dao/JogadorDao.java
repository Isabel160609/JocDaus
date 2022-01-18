package com.init.JocDausS.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.init.JocDausS.Entity.Jogador;

@Repository
public interface JogadorDao extends JpaRepository<Jogador, Integer>{

}
