package it.andthink.fastclipper;

import it.andthink.utils.Clipboard;
import it.andthink.utils.Open;
import java.awt.*;
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
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe per la gestione della SystemTray del server
 *
 * @author SCOCRI
 */
public class FastClipperSystemTray {

	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private Image imgClip;
	private JPopupMenu popupMenu;
	private File FoundFile = null;
	private Open OpenUtil = new Open();
	private String WorkingFile = null;
	
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
				trayIcon = new TrayIcon(imgClip, "FastClipper" + (WorkingFile==null?"":WorkingFile) , null);
				
				trayIcon.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						if (e.isPopupTrigger()) {
							popupMenu.setLocation(e.getX(), e.getY()-120);
							popupMenu.setInvoker(popupMenu);
							popupMenu.setVisible(true);
						}
					}
				});

				systemTray.add(trayIcon);
				trayIcon.addMouseListener(new SystemTrayMouseListener());
			}
		} catch (AWTException ex) {
			System.out.println(ex.getMessage());
		}


	}

	private void searchFile(File dir, String pattern) {
		if (FoundFile == null) {
			if (dir.isDirectory()) {

				String[] children = dir.list();
				for (int i = 0; i < children.length; i++) {
					searchFile(new File(dir, children[i]), pattern);
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
	private JPopupMenu createPopupMenu() {
		JPopupMenu p = new JPopupMenu();

		JMenuItem WorkingFileItem = new JMenuItem("Set working file");
		JMenuItem SearchAndOpen = new JMenuItem("Search and open");
		JMenuItem OpenEnumeration = new JMenuItem("Open Enumeration");
		java.net.URL imgURL = getClass().getResource("Goku.png");
		Image vqrIco = Toolkit.getDefaultToolkit().getImage(imgURL);
		JMenuItem VisualQueryExplorer = new JMenuItem("Visual query explorer", new ImageIcon(vqrIco));
		JMenuItem about = new JMenuItem("About");
		JMenuItem esci = new JMenuItem("Quit");

		//Azione per il click su SearchAndOpen
		SearchAndOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String text = parseClipboard();

				File path;
				FoundFile = null;
				boolean isVqr = false;
				if (text.endsWith(".vqr")) {
					path = new File("C:/Sviluppo/PagoWEB/pago/exe");
					isVqr = true;
				} else {
					path = new File("C:/Sviluppo/PagoWEB/pago/");
				}

				searchFile(path, text);

				if (FoundFile != null) {
					if (isVqr) {
						Open.launchVQInBrowser(FoundFile.getName());
					} else {
						trayIcon.displayMessage("Apertura in corso", FoundFile.getName(), TrayIcon.MessageType.INFO);
						Open.Edit(FoundFile);
					}
				}else{
					trayIcon.displayMessage("Nessun file trovato", "Nessun file trovato", TrayIcon.MessageType.WARNING);
				}
			}
		});
		
		//Azione per il click su SearchAndOpen
		VisualQueryExplorer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String text = parseClipboard();
			
				if (text.endsWith(".vqr")) {
					
					String cmd = "java -jar" + " \"C:\\Users\\meranr\\andthink\\NetBeansProjects\\VisualQueryExplorer\\dist\\VisualQueryExplorer.jar\" " + text;
					trayIcon.displayMessage("Apertura vqr", cmd, TrayIcon.MessageType.INFO);					
					try {
						Runtime.getRuntime().exec(cmd);
						
					} catch (IOException ex) {
						Logger.getLogger(FastClipperSystemTray.class.getName()).log(Level.SEVERE, null, ex);
					}
				} else{
					trayIcon.displayMessage("Nessun file trovato", "Nessuna vqr corrispondente", TrayIcon.MessageType.WARNING);
				}
				
			}
		});
		
		
		
		
		//Azione per il click su SearchAndOpen
		OpenEnumeration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = parseClipboard();
				
				text = text.replace(".", "/");
				File path = new File("C:/Sviluppo/PagoJ/src/" + text +".java");
				if(path.exists()){
					Open.Edit(path);
				}else{
					trayIcon.displayMessage("Nessun file trovato", "Il percorso cercato non è un enumerato", TrayIcon.MessageType.INFO);
				}
			}
		});
		
		
		//Azione per il click su About

		WorkingFileItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = parseClipboard();
				File path = new File("C:/Sviluppo/PagoWEB/pago/Entity");
				searchFile(path, text);
				if(FoundFile!=null){
					setWorkingFile(text);
					trayIcon.displayMessage("File di lavoro impostato", text, TrayIcon.MessageType.INFO);
					trayIcon.setToolTip("FastClipper: " + text);
				}else{
					trayIcon.displayMessage("File di lavoro non valido", text, TrayIcon.MessageType.WARNING);
					trayIcon.setToolTip("FastClipper");
				}
			}
		});
		


		//Azione per il click su About

		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AboutFrame frame = new AboutFrame("Per correre più veloci...");

				frame.getClipboardArea().setText(parseClipboard());
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
		p.add(WorkingFileItem);
		p.addSeparator();
		p.add(SearchAndOpen);
		p.add(OpenEnumeration);
		p.add(VisualQueryExplorer);
		p.addSeparator();
		p.add(about);
		p.addSeparator();
		p.add(esci);
		return p;
	}
	
	private String parseClipboard(){
		
		String clip = Clipboard.getClipboard();
		String[] split = clip.split("[\r]*\n");
		
		if("* --- CodePainter Revolution Batch Cut Vers. 1.0".equals(split[0])){
			int i=1;
			for (int j = 0; j < split.length; j++) {
				if(split[j].startsWith("frm_seek='") && split[j].endsWith(".MCRDef")){
					clip = split[j].replace("frm_seek='", "");
					break;
				}
				if(split[j].startsWith("sel_name=")){
					clip = split[j].replace("sel_name='", "")+".vqr";
					break;
				}
			}
		}
		
		System.out.println(clip);
	
		return clip;
	}

	/**
	 * @return the WorkingFileItem
	 */
	public String getWorkingFile() {
		return WorkingFile;
	}

	/**
	 * @param WorkingFileItem the WorkingFileItem to set
	 */
	public void setWorkingFile(String WorkingFile) {
		this.WorkingFile = WorkingFile.trim();
	}

	/**
	 * Aggiunge l'evento alle pressione dell'icona
	 */
	private class SystemTrayMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == 1) {

				//Pulsante sinistro del mouse crea file dai contenuti clipboard
				String text = parseClipboard();

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
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(FastClipperSystemTray.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
			/*
			 * try { for (javax.swing.UIManager.LookAndFeelInfo info :
			 * javax.swing.UIManager.getInstalledLookAndFeels()) { if ("Nimbus".equals(info.getName())) {
			 * javax.swing.UIManager.setLookAndFeel(info.getClassName()); break; } } } catch (
			 * ClassNotFoundException | InstantiationException | IllegalAccessException |
			 * javax.swing.UnsupportedLookAndFeelException exc) {
			 * java.util.logging.Logger.getLogger(ShelfFrame.class.getName()).log(java.util.logging.Level.SEVERE,
			 * null, exc);
			}
			 */
		}
		new FastClipperSystemTray();
	}
}