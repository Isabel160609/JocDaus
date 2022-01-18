package com.init.JocDausS.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tirades")
public class Tirada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "valortirada1")
	private int valorTirada1;

	@Column(name = "valortirada2")
	private int valorTirada2;

	@Column(name = "guanyar")
	private boolean guanyar;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "jogador_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Jogador jogador;

	public Tirada() {
	}

	public Tirada(Jogador jogador) {
		this.valorTirada1 = devuelveRandomEntreUnoYseis();
		this.valorTirada2 = devuelveRandomEntreUnoYseis();
		this.guanyar = calculateResult();
		this.jogador = jogador;
	}

	
	public Tirada(int id, int valorTirada1, int valorTirada2, boolean guanyar, Jogador jogador) {
		this.id = id;
		this.valorTirada1 = valorTirada1;
		this.valorTirada2 = valorTirada2;
		this.guanyar = guanyar;
		this.jogador = jogador;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

}
