
import java.io.File;
import java.io.FilenameFilter;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author SCOCRI
 */
public class VQList {

	private String path;
	private File[] List;

	VQList(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the List
	 */
	public File[] getList() {
		return List;
	}

	/**
	 * @param List the List to set
	 */
	private void setList(File[] List) {
		this.List = List;
	}

	public void generateList() {
		this.setList(new File(this.path).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".vqr");
			}
		}));
	}
}
