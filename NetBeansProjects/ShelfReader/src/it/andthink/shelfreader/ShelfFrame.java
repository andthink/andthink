package it.andthink.shelfreader;


import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.*;

/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author meranr
 */
public class ShelfFrame extends javax.swing.JFrame {

	private ArrayList<tableField> listaTabelle = new ArrayList();
	//TrayIcon trayIcon;
	private javax.swing.table.DefaultTableModel model;
	private DefaultTableCellRenderer renderer;
	private ArrayList<Integer> selectRows = new ArrayList();
	//private int selectColumn =-1;

	/**
	 * Creates new form ShelfFrame
	 */
	public ShelfFrame(String[] args) {

		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ShelfFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
//		displayTray();
		initTableModel();
		initComponents();
		main(args);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
	 * code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        renderer = new DefaultTableCellRenderer();
        jTableResults = new JTable() {
  	public TableCellRenderer getCellRenderer(int row, int column) {
 
 		if ((selectRows.contains(row))) {
 			return renderer;
 		}
 // else...	
 		return super.getCellRenderer(row, column);
 	}
 };
        Image icon=null;
try{
	icon = ImageIO.read(this.getClass().getResource("/it/andthink/images/ShelfReader.png"));
}catch(Exception e){
}
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ShelfReader");
        setIconImage(icon);

        jTextSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSearchActionPerformed(evt);
            }
        });
        jTextSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextSearchKeyTyped(evt);
            }
        });

        jLabel1.setText("Nome tabella");

        jTableResults.setAutoCreateRowSorter(true);
        jTableResults.setForeground(new java.awt.Color(0, 102, 102));
        jTableResults.setModel(model);
        jScrollPane2.setViewportView(jTableResults);

        jButton1.setText("Filter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		executeFilter();
	}//GEN-LAST:event_jButton1ActionPerformed

	private void jTextSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextSearchKeyTyped
		/*
		 * int key = evt.getKeyCode(); if (key == KeyEvent.VK_ENTER) { executeFilter(); }	// TODO add your
		 * handling code here:
		 */
	}//GEN-LAST:event_jTextSearchKeyTyped

	private void jTextSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextSearchKeyReleased
		int key = evt.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			executeFilter();
		}
	}//GEN-LAST:event_jTextSearchKeyReleased

	private void jTextSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextSearchKeyPressed
		/*
		 * int key = evt.getKeyCode(); if (key == KeyEvent.VK_ENTER) { executeFilter(); }
		 */
	}//GEN-LAST:event_jTextSearchKeyPressed

	private void jTextSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSearchActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_jTextSearchActionPerformed

	private void initTableModel() {
		model = new DefaultTableModel();
		model.addColumn("Tabella");
		model.addColumn("Campo");
		model.addColumn("Descrizione");
		model.addColumn("Tipo");
		model.addColumn("Len.");
		model.addColumn("Dec.");
		model.addColumn("Key");
	}

	private void executeFilter() {
		model.setRowCount(0);
		selectRows.removeAll(selectRows);
		 
		
				

		try (BufferedReader in = new BufferedReader(new FileReader("C:/Sviluppo/PagoWEB/pago/pago.designdef.shelve"))) {
			String str;

			int foundTables = 0;
			int rows = 0;
			int currTable = 1;
			int section = 0;
			tableField tabella = new tableField();
			int indiceIndex;
			String descrizione;
			String nomeTabella;
			CampoShelf campo = null;
			boolean matching = false;
			boolean filtro = false;
			String testoFiltro = jTextSearch.getText();
			String testoCampo = "";
			int indicePunto = testoFiltro.indexOf(".");
			if(indicePunto >= 0){
				testoCampo = testoFiltro.substring(indicePunto+1);
				testoFiltro = testoFiltro.substring(0,indicePunto);
			}
			
			
			if (!testoFiltro.isEmpty()){
//				trayIcon.displayMessage("Filtering started","Filtro le tue tabelle per "+jTextSearch.getText(),TrayIcon.MessageType.INFO);
				filtro = true;
			}

			while ((str = in.readLine()) != null) {

				indiceIndex = str.indexOf("indexes=");
				if (indiceIndex >= 0) {
					section = 2;
					listaTabelle.add(tabella);

					tabella = new tableField();
					currTable++;
					indiceIndex = -1;
				}

				int indiceTabella = str.indexOf("TABLE.");
				if (indiceTabella >= 0) {
					section = 1;

					nomeTabella = str.substring(indiceTabella + 6);
					tabella.setTabella(nomeTabella);

					if (filtro) {
						
						matching = (nomeTabella.indexOf(testoFiltro) != -1);
						if(!("".equals(testoCampo))){
							matching = (nomeTabella.equals(testoFiltro));
						}
						foundTables++;
					} else {
						matching = true;
						foundTables++;
					}

				} else if (matching && foundTables > 0 && section == 1) {

					int indiceCampo = str.indexOf("comment=");
					if (indiceCampo >= 0 && indiceIndex == -1) {
						descrizione = str.substring(indiceCampo + 9);
						campo = new CampoShelf("", descrizione);

						model.insertRow(rows, new Object[]{tabella.getTabella()});
						rows++;
						model.setValueAt(descrizione, rows - 1, 2);
					}

					indiceCampo = str.indexOf("key=");
					if (indiceCampo >= 0 && indiceIndex == -1) {
						if (campo != null) {
							if ("1".equals(str.substring(indiceCampo + 4))) {
								campo.setKey(true);
								model.setValueAt("K", rows - 1, 6);
							}
						}
					}

					indiceCampo = str.indexOf("type=");
					if (indiceCampo >= 0 && indiceIndex == -1) {
						if (campo != null) {
							campo.setType(str.substring(indiceCampo + 6));
							model.setValueAt(campo.getType(), rows - 1, 3);
						}
					}

					indiceCampo = str.indexOf("len=");
					if (indiceCampo >= 0 && indiceIndex == -1) {
						if (campo != null) {
							String lung = str.substring(indiceCampo + 4);
							campo.setLunghezza(lung);
							model.setValueAt(campo.getLunghezza(), rows - 1, 4);
						}

					}
					
					indiceCampo = str.indexOf("dec=");
					if (indiceCampo >= 0 && indiceIndex == -1) {
						if (campo != null) {
							String dec = str.substring(indiceCampo + 4);
							campo.setDecimali(dec);
							model.setValueAt(campo.getDecimali(), rows - 1, 5);
						}

					}

					indiceCampo = str.indexOf("name=");
					if (indiceCampo >= 0 && indiceIndex == -1) {
						if (campo != null) {
							campo.setNome(str.substring(indiceCampo + 6));
							if(campo.getNome().indexOf(testoCampo) != -1 && !testoCampo.equals("")){
								selectRows.add(rows -1);
							}
							tabella.addCampo(campo);
							model.setValueAt(campo.getNome(), rows - 1, 1);
							model.setValueAt("C", rows - 1, 3);
							
						}
					}
				}
			}
			for (Iterator<tableField> it = listaTabelle.iterator(); it.hasNext();) {
				tableField field = it.next();
				//System.out.println(field.print());
			}
		} catch (IOException e) {
		}
		setVisible(true);
		//jTableResults.setModel(model);
		renderer.setBackground(Color.yellow);
		jTableResults.getCellRenderer(0, 0);
		
		jTableResults.repaint();

	//	jTableResults.updateUI();
	}

	/**
	 * @param args the command line arguments
	 */
	public final void main(String args[]) {

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				executeFilter();
				initTableAppereance();
				SiteAdapter siteAdapter = new SiteAdapter(jTableResults);
				
				
			}
		});
	}

	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableResults;
    private javax.swing.JTextField jTextSearch;
    // End of variables declaration//GEN-END:variables

	/**
	 * @return the listaTabelle
	 */
	public ArrayList getListaTabelle() {
		return listaTabelle;
	}

	/**
	 * @param listaTabelle the listaTabelle to set
	 */
	public void setListaTabelle(ArrayList listaTabelle) {
		this.listaTabelle = listaTabelle;
	}

	private void initTableAppereance() {
		int[] colsizes = {140, 160, 300, 30, 30, 30, 30};
		for (int i = 0; i < colsizes.length; i++) {
			jTableResults.getColumnModel().getColumn(i).setPreferredWidth(colsizes[i]);
		}
	}
	
	/*private void displayTray(){

		if (SystemTray.isSupported()) {

			SystemTray tray = SystemTray.getSystemTray();
			//C:/Users/meranr/Documents/NetBeansProjects/ShelfReader/src/

			Image image = this.getToolkit().getImage(getClass().getClassLoader().getResource("ShelfReader.png"));//Toolkit.getDefaultToolkit().getImage("ShelfReader.png");

			MouseListener mouseListener = new MouseListener() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					throw new UnsupportedOperationException("Not supported yet.");
				}

				@Override
				public void mousePressed(java.awt.event.MouseEvent e) {
					throw new UnsupportedOperationException("Not supported yet.");
				}

				@Override
				public void mouseReleased(java.awt.event.MouseEvent e) {
					throw new UnsupportedOperationException("Not supported yet.");
				}

				@Override
				public void mouseEntered(java.awt.event.MouseEvent e) {
					throw new UnsupportedOperationException("Not supported yet.");
				}

				@Override
				public void mouseExited(java.awt.event.MouseEvent e) {
					throw new UnsupportedOperationException("Not supported yet.");
				}
			};

			ActionListener exitListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Exiting...");
					System.exit(0);
				}
			};

			PopupMenu popup = new PopupMenu();
			MenuItem defaultItem = new MenuItem("Exit");
			defaultItem.addActionListener(exitListener);
			popup.add(defaultItem);

			trayIcon = new TrayIcon(image, "ShelfReader", popup);

			ActionListener actionListener = new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					trayIcon.displayMessage("Action Event",
							"An Action Event Has Been Performed!",
							TrayIcon.MessageType.INFO);
				}
			};

			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(actionListener);
			trayIcon.addMouseListener(mouseListener);

			try {
				tray.add(trayIcon);
				
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}

		} else {
			//  System Tray is not supported
		}

	
	}*/
}

class ColoredTableCellRenderer
    extends DefaultTableCellRenderer
{
    public Component getTableCellRendererComponent
        (JTable table, Object value, boolean selected, boolean focused, int row, int column)
    {
        setEnabled(table == null || table.isEnabled()); // see question above
        setBackground(Color.green);
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        return this;
    }
}
