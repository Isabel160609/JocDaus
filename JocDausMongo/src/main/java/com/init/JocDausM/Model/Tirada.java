package com.init.JocDausM.Model;



import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.NonNull;

@Document(collection = "tirades")
public class Tirada implements Serializable{

	@Id
	@NonNull
	private String id;

	@Field(name = "valortirada1")
	private int valorTirada1;

	@Field(name = "valortirada2")
	private int valorTirada2;

	@Field(name = "guanyar")
	private boolean guanyar;
	
	@Field(name = "id_jogador")
	String id_jogador;

	public Tirada() {
	}

	public Tirada(String id,String id_jogador) {
		this.id=id;
		this.valorTirada1 = devuelveRandomEntreUnoYseis();
		this.valorTirada2 = devuelveRandomEntreUnoYseis();
		this.guanyar = calculateResult();
		this.id_jogador = id_jogador;
	}

	public Tirada(String id_jogador) {
		
		this.valorTirada1 = devuelveRandomEntreUnoYseis();
		this.valorTirada2 = devuelveRandomEntreUnoYseis();
		this.guanyar = calculateResult();
		this.id_jogador = id_jogador;
	}

	public Tirada(String id, int valorTirada1, int valorTirada2, boolean guanyar, String id_jogador) {
		this.id = id;
		this.valorTirada1 = valorTirada1;
		this.valorTirada2 = valorTirada2;
		this.guanyar = guanyar;
		this.id_jogador = id_jogador;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getValorTirada1() {
		return valorTirada1;
	}

	public void setValorTirada1(int valorTirada1) {
		this.valorTirada1 = valorTirada1;
	}

	public int getValorTirada2() {
		return valorTirada2;
	}

	public void setValorTirada2(int valorTirada2) {
		this.valorTirada2 = valorTirada2;
	}

	public boolean isGuanyar() {
		return guanyar;
	}

	public void setGuanyar(boolean guanyar) {
		this.guanyar = guanyar;
	}


	public String getId_jogador() {
		return id_jogador;
	}

	public void setId_jogador(String id_jogador) {
		this.id_jogador = id_jogador;
	}

	public boolean calculateResult() {
		if (this.valorTirada1 + this.valorTirada2 == 7) {
			return true;
		} else {
			return false;
		}
	}

	public int devuelveRandomEntreUnoYseis() {
		int random = (int) Math.floor(Math.random() * 6 + 1);
		return random;
	}

}
