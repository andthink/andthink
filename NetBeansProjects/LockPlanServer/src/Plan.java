
import java.io.*;

/**
 * Classe per la gestione del plan
 *
 * @author SCOCRI
 */
public class Plan {

	private final String path = "lock.txt";
	private String user = "";
	
	//Possibili valori di ritorno della funzione WHO
	public static final String WHO_ERROR = "1";
	public static final String WHO_PLAN_FREE = "";
	
	//Possibili valori di ritorno della funzione LOCK
	public static final int LOCK_OK = 1;
	public static final int LOCK_ALREADY_EDIT_BY_YOU = 2;
	public static final int LOCK_ALREADY_EDIT_BY_ANOTHER = 3;
	public static final int LOCK_ERROR = 4;
	
	//Possibili valori di ritorno della funzione UNLOCK
	public static final int UNLOCK_OK = 1;
	public static final int UNLOCK_EDIT_BY_ANOTHER = 2;
	public static final int UNLOCK_ERROR = 3;
	public static final int UNLOCK_PLAN_FREE = 4;

	/**
	 * Costruttore
	 *
	 * @param user Utente che richiede interazione con il plan
	 */
	public Plan(String user) {
		this.user = user;
	}

	/**
	 * Controlla chi sta modificando il plan
	 *
	 * @return
	 */
	public String who() {
		String lock = "";
		char[] in = new char[9];

		try {
			File file = new File(path);

			if (file.exists()) {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				int size = br.read(in);

				for (int i = 0; i < size; i++) {
					lock += in[i];
				}

				br.close();
			}
		} catch (IOException e) {
			//Errore interno
			return WHO_ERROR;
		}

		//Utente che blocca il plan
		return lock;
	}

	/**
	 * Blocca il plan se non bliccato da altri
	 *
	 * @return
	 */
	public int lock() {
		if (this.who().equals(user)) {
			//File già in editing
			return LOCK_ALREADY_EDIT_BY_YOU;
		} else if (this.who().equals("")) {
			try {
				File file = new File(path);
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(user);
				bw.flush();
				bw.close();

				//File messo in editing
				return LOCK_OK;
			} catch (IOException e) {
				//Errore
				return LOCK_ERROR;
			}
		}

		//File in editing da altri
		return LOCK_ALREADY_EDIT_BY_ANOTHER;
	}

	/**
	 * Sblocca il plan se bloccato dall'utente che ne richiede lo sblocco
	 *
	 * @return
	 */
	public int unlock() {
		if (this.who().equals(user)) {
			try {
				File file = new File(path);
				file.delete();

				//Plan sbloccato
				return UNLOCK_OK;
			} catch (Exception e) {
				//Errore interno
				return UNLOCK_ERROR;
			}
		} else if (this.who().equals("")) {
			return UNLOCK_PLAN_FREE;
		}

		//Plan bloccato da un altro utente o libero
		return UNLOCK_EDIT_BY_ANOTHER;
	}
}
