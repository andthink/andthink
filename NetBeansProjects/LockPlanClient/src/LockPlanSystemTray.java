
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Classe per la gestione della SystemTray del server
 *
 * @author SCOCRI
 */
public class LockPlanSystemTray {

	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private Image imgLockPlan;
	private PopupMenu popupMenu;

	/**
	 * Istanzia il programma nella systemTray
	 */
	public LockPlanSystemTray() {
		try {
			if (SystemTray.isSupported()) {
				popupMenu = this.createPopupMenu();
				systemTray = SystemTray.getSystemTray();

				java.net.URL imgURL = getClass().getResource("lion.png");
				imgLockPlan = Toolkit.getDefaultToolkit().getImage(imgURL);
				trayIcon = new TrayIcon(imgLockPlan, "LockPlan 8.02", popupMenu);

				systemTray.add(trayIcon);
				trayIcon.addMouseListener(new SystemTrayMouseListener());
			} else {
				JOptionPane.showMessageDialog(null, "Il tuo sistema operativo non supporta il System Tray, richiedere la versione con JFrame",
						"Errore", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		} catch (AWTException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Traduce il tipo del messaggio di stato ricevuto dal server
	 *
	 * @param type Tipo del messaggio della richiesta inviata al server
	 *
	 * @return Tipo del messaggio tradotto
	 */
	private TrayIcon.MessageType translateType(String type) {
		TrayIcon.MessageType cmd;

		switch (type) {
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
	 * Crea il menù contestuale della systemTray
	 *
	 * @return PopupMenu Menu contestuale del SystemTray
	 */
	private PopupMenu createPopupMenu() {
		PopupMenu p = new PopupMenu();
		MenuItem lock = new MenuItem("Lock");
		MenuItem unlock = new MenuItem("Unlock");
		MenuItem who = new MenuItem("Who");
		MenuItem about = new MenuItem("About");
		MenuItem esci = new MenuItem("Quit");


		//Azione per il click su Lock
		lock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] risp = new String[2];
				try {
					risp = LockPlanClient.askServer("1");
				} catch (Exception ex) {
					Logger.getLogger(LockPlanSystemTray.class.getName()).log(Level.SEVERE, null, ex);
				}

				trayIcon.displayMessage("Lock", risp[1], translateType(risp[0]));
			}
		});

		//Azione per il click su Unlock
		unlock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] risp = new String[2];
				try {
					risp = LockPlanClient.askServer("2");
				} catch (Exception ex) {
					Logger.getLogger(LockPlanSystemTray.class.getName()).log(Level.SEVERE, null, ex);
				}

				trayIcon.displayMessage("Unlock", risp[1], translateType(risp[0]));
			}
		});

		//Azione per il click su Who
		who.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] risp = new String[2];
				try {
					risp = LockPlanClient.askServer("3");
				} catch (Exception ex) {
					Logger.getLogger(LockPlanSystemTray.class.getName()).log(Level.SEVERE, null, ex);
				}

				trayIcon.displayMessage("Who", risp[1], translateType(risp[0]));
			}
		});

		//Azione per il click su About
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Per non dimenticare...");
				OldLockPlan back = new OldLockPlan();
				frame.getContentPane().add(back);
				frame.setSize(343, 612);
				frame.setResizable(false);
				frame.setIconImage(imgLockPlan);

				//Centro il frame nello schermo
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Dimension size = frame.getSize();
				screenSize.height = screenSize.height / 2;
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
		p.add(lock);
		p.add(unlock);
		p.add(who);
		p.addSeparator();
		p.add(about);
		p.addSeparator();
		p.add(esci);
		return p;
	}

	/**
	 * Aggiunge l'evento alla pressione dell'icona
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
				} catch (Exception ex) {
					Logger.getLogger(LockPlanSystemTray.class.getName()).log(Level.SEVERE, null, ex);
				}

				trayIcon.displayMessage("Who", risp[1], translateType(risp[0]));
			}
		}
	}

	/**
	 * MAIN
	 */
	public static void main(String[] args){
		LockPlanSystemTray LPST = new LockPlanSystemTray();
	}
}