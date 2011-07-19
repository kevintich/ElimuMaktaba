package elimu_maktabaLibrarianScreen;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class RegisterTableModel extends AbstractTableModel {
	
	private HashMap<Integer, HashMap<Integer, String>> map = null;
	private Vector<String> vectorColumnName = null;
	private RegisterJPanel register = null;

	public RegisterTableModel(HashMap<Integer,HashMap<Integer,String>> map, Vector<String> vectorColumnName, RegisterJPanel register) {
		this.map = map;
		this.vectorColumnName = vectorColumnName;
		this.register = register;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return map.get(0).size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return map.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return map.get(row).get(col+1);
	}
	
	public String getColumnName(int column) {
		return vectorColumnName.get(column);
	}
	
	public boolean isCellEditable(int row, int col) {
		return true;
	}
	
	public void setValueAt(Object value, int row, int col) {
		map.get(row).remove(getValueAt(row, col).toString());
		map.get(row).put(col + 1, value.toString());
				
        fireTableCellUpdated(row, col);
	}

	public void fireTableCellUpdated(int row, int col) {
		fireTableChanged(new TableModelEvent(this, row, row, col));
		register.getTable().validate();
		register.validate();
	}
	
	public void setMapVector(HashMap<Integer, HashMap<Integer, String>> map, Vector<String> vectorColumnName) {
		this.map = map;
		this.vectorColumnName = vectorColumnName;
	}
	
	public HashMap<Integer, HashMap<Integer, String>> getMap() {
		return map;
	}

}
