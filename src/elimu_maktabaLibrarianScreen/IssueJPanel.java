package elimu_maktabaLibrarianScreen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;
import elimu_maktabaDatabaseConnection.RecordsAllBooks;
import elimu_maktabaDatabaseConnection.RecordsMemberRegDetails;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.ListIterator;
import java.util.Vector;

public class IssueJPanel extends JPanel {
	Timer timer = null;
	int count = 0;
	JPanel registeRadioBntJPanel, regDetailPanel, regDisplayPanel;
	JRadioButton jrbRegisterBooks, jrbscanbooks, jrbReturnBook;
	ButtonGroup bg;
	JPanel detailDisplayPANEL, innerregDetailPanel, panelIssueBooks;;
	JPanel panelRegisterBooks;
	JPanel createRegTextFields;
	JTextField[] registrationTextFields = new JTextField[7];
	JTextField[] modifyDetailsJF = new JTextField[9];
	JLabel[] regLabels = new JLabel[9];

	String[] memberDetailsLabels = { "Bookbarcodenumbers	", "BookTitle",
			"BookVersion", "BookAuthor", "BookPublisher", "NumberPage",
			"BookGenre" };
	JPanel registrationButtonsPanel, removeBooksPanel;
	JPanel panelReturnBooks;
	JButton addbookDetailsButton;
	JTextArea processingAreaJTextArea, logAreaJTextArea;
	JTextField scanbarcodeJTextField;
	JScrollPane tableBooksScrollPane;
	String[] modifyBookLabels = { "BookBarcodeNumber", "MembershipID",
			"DateRecorded", "Returned(Input 'YES' only)" };
	JTextField[] updateTextFields = new JTextField[4];
	JLabel[] updateLabels = new JLabel[4];
	static ElimuMaktabaMainDatabase mainDB = null;
	DefaultTableModel tableBooksModel;
	String[] colNames = { "BarcodeNum", "Title", "Version", "Author",
			"Publisher", "Pages", "Genre" };
	JTable booksTable;
	JPanel findReturn, createReturnyTextFieldsPn;
	JLabel findLabel;
	JTextField findByIDTextField;
	JButton findBookButton, buttonUpdate;
	Vector rowVec = new Vector();
	Vector colHeadingsVec = new Vector();
	JComboBox combomember;
	Vector<String> membershipIDV = new Vector<String>();

	public IssueJPanel(ElimuMaktabaMainDatabase db) {
		mainDB = new ElimuMaktabaMainDatabase();
		db = mainDB;
		setLayout(new BorderLayout());

		panelIssueBooks = new JPanel(new BorderLayout());
		panelReturnBooks = new JPanel(new BorderLayout());

		registeRadioBntJPanel = new JPanel(new BorderLayout());
		JPanel innerRadioBntJPanel = new JPanel(new GridLayout(3, 0));
		panelRegisterBooks = new JPanel();
		panelRegisterBooks.setVisible(false);
		JLabel spaceLabel01 = new JLabel();
		// innerRadioBntJPanel.add(spaceLabel01);
		bg = new ButtonGroup();
		jrbscanbooks = new JRadioButton("Issue Books");
		jrbscanbooks.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				panelIssueBooks.setVisible(true);
				panelRegisterBooks.setVisible(false);
				panelReturnBooks.setVisible(false);
			}
		});
		jrbscanbooks.setSelected(true);

		bg.add(jrbscanbooks);
		innerRadioBntJPanel.add(jrbscanbooks);
		JLabel spaceLabel02 = new JLabel();
		// innerRadioBntJPanel.add(spaceLabel02);
		JLabel spaceLabel03 = new JLabel();
		// innerRadioBntJPanel.add(spaceLabel03);
		jrbReturnBook = new JRadioButton("Return Book");
		jrbReturnBook.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				panelIssueBooks.setVisible(false);
				panelRegisterBooks.setVisible(false);
				panelReturnBooks.setVisible(true);

			}
		});
		jrbRegisterBooks = new JRadioButton("Register Books");
		jrbRegisterBooks.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				panelIssueBooks.setVisible(false);
				panelRegisterBooks.setVisible(true);
				panelReturnBooks.setVisible(false);
			}
		});
		bg.add(jrbRegisterBooks);
		bg.add(jrbReturnBook);
		innerRadioBntJPanel.add(jrbReturnBook);
		JLabel spaceLabel04 = new JLabel();
		JLabel spaceLabel05 = new JLabel();
		innerRadioBntJPanel.add(jrbRegisterBooks);

		registeRadioBntJPanel.add(innerRadioBntJPanel, BorderLayout.NORTH);
		add(registeRadioBntJPanel, BorderLayout.WEST);
		detailDisplayPANEL = new JPanel(new BorderLayout());
		detailDisplayPANEL.setBackground(Color.DARK_GRAY);
		regDetailPanel = new JPanel(new BorderLayout());
		innerregDetailPanel = new JPanel(new BorderLayout());
		innerregDetailPanel.setBorder(BorderFactory
				.createTitledBorder("Enter New Books"));
		innerregDetailPanel.setPreferredSize(new Dimension(1000, 300));
		JPanel holdfields = new JPanel();
		createRegTextFields = new JPanel(new GridLayout(9, 2, 5, 5));
		for (int i = 0; i < memberDetailsLabels.length; i++) {
			regLabels[i] = new JLabel(memberDetailsLabels[i],
					SwingConstants.RIGHT);
			createRegTextFields.add(regLabels[i]);
			registrationTextFields[i] = new JTextField(15);
			createRegTextFields.add(registrationTextFields[i]);
		}
		holdfields.add(createRegTextFields);
		registrationButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		addbookDetailsButton = new JButton("Add books");
		addbookDetailsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				verifyBookDetails();

			}
		});
		registrationButtonsPanel.add(addbookDetailsButton);
		innerregDetailPanel.add(holdfields, "North");
		innerregDetailPanel.add(registrationButtonsPanel, "South");
		regDetailPanel.add(innerregDetailPanel, BorderLayout.WEST);
		regDisplayPanel = new JPanel();
		regDisplayPanel.setBorder(BorderFactory
				.createTitledBorder("View Number of Books"));
		regDisplayPanel.setPreferredSize(new Dimension(900, 350));
		tableBooksScrollPane = new JScrollPane();
		{
			tableBooksModel = new DefaultTableModel();
			addColumnsHeading(colNames);
			tableBooksModel.setDataVector(rowVec, colHeadingsVec);
			booksTable = new JTable();
			booksTable.sizeColumnsToFit(true);
			booksTable.setAutoscrolls(true);

			tableBooksScrollPane.setViewportView(booksTable);
			booksTable.getParent().setBackground(Color.WHITE);
			booksTable.setModel(tableBooksModel);
			tableBooksScrollPane.setPreferredSize(new Dimension(950, 280));

		}
		
		JPanel panelRefreshButton = new JPanel();
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel dm = (DefaultTableModel) booksTable.getModel();
				dm.getDataVector().removeAllElements();
				showRecordsOnJTable();
			}
		});
		
		panelRefreshButton.add(buttonRefresh,SwingConstants.CENTER);
		
		regDisplayPanel.add(tableBooksScrollPane, BorderLayout.NORTH);
		detailDisplayPANEL.add(regDetailPanel, BorderLayout.NORTH);
		detailDisplayPANEL.add(regDisplayPanel, BorderLayout.CENTER);
		detailDisplayPANEL.add(panelRefreshButton,BorderLayout.SOUTH);
		panelRegisterBooks.add(detailDisplayPANEL);

		// add(panelRegisterBooks, BorderLayout.CENTER);

		// ............................................................................
		JPanel panelHoldCenterPanels = new JPanel();
		panelIssueBooks.setVisible(true);
		JPanel cntborderNORTH = new JPanel();
		JPanel cntPanel = new JPanel();
		cntPanel.setLayout(null);
		cntPanel.setPreferredSize(new Dimension(1000, 200));
		// cntPanel.setBorder(BorderFactory.createBevelBorder(1));
		JLabel memIDlabel = new JLabel("Select Member ID");
		memIDlabel.setFont(new Font("SERIF", Font.BOLD, 20));
		memIDlabel.setBounds(150, 0, 300, 50);

		combomember = new JComboBox(membershipIDV);

		combomember.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				String dbstatus = mainDB.returnBorroweredStatus(combomember
						.getSelectedItem().toString());
				String bookNotReturned = mainDB.getWitheldBook(combomember
						.getSelectedItem().toString());
				if (dbstatus.equals("NO")) {
					logAreaJTextArea.setForeground(Color.red);
					logAreaJTextArea
							.setText("The member "
									+ combomember.getSelectedItem()
									+ " has not returned a book with the identified by the barcode number "
									+ bookNotReturned + "\nCannot Issue Book");
					processingAreaJTextArea.setText("");
					scanbarcodeJTextField.setEnabled(false);
				} else {
					scanbarcodeJTextField.setEnabled(true);
					logAreaJTextArea.setForeground(Color.black);
					logAreaJTextArea
							.setText("No book is currently held by the Member represented by the ID: "
									+ combomember.getSelectedItem());

					processingAreaJTextArea
							.setText("Member ID successfully chosen, now scan book");
					combomember.transferFocus();
					processingAreaJTextArea.setForeground(Color.black);

				}
			}
		});
		getMemberIDs();
		combomember.setBorder(BorderFactory.createBevelBorder(1));
		combomember.setBounds(120, 60, 250, 40);
		cntPanel.add(combomember);

		JLabel titleLabel = new JLabel("Scan book barcode in the field below");
		titleLabel.setFont(new Font("SERIF", Font.BOLD, 20));
		titleLabel.setBounds(520, 0, 600, 50);
		cntPanel.add(titleLabel);
		cntPanel.add(memIDlabel);

		JPanel textPanel = new JPanel();
		scanbarcodeJTextField = new JTextField(14);
		scanbarcodeJTextField.setEnabled(false);

		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = scanbarcodeJTextField.getText();
				System.out.println("Perform action on: " + code + "!");
				String input = scanbarcodeJTextField.getText();

				Vector<String> holdIDs = new Vector<String>();
				holdIDs = mainDB.getBookBCNumbers();
				if (holdIDs.contains(input)) {
					int success = mainDB.addBookToBorrowerDetails(combomember
							.getSelectedItem().toString(),
							scanbarcodeJTextField.getText());
					if (success == 1) {
						processingAreaJTextArea
								.append("\n\nMember "
										+ combomember.getSelectedItem()
										+ " successfully linked to scanned book (barcode number "
										+ scanbarcodeJTextField.getText()
										+ ") . RECORD SAVED: "
										+ new Date().toString());
						scanbarcodeJTextField.setEnabled(false);
						logAreaJTextArea.setText("A book is currently being held by the member represented by the ID " + combomember.getSelectedItem());
					} else {
						processingAreaJTextArea.setForeground(Color.red);
						processingAreaJTextArea
								.setText("Error saving details, check netowork connection");
					}

				} else {
					processingAreaJTextArea.setForeground(Color.red);
					processingAreaJTextArea
							.setText("Book Scanned does not exist in the database.Repeat proccess");
					// combomember.showPopup();
					scanbarcodeJTextField.setEnabled(false);
				}
				scanbarcodeJTextField.setText("");

				timer.stop();
			}
		});

		scanbarcodeJTextField.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {
				// TODO Auto-generated method stub
				if (scanbarcodeJTextField.getText().length() > 0) {
					if (count == 0) {
						timer.start();
						count++;
					} else
						timer.restart();
				}
			}
		});

		scanbarcodeJTextField.setFont(new Font("SERIF", Font.BOLD, 30));
		scanbarcodeJTextField.setHorizontalAlignment(JTextField.CENTER);
		scanbarcodeJTextField.setBorder(BorderFactory.createBevelBorder(1));
		scanbarcodeJTextField.setBounds(500, 60, 400, 40);

		// textPanel.add(scanbarcodeJTextField);
		cntPanel.add(scanbarcodeJTextField);

		cntborderNORTH.add(cntPanel);

		JPanel cntborderCENTAL = new JPanel(new BorderLayout());
		cntborderCENTAL.setBackground(Color.blue);
		JPanel processingJPanel = new JPanel();
		processingJPanel.setBorder(BorderFactory
				.createTitledBorder("Processing......"));
		processingAreaJTextArea = new JTextArea(13, 100);
		processingAreaJTextArea.setBackground(Color.lightGray);
		processingAreaJTextArea
				.setText("Select Member ID first then scan book barcode");
		processingAreaJTextArea.setEditable(false);
		processingJPanel.add(processingAreaJTextArea);
		JPanel logJPanel = new JPanel();
		logJPanel.setBorder(BorderFactory
				.createTitledBorder("Items Linked to this person's ID"));
		logAreaJTextArea = new JTextArea(13, 100);
		logAreaJTextArea.setBackground(Color.lightGray);
		logAreaJTextArea.setEditable(false);
		logJPanel.add(logAreaJTextArea);
		cntborderCENTAL.add(processingJPanel, BorderLayout.NORTH);
		cntborderCENTAL.add(logJPanel, BorderLayout.SOUTH);

		panelIssueBooks.add(cntborderNORTH, BorderLayout.NORTH);
		panelIssueBooks.add(cntborderCENTAL, BorderLayout.CENTER);

		panelReturnBooks.setBorder(BorderFactory
				.createTitledBorder("Return Books..."));
		panelReturnBooks.setPreferredSize(new Dimension(650, 450));

		findReturn = new JPanel(new FlowLayout(FlowLayout.CENTER));
		findReturn.setBorder(BorderFactory.createTitledBorder("Find..."));
		findReturn.setPreferredSize(new Dimension(600, 70));
		findLabel = new JLabel("Find By Book Barcode Number: ");
		findByIDTextField = new JTextField(15);
		findBookButton = new JButton("Find...");
		findBookButton.addActionListener(new ActionListener() {
			String[] foundDetails = new String[4];

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// System.err.println("Main db is :" + mainDB);
				foundDetails = mainDB.findReturnBook(findByIDTextField
						.getText());
				if (foundDetails[0] != null) {
					for (int i = 0; i < foundDetails.length; i++) {
						updateTextFields[i].setText(foundDetails[i]);
					}
					buttonUpdate.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null,
							"Book specified doesn't exist or has not been borrowed!", "Oops!",
							JOptionPane.PLAIN_MESSAGE);

				}
			}
		});
		findReturn.add(findLabel);
		findReturn.add(findByIDTextField);
		findReturn.add(findBookButton);

		JPanel holdfieldsModify = new JPanel();
		createReturnyTextFieldsPn = new JPanel(new GridLayout(4, 2, 5, 5));
		for (int i = 0; i < modifyBookLabels.length; i++) {
			updateLabels[i] = new JLabel(modifyBookLabels[i],
					SwingConstants.RIGHT);
			createReturnyTextFieldsPn.add(updateLabels[i]);

			updateTextFields[i] = new JTextField(15);
			createReturnyTextFieldsPn.add(updateTextFields[i]);
		}
		updateTextFields[0].setEditable(false);
		updateTextFields[1].setEditable(false);
		holdfieldsModify.add(createReturnyTextFieldsPn);
		JPanel holdButtonUpdate = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonUpdate = new JButton("Update");
		buttonUpdate.setEnabled(false);
		buttonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] details = new String[4];
					String yes = updateTextFields[3].getText();

					if (!yes.equals("YES")) {
						JOptionPane.showMessageDialog(null,
								"'Returned' input should only be YES");
						updateTextFields[3].setText("");
						return;
					}
					int confirm = JOptionPane
							.showConfirmDialog(
									null,
									"Are you you sure you want to modify These details?",
									"Confirm Modify!",
									JOptionPane.YES_NO_OPTION);

					if (confirm == 0) {
						for (int i = 0; i < details.length; i++) {
							details[i] = updateTextFields[i].getText();
						}

						int result = mainDB.updateReturnDetails(details);

						if (result == 1) {
							JOptionPane.showMessageDialog(null,
									"Return details modified!",
									"Process Successful!",
									JOptionPane.PLAIN_MESSAGE);

							for (int i = 0; i < updateTextFields.length; i++) {
								updateTextFields[i].setText("");
							}

							findByIDTextField.setText("");
						} else {
							JOptionPane.showMessageDialog(null,
									"Error modifying return details!!",
									"Process Failed!",
									JOptionPane.PLAIN_MESSAGE);
							findByIDTextField.setText("");
							for (int i = 0; i < updateTextFields.length; i++) {
								updateTextFields[i].setText("");
							}
						}
					}
				} catch (Exception x) {
					x.printStackTrace();

				}
			}

		});
		holdButtonUpdate.add(buttonUpdate);

		panelReturnBooks.add(findReturn, "North");
		panelReturnBooks.add(holdfieldsModify);
		panelReturnBooks.add(holdButtonUpdate, "South");

		panelReturnBooks.setVisible(false);
		panelHoldCenterPanels.add(panelIssueBooks);
		panelHoldCenterPanels.add(panelRegisterBooks);
		panelHoldCenterPanels.add(panelReturnBooks);
		add(panelHoldCenterPanels);

	}

	public void addColumnsHeading(String[] colNames) {
		for (int i = 0; i < colNames.length; i++) {
			colHeadingsVec.addElement((String) colNames[i]);
		}
	}

	public void getMemberIDs(){
		Vector<String> ids4rmDB = new Vector<String>();
		ids4rmDB = mainDB.getMemberIDs();
		for (int loop = 0; loop < ids4rmDB.size(); loop++) {
			membershipIDV.add(ids4rmDB.get(loop));
		}
	}
	public void showRecordsOnJTable() {
		ListIterator<RecordsAllBooks> li = mainDB.getAllBookRecords();
		while (li.hasNext()) {
			RecordsAllBooks records = li.next();
			addRows(records.getBarcodNum(), records.getTitle(), records
					.getVersion(), records.getAuthor(), records.getPublisher(),
					records.getPages(), records.getGenre());
		}
		booksTable.addNotify();
	}

	public void addRows(String barcodNum, String title, String version,
			String author, String publisher, String pages, String genre) {
		Vector r = new Vector();
		r = returnElements(barcodNum, title, version, author, publisher, pages,
				genre);
		rowVec.addElement(r);
	}

	public Vector returnElements(String barcodNum, String title,
			String version, String author, String publisher, String pages,
			String genre) {
		Vector t = new Vector();
		t.addElement((String) barcodNum);
		t.addElement((String) title);
		t.addElement((String) version);
		t.addElement((String) author);
		t.addElement((String) publisher);
		t.addElement((String) pages);
		t.addElement((String) genre);

		return t;
	}

	public void verifyBookDetails() {
		String[] newBookDetails = new String[7];
		String barcode = registrationTextFields[0].getText();
		String title = registrationTextFields[1].getText();
		String version = registrationTextFields[2].getText();
		String author = registrationTextFields[3].getText();
		String publisher = registrationTextFields[4].getText();
		String pages = registrationTextFields[5].getText();
		String genre = registrationTextFields[6].getText();
		if (barcode.equals("") || barcode == "") {
			JOptionPane.showMessageDialog(null,
					"Book Barcode Number field should not be empty");
			return;
		}
		if (title.equals("") || title == "") {
			JOptionPane.showMessageDialog(null,
					"Book Title Number field should not be empty");
			return;
		}
		if (version.equals("") || version == "") {
			JOptionPane.showMessageDialog(null,
					"Book Version field should not be empty");
			return;
		}
		if (author.equals("") || author == "") {
			JOptionPane.showMessageDialog(null,
					"Book Author field should not be empty");
			return;
		}
		if (publisher.equals("") || publisher == "") {
			JOptionPane.showMessageDialog(null,
					"Book Publisher field should not be empty");
			return;
		}
		if (pages.equals("") || pages == "") {
			JOptionPane.showMessageDialog(null,
					"Number of Pages  field should not be empty");
			return;
		}
		if (genre.equals("") || genre == "") {
			JOptionPane.showMessageDialog(null,
					"Book Genre field should not be empty");
			return;
		}

		try {
			int nump = Integer.parseInt(pages);
		} catch (NumberFormatException nfe) {
			JOptionPane.showConfirmDialog(null,
					"Number of Pages Field should contian digits only");
			return;
		}

		for (int i = 0; i < newBookDetails.length; i++) {

			newBookDetails[i] = registrationTextFields[i].getText();
		}
		int result = mainDB.addABook(newBookDetails);
		if (result == 1) {
			DefaultTableModel dm = (DefaultTableModel) booksTable.getModel();
			dm.getDataVector().removeAllElements();
			showRecordsOnJTable();
			JOptionPane.showMessageDialog(null, "Book successfully Added!",
					"Process Successful!", JOptionPane.PLAIN_MESSAGE);
			for (int i = 0; i < newBookDetails.length; i++) {
				registrationTextFields[i].setText("");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error adding book!",
					"Process Failed!", JOptionPane.PLAIN_MESSAGE);
		}
	}

	/*public static void main(String[] args) {
		JFrame f = new JFrame("Issue");
		IssueJPanel panel = new IssueJPanel(mainDB);

		f.add(panel);
		// f.setVisible(true);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setSize(screenSize);
		f.setDefaultLookAndFeelDecorated(f.isDefaultLookAndFeelDecorated());
		f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
		f.setVisible(true);

	}*/
}