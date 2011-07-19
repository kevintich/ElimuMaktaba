package elimu_maktabaAdminScreen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;
import elimu_maktabaDatabaseConnection.RecordsMemberBlackList;
import elimu_maktabaDatabaseConnection.RecordsMemberRegisrationAccounts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ListIterator;
import java.util.Vector;

public class AccountsTabbedPanePanel extends JPanel {
	JRadioButton registrationAccountsRadioB, blackListAccountsRadioB;
	ButtonGroup buttongroupAccounts;
	JPanel accountsPaneRadio, accountsRegPanel, accountsBLPanel,
			mainAccountsJPanel;
	GridLayout UGrid;
	JScrollPane tableRegScrllPane, tableBLScrollpane;
	JTable tableRegistration, tableBlackList;
	DefaultTableModel tableModelRegistration, tableModelBlackList;
	JTextField searchMemberTextReg, searchMemberTextBL;
	JButton searchMemberIDbtn1, searchMemberIDbtn2, populateTabelReg,
			populateTableBL;
	JPanel searchPanel1, searchPanel2;
	JPanel centerRegAccountspanel, memberRegistrationSearchFields,
			memberBlackListSearchFields;
	JPanel createMGrid, createBGrid;
	JLabel[] createLabels = new JLabel[5];
	JLabel[] createBLLabel = new JLabel[2];
	String[] labels = { "Membership ID: ", "Amount Paid : ",
			"Membership Duration: ", "Date Recorded: ", "Temorary Memeber:" };
	String[] blackListLabels = { "MemberID", "Offence_Paid" };
	JTextField[] createMTextFields = new JTextField[5];
	JTextField[] createBTextFields = new JTextField[5];

	ElimuMaktabaMainDatabase mainDB = null;
	JPanel displayAllRegPanel, displayAllBlPanel;
	Vector rowsReg = new Vector();
	Vector colsReg = new Vector();
	Vector rowsBL = new Vector();
	Vector colsBL = new Vector();
	JButton buttonRefreshReg;
	JButton buttonRefreshBL;
	String[] regCol = {"MemberID","Amount(ksh)","Duration","Date Recorded","Temporary"};
	String[] blCol = {"MemberID","OffencePaid"};
	public AccountsTabbedPanePanel(ElimuMaktabaMainDatabase db) {
		mainDB = db;

		setLayout(new BorderLayout());

		registrationAccountsRadioB = new JRadioButton(
				"Member Registration Accounts");
		registrationAccountsRadioB.setSelected(true);
		blackListAccountsRadioB = new JRadioButton("Blacklist Accounts");

		buttongroupAccounts = new ButtonGroup();
		buttongroupAccounts.add(registrationAccountsRadioB);
		buttongroupAccounts.add(blackListAccountsRadioB);

		accountsPaneRadio = new JPanel();
		accountsPaneRadio.setLayout(new BoxLayout(accountsPaneRadio,
				BoxLayout.PAGE_AXIS));
		accountsPaneRadio.add(registrationAccountsRadioB);
		accountsPaneRadio.add(blackListAccountsRadioB);

		add(accountsPaneRadio, BorderLayout.WEST);

		mainAccountsJPanel = new JPanel();

		accountsRegPanel = new JPanel();
		accountsRegPanel.setBorder(BorderFactory
				.createTitledBorder("Registration Accounts..."));
		accountsRegPanel.setPreferredSize(new Dimension(505, 590));
		//accountsRegPanel.setVisible(false);

		searchPanel1 = new JPanel();
		searchPanel1.setBorder(BorderFactory
				.createTitledBorder("Search specific memberID"));
		searchPanel1.setPreferredSize(new Dimension(400, 60));
		searchMemberTextReg = new JTextField(20);
		searchMemberIDbtn1 = new JButton("Search");

		searchPanel1.add(searchMemberTextReg);
		searchPanel1.add(searchMemberIDbtn1);
		accountsRegPanel.add(searchPanel1, BorderLayout.NORTH);

		createMGrid = new JPanel();
		UGrid = new GridLayout(5, 2);
		UGrid.setVgap(5);
		createMGrid.setLayout(UGrid);
		{
			for (int i = 0; i < createLabels.length; i++) {
				createLabels[i] = new JLabel(labels[i],SwingConstants.RIGHT);
				createMGrid.add(createLabels[i]);

				createMTextFields[i] = new JTextField(15);
				createMGrid.add(createMTextFields[i]);
			}

			memberRegistrationSearchFields = new JPanel(new FlowLayout(
					FlowLayout.CENTER));
			memberRegistrationSearchFields
					.setBorder(BorderFactory
							.createTitledBorder("Found specific member reg account details..."));
			// memberRegistrationSearchFields.setVisible(false);
			memberRegistrationSearchFields.setPreferredSize(new Dimension(490,
					200));
			memberRegistrationSearchFields.add(createMGrid);

			searchMemberIDbtn1.addActionListener(new ActionListener() {

				int count = 0;
				String[] foundDetails = new String[5];

				public void actionPerformed(ActionEvent e) {

					foundDetails = mainDB.getUserRegistrationDetails(searchMemberTextReg.getText());

					if (foundDetails[0] != null) {
						for (int i = 0; i < foundDetails.length; i++) {
							createMTextFields[i].setText(foundDetails[i]);
						}

						// memberRegistrationSearchFields.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null,
								"User specified doesn't exist!", "Oops!",
								JOptionPane.PLAIN_MESSAGE);
					}

				}
			});

			accountsRegPanel.add(memberRegistrationSearchFields,
					BorderLayout.CENTER);

			displayAllRegPanel = new JPanel(new BorderLayout());
			displayAllRegPanel
					.setBorder(BorderFactory
							.createTitledBorder("Displaying all Reg Accounts details..."));
			tableRegScrllPane = new JScrollPane();
			{
				// accountsBLPanel.add(tableBLScrollpane, BorderLayout.NORTH);
				tableModelRegistration = new DefaultTableModel();
				addColumnsHeadingReg(regCol);
				tableModelRegistration.setDataVector(rowsReg, colsReg);
				
				tableRegistration = new JTable();
				tableRegistration.enable(false);
				// table.setRowSelectionAllowed(true);
				tableRegistration.sizeColumnsToFit(true);

				tableRegScrllPane.setViewportView(tableRegistration);
				tableRegistration.getParent().setBackground(Color.WHITE);
				tableRegistration.setModel(tableModelRegistration);
				tableRegScrllPane.setPreferredSize(new Dimension(480, 230));

			}
	
			displayAllRegPanel.add(tableRegScrllPane,BorderLayout.NORTH);
			JPanel panelHoldRefreshReg = new JPanel(new FlowLayout(FlowLayout.CENTER));
			buttonRefreshReg = new JButton("Refresh");
			panelHoldRefreshReg.add(buttonRefreshReg);
			buttonRefreshReg.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					DefaultTableModel dm = (DefaultTableModel)tableRegistration.getModel();
					dm.getDataVector().removeAllElements();
					showOnJTableReg();
				}
			});
			displayAllRegPanel.add(panelHoldRefreshReg,BorderLayout.CENTER);
	
			accountsRegPanel.add(displayAllRegPanel, BorderLayout.SOUTH);

			registrationAccountsRadioB.addItemListener(new ItemListener() {
				boolean display = false;

				public void itemStateChanged(ItemEvent e) {
					display = true;
					accountsRegPanel.setVisible(display);
					display = false;
					accountsBLPanel.setVisible(display);
				}
			});

			accountsBLPanel = new JPanel();
			accountsBLPanel.setBorder(BorderFactory
					.createTitledBorder("BlackList Accounts....."));
			accountsBLPanel.setPreferredSize(new Dimension(505, 590));
			accountsBLPanel.setVisible(false);

			searchPanel2 = new JPanel();
			searchPanel2.setBorder(BorderFactory
					.createTitledBorder("Search specific blacklist memberID"));
			searchPanel2.setPreferredSize(new Dimension(400, 60));
			searchMemberTextBL = new JTextField(20);
			searchMemberIDbtn2 = new JButton("Search");

			searchPanel2.add(searchMemberTextBL);
			searchPanel2.add(searchMemberIDbtn2);

			createBGrid = new JPanel();
			UGrid = new GridLayout(2, 2);
			UGrid.setVgap(5);
			createBGrid.setLayout(UGrid);
			{
				for (int i = 0; i < createBLLabel.length; i++) {
					createBLLabel[i] = new JLabel(blackListLabels[i],SwingConstants.RIGHT);
					createBGrid.add(createBLLabel[i]);

					createBTextFields[i] = new JTextField(15);
					createBGrid.add(createBTextFields[i]);
				}

				memberBlackListSearchFields = new JPanel(new FlowLayout(
						FlowLayout.CENTER));
				memberBlackListSearchFields
						.setBorder(BorderFactory
								.createTitledBorder("Found specific blacklist account details..."));
				// memberRegistrationSearchFields.setVisible(false);
				memberBlackListSearchFields.setPreferredSize(new Dimension(490,
						200));
				memberBlackListSearchFields.add(createBGrid);

				searchMemberIDbtn2.addActionListener(new ActionListener() {

					String[] foundDetails = new String[2];

					public void actionPerformed(ActionEvent e) {
						foundDetails = mainDB
								.getBlackListDetails(searchMemberTextBL
										.getText());

						if (foundDetails[0] != null) {
							for (int i = 0; i < foundDetails.length; i++) {
								createBTextFields[i].setText(foundDetails[i]);
							}

							// memberRegistrationSearchFields.setVisible(true);

						} else {
							JOptionPane.showMessageDialog(null,
									"User specified doesn't exist in the blacklist!", "Oops!",
									JOptionPane.PLAIN_MESSAGE);
						}

					}
				});
				accountsBLPanel.add(searchPanel2, BorderLayout.NORTH);

				accountsBLPanel.add(memberBlackListSearchFields,
						BorderLayout.CENTER);

				displayAllBlPanel = new JPanel(new BorderLayout());
				displayAllBlPanel
						.setBorder(BorderFactory
								.createTitledBorder("Displaying all BlackList Accounts..."));
				tableBLScrollpane = new JScrollPane();
				{
					// accountsBLPanel.add(tableBLScrollpane,
					// BorderLayout.NORTH);
					tableModelBlackList = new DefaultTableModel();
					addColumnsHeadingBL(blCol);
					tableModelBlackList.setDataVector(rowsBL, colsBL);
					tableBlackList = new JTable();
					tableBlackList.enable(false);
					// table.setRowSelectionAllowed(true);
					tableBlackList.sizeColumnsToFit(true);

					tableBLScrollpane.setViewportView(tableBlackList);
					tableBlackList.getParent().setBackground(Color.WHITE);
					tableBlackList.setModel(tableModelBlackList);
					tableBLScrollpane.setPreferredSize(new Dimension(480, 230));

				}
				displayAllBlPanel.add(tableBLScrollpane,BorderLayout.NORTH);
				JPanel panelHoldrefreshBL = new JPanel(new FlowLayout(FlowLayout.CENTER));
				buttonRefreshBL = new JButton("Refresh");
				panelHoldrefreshBL.add(buttonRefreshBL);
				buttonRefreshBL.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						DefaultTableModel dm = (DefaultTableModel)tableBlackList.getModel();
						dm.getDataVector().removeAllElements();
						showOnJTableBL();
					}
				});
				displayAllBlPanel.add(panelHoldrefreshBL,BorderLayout.CENTER);
				accountsBLPanel.add(displayAllBlPanel, BorderLayout.SOUTH);

				blackListAccountsRadioB.addItemListener(new ItemListener() {
					boolean display = false;

					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						display = true;
						accountsBLPanel.setVisible(display);
						display = false;
						accountsRegPanel.setVisible(false);

					}
				});

				mainAccountsJPanel.add(accountsRegPanel);
				mainAccountsJPanel.add(accountsBLPanel);
				add(mainAccountsJPanel);
				
			}

		}
	}
	Vector<String> listelements = new Vector<String>();

	public void addRowReg(String meID, String amount, String duration,String date,String temp) {
		Vector r = new Vector();
		r = returnElementReg(meID,amount, duration,date,temp);
		rowsReg.addElement(r);
		//table.addNotify();
	}

	public Vector returnElementReg(String meID, String amount, String duration,String date,String temp) {
		Vector t = new Vector();
		t.addElement((String) meID);
		t.addElement((String) amount);
		t.addElement((String) duration);
		t.addElement((String) date);
		t.addElement((String) temp);

		return t;
	}
	public void showOnJTableReg() {
		ListIterator<RecordsMemberRegisrationAccounts> li = mainDB.getAllDetailsFromRegAccount();
		while (li.hasNext()) {
			RecordsMemberRegisrationAccounts recordsMemberRegisrationAccounts = li.next();
		   	addRowReg(recordsMemberRegisrationAccounts.getMemberID(), recordsMemberRegisrationAccounts.getAmountPaid(), recordsMemberRegisrationAccounts.getDuration(),recordsMemberRegisrationAccounts.getDateID(),recordsMemberRegisrationAccounts.getTempMember());
		}
		

		tableRegistration.addNotify();
	}
	public void addColumnsHeadingReg(String[] colName){
		for (int i = 0; i < colName.length; i++)
			colsReg.addElement((String) colName[i]);
	}

	public void showOnJTableBL() {
		ListIterator<RecordsMemberBlackList> li = mainDB.getAllDetailsFromAccountsBL();
		while (li.hasNext()) {
			RecordsMemberBlackList recordsBl = li.next();
		   	addRowBL(recordsBl.getMemID(), recordsBl.getOffencePaid());
		}
		tableBlackList.addNotify();
	}
	public void addRowBL(String memID,String offencePaid){
		Vector r = new Vector();
		r = returnElementBL(memID,offencePaid);
		rowsBL.addElement(r);
	}
	public Vector returnElementBL(String memID,String offencePaid){
		Vector t = new Vector();
		t.addElement((String) memID);
		t.addElement((String) offencePaid);
		return t;
	}
	public void addColumnsHeadingBL(String[] colName){
		for (int i = 0; i < colName.length; i++)
			colsBL.addElement((String) colName[i]);
	}
}