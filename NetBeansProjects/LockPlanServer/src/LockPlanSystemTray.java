
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

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

				java.net.URL imgURL = getClass().getResource("server.png");
				imgLockPlan = Toolkit.getDefaultToolkit().getImage(imgURL);
				trayIcon = new TrayIcon(imgLockPlan, "LockPlan 8.02", popupMenu);

				systemTray.add(trayIcon);
				trayIcon.addMouseListener(new SystemTrayMouseListener());
			}
		} catch (AWTException ex) {
			System.out.println(ex.getMessage());
		}

		LockPlanMultiServer server = new LockPlanMultiServer();
	}

	/**
	 * Crea il menù contestuale della systemTray
	 *
	 * @return
	 */
	private PopupMenu createPopupMenu() {
		PopupMenu p = new PopupMenu();
		MenuItem who = new MenuItem("Who");
		MenuItem about = new MenuItem("About");
		MenuItem esci = new MenuItem("Quit");

		//Azione per il click su Who
		who.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LockPlanProtocol LPp = new LockPlanProtocol();

				//Richiedo l'utente connesso
				String[] whois = LPp.processInput("3", "").split("-");
				
				//se il plan è in editing da un utente ne estragog il nome
				if(whois[0].equals(Plan.WHO_PLAN_FREE) && whois[0].equals(Plan.WHO_ERROR)){
					whois[1] = whois[1].substring(0, 6);
				}

				trayIcon.displayMessage("Who", whois[1], TrayIcon.MessageType.INFO);
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
		p.add(who);
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
				//Pulsante sinistro del mouse mostra Stato
				LockPlanProtocol LPp = new LockPlanProtocol();

				//Richiedo l'utente connesso
				String whois = LPp.processInput("3", "");

				trayIcon.displayMessage("Who", whois.substring(2), TrayIcon.MessageType.INFO);
			}
		}
	}

	/**
	 * MAIN
	 */
	public static void main(String[] args) {
		LockPlanSystemTray LPST = new LockPlanSystemTray();
	}
}