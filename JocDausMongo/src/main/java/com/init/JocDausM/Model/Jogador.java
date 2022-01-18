package com.init.JocDausM.Model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.NonNull;


@Document(collection="Jogador")
public class Jogador implements Serializable{

	
	@Id
	@NonNull
	private String id;
	
	@Field(name="nom")
	private String nom;
	
	@Field(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date data;
	
	
//	//@OneToMany(mappedBy="jogador", cascade = {CascadeType.ALL})
//	@JsonIgnore
//	private List<Tirada>llistaTirades;
	
	@Transient
	private Double porcentatgeExit;

	public Jogador() {
	}

	
	public Jogador(String id, String nom, java.util.Date data) {
	this.id = id;
	this.nom = nom;
	this.data = data;
}


	public String getId() {
		return id;
	}

	public void setId(String id) {
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

//	public List<Tirada> getTirades() {
//		return llistaTirades;
//	}
//
//	public void setTirades(List<Tirada> tirades) {
//		this.llistaTirades = tirades;
//	}
	
	
	
	public Double getPorcentatgeExit() {
		return porcentatgeExit;
	}


	public void setPorcentatgeExit(Double porcentatgeExit) {
		this.porcentatgeExit = porcentatgeExit;
	}

//
//	public void addTirada(Tirada laTirada) {
//		
//		if(llistaTirades==null) llistaTirades=new ArrayList<>();
//				
//		llistaTirades.add(laTirada);
//				
//		laTirada.setId_jogador(this.id);
//				
//				}
	
	
}
