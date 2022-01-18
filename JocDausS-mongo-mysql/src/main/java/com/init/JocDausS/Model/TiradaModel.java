package com.init.JocDausS.Model;



public class TiradaModel {

	
	private String id;

	private int valorTirada1;

	private int valorTirada2;

	private boolean guanyar;
	
	int idjogador;

	public TiradaModel () {
	}

	public TiradaModel (String id,int id_jogador) {
		this.id=id;
		this.valorTirada1 = devuelveRandomEntreUnoYseis();
		this.valorTirada2 = devuelveRandomEntreUnoYseis();
		this.guanyar = calculateResult();
		this.idjogador = id_jogador;
	}

	public TiradaModel (int id_jogador) {
		
		this.valorTirada1 = devuelveRandomEntreUnoYseis();
		this.valorTirada2 = devuelveRandomEntreUnoYseis();
		this.guanyar = calculateResult();
		this.idjogador = id_jogador;
	}

	public TiradaModel (String id, int valorTirada1, int valorTirada2, boolean guanyar, int id_jogador) {
		this.id = id;
		this.valorTirada1 = valorTirada1;
		this.valorTirada2 = valorTirada2;
		this.guanyar = guanyar;
		this.idjogador = id_jogador;
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


	public int getId_jogador() {
		return idjogador;
	}

	public void setId_jogador(int id_jogador) {
		this.idjogador = id_jogador;
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

