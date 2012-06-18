package it.andthink.shelfreader;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LockPlanSystemTray {

	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private PopupMenu popupMenu;

	/**
	 * Istanzio il programma nella systemTray
	 */
	public LockPlanSystemTray() {
		try {
			if (SystemTray.isSupported()) {
				popupMenu = this.createPopupMenu();
				systemTray = SystemTray.getSystemTray();

				java.net.URL imgURL = getClass().getResource("lion.png");
				Image img = Toolkit.getDefaultToolkit().getImage(imgURL);
				trayIcon = new TrayIcon(img, "LockPlan", popupMenu);

				systemTray.add(trayIcon);
				trayIcon.addMouseListener(new SystemTrayMouseListener());
			}
		} catch (AWTException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Traduco il messaggio di stato del server
	 *
	 * @param String Messaggio di stato del server
	 *
	 * @return Messaggio tradotto
	 */
	private TrayIcon.MessageType translateType(String type){
		TrayIcon.MessageType cmd;

		switch(type){
			case "0":
				cmd = TrayIcon.MessageType.ERROR;
			break;
			case "1":
				cmd = TrayIcon.MessageType.WARNING;
			break;
			default:
				cmd = TrayIcon.MessageType.INFO;
			break;

		}
		return cmd;
	}

	/**
	 * Creo il menù contestuale della systemTray
	 *
	 * @return
	 */
	private PopupMenu createPopupMenu() {
		PopupMenu p = new PopupMenu();
		MenuItem lock = new MenuItem("Lock");
		MenuItem unlock = new MenuItem("Unlock");
		MenuItem who = new MenuItem("Who");
		MenuItem esci = new MenuItem("Quit");

		lock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] risp = new String[2];
				try {
					//Azione per il click su Lock
					risp = LockPlanClient.askServer("1");
				} catch (IOException ex) {
					Logger.getLogger(LockPlanSystemTray.class.getName()).log(Level.SEVERE, null, ex);
				}

				System.out.println(risp[0] + "-" + risp[1]);
				trayIcon.displayMessage("Operazione compeltata", risp[1], translateType(risp[0]));
			}
		});

		unlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] risp = new String[2];
				try {
					//Azione per il click su Unlock
					risp = LockPlanClient.askServer("2");
				} catch (IOException ex) {
					Logger.getLogger(LockPlanSystemTray.class.getName()).log(Level.SEVERE, null, ex);
				}

				trayIcon.displayMessage("Operazione compeltata", risp[1], translateType(risp[0]));
			}
		});

		who.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] risp = new String[2];
				try {
					//Azione per il click su Who
					risp = LockPlanClient.askServer("3");
				} catch (IOException ex) {
					Logger.getLogger(LockPlanSystemTray.class.getName()).log(Level.SEVERE, null, ex);
				}

				trayIcon.displayMessage("Operazione compeltata", risp[1], translateType(risp[0]));
			}
		});

		esci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Azione per il click su Esci
				SystemTray.getSystemTray().remove(trayIcon);
				System.exit(0);
			}
		});

		p.add(lock);
		p.add(unlock);
		p.add(who);
		p.addSeparator();
		p.add(esci);
		return p;
	}

	/**
	 * Aggiungo l'evento alal pressione dell'icona
	 */
	private class SystemTrayMouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == 1) {
				//Pulsante sinistro del mouse mostra Stato
				String[] risp = new String[2];
				try {
					//Azione per il click su Who
					risp = LockPlanClient.askServer("3");
				} catch (IOException ex) {
					Logger.getLogger(LockPlanSystemTray.class.getName()).log(Level.SEVERE, null, ex);
				}

				trayIcon.displayMessage("Operazione compeltata", risp[1], translateType(risp[0]));
			}
		}
	}

	public static void main(String[] args) throws IOException {

		/*
		 * Use an appropriate Look and Feel
		 */
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException |
				IllegalAccessException |
				InstantiationException |
				ClassNotFoundException ex) {
		}

		LockPlanSystemTray demo = new LockPlanSystemTray();
	}
}