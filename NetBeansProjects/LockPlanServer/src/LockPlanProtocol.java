
/**
 * Classe di definizione del protocoollo di comunicazione CLIENT-SERVER
 *
 * @author SCOCRI
 */
public class LockPlanProtocol {

	//Comandi Inviati dal Client
	private final String PING = "0";
	private final String LOCK = "1";
	private final String UNLOCK = "2";
	private final String WHO = "3";
	private final String CKU = "CKU";
	
	//Stato finale della richiesta
	private final String OK = "2";
	private final String WARNING = "1";
	private final String ERROR = "0";

	/**
	 * Processa il comando in input dal Client 
	 * 
	 * @param state Comando ricevuto dal client
	 * @param user Utente che vuole eseguire il comando
	 * 
	 * @return Risposta del sever al comando del Client
	 */
	public synchronized String processInput(String state, String user) {
		String theOutput = "";
		Plan pl = new Plan(user);

		switch (state) {
			case LOCK:
				switch (pl.lock()) {
					case Plan.LOCK_OK:
						theOutput = OK + "-Hai bloccato il plan";
						break;
					case Plan.LOCK_ALREADY_EDIT_BY_YOU:
						theOutput = OK + "-Stai già bloccando il plan";
						break;
					case Plan.LOCK_ALREADY_EDIT_BY_ANOTHER:
						theOutput = WARNING + "-Il plan è già bloccato da " + pl.who();
						break;
					case Plan.LOCK_ERROR:
						theOutput = ERROR + "-Errore interno del server";
						break;
				}
				break;
			case UNLOCK:
				switch (pl.unlock()) {
					case Plan.UNLOCK_OK:
						theOutput = OK + "-Hai sbloccato il plan";
						break;
					case Plan.UNLOCK_EDIT_BY_ANOTHER:
						theOutput = WARNING + "-Il plan è bloccato da " + pl.who();
						break;
					case Plan.UNLOCK_PLAN_FREE:
						theOutput = WARNING + "-Non stai bloccando il plan";
						break;
					case Plan.UNLOCK_ERROR:
						theOutput = ERROR + "-Errore interno del server";
						break;
				}

				break;
			case WHO:
				switch (pl.who()) {
					case Plan.WHO_PLAN_FREE:
						theOutput = OK + "-Nessuno sta bloccando il plan";
						break;
					case Plan.WHO_ERROR:
						theOutput = ERROR + "-Errore interno del server";
						break;
					default:
						theOutput = WARNING + "-Il plan è bloccato da " + pl.who();
						break;
				}
				break;
			case PING:
				theOutput = OK + "-Server disponibile";
				break;
			case CKU:
				theOutput = CKU;
				break;
			default:
				theOutput = ERROR + "-Comando non riconosciuto";
				break;
		}

		return theOutput;
	}
}
