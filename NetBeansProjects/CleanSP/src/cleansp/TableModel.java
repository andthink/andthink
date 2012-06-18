/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cleansp;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author SCOCRI
 */
class TableModel extends AbstractTableModel {
    private String[] columnNames = {"File", "Type Found"};
    private Object[][] data = {};

	@Override
    public int getColumnCount() {
        return columnNames.length;
    }

	@Override
    public int getRowCount() {
        return data.length;
    }

	@Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

	@Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	public void setData(Object[][] dati){
		this.data = dati;
	}

	/*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(String value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
