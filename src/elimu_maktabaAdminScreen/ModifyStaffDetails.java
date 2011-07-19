package elimu_maktabaAdminScreen;

import javax.activation.MailcapCommandMap;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;
import elimu_maktabaDatabaseConnection.*;

public class ModifyStaffDetails {
	JTable table;
	Vector<String> allUsersVector = new Vector<String>();
	Vector<HashMap<String, String>> data;
	ElimuMaktabaMainDatabase db = null;
	HashMap<String, String> hashFirstName;
	HashMap<String, String> hashSurname;
	HashMap<String, String> hashMiddleName;
	HashMap<String, String> hashDateOfBirth;
	HashMap<String, String> hashAddresses;
	HashMap<String, String> hashNationalID;
	HashMap<String, String> hashEmailAddress;
	Vector<String> staffIDVector;
	public TreeSet<Integer> changed = new TreeSet<Integer>();

	public ModifyStaffDetails(ElimuMaktabaMainDatabase db) {
		this.db = db;
		obtainRecords();
		data = new Vector<HashMap<String, String>>();
		hashFirstName = db.hashFirstName;
		data.add(hashFirstName);
		hashSurname = db.hashSurname;
		data.add(hashSurname);
		hashMiddleName = db.hashMiddleName;
		data.add(hashMiddleName);
		hashDateOfBirth = db.hashDateOfBirth;
		data.add(hashDateOfBirth);
		hashAddresses = db.hashAddresses;
		data.add(hashAddresses);
		hashNationalID = db.hashNationalID;
		data.add(hashNationalID);
		hashEmailAddress = db.hashEmailAddress;
		data.add(hashEmailAddress);

		staffIDVector = db.staffIDVector;

	}

	class JTableModel extends AbstractTableModel {
		String[] colnames = new String[] { "StaffID", "First Name", "Surname",
				"Middle Name", "Date Of Birth", "Address", "National ID",
				"Email Address" };

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return staffIDVector.size();
		}

		public String getColumnName(int column) {
			return colnames[column];
		}

		public int getColumnCount() {
			// TODO Auto-generated method stub
			return colnames.length;

		}

		public boolean isCellEditable(int row, int col) {
			if (col == 0 || col == 4) {
				return false;
			} else {
				return true;
			}
		}

		public void setValueAt(Object value, int row, int col) {
			int x = 0;
			x = col - 1;

			HashMap<String, String> hms = data.get(x);
			String id = staffIDVector.get(row);
			// System.out.println(hms.get(id));
			hms.put(id, (String) value);
			// hms.remove(id);
			fireTableCellUpdated(row, col);

		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			Object value = null;
			String id = null;
			id = staffIDVector.get(rowIndex);
			switch (columnIndex) {
			case 0:
				value = new String(id);
				break;
			case 1:
				value = hashFirstName.get(id);
				break;
			case 2:
				value = hashSurname.get(id);
				break;
			case 3:
				value = hashMiddleName.get(id);
				break;
			case 4:
				value = hashDateOfBirth.get(id);
				break;
			case 5:
				value = hashAddresses.get(id);
				break;
			case 6:
				value = hashNationalID.get(id);
				break;
			case 7:
				value = hashEmailAddress.get(id);
				break;
			default:
				value = null;

			}
			return value;
		}

		public void fireTableCellUpdated(int row, int col) {
			Object o = getValueAt(row, col);
			// System.err.println("[row, column] - [" + row + "," + col + "]: "
			// + o);
			fireTableChanged(new TableModelEvent(this, row, row, col));
			changed.add(row);
			System.out.println("fireTableCellUpdated");
			table.validate();
		}
	}

	public JScrollPane setUpTable() {
		JTableModel model = new JTableModel();
		table = new JTable(model);
		table.setForeground(new Color(36, 67, 93));
		table.setFont(new Font("Serif", Font.PLAIN, 13));
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(690, 500));
		return scrollPane;
	}

	public void obtainRecords() {
		data = db.getStaffUserDetailsRecords();
	}
}
