/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SCOCRI
 */
public class VQOrderBy {

	private String Description;
	private String FieldName;
	private String Cardinality;

	public VQOrderBy(String Description, String FieldName, String Cardinality) {
		this.Description = Description;
		this.FieldName = FieldName;
		this.Cardinality = Cardinality;
	}

	public String getCardinality() {
		return Cardinality;
	}

	public void setCardinality(String Cardinality) {
		this.Cardinality = Cardinality;
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
