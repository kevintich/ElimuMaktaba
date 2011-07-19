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
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

//import org.jvnet.substance.skin.FindingNemoSkin;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class ModifyBlackListPanel extends JPanel {
	ElimuMaktabaMainDatabase mainDB = null;
	
	private HashMap<String, HashMap<Integer, Object>> getFormerColMaps;
	private JTable table;
	private JScrollPane scrollPane;
	private BlackListTableModel tableModel;
	private JPanel mainPanel, centerBoxPanel, editEntriesPanel, findPanel, foundPanel, memberFoundPanel, 
	               offenceAndPenaltyTablePanel, editTableButtonPanel, radioButtonPanel, radioButtonLayoutPanel,
	               radioButtonButtonPanel, centerRadioAddPanel, centerRadioRemovePanel, addRemovePanel, donePanel;
	private ButtonGroup buttonGroup;
	private JLabel findByIDLabel, foundLabel, addRadioOffenceLabel, addRadioPenaltyLabel, removeRadioOffenceLabel;
	private JTextField findByIDTF, foundTF, addRadioTF;
	private JButton findButton, applyChangesButton, discardChangesButton, radioActionButton, cancelRadioButton,
	                doneModifyButton;
	private JRadioButton addRadioButton, removeRadioButton;
    private HashMap <String, String> offAndPen;
	private JComboBox addRadioComboBox, removeRadioOffenceComboBox;
	private Object[] keySet;
	private int count = 0, countKeys = 0, countDiscards = 0;
	private AddOffenceDialog addOtherDialog = null;

	
	public ModifyBlackListPanel(ElimuMaktabaMainDatabase db, HashMap <String, String> offAndPen) {
		mainDB = db;
		this.offAndPen = offAndPen;
		
		buildUI();
	}
	
	public void setDiscards() {
		++countDiscards;
	}
	
	public void buildUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Modify Blacklist Record..."));
		this.setPreferredSize(new Dimension(550, 620));
		
		mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
				
		findPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		findPanel.setBorder(BorderFactory.createTitledBorder("Find Member By ID..."));
		findByIDLabel = new JLabel("Membership ID: ");
		findByIDTF = new JTextField(25);
		findButton = new JButton("Find");
		findPanel.add(findByIDLabel);
		findPanel.add(findByIDTF);
		findPanel.add(findButton);
		
		findButton.addActionListener(new ActionListener() {
			String name = null;
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(findByIDTF.getText());
				} catch(NumberFormatException ep) {
					JOptionPane.showMessageDialog(null, "Member ID has to be an number!", "Input Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!mainDB.inBlackList(findByIDTF.getText())) {
					JOptionPane.showMessageDialog(null, "Member is not in the BlackList!", "Oops!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				name = mainDB.getFullName(findByIDTF.getText());
				
				if(name == null) {
					JOptionPane.showMessageDialog(null, "Member specified doesn't exist!", "Oops!", JOptionPane.ERROR_MESSAGE);
				} else {					
					
					foundTF.setText(name);
					doneModifyButton.setEnabled(true);
					tableModel.fireTableStructureChanged();
					
					updateComboBox();
					
					mainPanel.setVisible(true);
				}
			}
		});
		
		add(findPanel, BorderLayout.NORTH);
			
		foundPanel = new JPanel();
		foundPanel.setBorder(BorderFactory.createTitledBorder("Found Member..."));
		foundPanel.setLayout(new BoxLayout(foundPanel, BoxLayout.PAGE_AXIS));
		
		memberFoundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		foundLabel = new JLabel("Member Name: ");
		foundTF = new JTextField(25);
		memberFoundPanel.add(foundLabel);
		memberFoundPanel.add(foundTF);
		
		foundPanel.add(memberFoundPanel);
		
		offenceAndPenaltyTablePanel = new JPanel(new BorderLayout());
		offenceAndPenaltyTablePanel.setBorder(BorderFactory.createTitledBorder("Edit Member's Blacklist Record..."));
		
		getFormerColMaps = new HashMap<String, HashMap<Integer, Object>>();
		
		tableModel = new BlackListTableModel(mainDB, findByIDTF, getFormerColMaps, this, true);
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 200));
		
		editTableButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		applyChangesButton = new JButton("Discard All Changes");
		
		applyChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(countDiscards <= 0) {
					JOptionPane.showMessageDialog(null, "No changes have been made!", "Process Failed!", JOptionPane.ERROR_MESSAGE);
					return;
				} 
				Object [] keySet = getFormerColMaps.keySet().toArray();
				
				for(int i=0; i<keySet.length; i++) {
					String rowS = keySet[i].toString();
					int row = Integer.parseInt(rowS.substring(0, rowS.indexOf("-")));
				
					HashMap<Integer, Object> map = getFormerColMaps.get(keySet[i]);
					Object [] keySet2 = map.keySet().toArray();
					String colS = keySet2[0].toString();
					int col = Integer.parseInt(colS);
					Object value = map.get(keySet2[0]);
				
					//System.out.println(value.toString() + " " + col + " " + row + " " + tableModel.getValueAt(row, col).toString() + " " + tableModel.getValueAt(row, col).toString() );
				
					if(col == 1) {
						mainDB.updateTableCell(findByIDTF.getText(), row, col, value, tableModel.getValueAt(row, col - 1).toString(), tableModel.getValueAt(row, col).toString());
					} else if(col == 0) {
						mainDB.updateTableCell(findByIDTF.getText(), row, col, value, tableModel.getValueAt(row, col).toString(), tableModel.getValueAt(row, col + 1).toString());
					}
					
					getFormerColMaps.remove(keySet[i]);
				}
				countDiscards = 0;
				
				updateComboBox();
				
				tableModel.fireTableStructureChanged();
			}
		});
		
		discardChangesButton = new JButton("Discard Last Change");

		discardChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(countDiscards <= 0) {
					JOptionPane.showMessageDialog(null, "No changes have been made!", "Process Failed!", JOptionPane.ERROR_MESSAGE);
				} else {
					Object [] keySet = getFormerColMaps.keySet().toArray();
					int pos = 0, posfin = 0, changePos = 0;
					
					for(int i=0; i<keySet.length; i++) {
						String temp = keySet[i].toString();
						String temp2 = temp.substring(temp.indexOf("-") + 1);
						pos = Integer.parseInt(temp2);
						
						System.out.println(pos + "");
						
						if(pos>=posfin) {
							posfin = pos;
							changePos = i;
					    }
				    }
					System.out.println();
					System.out.println(posfin + "");
					System.out.println();
					
					String rowS = keySet[changePos].toString();
					int row = Integer.parseInt(rowS.substring(0, rowS.indexOf("-")));
					
					HashMap<Integer, Object> map = getFormerColMaps.get(keySet[changePos]);
					Object [] keySet2 = map.keySet().toArray();
					String colS = keySet2[0].toString();
					int col = Integer.parseInt(colS);
					Object value = map.get(keySet2[0]);
					
					//System.out.println(value.toString() + " " + col + " " + row + " " + tableModel.getValueAt(row, col).toString() + " " + tableModel.getValueAt(row, col).toString() );
					
					if(col == 1) {
						mainDB.updateTableCell(findByIDTF.getText(), row, col, value, tableModel.getValueAt(row, col - 1).toString(), tableModel.getValueAt(row, col).toString());
					} else if(col == 0){
						mainDB.updateTableCell(findByIDTF.getText(), row, col, value, tableModel.getValueAt(row, col).toString(), tableModel.getValueAt(row, col + 1).toString());
					}
					countDiscards--;
					
					updateComboBox();
					
					getFormerColMaps.remove(keySet[changePos]);
					tableModel.fireTableStructureChanged();
				}
			}
		});
		
		editTableButtonPanel.add(applyChangesButton);
		editTableButtonPanel.add(discardChangesButton);
		
		offenceAndPenaltyTablePanel.add(scrollPane, BorderLayout.CENTER);
		offenceAndPenaltyTablePanel.add(editTableButtonPanel, BorderLayout.SOUTH);
		
		foundPanel.add(offenceAndPenaltyTablePanel);
		
		radioButtonLayoutPanel = new JPanel(new BorderLayout());
		radioButtonLayoutPanel.setPreferredSize(new Dimension(500, 180));
		
		radioButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		radioButtonPanel.setBorder(BorderFactory.createTitledBorder("Select Option..."));
		buttonGroup = new ButtonGroup();
		addRadioButton = new JRadioButton("Add Offence");
		
		addRadioButton.addItemListener(new ItemListener() {
			int count = 0;
			public void itemStateChanged(ItemEvent e) {
				switch(count) {
					case 0:
				        addRemovePanel.setVisible(true);
				        centerRadioAddPanel.setVisible(true);
				        centerRadioRemovePanel.setVisible(false);
				        radioActionButton.setText("Add");
					    radioActionButton.setActionCommand("Add");
				        count = 1;
				        break;
				       
					case 1:
						addRemovePanel.setVisible(false);
					    centerRadioAddPanel.setVisible(false);
					    centerRadioRemovePanel.setVisible(false);
					    count = 0;
					    break;
				}		
			}
		});
		
		buttonGroup.add(addRadioButton);
		removeRadioButton = new JRadioButton("Remove an Offence");
		
		removeRadioButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				switch(count) {
					case 0:
				        addRemovePanel.setVisible(true);
				        centerRadioAddPanel.setVisible(false);
				        centerRadioRemovePanel.setVisible(true);
				        radioActionButton.setText("Remove");
				        radioActionButton.setActionCommand("Remove");
				        
				        updateComboBox();
				        
				        count = 1;
				        break;
				       
					case 1:
						addRemovePanel.setVisible(false);
					    centerRadioAddPanel.setVisible(false);
					    centerRadioRemovePanel.setVisible(false);
					    count = 0;
					    break;
				}		
			}
		});
		
		buttonGroup.add(removeRadioButton);
		
		radioButtonPanel.add(addRadioButton);
		radioButtonPanel.add(removeRadioButton);
		
		radioButtonLayoutPanel.add(radioButtonPanel, BorderLayout.NORTH);
		
		addRemovePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		centerRadioAddPanel = new JPanel(new GridLayout(2,2));
		centerRadioAddPanel.setBorder(BorderFactory.createTitledBorder("Add Offence..."));
		((GridLayout)centerRadioAddPanel.getLayout()).setVgap(5);
		addRadioOffenceLabel = new JLabel("Offence: ");
		addRadioComboBox = new JComboBox();
		
		keySet = offAndPen.keySet().toArray();
		addRadioComboBox.addItem("None");
		for(int i=0; i<keySet.length; i++) {
			addRadioComboBox.addItem((String)keySet[i]);
		}
		addRadioComboBox.addItem("Other");
		
		addRadioComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(((String)addRadioComboBox.getSelectedItem()) == "Other") {
					if(addOtherDialog == null) {
						addOtherDialog = new AddOffenceDialog(mainDB, ModifyBlackListPanel.this, findByIDTF.getText(), tableModel);
						addOtherDialog.setLocationRelativeTo(null);
					}
			    	addRadioComboBox.setSelectedIndex(0);
	    		} else if(addRadioComboBox.getSelectedIndex() == 0){
	    			addRadioTF.setText("None");
	    		} else {	
	    			addRadioTF.setText(offAndPen.get((String)addRadioComboBox.getSelectedItem()));
	    		}
			}
		});
		
		addRadioPenaltyLabel = new JLabel("Penalty: ");
		addRadioTF = new JTextField(25);
		addRadioTF.setText("None");
		
		centerRadioAddPanel.add(addRadioOffenceLabel);
		centerRadioAddPanel.add(addRadioComboBox);
		centerRadioAddPanel.add(addRadioPenaltyLabel);
		centerRadioAddPanel.add(addRadioTF);
		centerRadioAddPanel.setVisible(false);
		
		centerRadioRemovePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerRadioRemovePanel.setBorder(BorderFactory.createTitledBorder("Remove Offence..."));
		removeRadioOffenceLabel = new JLabel("Offence : ");
		removeRadioOffenceComboBox = new JComboBox();
		removeRadioOffenceComboBox.setPreferredSize(new Dimension(300, 22));
		
		updateComboBox();
		
		centerRadioRemovePanel.add(removeRadioOffenceLabel);
		centerRadioRemovePanel.add(removeRadioOffenceComboBox);
		centerRadioRemovePanel.setVisible(false);
		
		radioButtonButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		radioActionButton = new JButton();
		
		radioActionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == "Add") {
				    int result = mainDB.addToBlacklist(findByIDTF.getText(), (String)addRadioComboBox.getSelectedItem(),  addRadioTF.getText());
				
				    if(result == 1) {
				    	 JOptionPane.showMessageDialog(null, "Member Record updated!", "Process Successful!", JOptionPane.PLAIN_MESSAGE);
				    	 
				    	 addRadioComboBox.setSelectedIndex(0);
				    	 addRadioTF.setText("None");
				    	 
				    	 tableModel.fireTableStructureChanged();
				    	 
				    	 updateComboBox();
				    } else {
				    	 JOptionPane.showMessageDialog(null, "Error updating Record!", "Process Failed!", JOptionPane.ERROR_MESSAGE);
				    }
				} else if(e.getActionCommand() == "Remove") {
					int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this offence?", "Confirm Remove!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (confirm == 0) {
						int result = mainDB.removeOffenceSpecific(findByIDTF.getText(), (String)removeRadioOffenceComboBox.getSelectedItem());
						
						if(result != 0) {
							JOptionPane.showMessageDialog(null, foundTF.getText() + "'s record updated!", "Process Successful!", JOptionPane.PLAIN_MESSAGE);
							
                            tableModel.fireTableStructureChanged();

							updateComboBox();

							removeRadioOffenceComboBox.setSelectedIndex(0);
						} else {
							JOptionPane.showMessageDialog(null, "Error updating " + foundTF.getText() + "'s record!", "Process Failed!", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		
		radioButtonButtonPanel.add(radioActionButton);
		
		addRemovePanel.add(centerRadioAddPanel);
		addRemovePanel.add(centerRadioRemovePanel);
		addRemovePanel.setVisible(false);
		
		radioButtonLayoutPanel.add(addRemovePanel, BorderLayout.CENTER);
		
		radioButtonLayoutPanel.add(radioButtonButtonPanel, BorderLayout.SOUTH);
		
		foundPanel.add(radioButtonLayoutPanel);
		
		mainPanel.add(foundPanel);
		mainPanel.setVisible(false);
		
		add(mainPanel, BorderLayout.CENTER);
		
		donePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		doneModifyButton = new JButton("Done");
		doneModifyButton.setEnabled(false);
		
		doneModifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		donePanel.add(doneModifyButton);
		add(donePanel, BorderLayout.SOUTH);
	}
	
	/*
	 * public static void main(String [] args) { ElimuMaktabaMainDatabase db =
	 * new ElimuMaktabaMainDatabase(); HashMap <String, String> offAndPen = new
	 * HashMap<String,String>(); JFrame f= new JFrame(); f.add(new
	 * ModifyBlackListPanel(db, offAndPen)); f.setVisible(true); f.setSize(800,
	 * 500); }
	 */
	
	public void setNull() {
		addOtherDialog = null;
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void houseKeep() {
		foundTF.setText("");
		findByIDTF.setText("");
		
		editEntriesPanel.setVisible(false);
		
		removeRadioOffenceComboBox.setSelectedIndex(0);
		addRadioComboBox.setSelectedIndex(0);
		
   	    addRadioTF.setText(offAndPen.get(keySet[0]));

		addRadioButton.setSelected(false);
		removeRadioButton.setSelected(false);
    }
	
	public void updateComboBox() {
		removeRadioOffenceComboBox.removeAllItems();
		
        Vector <String> offences = mainDB.getMemberOffences(findByIDTF.getText());
		
		for(int i=0; i<offences.size(); i++) {
			removeRadioOffenceComboBox.addItem(offences.get(i));
		}
	}	
}
