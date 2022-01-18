package com.init.JocDausS.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.init.JocDausS.document.Tirada;

public interface TiradaRepository  extends MongoRepository<Tirada, String>{

    public List<Tirada> findTiradaByIdjogador(int idjogador);

    public void deleteAllByIdjogador(int Idjogador);
}
