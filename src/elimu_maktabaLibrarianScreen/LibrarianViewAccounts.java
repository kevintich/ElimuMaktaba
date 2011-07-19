package elimu_maktabaLibrarianScreen;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class LibrarianViewAccounts extends JPanel {
	
	public static final FlowLayout EASTERN_FLOWLAYOUT = new FlowLayout(FlowLayout.CENTER,40,40);
	
	private JTable jtableReports;
	// private JSpinner jsLimit;
	private JComboBox jcbActions;
	private JScrollPane jspTable;
	private JLabel jlReports;
	private JPanel jpWest, jpEast, jpWithComboBox, jpWithInfoLabel;
	private String[] items = new String[3];
	private String[] itemDescription = new String[3];
	private final String JCB_ERROR_STRING = new String(
			"An unknown error occurred when reloading the table");
	private Vector<Vector<String>> vTableItems;
	private Vector<String> vTableColumns;
	private BorderLayout mainBorderLayout;

	ElimuMaktabaMainDatabase mainDB = null;

	public LibrarianViewAccounts() {

		mainDB = new ElimuMaktabaMainDatabase();
		setBorder(BorderFactory.createTitledBorder("Account Reports"));

		// initialize array items

		// TODO Change working of accounts of past hour.
		items[0] = new String("View accounts for the past hour");
		items[1] = new String("View accounts for today.");
		items[2] = new String("View accounts for this week.");

		// Initialize array item descriptions for use by the JLabel
		itemDescription[0] = new String(
				"<HTML>The table shows the accounts <BR> for the past hour</HTML>");
		itemDescription[1] = new String(
				"<HTML>The table shows the accounts<BR> for today.</HTML>");
		itemDescription[2] = new String(
				"<HTML>The table shows the accounts<BR> for this week.</HTML>");

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
		jpEast.setLayout(EASTERN_FLOWLAYOUT);
		add(jpEast, "Center");
	}

	public Vector<Vector<String>> obtainTableItemsFromDbase(int index) {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		Vector<String> tempVector = new Vector<String>();
		if (index == 0) {
			return mainDB.librarianViewAccoutsForPastHour();
		} else if (index == 1) {
			return mainDB.librarianViewAccountsForToday();
		} else if (index == 2) {
			return mainDB.librarianViewAccountsForThisWeek();
		}
		return null; // /to be changed...
	}

	public Vector<String> obtainTableColumnsFromDbase(int index) {
		if (index == 0) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("MemberID"));
			columnNameVector.add(new String("MemberName"));
			columnNameVector.add(new String("Amount Paid"));
			columnNameVector.add(new String("Time of payment"));
			return columnNameVector;
		} else if (index == 1) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("MemberID"));
			columnNameVector.add(new String("MemberName"));
			columnNameVector.add(new String("Amount Paid"));
			columnNameVector.add(new String("Time of payment"));
			return columnNameVector;
		} else if (index == 2) {
			Vector<String> columnNameVector = new Vector<String>();
			columnNameVector.add(new String("MemberID"));
			columnNameVector.add(new String("MemberName"));
			columnNameVector.add(new String("Amount Paid"));
			columnNameVector.add(new String("Date of payment"));
			return columnNameVector;
		}

		return null; // /to be changed...

	}

}
