/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SCOCRI
 */
public class VQJoin {

	private String Description;
	private String Expression;
	private String Type;
	private String Table1;
	private String Table2;

	public VQJoin(String Description, String Expression, String Type, String Table1, String Table2) {
		this.Description = Description;
		this.Expression = Expression;
		this.Type = Type;
		this.Table1 = Table1;
		this.Table2 = Table2;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public String getExpression() {
		return Expression;
	}

	public void setExpression(String Expression) {
		this.Expression = Expression;
	}

	public String getTable1() {
		return Table1;
	}

	public void setTable1(String Table1) {
		this.Table1 = Table1;
	}

	public String getTable2() {
		return Table2;
	}

	public void setTable2(String Table2) {
		this.Table2 = Table2;
	}

	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}
}
