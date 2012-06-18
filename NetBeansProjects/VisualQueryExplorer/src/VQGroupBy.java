/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SCOCRI
 */
public class VQGroupBy {

	private String Description;
	private String FieldName;

	public VQGroupBy(String Description, String FieldName) {
		this.Description = Description;
		this.FieldName = FieldName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public String getFieldName() {
		return FieldName;
	}

	public void setFieldName(String FieldName) {
		this.FieldName = FieldName;
	}
}
