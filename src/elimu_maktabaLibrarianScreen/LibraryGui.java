package elimu_maktabaLibrarianScreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class LibraryGui extends JFrame {

	JPanel screenJPanel, registerPanel, issuebooksPanel, blacklistPanel, librarianReportsPanel, accountingPanel;
	JMenuBar menuBar;
	JTabbedPane mainTabbedPane;
	ElimuMaktabaMainDatabase mainDB;
	JMenu menuSystem;
	JButton buttonRestart;
	public LibraryGui(ElimuMaktabaMainDatabase db) {
		super("Librarian Screen");
		
		this.mainDB = db;
		// TODO Auto-generated constructor stub
		screenJPanel = new JPanel(new BorderLayout());
		buttonRestart = new JButton("Restart");
		buttonRestart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout());
		menuBar.add(buttonRestart,"Center");

		mainTabbedPane = new JTabbedPane();
		//screenJPanel.add(menuBar,BorderLayout.NORTH);
		screenJPanel.add(mainTabbedPane, BorderLayout.CENTER);
		
		registerPanel = new RegisterJPanel(mainDB);
		mainTabbedPane.add(registerPanel, "Register Members");
		
		issuebooksPanel = new IssueJPanel(mainDB);
		mainTabbedPane.add(issuebooksPanel, "Issue Books");
		
		blacklistPanel = new BlacklistPanel(mainDB);
		mainTabbedPane.add(blacklistPanel, "Blacklisting");
		
		librarianReportsPanel = new LibrarianReports();
		mainTabbedPane.add(librarianReportsPanel, "Library Reports");
		
		accountingPanel = new LibrarianViewAccounts();
		mainTabbedPane.add(accountingPanel, "Library Accounts");
		
		add(screenJPanel);

		// set size of screen to
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize);
		setDefaultLookAndFeelDecorated(isDefaultLookAndFeelDecorated());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	
}
