package com.init.JocDausS.Entity;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="jogador")
public class Jogador {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_jogador")
	private int id_jogador;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date data;
	
	
	@OneToMany(mappedBy="jogador", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Tirada>llistaTirades;
	
	@Transient
	private Double porcentatgeExit;

	public Jogador() {
	}

	
	public Jogador(int id_jogador, String nom, java.util.Date data) {
	this.id_jogador = id_jogador;
	this.nom = nom;
	this.data = data;
}


	public int getId() {
		return id_jogador;
	}

	public void setId(int id) {
		this.id_jogador = id;
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

	public List<Tirada> getTirades() {
		return llistaTirades;
	}

	public void setTirades(List<Tirada> tirades) {
		this.llistaTirades = tirades;
	}
	
	
	
	public Double getPorcentatgeExit() {
		return porcentatgeExit;
	}


	public void setPorcentatgeExit(Double porcentatgeExit) {
		this.porcentatgeExit = porcentatgeExit;
	}


	public void addTirada(Tirada laTirada) {
		
		if(llistaTirades==null) llistaTirades=new ArrayList<>();
				
		llistaTirades.add(laTirada);
				
		laTirada.setJogador(this);
				
				}
	
	public double calcularPorcentaje() {
		int contador=0;
		for(int i=0;i<llistaTirades.size();i++) {
			if(llistaTirades.get(i).isGuanyar()) {
				contador+=1;
			}
		}
		double porcentaje=pasaAPorcentaje(llistaTirades.size(),contador);
		return porcentaje;
	}
	
	public double pasaAPorcentaje(int total, int totalTrue) {
		double porcentaje=(totalTrue*100)/total;
		return porcentaje;
	}
}
