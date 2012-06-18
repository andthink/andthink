/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.andthink.shelfreader;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import javax.swing.*;

/**
 * ExcelAdapter enables Copy-Paste Clipboard functionality on JTables. The clipboard data format used by the
 * adapter is compatible with the clipboard format used by Excel. This provides for clipboard interoperability
 * between enabled JTables and Excel.
 */
public class SiteAdapter implements ActionListener {

	private String rowstring, value;
	private Clipboard system;
	private StringSelection stsel;
	private JTable jTable1;

	/**
	 * The Excel Adapter is constructed with a JTable on which it enables Copy-Paste and acts as a Clipboard
	 * listener.
	 */
	public SiteAdapter(JTable myJTable) {
		jTable1 = myJTable;
		jTable1.getTableHeader().setReorderingAllowed(false);
		KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
		// Identifying the copy KeyStroke user can modify this
		// to copy on some other Key combination.
		// Identifying the Paste KeyStroke user can modify this
		//to copy on some other Key combination.
		jTable1.registerKeyboardAction(this, "Copy", copy, JComponent.WHEN_FOCUSED);
		jTable1.addMouseListener(new MouseAdapter() {
				 @Override
					public void mouseReleased(MouseEvent e) {
					 int rowindex = jTable1.getSelectedRow();
						if (rowindex < 0)
							return;
						if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
							JPopupMenu popup = createYourPopUp();
							popup.show(e.getComponent(), e.getX(), e.getY());
						}
					}

					private JPopupMenu createYourPopUp() {
						JPopupMenu jp = new JPopupMenu();
						JMenuItem copy = new JMenuItem("Copy (Site Variable Declaration)");
						copy.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								siteCopy();
							}
						});
						jp.add(copy);
						
						copy = new JMenuItem("Copy (Memory Cursor Variable Declaration)");
						copy.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								MCCopy();
							}
						});
						jp.add(copy);
						return jp;
					}
				});
	}

	/**
	 * Public Accessor methods for the Table on which this adapter acts.
	 */
	public JTable getJTable() {
		return jTable1;
	}

	public void setJTable(JTable jTable1) {
		this.jTable1 = jTable1;
	}
	
	public void siteCopy(){
			StringBuilder sbf = new StringBuilder();
			// Check to ensure we have selected only a contiguous block of
			// cells
			int numcols = jTable1.getSelectedColumnCount();
			int numrows = jTable1.getSelectedRowCount();
			int[] rowsselected = jTable1.getSelectedRows();
			//seleziono tutte le colonne
			int[] colsselected = {0,1,2,3,4,5,6};
			if (numrows != 1) {
				JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
						"Invalid Copy Selection",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (numrows == 1) {
				sbf.append("* --- CodePainter Revolution Batch Cut Vers. 1.0");
				sbf.append("\n");
				sbf.append("[\n");
				sbf.append("Icpitembatch.CPVarDecl\n");
				sbf.append("comment='").append((String) jTable1.getValueAt(rowsselected[0], colsselected[2])).append("\n");
				sbf.append("len=").append((String) jTable1.getValueAt(rowsselected[0], colsselected[4])).append("\n");
				sbf.append("name='").append((String) jTable1.getValueAt(rowsselected[0], colsselected[1])).append("\n");
				sbf.append("obj='Var\n");
				sbf.append("obj_type='").append((String) jTable1.getValueAt(rowsselected[0], colsselected[3])).append("\n");
				sbf.append("pag=1\n");
				sbf.append("pagelocal=True\n");
				sbf.append("I\n");
				sbf.append("]\n");
				sbf.append("*--*\n");
			}


			stsel = new StringSelection(sbf.toString());
			system = Toolkit.getDefaultToolkit().getSystemClipboard();
			system.setContents(stsel, stsel);
	}
	
	public void MCCopy(){
			StringBuilder sbf = new StringBuilder();
			// Check to ensure we have selected only a contiguous block of
			// cells
			int numrows = jTable1.getSelectedRowCount();
			int[] rowsselected = jTable1.getSelectedRows();
			//seleziono tutte le colonne
			int[] colsselected = {0,1,2,3,4,5,6};
			
			for(int i = 0; i < numrows; i++){
				sbf.append("<variable>\n");
				sbf.append("\t<name>").append((String) jTable1.getValueAt(rowsselected[i], colsselected[1])).append("</name>\n");
				sbf.append("\t<description>").append((String) jTable1.getValueAt(rowsselected[i], colsselected[2])).append("</description>\n");
				sbf.append("\t<type>").append((String) jTable1.getValueAt(rowsselected[i], colsselected[3])).append("</type>\n");
				sbf.append("</variable>\n");
			}

			stsel = new StringSelection(sbf.toString());
			system = Toolkit.getDefaultToolkit().getSystemClipboard();
			system.setContents(stsel, stsel);
	}

	/**
	 * This method is activated on the Keystrokes we are listening to in this implementation. Here it listens
	 * for Copy and Paste ActionCommands. 
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().compareTo("Copy") == 0) {
			siteCopy();
		}

	}
}
