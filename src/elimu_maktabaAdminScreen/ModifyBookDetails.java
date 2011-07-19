package elimu_maktabaAdminScreen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class ModifyBookDetails {
	JTable table;
	Vector<HashMap<String, String>> data;
	ElimuMaktabaMainDatabase db = null;
	public TreeSet<Integer> changed = new TreeSet<Integer>();

	public Vector<String> bookIDVector;
	public HashMap<String, String> hashBookTitle;
	public HashMap<String, String> hashBVersion;
	public HashMap<String, String> hashBAuthor;
	public HashMap<String, String> hashBPublisher;
	public HashMap<String, String> hashBPages;
	public HashMap<String, String> hashBGenre;

	public ModifyBookDetails(ElimuMaktabaMainDatabase db) {

		this.db = db;
		obtainRecords();
		data = new Vector<HashMap<String, String>>();
		hashBookTitle = db.hashBookTitle;
		data.add(hashBookTitle);
		hashBVersion = db.hashBVersion;
		data.add(hashBVersion);
		hashBAuthor = db.hashBAuthor;
		data.add(hashBAuthor);
		hashBPublisher = db.hashBPublisher;
		data.add(hashBPublisher);
		hashBPages = db.hashBPages;
		data.add(hashBPages);
		hashBGenre = db.hashBGenre;
		data.add(hashBGenre);
		bookIDVector = db.bookIDVector;

	}

	class JTableModel extends AbstractTableModel {
		String[] colnames = new String[] { "BookBarcode", "Title", "Version",
				"Author", "Publisher", "No.Of Pages", "Genre" };

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return bookIDVector.size();
		}

		public String getColumnName(int column) {
			return colnames[column];
		}

		public int getColumnCount() {
			// TODO Auto-generated method stub
			return colnames.length;

		}

		public boolean isCellEditable(int row, int col) {
			if (col == 0
					) {
				return false;
			} else {
				return true;
			}
		}

		public void setValueAt(Object value, int row, int col) {
			int x = 0;
			x = col - 1;

			HashMap<String, String> hms = data.get(x);
			String id = bookIDVector.get(row);
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
			id = bookIDVector.get(rowIndex);
			switch (columnIndex) {
			case 0:
				value = new String(id);
				break;
			case 1:
				value = hashBookTitle.get(id);
				break;
			case 2:
				value = hashBVersion.get(id);
				break;
			case 3:
				value = hashBAuthor.get(id);
				break;
			case 4:
				value = hashBPublisher.get(id);
				break;
			case 5:
				value = hashBPages.get(id);
				break;
			case 6:
				value = hashBGenre.get(id);
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
		data = db.getBookDetailsRecords();
	}

}
