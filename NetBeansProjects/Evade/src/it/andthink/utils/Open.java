package it.andthink.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author meranr
 */
public class Open {

	public static void Edit(File file) {

		try {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				String cmd = "rundll32 url.dll,FileProtocolHandler " + file.getCanonicalPath();

				Runtime.getRuntime().exec(cmd);
			} else {
				Desktop.getDesktop().edit(file);
			}
		} catch (IOException ex) {
		}

	}

	public static void launchVQInBrowser(String visQue) {
		String url = "http://localhost:8080/PagoWEB/visualquery/index.jsp?filename=" + visQue;
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();

		try {
			if (os.indexOf("win") >= 0) {

				// this doesn't support showing urls in the form of "page.html#nameLink"
				rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

			} else if (os.indexOf("mac") >= 0) {

				rt.exec("open " + url);

			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

				// Do a best guess on unix until we get a platform independent way
				// Build a list of browsers to try, in this order.
				String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
					"netscape", "opera", "links", "lynx"};

				// Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
				StringBuilder cmd = new StringBuilder();
				for (int i = 0; i < browsers.length; i++) {
					cmd.append(i == 0 ? "" : " || ").append(browsers[i]).append(" \"").append(url).append("\" ");
				}

				rt.exec(new String[]{"sh", "-c", cmd.toString()});
			}
		} catch (Exception ex) {
		}
	}
}
