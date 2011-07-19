package elimu_maktabaLibrarianScreen;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class LibrarianReports extends JPanel {
	private JTable jtableReports;
	// private JSpinner jsLimit;
	private JComboBox jcbActions;
	private JScrollPane jspTable;
	private JLabel jlReports;
	private JPanel jpWest, jpEast, jpWithComboBox, jpWithInfoLabel;
	private String[] items = new String[8];
	private String[] itemDescription = new String[8];
	private final String JCB_ERROR_STRING = new String(
			"An unknown error occurred when reloading the table");
	private Vector<Vector<String>> vTableItems;
	private Vector<String> vTableColumns;
	private BorderLayout mainBorderLayout;

	ElimuMaktabaMainDatabase mainDB = null;

	public LibrarianReports() {
		mainDB = new ElimuMaktabaMainDatabase();
		setBorder(BorderFactory.createTitledBorder("Library Reports"));
		
		// initialize array items
		items[0] = new String("View books borrowed today.");
		items[1] = new String("View books borrowed this week.");
		items[2] = new String("View books borrowed this month.");
		items[3] = new String("View people in the blacklist.");
		items[4] = new String("View books in bad condition.");
		items[5] = new String("View books lost.");
		items[6] = new String("View members registered today.");
		items[7] = new String("View members registered this week.");

		// Initialize array item descriptions for use by the JLabel
		itemDescription[0] = new String(
				"<HTML>The table shows the books borrowed<BR> today</HTML>");
		itemDescription[1] = new String(
				"<HTML>The table shows the books borrowed<BR> this week.</HTML>");
		itemDescription[2] = new String(
				"<HTML>The table shows the books borrowed<BR> this month.</HTML>");
		itemDescription[3] = new String(
				"<HTML>The table shows all members in<BR> the blacklist.</HTML>");
		itemDescription[4] = new String(
				"<HTML>The table shows the books in <BR>bad condition.</HTML>");
		itemDescription[5] = new String(
				"<HTML>The table shows all the books <BR>that are lost.</HTML>");
		itemDescription[6] = new String(
				"<HTML>The table shows all members <BR>registered today.</HTML>");
		itemDescription[7] = new String(
				"<HTML>The table shows all members <BR>registered this week.</HTML>");

		// initialize vectors with items from dbase and create table using the
		// vectors. Then add the table to the JScrollPane.
		vTableItems = obtainTableItemsFromDbase(0);
		vTableColumns = obtainTableColumnsFromDbase(0);
		jtableReports = new JTable(vTableItems, vTableColumns);
		jspTable = new JScrollPane(jtableReports);

		mainBorderLayout = new BorderLayout();
		mainBorderLayout.setHgap(40);
		setLayout(mainBorderLayout);

		jcbActions = new JComboBox(items);
		jcbActions.setBorder(BorderFactory
				.createTitledBorder("Choose table to view"));
		jcbActions.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				switch (jcbActions.getSelectedIndex()) {
				case 0:
					// System.out.println(jcbActions.getSelectedItem());
					vTableItems = obtainTableItemsFromDbase(0);
					vTableColumns = obtainTableColumnsFromDbase(0);
					jtableReports = new JTable(vTableItems, vTableColumns);
					jspTable = new JScrollPane(jtableReports);
					jpEast.removeAll();
					jpEast.add(jspTable, "Center");
					jlReports.setText(itemDescription[0]);
					break;
				case 1:
					// System.out.println(jcbActions.getSelectedItem());
					vTableItems = obtainTableItemsFromDbase(1);
					vTableColumns = obtainTableColumnsFromDbase(1);
					jtableReports = new JTable(vTableItems, vTableColumns);
					jspTable = new JScrollPane(jtableReports);
					jpEast.removeAll();
					jpEast.add(jspTable, "Center");
					repaint();
					jlReports.setText(itemDescription[1]);
					break;
				case 2:
					// System.out.println(jcbActions.getSelectedItem());
					vTableItems = obtainTableItemsFromDbase(2);
					vTableColumns = obtainTableColumnsFromDbase(2);
					jtableReports = new JTable(vTableItems, vTableColumns);
					jspTable = new JScrollPane(jtableReports);
					jpEast.removeAll();
					jpEast.add(jspTable, "Center");
					repaint();
					jlReports.setText(itemDescription[2]);
					break;
				case 3:
					// System.out.println(jcbActions.getSelectedItem());
					vTableItems = obtainTableItemsFromDbase(3);
					vTableColumns = obtainTableColumnsFromDbase(3);
					jtableReports = new JTable(vTableItems, vTableColumns);
					jspTable = new JScrollPane(jtableReports);
					jpEast.removeAll();
					jpEast.add(jspTable, "Center");
					repaint();
					jlReports.setText(itemDescription[3]);
					break;
				case 4:
					// System.out.println(jcbActions.getSelectedItem());
					vTableItems = obtainTableItemsFromDbase(4);
					vTableColumns = obtainTableColumnsFromDbase(4);
					jtableReports = new JTable(vTableItems, vTableColumns);
					jspTable = new JScrollPane(jtableReports);
					jpEast.removeAll();
					jpEast.add(jspTable, "Center");
					repaint();
					jlReports.setText(itemDescription[4]);
					break;
				case 5:
					// System.out.println(jcbActions.getSelectedItem());
					vTableItems = obtainTableItemsFromDbase(5);
					vTableColumns = obtainTableColumnsFromDbase(5);
					jtableReports = new JTable(vTableItems, vTableColumns);
					jspTable = new JScrollPane(jtableReports);
					jpEast.removeAll();
					jpEast.add(jspTable, "Center");
					repaint();
					jlReports.setText(itemDescription[5]);
					break;
				case 6:
					// System.out.println(jcbActions.getSelectedItem());
					vTableItems = obtainTableItemsFromDbase(6);
					vTableColumns = obtainTableColumnsFromDbase(6);
					jtableReports = new JTable(vTableItems, vTableColumns);
					jspTable = new JScrollPane(jtableReports);
					jpEast.removeAll();
					jpEast.add(jspTable, "Center");
					repaint();
					jlReports.setText(itemDescription[6]);
					break;
				case 7:
					// System.out.println(jcbActions.getSelectedItem());
					vTableItems = obtainTableItemsFromDbase(7);
					vTableColumns = obtainTableColumnsFromDbase(7);
					jtableReports = new JTable(vTableItems, vTableColumns);
					jspTable = new JScrollPane(jtableReports);
					jpEast.removeAll();
					jpEast.add(jspTable, "Center");
					repaint();
					jlReports.setText(itemDescription[7]);
					break;
				default:
					// System.out.println(jcbError);
					jlReports.setText(JCB_ERROR_STRING);
					break;
				}
			}
		});
		// jsLimit = new JSpinner();
		jlReports = new JLabel(itemDescription[0]);
		jlReports.setBorder(BorderFactory.createTitledBorder(""));
		jpWest = new JPanel(new BorderLayout());
		jpEast = new JPanel(new BorderLayout());
		jpWithComboBox = new JPanel(new GridLayout(5, 0));
		jpWithInfoLabel = new JPanel(new GridLayout(5, 0));
		jpWithInfoLabel.add(new JLabel());
		jpWithInfoLabel.add(jlReports);
		jpWithInfoLabel.add(new JLabel());
		jpWithInfoLabel.add(new JLabel());
		jpWithInfoLabel.add(new JLabel());
		jpWithComboBox.add(new JLabel());
		jpWithComboBox.add(jcbActions);
		jpWithComboBox.add(new JLabel());
		jpWithComboBox.add(new JLabel());
		jpWithComboBox.add(new JLabel());
		jpWest.add(jpWithComboBox, "North");
		jpWest.add(jpWithInfoLabel, "Center");

		// jpEast.add(jsLimit, "North");
		jpEast.add(jspTable, "Center");
		add(jpWest, "West");
		jpEast.setLayout(LibrarianViewAccounts.EASTERN_FLOWLAYOUT);
		add(jpEast, "Center");
	}

	public Vector<Vector<String>> obtainTableItemsFromDbase(int index) {
		if (index == 0) {
			return mainDB.librarianViewBooksBorrowedToday();
		} else if (index == 1) {
			return mainDB.librarianViewBooksBorrowedThisWeek();
		} else if (index == 2) {
			return mainDB.librarianViewBooksBorrowedThisMonth();
		} else if (index == 3) {
			return mainDB.librarianViewPeopleInBlacklist();
		} else if (index == 4) {
			return mainDB.librarianViewBooksInBadCondition();
		} else if (index == 5) {
			return mainDB.librarianViewBooksLost();
		} else if (index == 6) {
			return mainDB.librarianViewMembersRegisteredToday();
		} else if (index == 7) {
			return mainDB.librarianViewMembersRegisteredThisWeek();
		}
		return null; // /to be changed...
	}

	public Vector<String> obtainTableColumnsFromDbase(int index) {
		if (index == 0) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("Name of Book"));
			columnNameVector.add(new String("Member who Borrowed"));
			columnNameVector.add(new String("Time Borrowed"));
			return columnNameVector;
		} else if (index == 1) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("Name of Book"));
			columnNameVector.add(new String("Member who Borrowed"));
			columnNameVector.add(new String("Time Borrowed"));
			return columnNameVector;
		} else if (index == 2) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("Name of Book"));
			columnNameVector.add(new String("Member who Borrowed"));
			columnNameVector.add(new String("Time Borrowed"));
			return columnNameVector;
		} else if (index == 3) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("Name of Member"));
			columnNameVector.add(new String("Date blacklisted"));
			columnNameVector.add(new String("Offense done"));
			return columnNameVector;
		} else if (index == 4) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("Barcode Number"));
			columnNameVector.add(new String("Name of Book"));
			columnNameVector.add(new String("Status of Book"));
			return columnNameVector;
		} else if (index == 5) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("Barcode Number"));
			columnNameVector.add(new String("Name of Book"));
			columnNameVector.add(new String("Status of Book"));
			return columnNameVector;
		} else if (index == 6) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("ID of Member"));
			columnNameVector.add(new String("Name of Member"));
			columnNameVector.add(new String("Gender"));
			columnNameVector.add(new String("Registration Date"));
			return columnNameVector;
		} else if (index == 7) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("ID of Member"));
			columnNameVector.add(new String("Name of Member"));
			columnNameVector.add(new String("Gender"));
			columnNameVector.add(new String("Registration Date"));
			return columnNameVector;
		}

		return null; // /to be changed...
	}
}
