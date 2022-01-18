package com.init.JocDausS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.init.JocDausS.entity.Jogador;



public interface  JogadorRepository  extends JpaRepository<Jogador, Integer>{

	 public boolean existsById(int id);
	 public Jogador findById(int id);
	 public void deleteById(int id);
	 
}
