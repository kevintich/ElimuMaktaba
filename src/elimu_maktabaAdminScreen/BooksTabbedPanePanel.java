package elimu_maktabaAdminScreen;

import javax.swing.*;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class BooksTabbedPanePanel extends JPanel {
	JRadioButton createUser, modifyUser, removeUser, rBooksRadio, mBooksRadioB,
			dBooksRadioB;
	ButtonGroup buttongroupUser, buttongroupBooks;
	JPanel mainPanel, createU, modifyU, removeU, userPane, bookPane,
			accountsPane, userPaneRadio, displayPaneUsers, displayPaneBooks,
			registerBook, modifyBook, deactivateBook, bookPaneRadio,
			createUGrid, centerBookPanel, southBookPanel, jpregisterbooks,
			jpmodifybooks, jpdeactivatebook, searchPaneM, searchPaneD,
			findByBIDM, findByBIDD, centerModifyPanel, southModifyPanel,
			createMGrid, createDGrid, centerDeacPanel, southDeacPanel;
	JTabbedPane mainTabpane;
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu, aboutMenu;
	JTextField jtfsearch;
	JLabel[] createLabels = new JLabel[7];
	JTextField[] createTextFields = new JTextField[7];
	JTextField[] createMTextFields = new JTextField[7];
	JTextField[] createDTextFields = new JTextField[7];
	String[] labels = { "Barcode Number: ", "Book Title: ", "Book Version: ",
			"Book Author: ", "Book Publisher: ", "Number Of Pages: ",
			"Book Genre: " };
	JButton registerB, cancelReg, searchB, searchDB, modifyB, cancelMod,
			deactivateB, cancelDeac;
	GridLayout UGrid;
	JLabel searchM, searchD;
	JTextField searchMTF, searchDTF;
	ElimuMaktabaMainDatabase mainDB = null;
	ModifyBookDetails modifyBooksDetails;

	public BooksTabbedPanePanel(ElimuMaktabaMainDatabase db) {
		setLayout(new BorderLayout());

		mainDB = db;
		modifyBooksDetails = new ModifyBookDetails(db);

		rBooksRadio = new JRadioButton("Register Book");

		mBooksRadioB = new JRadioButton("Modify Book");
		mBooksRadioB.addItemListener(new ItemListener() {
			boolean display = false;

			public void itemStateChanged(ItemEvent e) {
				display = true;
				modifyBook.setVisible(display);
				display = false;
				registerBook.setVisible(display);
				deactivateBook.setVisible(display);
			}
		});

		dBooksRadioB = new JRadioButton("Deactivate Book");
		dBooksRadioB.addItemListener(new ItemListener() {
			boolean display = false;

			public void itemStateChanged(ItemEvent e) {
				display = true;
				deactivateBook.setVisible(display);
				display = false;
				registerBook.setVisible(display);
				modifyBook.setVisible(display);
			}
		});

		buttongroupBooks = new ButtonGroup();

		buttongroupBooks.add(rBooksRadio);
		buttongroupBooks.add(mBooksRadioB);
		buttongroupBooks.add(dBooksRadioB);

		bookPaneRadio = new JPanel();
		bookPaneRadio.setLayout(new BoxLayout(bookPaneRadio,
				BoxLayout.PAGE_AXIS));
		bookPaneRadio.add(rBooksRadio);
		bookPaneRadio.add(mBooksRadioB);
		bookPaneRadio.add(dBooksRadioB);

		add(bookPaneRadio, BorderLayout.WEST);

		UGrid = new GridLayout(8, 2);
		UGrid.setVgap(5);

		displayPaneBooks = new JPanel();

		registerBook = new JPanel(new BorderLayout());
		registerBook.setBorder(BorderFactory
				.createTitledBorder("Register Books..."));
		registerBook.setPreferredSize(new Dimension(500, 350));
		registerBook.setVisible(true);
		rBooksRadio.setSelected(true);

		createUGrid = new JPanel();
		createUGrid.setLayout(UGrid);

		centerBookPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		for (int i = 0; i < createLabels.length; i++) {
			createLabels[i] = new JLabel(labels[i], SwingConstants.RIGHT);
			createUGrid.add(createLabels[i]);

			createTextFields[i] = new JTextField(15);
			createUGrid.add(createTextFields[i]);
		}

		centerBookPanel.add(createUGrid);

		registerBook.add(centerBookPanel, BorderLayout.CENTER);

		southBookPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		registerB = new JButton("Register Book");

		registerB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				verifyBookRegistration();

			}
		});

		cancelReg = new JButton("Cancel");

		cancelReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		southBookPanel.add(registerB);
		southBookPanel.add(cancelReg);

		registerBook.add(southBookPanel, BorderLayout.SOUTH);

		modifyBook = new JPanel(new BorderLayout());
		modifyBook.setBorder(BorderFactory
				.createTitledBorder("Modify Books..."));
		modifyBook.setPreferredSize(new Dimension(710, 570));
		JScrollPane scrollPaneMod = modifyBooksDetails.setUpTable();
		modifyBook.add(scrollPaneMod, BorderLayout.NORTH);

		southModifyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		modifyB = new JButton("Save modified Details");
		modifyB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// System.out.println(modifyBooksDetails.table.getRowCount());
				Vector<String> vOfItems = new Vector<String>();
				for (int row = 0; row < modifyBooksDetails.table.getModel()
						.getRowCount(); row++) {
					for (int column = 0; column < modifyBooksDetails.table
							.getModel().getColumnCount(); column++) {
						// System.out.print(modifyBooksDetails.table.getValueAt(
						// row, column) + "\t");
						vOfItems.add((String) modifyBooksDetails.table
								.getValueAt(row, column));

					}
					String bookBarcode = vOfItems.get(0);
					String title = vOfItems.get(1);
					String version = vOfItems.get(2);
					String author = vOfItems.get(3);
					String publisher = vOfItems.get(4);
					String noOfPages = vOfItems.get(5);
					String genre = vOfItems.get(6);
					mainDB.updateBookDetailsTable(bookBarcode, title, version,
							author, publisher, noOfPages, genre);
					vOfItems.removeAllElements();
				}
				JOptionPane.showMessageDialog(null, "Changes saved");
			}

		});
		southModifyPanel.add(modifyB);
		modifyBook.add(southModifyPanel, BorderLayout.SOUTH);
		modifyBook.setVisible(false);

		deactivateBook = new JPanel(new BorderLayout());
		deactivateBook.setBorder(BorderFactory
				.createTitledBorder("Deactivate Book..."));
		deactivateBook.setPreferredSize(new Dimension(650, 450));
		deactivateBook.setVisible(false);

		searchPaneD = new JPanel(new FlowLayout(FlowLayout.CENTER));
		searchPaneD.setBorder(BorderFactory.createTitledBorder("Find Book..."));
		searchD = new JLabel("Find By Book ID: ");
		searchDTF = new JTextField(15);
		searchDB = new JButton("Find");

		searchDB.addActionListener(new ActionListener() {
			String[] foundBookDetails = new String[7];

			public void actionPerformed(ActionEvent e) {
				foundBookDetails = mainDB.findBook(searchDTF.getText());

				if (foundBookDetails[0] != null) {
					for (int i = 0; i < foundBookDetails.length; i++) {
						createDTextFields[i].setText(foundBookDetails[i]);
					}

					centerDeacPanel.setVisible(true);
					deactivateB.setEnabled(true);
					cancelDeac.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null,
							"Book specified doesn't exist!", "Oops!",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		});

		searchPaneD.add(searchD);
		searchPaneD.add(searchDTF);
		searchPaneD.add(searchDB);

		deactivateBook.add(searchPaneD, BorderLayout.NORTH);

		centerDeacPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerDeacPanel.setBorder(BorderFactory
				.createTitledBorder("Found The Book..."));
		createDGrid = new JPanel();
		createDGrid.setLayout(UGrid);

		for (int i = 0; i < createLabels.length; i++) {
			createLabels[i] = new JLabel(labels[i], SwingConstants.RIGHT);
			createDGrid.add(createLabels[i]);

			createDTextFields[i] = new JTextField(15);
			createDGrid.add(createDTextFields[i]);
		}

		// jpdeactivatebook.add(jpsearch);
		centerDeacPanel.add(createDGrid);
		centerDeacPanel.setVisible(false);
		rBooksRadio.addItemListener(new ItemListener() {
			boolean display = false;

			public void itemStateChanged(ItemEvent e) {
				display = true;
				registerBook.setVisible(display);
				display = false;
				modifyBook.setVisible(display);
				deactivateBook.setVisible(display);
			}
		});

		deactivateBook.add(centerDeacPanel, BorderLayout.CENTER);

		southDeacPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		deactivateB = new JButton("Deactivate Book");

		deactivateB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete book?",
						"Confirm Delete!", JOptionPane.YES_NO_OPTION);

				if (confirm == 0) {
					int result = mainDB.removeBook(searchDTF.getText());

					if (result == 1) {
						JOptionPane.showMessageDialog(null,
								"Book successfully deleted!",
								"Process Successful!",
								JOptionPane.PLAIN_MESSAGE);

						for (int i = 0; i < createDTextFields.length; i++) {
							createDTextFields[i].setText("");
						}

						searchDTF.setText("");
						centerDeacPanel.setVisible(false);
						deactivateB.setEnabled(false);
						cancelDeac.setEnabled(false);

					} else {
						JOptionPane.showMessageDialog(null,
								"Error deleting Book!", "Process Failed!",
								JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});

		deactivateB.setEnabled(false);
		cancelDeac = new JButton("Cancel");

		cancelDeac.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deactivateB.setEnabled(false);
				createDGrid.setVisible(false);
				searchDTF.setText("");
				cancelDeac.setEnabled(false);
			}
		});

		cancelDeac.setEnabled(false);
		southDeacPanel.add(deactivateB);
		southDeacPanel.add(cancelDeac);

		deactivateBook.add(southDeacPanel, BorderLayout.SOUTH);

		// jtfsearch.setFocusable(false);
		displayPaneBooks.add(registerBook);
		displayPaneBooks.add(modifyBook);
		displayPaneBooks.add(deactivateBook);

		add(displayPaneBooks);
	}

	public void verifyBookRegistration() {
		String barcode = createTextFields[0].getText();
		String title = createTextFields[1].getText();
		String version = createTextFields[2].getText();
		String author = createTextFields[3].getText();
		String publisher = createTextFields[4].getText();
		String numberOfPages = createTextFields[5].getText();
		String genre = createTextFields[6].getText();

		if (barcode.equals("") || barcode == null) {
			JOptionPane.showMessageDialog(null, "Book barcode number field  should not be empty");
			return;
		}
		if (title.equals("") || title == null) {
			JOptionPane.showMessageDialog(null, "Book title field should not be empty");
			return;
		}
		if (version.equals("") || version == null) {
			JOptionPane.showMessageDialog(null, "Book version field should not be empty");
			return;
		}
		if (author == null || author.equals("")) {
			JOptionPane.showMessageDialog(null, "Book author field  should not be empty");
			return;
		}
		if (publisher.equals("") || publisher == null) {
			JOptionPane.showMessageDialog(null, "Book publisher field  should not be empty");
			return;
		}
		if (numberOfPages.equals("") || numberOfPages == null) {
			JOptionPane.showMessageDialog(null, "number of pages field  should not be empty");
			return;
		}
		if (genre.equals("") || genre == null) {
			JOptionPane.showMessageDialog(null, "barcode genre should not be empty");
			return;
		}
		
		String[] bookCredentials = new String[7];

		for (int i = 0; i < bookCredentials.length; i++) {
			bookCredentials[i] = createTextFields[i].getText();
		}

		int result = mainDB.addABook(bookCredentials);

		if (result == 1) {
			JOptionPane.showMessageDialog(null, "Book Added!",
					"Process Successful!", JOptionPane.PLAIN_MESSAGE);
			for (int i = 0; i < bookCredentials.length; i++) {
				createTextFields[i].setText("");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error adding Book!",
					"Process Failed!", JOptionPane.PLAIN_MESSAGE);
		}
	}
}