/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SCOCRI
 */
public class VQFilter {

	private String FieldName;
	private String Not;
	private String Criteria;
	private String Example;
	private String Operator;
	private String Having;

	public VQFilter(String FieldName, String Not, String Criteria, String Example, String Operator, String Having) {
		this.FieldName = FieldName;
		this.Not = Not;
		this.Criteria = Criteria;
		this.Example = Example;
		this.Operator = Operator;
		this.Having = Having;
	}

	public String getCriteria() {
		return Criteria;
	}

	public void setCriteria(String Criteria) {
		this.Criteria = Criteria;
	}

	public String getExample() {
		return Example;
	}

	public void setExample(String Example) {
		this.Example = Example;
	}

	public String getFieldName() {
		return FieldName;
	}

	public void setFieldName(String FieldName) {
		this.FieldName = FieldName;
	}

	public String getHaving() {
		return Having;
	}

	public void setHaving(String Having) {
		this.Having = Having;
	}

	public String getNot() {
		return Not;
	}

	public void setNot(String Not) {
		this.Not = Not;
	}

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String Operator) {
		this.Operator = Operator;
	}
}
