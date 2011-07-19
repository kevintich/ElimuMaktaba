package elimu_maktabaLibrarianScreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class AddToBlackListPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6274296737258505691L;

	ElimuMaktabaMainDatabase mainDB = null;
	
	private JPanel mainPanel, findPanel, foundPanel, entriesGridPanel, entriesPanel,
	               buttonPanel;
	private JLabel findByIDLabel, foundLabel, offenceLabel, penaltyLabel;
	private JTextField findByIDTF, foundTF, penaltyTF;
	private JButton findButton, doneAddingButton, addMemberButton, cancelAddButton;
	private JComboBox offenceComboBox;
	private AddOffenceDialog addOtherDialog;
	private HashMap <String, String> offenceAndPenalty;
	
	public AddToBlackListPanel(ElimuMaktabaMainDatabase db) {
		mainDB = db;
		
		buildUI();
	}
	
	public void buildUI() {
		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Add Member..."));
		this.setPreferredSize(new Dimension(500, 250));

		
		mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
     
		findPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		findPanel.setBorder(BorderFactory.createTitledBorder("Find Member By ID..."));
		findByIDLabel = new JLabel("Membership ID: ");
		findByIDTF = new JTextField(25);
		
		findButton = new JButton("Find");
		findButton.addActionListener(new ActionListener() {
			String name = null;
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(findByIDTF.getText());
				} catch(NumberFormatException ep) {
					JOptionPane.showMessageDialog(null, "Member ID has to be an number!", "Input Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				name = mainDB.getFullName(findByIDTF.getText());
				
				if(name == null) {
					JOptionPane.showMessageDialog(null, "Member specified doesn't exist!", "Oops!", JOptionPane.PLAIN_MESSAGE);
				} else {					
					addMemberButton.setEnabled(true);
					cancelAddButton.setEnabled(true);
					doneAddingButton.setEnabled(true);
					
					foundTF.setText(name);
					
					entriesPanel.setVisible(true);
				}
			}
		});
		
		findPanel.add(findByIDLabel);
		findPanel.add(findByIDTF);
		findPanel.add(findButton);
			
		entriesPanel = new JPanel();
		entriesPanel.setBorder(BorderFactory.createTitledBorder("Found Member..."));
		entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.PAGE_AXIS));
		entriesPanel.setVisible(false);
		
		foundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		foundLabel = new JLabel("Member Name: ");
		foundTF = new JTextField(25);
		foundPanel.add(foundLabel);
		foundPanel.add(foundTF);
		
		entriesGridPanel = new JPanel(new GridLayout(2,2));
		entriesGridPanel.setBorder(BorderFactory.createTitledBorder("Offence and Penalty..."));
		((GridLayout)entriesGridPanel.getLayout()).setVgap(5);
		
		offenceLabel = new JLabel("Offence: ");
	    offenceComboBox = new JComboBox();
	    
		penaltyTF = new JTextField(25);
		penaltyLabel = new JLabel("Penalty: ");
		penaltyTF.setEditable(false);
	    
	    offenceComboBox.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if(((String)offenceComboBox.getSelectedItem()) == "Other") {
	    			addOtherDialog = new AddOffenceDialog(mainDB, findByIDTF.getText());
	    		} else if(offenceComboBox.getSelectedIndex() == 0){
	    			penaltyTF.setText("None");
	    		} else {	
	    			penaltyTF.setText(offenceAndPenalty.get((String)offenceComboBox.getSelectedItem()));
	    		}
	    	}
	    });
	    
	    offenceAndPenalty = new HashMap <String, String>();
	    
	    for(int i=0; i<10; i++) {
	       offenceAndPenalty.put("Some Offence " + i, "Penalty for Offence " + i);
	    }
	    
	    offenceComboBox.addItem("None");
	    
	    Object [] keySet = offenceAndPenalty.keySet().toArray();
	    
	    for(int i=0; i<offenceAndPenalty.size(); i++) {
	    	offenceComboBox.addItem(keySet[i].toString());
	    }
	    
	    offenceComboBox.addItem("Other");
		
		entriesGridPanel.add(offenceLabel);
		entriesGridPanel.add(offenceComboBox);
		entriesGridPanel.add(penaltyLabel);
		entriesGridPanel.add(penaltyTF);
		
		entriesPanel.add(foundPanel);
		entriesPanel.add(entriesGridPanel);
				
		mainPanel.add(entriesPanel);
		
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		addMemberButton = new JButton("Add");
		addMemberButton.setEnabled(false);
		
		addMemberButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = mainDB.addToBlacklist(findByIDTF.getText(), (String)offenceComboBox.getSelectedItem(), penaltyTF.getText());
				
				if(result == 1) {
					JOptionPane.showMessageDialog(null, "Record Updated!", "Process SuccessFul!", JOptionPane.PLAIN_MESSAGE);
					
					offenceComboBox.setSelectedIndex(0);
					penaltyTF.setText("None");
					
				} else {
					JOptionPane.showMessageDialog(null, "Error updating Record!", "Process Failed!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		cancelAddButton = new JButton("Cancel");
		cancelAddButton.setEnabled(false);
		
		cancelAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				offenceComboBox.setSelectedIndex(0);
				penaltyTF.setText("None");
			}
		});
		
		doneAddingButton = new JButton("Done");
		doneAddingButton.setEnabled(false);
		
		doneAddingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entriesPanel.setVisible(false);
				
				addMemberButton.setEnabled(false);
				cancelAddButton.setEnabled(false);
				doneAddingButton.setEnabled(false);
				
				foundTF.setText("");
				findByIDTF.setText("");
				
				offenceComboBox.setSelectedIndex(0);
				penaltyTF.setText("None");
				
			}
		});
		
		buttonPanel.add(addMemberButton);
		buttonPanel.add(cancelAddButton);
		buttonPanel.add(doneAddingButton);
		
		add(findPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);	
		
		setVisible(true);
		setSize(500,500);
	}

	public HashMap <String, String> getOffencesHashMap() {
		// TODO Auto-generated method stub
		return offenceAndPenalty;
	}
	
	public void houseKeep() {
		entriesPanel.setVisible(false);
		
		addMemberButton.setEnabled(false);
		cancelAddButton.setEnabled(false);
		doneAddingButton.setEnabled(false);
		
		foundTF.setText("");
		findByIDTF.setText("");
		
		offenceComboBox.setSelectedIndex(0);
		penaltyTF.setText(offenceAndPenalty.get((String)offenceComboBox.getSelectedItem()));
	}
	
	/*
	 * public static void main(String [] args) { ElimuMaktabaMainDatabase db =
	 * new ElimuMaktabaMainDatabase(); JFrame f= new JFrame(); f.add(new
	 * AddToBlackListPanel(db)); f.setVisible(true); f.setSize(800, 500); }
	 */
}
