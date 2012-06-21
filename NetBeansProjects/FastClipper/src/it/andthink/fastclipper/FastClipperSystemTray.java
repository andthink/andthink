package it.andthink.fastclipper;

import it.andthink.utils.Open;
import it.andthink.utils.Clipboard;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe per la gestione della SystemTray del server
 *
 * @author SCOCRI
 */
public class FastClipperSystemTray {

	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private Image imgClip;
	private PopupMenu popupMenu;
	private File FoundFile = null;
	private Open OpenUtil = new Open();
	private Clipboard clipboard = new Clipboard();

	/**
	 * Istanzia il programma nella systemTray
	 */
	public FastClipperSystemTray() {
		try {
			if (SystemTray.isSupported()) {
				popupMenu = this.createPopupMenu();
				systemTray = SystemTray.getSystemTray();

				java.net.URL imgURL = getClass().getResource("clipboard.png");
				imgClip = Toolkit.getDefaultToolkit().getImage(imgURL);
				trayIcon = new TrayIcon(imgClip, "FastClipper", popupMenu);

				systemTray.add(trayIcon);
				trayIcon.addMouseListener(new SystemTrayMouseListener());
			}
		} catch (AWTException ex) {
			System.out.println(ex.getMessage());
		}


	}

	private void searchAndOpen(File dir, String pattern) {
		if (FoundFile == null) {
			if (dir.isDirectory()) {

				String[] children = dir.list();
				for (int i = 0; i < children.length; i++) {
					searchAndOpen(new File(dir, children[i]), pattern);
				}
			} else {

				if (dir.getName().indexOf(pattern) != -1) {
					System.out.println(dir.getName());
					FoundFile = dir;
				}
			}
		}


	}

	/**
	 * Crea il menù contestuale della systemTray
	 *
	 * @return
	 */
	private PopupMenu createPopupMenu() {
		PopupMenu p = new PopupMenu();

		MenuItem SearchAndOpen = new MenuItem("Search and open");
		MenuItem about = new MenuItem("About");
		MenuItem esci = new MenuItem("Quit");

		//Azione per il click su SearchAndOpen
		SearchAndOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String text = clipboard.getClipboard();

				File path = null;
				boolean isVqr = false;
				if (text.endsWith(".vqr")) {
					path = new File("C:/Sviluppo/PagoWEB/pago/exe");
					isVqr = true;
				} else {
					path = new File("C:/Sviluppo/PagoWEB/pago/");
				}

				searchAndOpen(path, text);

				if (FoundFile != null) {
					if (isVqr) {
						OpenUtil.launchVQInBrowser(FoundFile.getName());
					} else {
						OpenUtil.Edit(FoundFile);
					}
				}
			}
		});


		//Azione per il click su About

		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AboutFrame frame = new AboutFrame("Per correre più veloci...");

				frame.getClipboardArea().setText(clipboard.getClipboard());
				frame.setVisible(true);
			}
		});


		//Azione per il click su Esci
		esci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SystemTray.getSystemTray().remove(trayIcon);
				System.exit(0);
			}
		});

		//Aggiungo gli elemento al PopupMenu

		p.add(SearchAndOpen);
		p.addSeparator();
		p.add(about);
		p.addSeparator();
		p.add(esci);
		return p;
	}

	/**
	 * Aggiunge l'evento alle pressione dell'icona
	 */
	private class SystemTrayMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == 1) {

				//Pulsante sinistro del mouse crea file dai contenuti clipboard
				String text = clipboard.getClipboard();

				try {
					// Create temp file.
					File temp = File.createTempFile("pattern", ".txt");

					// Delete temp file when program exits.
					temp.deleteOnExit();
					try (BufferedWriter out = new BufferedWriter(new FileWriter(temp))) {
						out.write(text);
					}
					if (temp != null) {
						OpenUtil.Edit(temp);
					}

				} catch (IOException exc) {
				}
				//	trayIcon.displayMessage("Who", whois.substring(2), TrayIcon.MessageType.INFO);
			}
		}
	}

	/**
	 * MAIN
	 */
	public static void main(String[] args) {
		FastClipperSystemTray LPST = new FastClipperSystemTray();
	}
}