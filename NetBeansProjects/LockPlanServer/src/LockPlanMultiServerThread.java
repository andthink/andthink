
import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Classe per la gestione del Thread di esaustione della richiesta del Client
 *
 * @author SCOCRI
 */
public class LockPlanMultiServerThread extends Thread {

	private Socket socket = null;
	private boolean wantLog = false;

	/**
	 * Istanzio il Thread per risoluzione della richiesta del Client
	 *
	 * @param socket Socket di connessione verso il client
	 * @param wantLog TRUE per abilitare la generazione dei log, FALSE in caso
	 * contrario
	 */
	public LockPlanMultiServerThread(Socket socket, boolean wantLog) {
		super("LockPLanMultiServerThread");
		this.socket = socket;
		this.wantLog = wantLog;
	}

	@Override
	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String inputLine, outputLine;
			LockPlanProtocol LPp = new LockPlanProtocol();

			//Registro l'utente connesso
			outputLine = LPp.processInput("CKU", "");
			out.println(outputLine);
			String user = in.readLine();

			//Attendo le richieste dei comandi
			inputLine = in.readLine();
			outputLine = LPp.processInput(inputLine, user);
			out.println(outputLine);

			//Se richiesto registro il log
			if (wantLog) {
				regLog(inputLine, user);
			}

			//Chiudo i canali di comunicazione
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
		}
	}

	/**
	 * Registro in un file di log l'utente, il comando e la sue coordinate
	 * temporali di esecuzione
	 *
	 * @param cmd Comando ricevuto dal Client
	 * @param user Utente che ha invito il comando
	 *
	 * @return TRUE se la registrazione del log ha avuto successo, FALSE in caso
	 * contrario
	 */
	public boolean regLog(String cmd, String user) {
		GregorianCalendar gc = new GregorianCalendar();
		int anno = gc.get(Calendar.YEAR);
		int mese = gc.get(Calendar.MONTH) + 1;
		int giorno = gc.get(Calendar.DATE);
		int ore = gc.get(Calendar.HOUR);
		int min = gc.get(Calendar.MINUTE);
		int sec = gc.get(Calendar.SECOND);

		try {
			File file = new File(
				"log" + String.format("%04d", anno)
				+ String.format("%02d", mese)
				+ String.format("%02d", giorno)
				+ ".txt");
			
			FileWriter fw = new FileWriter(file, true);
			try (BufferedWriter bw = new BufferedWriter(fw)) {
				bw.write(
					String.format("%02d", ore)
					+ String.format("%02d", min)
					+ String.format("%02d", sec)
					+ " - " + cmd + " - " + user);
				bw.flush();
			}

			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
