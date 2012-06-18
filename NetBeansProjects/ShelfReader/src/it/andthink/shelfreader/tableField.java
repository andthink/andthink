package it.andthink.shelfreader;


import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author meranr
 */
public class tableField {

	private String tabella;
	private ArrayList<CampoShelf> campi;

	public tableField() {
		tabella = new String();
		campi = new ArrayList();
	}

	/**
	 * @return the tabella
	 */
	public String getTabella() {
		return tabella;
	}

	/**
	 * @param tabella the tabella to set
	 */
	public void setTabella(String tabella) {
		this.tabella = tabella;
	}

	/**
	 * @return the campo
	 */
	public ArrayList getCampi() {
		return campi;
	}

	/**
	 * @param campo the campo to set
	 */
	public void addCampo(String campo) {
		CampoShelf c = new CampoShelf(campo);
		this.campi.add(c);
	}
	
	/**
	 * @param campo the campo to set
	 */
	public void addCampo(String campo,String descrizione) {
		CampoShelf c = new CampoShelf(campo, descrizione);
		this.campi.add(c);
	}
	
	
	public void addCampo(CampoShelf campo) {
		this.campi.add(campo);
	}
	
	

	public String print() {
		//if("h1ra_entigestsepr".equals(tabella)){
			String a;
			a = tabella+"\n";
			System.out.println(tabella);
			Iterator<CampoShelf> itr = campi.iterator();
			while (itr.hasNext()) {
				CampoShelf element = itr.next();
				a += element.print();
			}
			a+="\n";
		//}
		return a;	
	}
}
