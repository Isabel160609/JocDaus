package com.init.JocDausS.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.init.JocDausS.document.Tirada;



public class JogadorModel {

	

	private int id;
	
	private String nom;
	
	private java.util.Date data;
	
	private List<TiradaModel>llistaTirades;
	
	private Double porcentatgeExit;

	public JogadorModel() {
	}

	
	public JogadorModel(int id_jogador, String nom, java.util.Date data) {
	this.id = id_jogador;
	this.nom = nom;
	this.data = data;
}
	public JogadorModel(int id_jogador, String nom, Date data, List<TiradaModel> llistaTirades, Double porcentatgeExit) {
		this.id = id_jogador;
		this.nom = nom;
		this.data = data;
		this.llistaTirades = llistaTirades;
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

	public List<TiradaModel> getTirades() {
		return llistaTirades;
	}

	public void setTirades(List<TiradaModel> tirades) {
		this.llistaTirades = tirades;
	}
	
	
	
	public Double getPorcentatgeExit() {
		return porcentatgeExit;
	}


	public void setPorcentatgeExit(Double porcentatgeExit) {
		this.porcentatgeExit = porcentatgeExit;
	}


	public void addTirada(TiradaModel laTirada) {
		
		if(llistaTirades==null) llistaTirades=new ArrayList<>();
				
		llistaTirades.add(laTirada);
				
		laTirada.setId_jogador(this.id);
				
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

