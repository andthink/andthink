/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SCOCRI
 */
public class VQFilterParameter {

	private String FieldName;
	private String Description;
	private String Type;
	private String Len;
	private String Dec;
	private String RoE;

	public VQFilterParameter(String FieldName, String Description, String Type, String Len, String Dec, String RoE) {
		this.FieldName = FieldName;
		this.Description = Description;
		this.Type = Type;
		this.Len = Len;
		this.Dec = Dec;
		this.RoE = RoE;
	}

	public String getDec() {
		return Dec;
	}

	public void setDec(String Dec) {
		this.Dec = Dec;
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

	public String getLen() {
		return Len;
	}

	public void setLen(String Len) {
		this.Len = Len;
	}

	public String getRoE() {
		return RoE;
	}

	public void setRoE(String RoE) {
		this.RoE = RoE;
	}

	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}
}
