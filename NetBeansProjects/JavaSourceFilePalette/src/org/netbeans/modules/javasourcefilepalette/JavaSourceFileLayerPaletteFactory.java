/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.javasourcefilepalette;

import java.io.IOException;
import java.lang.invoke.MethodHandles.Lookup;
import javax.swing.Action;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.util.Exceptions;
import org.openide.util.datatransfer.ExTransferable;

/**
 *
 * @author meranr
 */
public class JavaSourceFileLayerPaletteFactory {

	//Name of the folder in the layer.xml file 
	//that is the root of the palette:
	public static final String JAVA_PALETTE_FOLDER = "JavaPalette";
	private static PaletteController palette = null;

	//Register the palette for the text/x-java MIME type:
	@MimeRegistration(mimeType = "text/x-java", service = PaletteController.class)
	public static PaletteController createPalette() {
		try {
			if (null == palette) {
				palette = PaletteFactory.createPalette(
						JAVA_PALETTE_FOLDER,
						new MyActions(), null,
						new MyDragAndDropHandler());
			}
			return palette;
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
		return null;
	}

	//Definition of the DragAndDropHandler:
	private static class MyDragAndDropHandler extends DragAndDropHandler {

		MyDragAndDropHandler() {
			super(true);
		}

		@Override
		public void customize(ExTransferable et, org.openide.util.Lookup lkp) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

	private static class MyActions extends PaletteActions {

		//Add new buttons to the Palette Manager here:
		@Override
		public Action[] getImportActions() {
			return null;
		}

		//Add new contextual menu items to the palette here:
		@Override
		public Action[] getCustomPaletteActions() {
			return null;
		}

		//Add new contextual menu items for palette categories here:
		public Action[] getCustomCategoryActions(Lookup arg0) {
			return null;
		}

		//Add new contextual menu items for palette items here:
		public Action[] getCustomItemActions(Lookup arg0) {
			return null;
		}

		//Define the double-click action here:
		public Action getPreferredAction(Lookup arg0) {
			return null;
		}

		@Override
		public Action[] getCustomCategoryActions(org.openide.util.Lookup lkp) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public Action[] getCustomItemActions(org.openide.util.Lookup lkp) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public Action getPreferredAction(org.openide.util.Lookup lkp) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}
}
