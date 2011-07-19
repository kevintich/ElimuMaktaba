package elimu_maktabaLibrarianScreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class RemoveFromBlackListPanel extends JPanel {
	
	private ElimuMaktabaMainDatabase mainDB = null;
	private JPanel centerPanel, packAllPanel, findByIDPanel, foundMemberPanel, memberNamePanel, tablePanel, foundButtonsPanel;
	private JTextField findByIDTF, foundMemberTF;
	private JLabel findByIDLabel, foundMemberLabel;
	private JButton removeButton, cancelButton, findButton;
	private JTable table;
	private JScrollPane tableScrollPane;
	private BlackListTableModel tableModel;
	
	public RemoveFromBlackListPanel(ElimuMaktabaMainDatabase db) {
		mainDB = db;
		
		createGUI();
	}
	
	public void createGUI() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Remove BlackList Member"));
		
		centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		packAllPanel = new JPanel();
		packAllPanel.setLayout(new BoxLayout(packAllPanel, BoxLayout.PAGE_AXIS));
		
		findByIDPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		findByIDPanel.setBorder(BorderFactory.createTitledBorder("Find Member..."));
		findByIDLabel = new JLabel("Member ID : ");
		findByIDTF = new JTextField(20);
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
				
				if(!mainDB.inBlackList(findByIDTF.getText())) {
					JOptionPane.showMessageDialog(null, "Member is not in the BlackList!", "Oops!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				name = mainDB.getFullName(findByIDTF.getText());
				
				if(name == null) {
					JOptionPane.showMessageDialog(null, "Member specified doesn't exist!", "Oops!", JOptionPane.ERROR_MESSAGE);
				} else {					
					foundMemberTF.setText(name);
					
					tableModel.fireTableStructureChanged();
					
					foundMemberPanel.setVisible(true);
				}
			}
		});
		
		findByIDPanel.add(findByIDLabel);
		findByIDPanel.add(findByIDTF);
		findByIDPanel.add(findButton);
		
		packAllPanel.add(findByIDPanel);
		
		foundMemberPanel = new JPanel(new BorderLayout());
		foundMemberPanel.setBorder(BorderFactory.createTitledBorder("Found Member..."));
		foundMemberPanel.setVisible(false);
		
		memberNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		foundMemberLabel = new JLabel("Member Name : ");
		foundMemberTF = new JTextField(25);
		memberNamePanel.add(foundMemberLabel);
		memberNamePanel.add(foundMemberTF);
		
		foundMemberPanel.add(memberNamePanel, BorderLayout.NORTH);
		
		tablePanel = new JPanel(new BorderLayout());
		tableModel = new BlackListTableModel(mainDB, findByIDTF, false);
		table = new JTable(tableModel);
		table.setAutoCreateRowSorter(true);
		tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(500, 200));
		tablePanel.add(tableScrollPane, BorderLayout.CENTER);
		
		foundMemberPanel.add(tablePanel, BorderLayout.CENTER);
		
		foundButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		removeButton = new JButton("Remove Member");
		
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(RemoveFromBlackListPanel.this, "Are you sure you want to remove " + foundMemberTF.getText() + " from the Blacklist?", "Confirm Remove!", JOptionPane.YES_NO_OPTION);
				
				if(confirm == 0) {
					int result = mainDB.removeBlacklistMember(findByIDTF.getText());
					
					if(result > 0) {
						JOptionPane.showMessageDialog(RemoveFromBlackListPanel.this, foundMemberTF.getText() + " has been removed from BlackList!", "Process Successful!", JOptionPane.PLAIN_MESSAGE);
						
						foundMemberPanel.setVisible(false);
						findByIDTF.setText("");
						foundMemberTF.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(RemoveFromBlackListPanel.this, foundMemberTF.getText() + " not removed from BlackList!", "Process aborted!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		cancelButton = new JButton("Cancel");
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				foundMemberPanel.setVisible(false);
				
				findByIDTF.setText("");
				foundMemberTF.setText("");
			}
		});
		
		foundButtonsPanel.add(removeButton);
		foundButtonsPanel.add(cancelButton);

		foundMemberPanel.add(foundButtonsPanel, BorderLayout.SOUTH);
		
		packAllPanel.add(foundMemberPanel);
		
		centerPanel.add(packAllPanel);
		
		this.add(centerPanel, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(600, 500));
	}
}
