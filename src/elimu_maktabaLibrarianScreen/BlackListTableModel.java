package elimu_maktabaLibrarianScreen;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class BlackListTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3300553423852242940L;
	
	ElimuMaktabaMainDatabase mainDB = null;
	private int count = 0;
	private JTextField id;
	private boolean canEdit;
	
	private ModifyBlackListPanel mod;
	private HashMap<String, HashMap<Integer, Object>> getFormerColMaps;
	
	
	public BlackListTableModel(ElimuMaktabaMainDatabase db, JTextField idTF, HashMap<String, HashMap<Integer, Object>> getFormerColMaps, ModifyBlackListPanel mod, boolean op) {
		mainDB = db;
		id = idTF;
		canEdit = op;
		this.mod = mod;
		this.getFormerColMaps = getFormerColMaps;
	}
	
	public BlackListTableModel(ElimuMaktabaMainDatabase db, JTextField idTF, boolean op) {
		this(db, idTF, null, null, op);
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return mainDB.getNoCols(id.getText());
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return mainDB.getNoRows(id.getText());
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return mainDB.getCellValue(id.getText(), row, col);
	}
	
	public String getColumnName(int col) {
		return mainDB.getColName(id.getText(), col);
	}
	
	public boolean isCellEditable(int row, int col) {
		return canEdit;
	}
	
	public void setValueAt(Object value, int row, int col) {
		getFormerColMaps.put(row + "-" + count, new HashMap<Integer, Object>());
    	getFormerColMaps.get(row + "-" + count).put(col, getValueAt(row, col));
    	count++;
    	
		mainDB.updateTableCell(id.getText(), row, col, value, getValueAt(row, 0).toString(), getValueAt(row, 1).toString());
		mod.updateComboBox();
		mod.setDiscards();
		
		fireTableCellUpdated(row, col);
	}

	public void fireTableCellUpdated(int row, int col) {
		fireTableChanged(new TableModelEvent(this, row, row, col));
		mod.getTable().validate();
		mod.validate();
	}

}
