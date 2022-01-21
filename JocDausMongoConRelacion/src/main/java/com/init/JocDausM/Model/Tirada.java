package com.init.JocDausM.Model;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.NonNull;

@Document(collection = "tirades")
public class Tirada implements Serializable {

	// esta es la variable global que utilzams para cambiar el id generado por mongo
	// por un autoincremental numerico
	// especifico para tiradas
	@Transient
	public static final String SEQUENCE_NAME = "tirades_sequence";

	@Id
	@NonNull
	private long id;

	@Field(name = "valortirada1")
	private int valorTirada1;

	@Field(name = "valortirada2")
	private int valorTirada2;

	@Field(name = "guanyar")
	private boolean guanyar;

	@JoinColumn(name = "jogador_id")
	@ManyToOne
	@JsonIgnore
	private Jogador jogador;

	public Tirada() {
	}

	public Tirada(long id, Jogador jogador) {
		this.id = id;
		this.valorTirada1 = devuelveRandomEntreUnoYseis();
		this.valorTirada2 = devuelveRandomEntreUnoYseis();
		this.guanyar = calculateResult();
		this.jogador = jogador;
	}

	public Tirada(Jogador jogador) {

		this.valorTirada1 = devuelveRandomEntreUnoYseis();
		this.valorTirada2 = devuelveRandomEntreUnoYseis();
		this.guanyar = calculateResult();
		this.jogador = jogador;
	}

	public Tirada(long id, int valorTirada1, int valorTirada2, boolean guanyar, Jogador jogador) {
		this.id = id;
		this.valorTirada1 = valorTirada1;
		this.valorTirada2 = valorTirada2;
		this.guanyar = guanyar;
		this.jogador = jogador;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
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

	@Override
	public String toString() {
		return "Tirada [id=" + id + ", valorTirada1=" + valorTirada1 + ", valorTirada2=" + valorTirada2 + ", guanyar="
				+ guanyar + ", jogador=" + jogador + "]";
	}

}
