
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * Classe per la creazione dell'about
 *
 * @author SCOCRI
 */
public class OldLockPlan extends JPanel {

	private Image img;

	/**
	 * Costruttore della classe
	 */
	public OldLockPlan() {
		java.net.URL imgURL = getClass().getResource("OldLockPlan.png");
		img = Toolkit.getDefaultToolkit().getImage(imgURL);
		loadImage(img);
	}

	/**
	 * Carica l'immagine da mettere di sfondo
	 * 
	 * @param img Immagine di sfondo
	 */
	private void loadImage(Image img) {
		try {
			MediaTracker track = new MediaTracker(this);
			track.addImage(img, 0);
			track.waitForID(0);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Disegna l'immagine
	 * 
	 * @param g Componente grafico
	 */
	@Override
	protected void paintComponent(Graphics g) {
		setOpaque(false);
		g.drawImage(img, 0, 0, null);
		super.paintComponent(g);
	}
}