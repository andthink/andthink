/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SCOCRI
 */
public class VQField {

	private String Description;
	private String Name;
	private String Alias;
	private String Type;
	private String Len;
	private String Dec;

	public VQField(String Description, String Name, String Alias, String Type, String Len, String Dec) {
		this.Description = Description;
		this.Name = Name;
		this.Alias = Alias;
		this.Type = Type;
		this.Len = Len;
		this.Dec = Dec;
	}

	public String getAlias() {
		return Alias;
	}

	public void setAlias(String Alias) {
		this.Alias = Alias;
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

	public String getLen() {
		return Len;
	}

	public void setLen(String Len) {
		this.Len = Len;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}
}
