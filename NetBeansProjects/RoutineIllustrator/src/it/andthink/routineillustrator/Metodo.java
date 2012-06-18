/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.andthink.routineillustrator;

/**
 *
 * @author meranr
 */
public class Metodo {
	private String nome;
	private String commento;

	Metodo(String funzione) {
		setNome(funzione);
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the commento
	 */
	public String getCommento() {
		return commento;
	}

	/**
	 * @param commento the commento to set
	 */
	public void setCommento(String commento) {
		this.commento = commento;
	}
}
