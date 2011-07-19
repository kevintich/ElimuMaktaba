package elimu_maktabaLogin;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginUI extends JFrame implements ActionListener {
	JLabel jlIntroduction, jlUserID, jlPasswordField;
	JTextField jtfUserID;
	JPasswordField jpfPasswordField;
	JButton jbLogin, jbExit;
	JPanel jpanelNorth, jpanelCenter, jpanelSouth;
	FromGUItoMainClass logindataInterface;

	public LoginUI(final FromGUItoMainClass logindataInterface) {
		super("Login Screen");
		
		this.logindataInterface = logindataInterface;
		
		jpanelNorth = new JPanel();
		//jlIntroduction = new JLabel("Welcome! Please Enter Your UserID and Password To Login");
		//jlIntroduction.setFont(new Font("Arial", Font.BOLD, 15));
		//jpanelNorth.add(jlIntroduction);

		GridLayout credGrid = new GridLayout(2,2);
		credGrid.setVgap(5);
		
		JPanel centerCred = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerCred.setBorder(BorderFactory.createEtchedBorder());
		
		jpanelCenter = new JPanel(credGrid);
		jlUserID = new JLabel("User ID:  ");
		jlUserID.setFont(new Font("Arial", Font.BOLD, 15));
		jtfUserID = new JTextField(25);
		jpanelCenter.add(jlUserID);
		jpanelCenter.add(jtfUserID);

		centerCred.add(jpanelCenter);
		
		jlPasswordField = new JLabel("Password: ");
		jlPasswordField.setFont(new Font("Arial", Font.BOLD, 15));
		jpfPasswordField = new JPasswordField(25);
		
		jpanelCenter.add(jlPasswordField);
		jpanelCenter.add(jpfPasswordField);

		jpfPasswordField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// logindataInterface.getLoginDetailsFromGUI(jtfUserID.getText(),
				// jpfPasswordField.getText());
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// logindataInterface.getLoginDetailsFromGUI(jtfUserID.getText(),
				// jpfPasswordField.getText());
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == (char) 10) {
					
				    char [] pass = jpfPasswordField.getPassword();
				    StringBuffer password = new StringBuffer();
				    
				    for(int i=0; i<pass.length; i++) {
				    	password.append(pass[i]);
				    }
				    
					logindataInterface.getLoginDetailsFromGUI(jtfUserID.getText(), password.toString());
				}
			}
		});

		jpanelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jbLogin = new JButton("Login");
		jbLogin.setFont(new Font("Arial", Font.BOLD, 15));
		jbLogin.addActionListener(this);
		jbExit = new JButton("Exit");
		jbExit.setFont(new Font("Arial", Font.BOLD, 15));
		jbExit.addActionListener(this);
		jpanelSouth.add(jbLogin);
		jpanelSouth.add(jbExit);

		setSize(600, 150);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		add(jpanelNorth, "North");
		add(centerCred, "Center");
		add(jpanelSouth, "South");

	}

	public void buildUI() {

	}

	public void failedLogin() {
		JOptionPane.showMessageDialog(null,
				"User password combination is incorrect! Try Again...",
				"Login Failed!", JOptionPane.PLAIN_MESSAGE);

		//jtfUserID.setText();
		jpfPasswordField.setText("");
		jpfPasswordField.transferFocus();
	}
	
	public void disposeFrame() {
		this.dispose();
	}

	public void actionPerformed(ActionEvent ae) {
		JButton jb = (JButton) ae.getSource();
		if (jb == jbLogin) {
			
			char [] pass = jpfPasswordField.getPassword();
		    StringBuffer password = new StringBuffer();
		    
		    for(int i=0; i<pass.length; i++) {
		    	password.append(pass[i]);
		    }
		    			
			logindataInterface.getLoginDetailsFromGUI(jtfUserID.getText(), password.toString());

		} else if (jb == jbExit) {
			this.dispose();
			
			System.exit(0);
			// jtfUserID.setText("");
			// jpfPasswordField.setText("");

		}
	}

}
