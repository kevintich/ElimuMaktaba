package elimu_maktabaLibrarianScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSetMetaData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Vector;

import jdatechooser.JDateChooser;

import elimu_maktabaDatabaseConnection.*;

public class RegisterJPanel extends JPanel {
	JPanel screenJPanel, registerPanel, registeRadioBntJPanel, regDetailPanel,
			regDisplayPanel, IssuebooksPanel, veiwlistviewerPanel, stockPANEL,
			AccountingPanel;
	JMenuBar menuBar;
	JTabbedPane mainTabbedPane;
	JRadioButton jrbRegisterMember, jrbModifyMember, jrRemoveMember;
	JTable membersTable;
	JScrollPane tableMembersScrollPane;
	ButtonGroup bg;
	JPanel detailDisplayPANEL, innerregDetailPanel;
	JLabel innerregDetailLabel, memIDLabel;
	JPanel modifyMembersPanel;
	JPanel centerPanel;
	DefaultTableModel memTableModel;
	JPanel createRegTextFields, createModifyTextFieldsPn,
			createRemoveTextFieldsPn;
	JTextField[] registrationTextFields = new JTextField[8];
	JTextField[] registerAccountFields = new JTextField[1];
	JTextField[] modifyDetailsJF = new JTextField[9];
	JTextField[] removeDetailsJF = new JTextField[9];

	JLabel[] regLabels = new JLabel[8];
	JLabel[] accLabels = new JLabel[1];
	JLabel[] modLabels = new JLabel[9];
	String[] modifyMemberLabels = { "Membership_ID", "FirstName", "Surname",
			"MiddleName", "Gender", "MobileNumber", "e-mail", "NationalID",
			"D.O.B" };
	String[] memberDetailsLabels = { "Membership_ID", "FirstName", "Surname",
			"MiddleName", "Gender(Input M/F)", "MobileNumber", "e-mail",
			"NationalID" };
	JPanel registrationButtonsPanel, accountsButtonsPanel;
	JButton saveDetailsButton, saveAccButton, findMemberButton,
			buttonModifyUpdate, buttonRemoveMember, findMemberRemoveButton,
			buttonRefreshTable;
	JPanel panelAccounts, panelAccFields, findModify, removeMemberPanel,
			panelFindRemove;
	JLabel labelAcc, temLabel, findLabel, labelDuration;
	JTextField jtfamount, findByID, accMemberID, durationText;
	ElimuMaktabaMainDatabase mainDB = null;
	JComboBox comboTemp;
	JPanel memPaneRadio;
	JTextField findByIDRemove;
	String colNames[] = { "MemID", "FirstName", "Surname", "MiddleName",
			"Gender", "Mobile", "e-mail", "NatID", "D.O.B", "Date Recorded" };
	Vector colHeadingsVec = new Vector();
	Vector rowVec = new Vector();
	private JScrollPane scrollPane;
	private JTable table;
	private RegisterTableModel tableModel;
	private HashMap<Integer, HashMap<Integer, String>> memberMap;
	private HashMap<String, String> valuesForMember;
	private Vector<HashMap<Integer, String>> vector;
	private Vector<String> vectorColumnName;
	private JButton discardButton;
	boolean set = false;
	JDateChooser dateChooser;
	JFormattedTextField textDate;
	JPanel panelDate;

	public RegisterJPanel(ElimuMaktabaMainDatabase db) {
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		mainDB = db;
		dateChooser = new JDateChooser(24, 6, 1995, 1900, 3000, 0);
		registeRadioBntJPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel();
		bg = new ButtonGroup();
		jrbRegisterMember = new JRadioButton("Register Members");
		jrbModifyMember = new JRadioButton("Modify Member");
		jrRemoveMember = new JRadioButton("Remove Member");
		panelDate = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bg.add(jrbModifyMember);
		bg.add(jrbRegisterMember);
		bg.add(jrRemoveMember);
		jrbRegisterMember.setSelected(true);
		jrbRegisterMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				modifyMembersPanel.setVisible(false);
				detailDisplayPANEL.setVisible(true);
				removeMemberPanel.setVisible(false);
			}
		});

		jrbModifyMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				detailDisplayPANEL.setVisible(false);
				modifyMembersPanel.setVisible(true);
				removeMemberPanel.setVisible(false);

			}
		});
		jrRemoveMember.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				detailDisplayPANEL.setVisible(false);
				modifyMembersPanel.setVisible(false);
				removeMemberPanel.setVisible(true);

			}
		});
		memPaneRadio = new JPanel();
		memPaneRadio
				.setLayout(new BoxLayout(memPaneRadio, BoxLayout.PAGE_AXIS));
		memPaneRadio.add(jrbRegisterMember);
		memPaneRadio.add(jrbModifyMember);
		memPaneRadio.add(jrRemoveMember);

		add(memPaneRadio, BorderLayout.WEST);

		// To set detailDisplayPanel visible or invisible
		detailDisplayPANEL = new JPanel(new BorderLayout());
		detailDisplayPANEL.setPreferredSize(new Dimension(900, 650));
		// detailDisplayPANEL.setVisible(false);

		regDetailPanel = new JPanel(new BorderLayout());
		innerregDetailPanel = new JPanel(new BorderLayout());
		innerregDetailPanel.setBorder(BorderFactory
				.createTitledBorder("Enter New Members"));
		innerregDetailPanel.setPreferredSize(new Dimension(600, 300));
		JPanel holdfields = new JPanel();
		createRegTextFields = new JPanel(new GridLayout(9, 2, 5, 5));
		for (int i = 0; i < memberDetailsLabels.length; i++) {
			regLabels[i] = new JLabel(memberDetailsLabels[i],
					SwingConstants.RIGHT);
			createRegTextFields.add(regLabels[i]);

			registrationTextFields[i] = new JTextField(15);
			createRegTextFields.add(registrationTextFields[i]);
		}

		registrationTextFields[0].setEditable(false);
		holdfields.add(createRegTextFields);
		JLabel labelDob = new JLabel(
				"                                                           D.O.B");
		JButton button = dateChooser.btnPop;
		textDate = dateChooser.txtDate;
		panelDate.add(labelDob);
		panelDate.add(textDate);
		panelDate.add(button);

		registrationButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		saveDetailsButton = new JButton("Save Registration Details");
		saveDetailsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				verifyMemberRegistrationDetails();
				

			}
		});
		registrationButtonsPanel.add(saveDetailsButton);

		innerregDetailPanel.add(holdfields, "North");
		innerregDetailPanel.add(panelDate, "Center");
		innerregDetailPanel.add(registrationButtonsPanel, "South");

		panelAccounts = new JPanel();
		panelAccounts.setBorder(BorderFactory
				.createTitledBorder("Pay Accounts Fee"));
		panelAccounts.setPreferredSize(new Dimension(400, 400));

		JPanel holdfields2 = new JPanel();
		panelAccFields = new JPanel(new GridLayout(4, 2, 4, 4));
		memIDLabel = new JLabel("Membership_ID");
		labelAcc = new JLabel("Amount Paid(Ksh.)");
		labelDuration = new JLabel("Duration");
		temLabel = new JLabel("Temporary Member");

		jtfamount = new JTextField(5);
		accMemberID = new JTextField(5);
		durationText = new JTextField(5);
		generateMemberID();
		generateMemberIDAccounts();
		registrationTextFields[0].addKeyListener(new KeyListener() {
			public synchronized void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				accMemberID.setText(registrationTextFields[0].getText());
			}

		});

		accMemberID.setEditable(false);
		comboTemp = new JComboBox();
		comboTemp.addItem("-select type-");
		comboTemp.addItem("Yes");
		comboTemp.addItem("No");
		panelAccFields.add(memIDLabel);
		panelAccFields.add(accMemberID);
		panelAccFields.add(labelAcc);
		panelAccFields.add(jtfamount);
		panelAccFields.add(labelDuration);
		panelAccFields.add(durationText);
		panelAccFields.add(temLabel);
		panelAccFields.add(comboTemp);

		holdfields2.add(panelAccFields);
		accountsButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		saveAccButton = new JButton("Save Account Details");
		saveAccButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				verifyMemberAccountsFields();
			}
		});
		accountsButtonsPanel.add(saveAccButton);

		panelAccounts.add(holdfields2, "North");
		panelAccounts.add(accountsButtonsPanel, "South");

		regDetailPanel.add(innerregDetailPanel, BorderLayout.WEST);
		regDetailPanel.add(panelAccounts, BorderLayout.CENTER);

		regDisplayPanel = new JPanel(new BorderLayout());
		regDisplayPanel.setBorder(BorderFactory
				.createTitledBorder("View All Members"));
		regDisplayPanel.setPreferredSize(new Dimension(700, 150));
		regDisplayPanel.setToolTipText("hahahahah");
		tableMembersScrollPane = new JScrollPane();
		{
			memTableModel = new DefaultTableModel();
			addColumnsHeading(colNames);
			memTableModel.setDataVector(rowVec, colHeadingsVec);
			membersTable = new JTable();
			// membersTable.setAutoCreateRowSorter(true);
			membersTable.setEnabled(false);
			membersTable.sizeColumnsToFit(true);

			tableMembersScrollPane.setViewportView(membersTable);
			membersTable.getParent().setBackground(Color.WHITE);
			membersTable.setModel(memTableModel);
			tableMembersScrollPane.setPreferredSize(new Dimension(700, 190));

		}
		regDisplayPanel.add(tableMembersScrollPane, BorderLayout.NORTH);
		JPanel holdButtonRefresh = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonRefreshTable = new JButton("Refresh");
		buttonRefreshTable.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel dm = (DefaultTableModel) membersTable
						.getModel();
				dm.getDataVector().removeAllElements();
				showRecordsOnJTableReg();
			}
		});

		holdButtonRefresh.add(buttonRefreshTable);
		regDisplayPanel.add(holdButtonRefresh, BorderLayout.CENTER);

		modifyMembersPanel = new JPanel(new BorderLayout());
		modifyMembersPanel.setBorder(BorderFactory
				.createTitledBorder("Modify members...."));
		modifyMembersPanel.setPreferredSize(new Dimension(900, 450));

		findModify = new JPanel(new FlowLayout(FlowLayout.CENTER));
		findModify.setBorder(BorderFactory.createTitledBorder("Find..."));
		findModify.setPreferredSize(new Dimension(700, 70));
		findLabel = new JLabel("Find By Member ID: ");
		findByID = new JTextField(15);
		findMemberButton = new JButton("Find...");
		findMemberButton.addActionListener(new ActionListener() {
			String[] foundDetails = new String[9];

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.err.println("Main db is :" + mainDB);
				foundDetails = mainDB.findSpecificMembers(findByID.getText());
				if (foundDetails[0] != null) {
					for (int i = 0; i < foundDetails.length; i++) {
						// modifyDetailsJF[i].setText(foundDetails[i]);
					}
					buttonModifyUpdate.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null,
							"MemberID specified doesn't exist!", "Oops!",
							JOptionPane.PLAIN_MESSAGE);

				}
			}
		});
		// findModify.add(findLabel);
		// findModify.add(findByIDTextField);
		// findModify.add(findBookButton);
		JPanel holdfieldsModify = new JPanel(new BorderLayout());

		createTableValues();
		set = true;

		tableModel = new RegisterTableModel(memberMap, vectorColumnName, this);
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(700, 400));

		holdfieldsModify.add(scrollPane, BorderLayout.CENTER);

		JPanel holdButtonUpdate = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonModifyUpdate = new JButton("Apply Changes");
		// buttonModifyUpdate.setEnabled(false);

		buttonModifyUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] details = new String[9];
				int confirm = JOptionPane
						.showConfirmDialog(
								null,
								"Are you sure you want to make these changes permanent?",
								"Confirm Modify!", JOptionPane.YES_NO_OPTION);

				if (confirm == 0) {
					ArrayList<Integer> result = mainDB.commitChanges(tableModel
							.getMap());

					for (int i = 0; i < result.size(); i++) {
						if (result.get(i) == 0) {
							JOptionPane
									.showMessageDialog(
											null,
											"One or more of the rows was not updated correctly!",
											"Process failed!",
											JOptionPane.ERROR_MESSAGE);
							return;
						}
					}

					JOptionPane.showMessageDialog(null, "Rows updated!",
							"Process successful!", JOptionPane.PLAIN_MESSAGE);

					tableModel.fireTableStructureChanged();
				}
			}
		});

		discardButton = new JButton("Discard Changes");

		discardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});

		holdButtonUpdate.add(buttonModifyUpdate);
		holdButtonUpdate.add(discardButton);

		modifyMembersPanel.add(findModify, BorderLayout.NORTH);
		modifyMembersPanel.add(holdfieldsModify, BorderLayout.CENTER);
		modifyMembersPanel.add(holdButtonUpdate, BorderLayout.SOUTH);

		modifyMembersPanel.setVisible(false);

		removeMemberPanel = new JPanel();
		removeMemberPanel.setBorder(BorderFactory
				.createTitledBorder("Remove members...."));
		removeMemberPanel.setPreferredSize(new Dimension(900, 450));
		removeMemberPanel.setVisible(false);
		panelFindRemove = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelFindRemove.setBorder(BorderFactory
				.createTitledBorder("Find Member To Remove..."));
		panelFindRemove.setPreferredSize(new Dimension(700, 70));
		findLabel = new JLabel("Find By Member ID: ");
		findByIDRemove = new JTextField(15);
		findMemberRemoveButton = new JButton("Find...");
		findMemberRemoveButton.addActionListener(new ActionListener() {

			String[] foundDetails = new String[9];

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// System.err.println("Main db is :" + mainDB);
				foundDetails = mainDB.findSpecificMembers(findByIDRemove
						.getText());
				if (foundDetails[0] != null) {
					for (int i = 0; i < foundDetails.length; i++) {
						removeDetailsJF[i].setText(foundDetails[i]);
					}
					buttonRemoveMember.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null,
							"MemberID specified doesn't exist!", "Oops!",
							JOptionPane.PLAIN_MESSAGE);

				}
			}
		});
		panelFindRemove.add(findLabel);
		panelFindRemove.add(findByIDRemove);
		panelFindRemove.add(findMemberRemoveButton);
		JPanel holdfieldsDelete = new JPanel();
		createRemoveTextFieldsPn = new JPanel(new GridLayout(9, 2, 5, 5));
		for (int i = 0; i < modifyMemberLabels.length; i++) {
			modLabels[i] = new JLabel(modifyMemberLabels[i],
					SwingConstants.RIGHT);
			createRemoveTextFieldsPn.add(modLabels[i]);

			removeDetailsJF[i] = new JTextField(15);
			removeDetailsJF[i].setEditable(false);
			createRemoveTextFieldsPn.add(removeDetailsJF[i]);
		}
		holdfieldsDelete.add(createRemoveTextFieldsPn);
		JPanel holdButtonDelete = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonRemoveMember = new JButton("Delete Member");
		buttonRemoveMember.setEnabled(false);
		buttonRemoveMember.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] details = new String[9];
				int confirm = JOptionPane.showConfirmDialog(null,
						"Are you you sure you want to delete Member details?",
						"Confirm Modify!", JOptionPane.YES_NO_OPTION);

				if (confirm == 0) {
					for (int i = 0; i < details.length; i++) {
						details[i] = removeDetailsJF[i].getText();
					}

					int result = mainDB.deleteMember(findByIDRemove.getText());

					if (result == 1) {
						JOptionPane.showMessageDialog(null,
								"Member successfully removed!",
								"Process Successful!",
								JOptionPane.PLAIN_MESSAGE);

						for (int i = 0; i < modifyDetailsJF.length; i++) {
							removeDetailsJF[i].setText("");
						}

						findByIDRemove.setText("");

					} else {
						JOptionPane.showMessageDialog(null,
								"Error removing member!!", "Process Failed!",
								JOptionPane.PLAIN_MESSAGE);
						findByIDRemove.setText("");

					}
				}
			}
		});
		holdButtonDelete.add(buttonRemoveMember);
		removeMemberPanel.add(panelFindRemove);
		removeMemberPanel.add(holdfieldsDelete);
		removeMemberPanel.add(holdButtonDelete);

		detailDisplayPANEL.add(regDetailPanel, BorderLayout.NORTH);
		detailDisplayPANEL.add(regDisplayPanel, BorderLayout.CENTER);

		centerPanel.add(modifyMembersPanel);
		centerPanel.add(detailDisplayPANEL);
		centerPanel.add(removeMemberPanel);

		add(centerPanel, BorderLayout.CENTER);

	}

	public void reset() {
		memberMap.clear();
		memberMap = null;

		vector.clear();
		vector = null;

		vectorColumnName.clear();
		vectorColumnName = null;

		createTableValues();

		tableModel.fireTableStructureChanged();
	}

	public void createTableValues() {
		int rowCount = 0;

		memberMap = new HashMap<Integer, HashMap<Integer, String>>();
		vector = new Vector<HashMap<Integer, String>>();
		vectorColumnName = new Vector<String>();

		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;

		try {
			resultSet = mainDB.allMembers();
			metaData = resultSet.getMetaData();

			while (resultSet.next()) {
				vector.add(new HashMap<Integer, String>());

				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					vector.get(vector.size() - 1)
							.put(i, resultSet.getString(i));
					vectorColumnName.add(metaData.getColumnName(i));
				}

				memberMap.put(rowCount, vector.get(vector.size() - 1));
				rowCount++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (set)
			tableModel.setMapVector(memberMap, vectorColumnName);
	}

	public void addColumnsHeading(String[] names) {
		for (int i = 0; i < names.length; i++)
			colHeadingsVec.addElement((String) names[i]);
	}

	public void showRecordsOnJTableReg() {
		ListIterator<RecordsMemberRegDetails> li = mainDB
				.getAllMemberRegistrationDetails();
		while (li.hasNext()) {
			RecordsMemberRegDetails records = li.next();
			addRows(records.getMemberID(), records.getFirstName(),
					records.getSurName(), records.getMiddleName(),
					records.getGender(), records.getMobileNo(),
					records.getEmail(), records.getNatID(),
					records.getDateOfBirth(), records.getDateReg());
		}
		membersTable.addNotify();
	}

	public void addRows(String memberID, String firstName, String surName,
			String middleName, String gender, String mobileNo, String email,
			String NatID, String dateOfBirth, String dateReg) {
		Vector r = new Vector();
		r = returnElements(memberID, firstName, surName, middleName, gender,
				mobileNo, email, NatID, dateOfBirth, dateReg);
		rowVec.addElement(r);
	}

	public Vector returnElements(String memberID, String firstName,
			String surName, String middleName, String gender, String mobileNo,
			String email, String NatID, String dateOfBirth, String dateReg) {
		Vector t = new Vector();
		t.addElement((String) memberID);
		t.addElement((String) firstName);
		t.addElement((String) surName);
		t.addElement((String) middleName);
		t.addElement((String) gender);
		t.addElement((String) mobileNo);
		t.addElement((String) email);
		t.addElement((String) NatID);
		t.addElement((String) dateOfBirth);
		t.addElement((String) dateReg);
		return t;
	}

	public void generateMemberIDAccounts() {
		String id = mainDB.getLastMemberID();
		int idInteger = Integer.parseInt(id);
		int addOne = idInteger + 1;
		accMemberID.setText(addOne + "");
	}

	public void generateMemberID() {
		String id = mainDB.getLastMemberID();
		int idInteger = Integer.parseInt(id);
		int addOne = idInteger + 1;
		registrationTextFields[0].setText(addOne + "");
	}

	public JTable getTable() {
		return table;
	}

	public void verifyMemberRegistrationDetails() {
		String firstN = registrationTextFields[1].getText();
		String surName = registrationTextFields[2].getText();
		String middleName = registrationTextFields[3].getText();
		String gender = registrationTextFields[4].getText();
		String mobileNumber = registrationTextFields[5].getText();
		String email = registrationTextFields[6].getText();
		String nationalID = registrationTextFields[7].getText();

		if (firstN.equals("") || firstN == null) {
			JOptionPane.showMessageDialog(null,
					"First Name field should not be empty");
			return;
		}
		if (surName.equals("") || surName == null) {
			JOptionPane.showMessageDialog(null,
					"SurName field should not be empty");
			return;
		}
		if (middleName.equals("") || middleName == null) {
			JOptionPane.showMessageDialog(null,
					"MiddleName field should not be empty");
			return;
		}
		
		if (!(gender.equals("M") || gender.equals("F"))) {
			JOptionPane.showMessageDialog(null, "Gender should be " + 'M'
					+ " or " + 'F' + " only");
			return;
		}
		
		try {
			int mobile = Integer.parseInt(mobileNumber);
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null,
					"wrong mobile number");
			return;
		}
		String at = "@";
		if (!(email.contains(at)) || email == null) {
			JOptionPane.showMessageDialog(null, "not a valid email address");
			return;
		}
		if (nationalID.equals("") || nationalID == null) {
			JOptionPane.showMessageDialog(null,
					"NationalID field should not be empty");
			return;
		}
		
		String[] newMemberDetails = new String[8];

		for (int i = 0; i < newMemberDetails.length; i++) {

			newMemberDetails[i] = registrationTextFields[i].getText();
		}
		int result = mainDB.addNewMember(newMemberDetails);
		int result2 = mainDB.updateMemberBirthDay(textDate.getText(),
				registrationTextFields[0].getText());
		if (result == 1 && result2 == 1) {
			JOptionPane.showMessageDialog(null, "Member successfully Added!",
					"Process Successful!", JOptionPane.PLAIN_MESSAGE);
			for (int i = 0; i < newMemberDetails.length; i++) {
				registrationTextFields[i].setText("");
			}
			generateMemberID();
			DefaultTableModel dm = (DefaultTableModel) membersTable.getModel();
			dm.getDataVector().removeAllElements();
			showRecordsOnJTableReg();
			
		} else {
			JOptionPane.showMessageDialog(null, "Error adding member!",
					"Process Failed!", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void verifyMemberAccountsFields() {
		String memID = accMemberID.getText();

		String amount = jtfamount.getText();
		String duration = durationText.getText();
		String temp = (String) comboTemp.getSelectedItem();
		try {
			int amountpaid = Integer.parseInt(amount);
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null,
					"wrong money value. Digits only");
			return;
		}

		if (temp.startsWith("-")) {
			JOptionPane
					.showMessageDialog(null, "please select membership type");
			return;

		}
		if (duration.equals("") || duration == null) {
			JOptionPane.showMessageDialog(null,
					"Duration field should not be empty");
			return;
		}
		if (temp.equals("") || temp == null) {
			JOptionPane.showMessageDialog(null,
					"please select the type of membership");
			return;
		}

		int result;
		result = mainDB.saveAccountDetails(memID, amount, duration, temp);
		// System.out.println(amount);
		if (result == 1) {
			JOptionPane.showMessageDialog(null, "Accounts successfully saved!",
					"Process Successful!", JOptionPane.PLAIN_MESSAGE);
			generateMemberIDAccounts();
			jtfamount.setText("");
			durationText.setText("");
			comboTemp.setSelectedIndex(0);
		} else {
			JOptionPane.showMessageDialog(null, "Error saving records!",
					"Process Failed!", JOptionPane.PLAIN_MESSAGE);
		}
	}

}
