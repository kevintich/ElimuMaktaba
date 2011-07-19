package elimu_maktabaAdminScreen;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.*;
import elimu_maktabaDatabaseConnection.*;

public class AdminMainFrame extends JFrame {
	JRadioButton createUser, modifyUser, removeUser, rBooks, mBooks, dBooks;
	ButtonGroup buttongroupUser, buttongroupBooks;
	JPanel mainPanel, createU, modifyU, removeU, userPane, bookPane,
			accountsPane, userPaneRadio, displayPaneUsers, displayPaneBooks,
			registerBook, modifyBook, deactivateBook, bookPaneRadio,
			createUGrid, centerUserPanel, southUserPanel;
	JTabbedPane mainTabpane;
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu, aboutMenu;
	JLabel[] createLabels = new JLabel[7];
	JTextField[] createTextFields = new JTextField[7];
	String[] labels = { "Staff ID: ", "First Name: ", "Surname: ",
			"Middle Name: ", "National ID Number: ", "Email Address: ",
			"Address: " };
	JButton createUserAcc, cancelCreate;
	GridLayout UGrid;
	ElimuMaktabaMainDatabase mainDB = null;

	public AdminMainFrame(ElimuMaktabaMainDatabase db) {
		super("Administator Frame");

		mainDB = db;

		buildInterface();

		setPreferredSize(new Dimension(900, 700));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void buildInterface() {
		mainTabpane = new JTabbedPane();

		userPane = new UserTabbedPanePanel(mainDB);
		userPane.setPreferredSize(new Dimension(900, 600));
		mainTabpane.addTab("Users", null, userPane);

		bookPane = new BooksTabbedPanePanel(mainDB);
		bookPane.setPreferredSize(new Dimension(900, 600));
		mainTabpane.addTab("Books", null, bookPane);

		accountsPane = new AccountsTabbedPanePanel(mainDB);
		accountsPane.setPreferredSize(new Dimension(900, 600));
		mainTabpane.addTab("Accounts", null, accountsPane);

		mainPanel = new JPanel(new BorderLayout());

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// mainPanel.setPreferredSize(new Dimension(400, 300));
		JPanel panelRestart = new JPanel();
		JButton buttonRestart = new JButton("Restart");
		buttonRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		panelRestart.add(buttonRestart);
		mainPanel.add(mainTabpane, BorderLayout.CENTER);
		setContentPane(mainPanel);
		pack();
	}

	/*
	 * public static void main(String[] args) { new AdminMainFrame(mainDB); }
	 */
}
