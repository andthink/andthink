/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SCOCRI
 */
public class VQTable {

	private String Description;
	private String Alias;
	private String TableName;
	private String TempTable;

	public VQTable(String Description, String Alias, String TableName, String TempTable) {
		this.Description = Description;
		this.Alias = Alias;
		this.TableName = TableName;
		this.TempTable = TempTable;
	}

	public String getAlias() {
		return Alias;
	}

	public void setAlias(String Alias) {
		this.Alias = Alias;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String TableName) {
		this.TableName = TableName;
	}

	public String getTempTable() {
		return TempTable;
	}

	public void setTempTable(String TempTable) {
		this.TempTable = TempTable;
	}


}
