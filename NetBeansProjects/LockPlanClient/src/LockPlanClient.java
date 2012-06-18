
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe per la gestioen della comunicazione con il server
 *
 * @author SCOCRI
 */
public class LockPlanClient {

	private static final String host = "RUSSIO-DT2";

	/**
	 * Dopo essersi autenticato con il server gli invia un comando e ne ritorna
	 * la risposta
	 *
	 * @param cmd Comando da eseguire
	 *
	 * @return Risposta del server
	 */
	public static String[] askServer(String cmd) {
		try {
			Socket LPSocket = new Socket(host, 4444);
			PrintWriter out = new PrintWriter(LPSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(LPSocket.getInputStream()));

			String fromServer;

			fromServer = in.readLine();
			if (fromServer.equals("CKU")) {
				out.println(InetAddress.getLocalHost().getHostName());
			}

			out.println(cmd);
			fromServer = in.readLine();

			out.close();
			in.close();
			LPSocket.close();

			return fromServer.split("-");
		} catch (UnknownHostException e) {
			String[] answer = {"0", "Host non riconosciuto: " + host + "."};
			return answer;
		} catch (IOException ex) {
			String[] answer = {"0", "Impossibile connettersi a " + host};
			return answer;
		}
	}
}