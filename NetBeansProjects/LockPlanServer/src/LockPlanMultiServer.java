
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * classe per la gestione delle richieste dei Client
 *
 * @author SCOCRI
 */
public class LockPlanMultiServer {

	public ServerSocket serverSocket = null;

	public LockPlanMultiServer() {
		boolean listening = true;
		try {
			serverSocket = new ServerSocket(4444);

			while (listening) {
				Socket sa = serverSocket.accept();
				launchThread(sa, false);
			}
		} catch (IOException e) {
			System.err.println("Porta 4444 occupata: impossibile mettersi in ascolto.");
			System.exit(-1);
		}
	}

	private void launchThread(Socket sa, boolean wantLog) {
		new LockPlanMultiServerThread(sa, wantLog).start();
	}
}
