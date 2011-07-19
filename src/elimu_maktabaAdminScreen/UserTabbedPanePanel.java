package elimu_maktabaAdminScreen;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ListIterator;
import java.util.Vector;

import elimu_maktabaLogin.*;
import elimu_maktabaDatabaseConnection.*;
import jdatechooser.*;

public class UserTabbedPanePanel extends JPanel {
	LoginDatabaseClass db = null;
	JRadioButton createUser, modifyUser, removeUser;
	ButtonGroup buttongroupUser;
	JPanel mainPanel, createU, modifyU, removeU, userPane, bookPane,
			accountsPane, userPaneRadio, displayPaneUsers, displayPaneBooks,
			registerBook, modifyBook, deactivateBook, bookPaneRadio,
			createUGrid, createMGrid, centerUserPanel, southUserPanel,
			centerModifyPanel, southModifyPanel, findModify, createRGrid,
			centerRemovePanel, southRemovePanel, findRemove;
	JLabel findLabel;
	JTextField findByID, findByIDR;
	JLabel[] createLabels = new JLabel[7];
	JLabel[] createRLabels = new JLabel[8];
	JTextField[] createTextFields = new JTextField[7];
	JTextField[] createRTextFields = new JTextField[8];
	JLabel jlSelectRole;
	JComboBox jcbSelectRole;
	String[] labels = { "StaffID", "First Name: ", "Surname: ",
			"Middle Name: ", "Email Address: ", "Address: ", "National ID: " };
	String labelsRemove[] = {"StaffID: ","First Name: ","Surname: ","Middle Name:","Date Of Birth: ","Address:","NationalID: ","email-Address"};
	JButton createUserAcc, resetFieldsButton, modifyUserB, cancelMod, findM,
			removeUserB, cancelRem, findR, btnRefresh;
	GridLayout UGrid;
	ElimuMaktabaMainDatabase mainDB = null;
	JPasswordField passwordField, passConf;
	JPanel displayAllUsersPanel;
	JPanel panelHoldTable;
	JScrollPane tableUserScrllPane;
	DefaultTableModel tableModelUser;
	JTable tableUser;
	String[] userCols = { "StaffID", "FirstName", "Surname", "MiddleName",
			"D.O.B", "Address", "NatID", "Email", "Date Recorded" };
	Vector rowsUser = new Vector();
	Vector colsUser = new Vector();
	JLabel labelStaffID;
	ModifyStaffDetails modJT;
	JLabel labelDateOfBirth;
	JDateChooser dateChooser;
	JFormattedTextField textDate;
	ImageIcon weezy;
	public UserTabbedPanePanel(ElimuMaktabaMainDatabase db) {
		setLayout(new BorderLayout());
		mainDB = db;
		modJT = new ModifyStaffDetails(db);
		dateChooser = new JDateChooser(24, 6, 1995, 1900, 3000, 0);
		createUser = new JRadioButton("Create User");
		modifyUser = new JRadioButton("Modify User");
		removeUser = new JRadioButton("Remove User");
		labelDateOfBirth = new JLabel("Date Of Birth:", SwingConstants.RIGHT);
		buttongroupUser = new ButtonGroup();

		buttongroupUser.add(createUser);
		buttongroupUser.add(modifyUser);
		buttongroupUser.add(removeUser);

		userPaneRadio = new JPanel();
		userPaneRadio.setLayout(new BoxLayout(userPaneRadio,
				BoxLayout.PAGE_AXIS));
		weezy = new ImageIcon("AlbumArtSmall.jpg");
		JLabel label = new JLabel(weezy);
		
		userPaneRadio.add(createUser);
		userPaneRadio.add(modifyUser);
		userPaneRadio.add(removeUser);
		userPaneRadio.add(label);
		add(userPaneRadio, BorderLayout.WEST);

		displayPaneUsers = new JPanel();
		createU = new JPanel(new BorderLayout());
		UGrid = new GridLayout(11, 2);
		UGrid.setVgap(5);

		createUGrid = new JPanel();

		createUGrid.setLayout(UGrid);
		jlSelectRole = new JLabel("Select role :", SwingConstants.RIGHT);
		createUGrid.add(jlSelectRole);
		jcbSelectRole = new JComboBox(new String[] { "--select role--",
				"Administrator", "Librarian" });

		jcbSelectRole.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (((String) jcbSelectRole.getSelectedItem())
						.equals("Administrator")) {
					displayAdminAutoGenID();
				} else if (((String) jcbSelectRole.getSelectedItem())
						.equals("Librarian")) {
					displayLibAutoGenID();
					System.out.println("Librarian selected");
				}
			}
		});
		createUGrid.add(jcbSelectRole);
		labelStaffID = new JLabel("StaffID:", SwingConstants.RIGHT);
		{
			for (int i = 0; i < createLabels.length; i++) {
				createLabels[i] = new JLabel(labels[i], SwingConstants.RIGHT);
				createUGrid.add(createLabels[i]);

				createTextFields[i] = new JTextField(12);
				createUGrid.add(createTextFields[i]);

			}
			
			createUGrid.add(labelDateOfBirth);
			JPanel panelDate = new JPanel(new GridLayout());
			JButton button = dateChooser.btnPop;
			textDate = dateChooser.txtDate;
			panelDate.add(textDate);
			panelDate.add(button);

			createUGrid.add(panelDate);

			createTextFields[0].setEditable(false);
			JLabel labelPassword = new JLabel("Password:", SwingConstants.RIGHT);
			JLabel labelConfirmPassword = new JLabel("Confirm Password:",
					SwingConstants.RIGHT);
			passwordField = new JPasswordField();
			passConf = new JPasswordField();
			createUGrid.add(labelPassword);
			createUGrid.add(passwordField);
			createUGrid.add(labelConfirmPassword);
			createUGrid.add(passConf);

			centerUserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			centerUserPanel.add(createUGrid);

			createUserAcc = new JButton("Create User");

			createUserAcc.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					validateUserRegistration();

				}
			});

			resetFieldsButton = new JButton("Reset Fields");

			resetFieldsButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < createTextFields.length; i++) {
						createTextFields[i].setText("");
					}
					passwordField.setText("");
					passConf.setText("");
				}
			});
			southUserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			southUserPanel.add(createUserAcc);
			southUserPanel.add(resetFieldsButton);

			panelHoldTable = new JPanel(new BorderLayout());
			panelHoldTable.setBorder(BorderFactory
					.createTitledBorder("Display all users...."));
			panelHoldTable.setPreferredSize(new Dimension(690, 180));

			tableUserScrllPane = new JScrollPane();
			{
				// accountsBLPanel.add(tableBLScrollpane, BorderLayout.NORTH);
				tableModelUser = new DefaultTableModel();
				addColumnsHeadingUser(userCols);
				tableModelUser.setDataVector(rowsUser, colsUser);

				tableUser = new JTable();
				tableUser.enable(false);
				// tableUser.setAutoCreateRowSorter(true);
				tableUser.sizeColumnsToFit(true);

				tableUserScrllPane.setViewportView(tableUser);
				tableUser.getParent().setBackground(Color.WHITE);
				tableUser.setModel(tableModelUser);
				tableUserScrllPane.setPreferredSize(new Dimension(480, 125));

			}

			JPanel panelBTNRefresh = new JPanel(new FlowLayout(
					FlowLayout.CENTER));
			btnRefresh = new JButton("Refresh..");
			btnRefresh.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					DefaultTableModel dm = (DefaultTableModel) tableUser
							.getModel();
					dm.getDataVector().removeAllElements();
					showUserDetails();
				}
			});
			panelBTNRefresh.add(btnRefresh);

			panelHoldTable.add(tableUserScrllPane, BorderLayout.NORTH);
			panelHoldTable.add(panelBTNRefresh, BorderLayout.CENTER);

			createU.add(centerUserPanel, BorderLayout.NORTH);
			createU.add(southUserPanel, BorderLayout.CENTER);
			createU.add(panelHoldTable, BorderLayout.SOUTH);
		}
		createU.setBorder(BorderFactory.createTitledBorder("Create User..."));
		createU.setPreferredSize(new Dimension(700, 570));
		createU.setVisible(true);
		createUser.setSelected(true);
		createUser.addItemListener(new ItemListener() {
			boolean display = false;

			public void itemStateChanged(ItemEvent e) {
				display = true;
				createU.setVisible(display);
				display = false;
				modifyU.setVisible(display);
				removeU.setVisible(display);
			}
		});

		modifyU = new JPanel(new BorderLayout());
		modifyU.setBorder(BorderFactory.createTitledBorder("Modify User..."));
		modifyU.setPreferredSize(new Dimension(710, 570));

		modifyUserB = new JButton("Update");
		modifyUserB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Vector<String> vOfItems = new Vector<String>();
				// System.out.println("rowsUserSize "
				// + modJT.table.getModel().getRowCount());
				for (int row = 0; row < modJT.table.getModel().getRowCount(); row++) {
					for (int column = 0; column < modJT.table.getModel()
							.getColumnCount(); column++) {
						// System.out.print(modJT.table.getModel().getValueAt(row,
						// column)
						// + "\t");
						vOfItems.add((String) modJT.table.getModel()
								.getValueAt(row, column));
					}
					String staffID = vOfItems.get(0);
					String firstName = vOfItems.get(1);
					String surname = vOfItems.get(2);
					String middlename = vOfItems.get(3);
					String dateOfBirth = vOfItems.get(4);
					String address = vOfItems.get(5);
					mainDB.updateStudentDetailsTable(staffID, firstName,
							surname, middlename, dateOfBirth, address);
					vOfItems.removeAllElements();
				}
				JOptionPane.showMessageDialog(null, "Changes saved");
			}
		});
		cancelMod = new JButton("Cancel");

		southModifyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		southModifyPanel.add(modifyUserB);
		southModifyPanel.add(cancelMod);

		JScrollPane scroll = modJT.setUpTable();
		modifyU.add(scroll, BorderLayout.CENTER);
		modifyU.add(southModifyPanel, BorderLayout.SOUTH);

		modifyU.setVisible(false);

		modifyUser.addItemListener(new ItemListener() {
			boolean display = false;

			public void itemStateChanged(ItemEvent e) {
				display = true;
				modifyU.setVisible(display);
				display = false;
				createU.setVisible(display);
				removeU.setVisible(display);
			}
		});

		removeU = new JPanel(new BorderLayout());
		removeU.setBorder(BorderFactory.createTitledBorder("Remove User..."));
		removeU.setPreferredSize(new Dimension(650, 450));

		createRGrid = new JPanel();
		createRGrid.setLayout(new GridLayout(8, 2));
		
		{
			for (int i = 0; i < createRLabels.length; i++) {
				createRLabels[i] = new JLabel(labelsRemove[i], SwingConstants.RIGHT);
				createRGrid.add(createRLabels[i]);

				createRTextFields[i] = new JTextField(15);
				createRGrid.add(createRTextFields[i]);
			}

			centerRemovePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			centerRemovePanel.setBorder(BorderFactory
					.createTitledBorder("Found Person To Remove..."));
			centerRemovePanel.setVisible(false);
			centerRemovePanel.add(createRGrid);

			removeUserB = new JButton("Remove");

			removeUserB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int confirm = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete user?",
							"Confirm Delete!", JOptionPane.YES_NO_OPTION);

					if (confirm == 0) {
						int result = mainDB.removeUser(findByIDR.getText());

						if (result == 1) {
							JOptionPane.showMessageDialog(null,
									"User successfully deleted!",
									"Process Successful!",
									JOptionPane.PLAIN_MESSAGE);

							for (int i = 0; i < createRTextFields.length; i++) {
								createRTextFields[i].setText("");
							}

							findByIDR.setText("");
							centerRemovePanel.setVisible(false);
							removeUserB.setEnabled(false);
							cancelRem.setEnabled(false);

						} else {
							JOptionPane.showMessageDialog(null,
									"Error deleting User!", "Process Failed!",
									JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			});

			removeUserB.setEnabled(false);
			cancelRem = new JButton("Cancel");

			cancelRem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < createRTextFields.length; i++) {
						createRTextFields[i].setText("");
					}

					findByIDR.setText("");
					centerRemovePanel.setVisible(false);
					removeUserB.setEnabled(false);
					cancelRem.setEnabled(false);
				}
			});

			cancelRem.setEnabled(false);

			southRemovePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			southRemovePanel.add(removeUserB);
			southRemovePanel.add(cancelRem);
			{
				findRemove = new JPanel(new FlowLayout(FlowLayout.CENTER));
				findRemove.setBorder(BorderFactory
						.createTitledBorder("Find..."));
				findLabel = new JLabel("Find By Staff ID: ");
				findByIDR = new JTextField(15);
				findR = new JButton("Find...");

				findR.addActionListener(new ActionListener() {
					String[] foundDetails = new String[8];

					public void actionPerformed(ActionEvent e) {

						foundDetails = mainDB.findUser(findByIDR.getText());

						if (foundDetails[0] != null) {
							for (int i = 0; i < foundDetails.length; i++) {
								createRTextFields[i].setText(foundDetails[i]);
							}

							centerRemovePanel.setVisible(true);
							removeUserB.setEnabled(true);
							cancelRem.setEnabled(true);
						} else {
							JOptionPane.showMessageDialog(null,
									"User specified doesn't exist!", "Oops!",
									JOptionPane.PLAIN_MESSAGE);
						}

					}
				});

				findRemove.add(findLabel);
				findRemove.add(findByIDR);
				findRemove.add(findR);
			}

			removeU.add(findRemove, BorderLayout.NORTH);
			removeU.add(centerRemovePanel, BorderLayout.CENTER);
			removeU.add(southRemovePanel, BorderLayout.SOUTH);
		}

		removeU.setVisible(false);

		removeUser.addItemListener(new ItemListener() {
			boolean display = false;

			public void itemStateChanged(ItemEvent e) {
				display = true;
				removeU.setVisible(display);
				display = false;
				createU.setVisible(display);
				modifyU.setVisible(display);
			}
		});

		displayPaneUsers.add(createU);
		displayPaneUsers.add(modifyU);
		displayPaneUsers.add(removeU);

		add(displayPaneUsers);
		System.out.println(createTextFields[0].getText());
		System.out.println("hello");
		if (createTextFields[1].getText().startsWith("1")) {
			jcbSelectRole.setSelectedItem("Administrator");
		} else if (createTextFields[0].getText().startsWith("2")) {
			jcbSelectRole.setSelectedItem("Librarian");
		}
	}

	public void displayLibAutoGenID() {
		int newID;
		int newIDLib;
		String lastID = mainDB.getLastLibsStaffRegID();
		int id = Integer.parseInt(lastID);
		newID = id + 1;
		createTextFields[0].setText(newID + "");
	}

	public void displayAdminAutoGenID() {
		int newID;
		int newIDLib;
		String lastID = mainDB.getLastAdminStaffRegID();
		int id = Integer.parseInt(lastID);
		newID = id + 1;
		createTextFields[0].setText(newID + "");
	}

	public void addColumnsHeadingUser(String[] userCols) {
		for (int i = 0; i < userCols.length; i++) {
			colsUser.addElement((String) userCols[i]);
		}
	}

	public void showUserDetails() {
		ListIterator<RecordsUserDetails> li = mainDB.getAllUserDetails();
		while (li.hasNext()) {
			RecordsUserDetails records = li.next();
			addUserRows(records.getStaffID(), records.getFistName(),
					records.getSurname(), records.getMiddlename(),
					records.getDateOB(), records.getAddress(),
					records.getNatID(), records.getEmail(),
					records.getDateReg());
		}
		tableUser.addNotify();
	}

	public void addUserRows(String staffID, String fistName, String surname,
			String middlename, String dateOB, String address, String NatID,
			String email, String dateReg) {
		Vector r = new Vector();
		r = returnElements(staffID, fistName, surname, middlename, dateOB,
				address, NatID, email, dateReg);
		rowsUser.addElement(r);
	}

	public Vector returnElements(String staffID, String fistName,
			String surname, String middlename, String dateOB, String address,
			String NatID, String email, String dateReg) {
		Vector t = new Vector();
		t.addElement((String) staffID);
		t.addElement((String) fistName);
		t.addElement((String) surname);
		t.addElement((String) middlename);
		t.addElement((String) dateOB);
		t.addElement((String) address);
		t.addElement((String) NatID);
		t.addElement((String) email);
		t.addElement((String) dateReg);

		return t;
	}

	public void validateUserRegistration() {

		if (!(passConf.getText().equals(passwordField.getText()))) {
			JOptionPane.showMessageDialog(this, "Passwords do not match",
					"Password-Error", JOptionPane.ERROR_MESSAGE);
			passConf.grabFocus();
			return;
		}

		if (createTextFields[0].getText().equals("")
				|| createTextFields[0].getText() == null) {
			JOptionPane.showMessageDialog(null,
					"Staff ID field should not be empty");
			createTextFields[0].grabFocus();
			return;
		}
		if (createTextFields[1].getText().equals("")
				|| createTextFields[1].getText() == null) {
			JOptionPane.showMessageDialog(null,
					"First Name field should not be empty");
			createTextFields[1].grabFocus();
			return;
		}
		if (createTextFields[2].getText().equals("")
				|| createTextFields[2].getText() == null) {
			JOptionPane.showMessageDialog(null,
					"Surname field should not be empty");
			createTextFields[2].grabFocus();
			return;
		}
		if (createTextFields[3].getText().equals("")
				|| createTextFields[3].getText() == null) {
			JOptionPane.showMessageDialog(null,
					"Middlename field should not be empty");
			createTextFields[3].grabFocus();
			return;
		}
		String at = "@";
		if (!(createTextFields[4].getText().contains(at))
				|| createTextFields[4].getText() == null) {
			JOptionPane.showMessageDialog(null, "not a valid email address");
			createTextFields[4].grabFocus();
			return;
		}
		if (createTextFields[5].getText().equals("")
				|| createTextFields[5].getText() == null) {
			JOptionPane.showMessageDialog(null,
					"national ID field should not be empty");
			createTextFields[5].grabFocus();
			return;
		}
		String[] credentials = new String[7];

		for (int i = 0; i < credentials.length; i++) {
			credentials[i] = createTextFields[i].getText();
		}
		int result = mainDB.addUser(credentials);
		int result2 = 0;
		int result3 = mainDB.updateUserBirthDay(textDate.getText(),
				createTextFields[0].getText());
		if (result == 1) {

			result2 = mainDB.saveUserLoginDetails(
					createTextFields[0].getText(), passwordField.getText());
		}
		if (result == 1 && result2 == 1 && result3 == 1) {
			JOptionPane.showMessageDialog(null, "User Added!",
					"Process Successful!", JOptionPane.PLAIN_MESSAGE);
			for (int i = 0; i < credentials.length; i++) {
				createTextFields[i].setText("");
			}
			passwordField.setText("");
			passConf.setText("");
			DefaultTableModel dm = (DefaultTableModel) tableUser.getModel();
			dm.getDataVector().removeAllElements();
			showUserDetails();

		} else {
			JOptionPane.showMessageDialog(null, "Error adding user!",
					"Process Failed!", JOptionPane.PLAIN_MESSAGE);
		}

	}
}
