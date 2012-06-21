package it.andthink.fastclipper;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
	private Clipboard clipbordella;
	private File FoundFile = null;

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

		//Azione per il click su Who

		SearchAndOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String text = getClipboard();

				File path = new File("C:/Sviluppo/PagoWEB/pago/");
				searchAndOpen(path, text);

				if (FoundFile != null) {
					Edit(FoundFile);
				}
			}
		});


		//Azione per il click su About

		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Per correre più veloci..."); 
				frame.setLayout(null);
				frame.setResizable(false);
				frame.setIconImage(imgClip);
				frame.setSize(613,433);
				JTextArea clipboardContent= new JTextArea(getClipboard());
				clipboardContent.setTabSize(1);
				JScrollPane jsp = new JScrollPane(clipboardContent);
				jsp.setBounds(3, 3, 600, 400);
				frame.add(jsp);
				
				//Centro il frame nello schermo 
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension size = frame.getSize();
				screenSize.height =	screenSize.height / 2;
				screenSize.width = screenSize.width / 2;
				size.height = size.height / 2;
				size.width = size.width / 2;
				int y = screenSize.height - size.height;
				int x = screenSize.width - size.width;
				frame.setLocation(x, y);

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

	private void Edit(File file) {
		System.out.println(file.getAbsolutePath());
		try {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				String cmd = "rundll32 url.dll,FileProtocolHandler " + file.getCanonicalPath();

				Runtime.getRuntime().exec(cmd);
			} else {
				Desktop.getDesktop().edit(file);
			}
		} catch (IOException ex) {
			Logger.getLogger(FastClipperSystemTray.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private String getClipboard() {

		Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		String text = "";

		try {
			if (content != null && content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				text = (String) content.getTransferData(DataFlavor.stringFlavor);
			}
		} catch (UnsupportedFlavorException | IOException excep) {
		}
		return text;
	}

	/**
	 * Aggiunge l'evento alle pressione dell'icona
	 */
	private class SystemTrayMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == 1) {


				//Pulsante sinistro del mouse crea file dai contenuti clipboard
				String text = getClipboard();

				try {
					// Create temp file.
					File temp = File.createTempFile("pattern", ".txt");

					// Delete temp file when program exits.
					temp.deleteOnExit();
					try (BufferedWriter out = new BufferedWriter(new FileWriter(temp))) {
						out.write(text);
					}
					if (temp != null) {
						Edit(temp);
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