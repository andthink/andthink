/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cleansp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Classe per la pulizia dei file di project
 *
 * @author SCOCRI
 */
public class CleanSP {

	private String dir;
	private ArrayList pattern;
	private ArrayList FileFound;

	/**
	 * Costruttore della classe
	 *
	 * @param dir Directory da analizzare
	 * @param pattern Pattern da cercare
	 */
	public CleanSP(String dir, ArrayList pattern){
		this.dir = dir;
		this.pattern = pattern;

		this.FileFound = new ArrayList();
	}

	public void setDir(String dir){
		this.dir = dir;
	}

	public String getDir(){
		return this.dir;
	}

	public void setPattern(ArrayList pattern){
		this.pattern = pattern;
	}

	public ArrayList getPattern(){
		return this.pattern;
	}

	public void setFileFound(ArrayList FileFound){
		this.FileFound = FileFound;
	}

	public ArrayList getFileFound(){
		return this.FileFound;
	}

	/**
	 * Esegue la pulizia
	 */
	public void processClean(){
		this.visitAllFiles(new File(this.dir));

		//this.FileFound = new ArrayList(new HashSet(this.FileFound));
	}

	/**
	 * Controllo che in tutti i file della cartella e delel sue sottocartelle ci
	 * siano file che contengono i pattern e li elimino
	 *
	 * @param dir Directory da analizzare
	 */
	private void visitAllFiles(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for(int i = 0; i < children.length; i++){
				visitAllFiles(new File(dir, children[i]));
			}
		} else {
			for(int j = 0; j < this.pattern.size(); j++){
				String singlePattern = this.pattern.get(j).toString();

				if(dir.getName().indexOf(singlePattern) != -1){
					this.FileFound.add(dir.getAbsoluteFile().getAbsolutePath());
					break;
				}
			}
		}
	}
}