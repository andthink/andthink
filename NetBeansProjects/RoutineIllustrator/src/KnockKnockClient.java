import java.io.*;
import java.net.*;

public class KnockKnockClient {
    public static void main(String[] args) throws IOException {

        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
		String host = "SCOCRI-DT";

        try {
            kkSocket = new Socket(host, 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host non riconosciuto: " + host + ".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host + ".");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        while ((fromServer = in.readLine()) != null) {
            if (fromServer.equals("0")){
				break;
			}

			if (fromServer.equals("CKU")){
				out.println(InetAddress.getLocalHost().getHostName());
				continue;
			}

            System.out.println("Server: " + fromServer);

            fromUser = stdIn.readLine();
			if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					out.println(fromUser);
			}
        }

        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
}