package com.init.JocDausS.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;



@Entity
@Table(name="jogador")
public class Jogador {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_jogador")
	private int id;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date data;
	
	@Column(name="porcentatgeexit")
	private Double porcentatgeExit;

	public Jogador() {
	}

	
	public Jogador(int id_jogador, String nom, java.util.Date data) {
	this.id = id_jogador;
	this.nom = nom;
	this.data = data;
}


	public Jogador(int id_jogador, String nom, Date data, Double porcentatgeExit) {
		this.id = id_jogador;
		this.nom = nom;
		this.data = data;
		this.porcentatgeExit = porcentatgeExit;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public java.util.Date getData() {
		return data;
	}

	public void setData(java.util.Date data) {
		this.data = data;
	}

	
	public Double getPorcentatgeExit() {
		return porcentatgeExit;
	}


	public void setPorcentatgeExit(Double porcentatgeExit) {
		this.porcentatgeExit = porcentatgeExit;
	}


}
