package it.andthink.shelfreader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author meranr
 */
public class CampoShelf {

	private String nome;
	private String descrizione;
	private String lunghezza;
	private String decimali="";

	public String getDecimali() {
		return decimali;
	}

	public void setDecimali(String decimali) {
		this.decimali = decimali;
	}
	private boolean key;
	private String type;

	public CampoShelf(String nome, String descrizione) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.key = false;
	}

	public CampoShelf(String nome) {
		this.nome = nome;
		this.descrizione = "";
		this.key = false;
		this.type = "";
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
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String print() {
		
			return nome+" "+descrizione + ((key)?" key: true":"")+" tipo: " + type+" len: " + lunghezza+"\n";
			/*System.out.print(nome);
			System.out.print(" " + descrizione);
			if (key) {
				System.out.print(" key: true");
			}
			System.out.print(" tipo: " + type);
			System.out.print(" len: " + Integer.toString(lunghezza));

			System.out.println();*/
	}

	/**
	 * @return the lunghezza
	 */
	public String getLunghezza() {
		return lunghezza;	
	}

	/**
	 * @param lunghezza the lunghezza to set
	 */
	public void setLunghezza(String lunghezza) {
		this.lunghezza = lunghezza;
	}

	/**
	 * @return the key
	 */
	public boolean isKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(boolean key) {
		this.key = key;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
