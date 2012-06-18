package it.andthink.shelfreader;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class LockPlanClient {
    public static String[] askServer(String cmd) throws IOException {

        Socket LPSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
		String host = "SCOCRI-DT";

        try {
            LPSocket = new Socket(host, 4444);
            out = new PrintWriter(LPSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(LPSocket.getInputStream()));
        } catch (UnknownHostException e) {
			String[] answer = {"0", "Host non riconosciuto: " + host + "."};
			return answer;
        } catch (IOException e) {
            String[] answer = {"0", "Impossibile reperire i canali di I/O per la connessione verso " + host + "."};
			return answer;
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;

        fromServer = in.readLine();
		if (fromServer.equals("CKU")){
			out.println(InetAddress.getLocalHost().getHostName());
		}

		out.println(cmd);
		fromServer = in.readLine();

        out.close();
        in.close();
        stdIn.close();
        LPSocket.close();

		return fromServer.split("-");
    }
}