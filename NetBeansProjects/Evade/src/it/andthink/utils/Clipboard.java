/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.andthink.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author meranr
 */
public class Clipboard {
	
	public String getClipboard() {

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
	
}
