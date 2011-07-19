package elimu_maktabaLibrarianScreen;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

//import org.jvnet.substance.SubstanceLookAndFeel;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;

public class AddOffenceDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2312045235696987316L;

	ElimuMaktabaMainDatabase mainDB = null;
	
	GridBagLayout dialogFrameGrid = new GridBagLayout();
	GridBagConstraints dialogFrameCons = new GridBagConstraints();
	
	private JPanel dialogPanel, buttonPanel;
	private JLabel offenceLabel, penaltyLabel;
	private JTextField offenceTF, penaltyTF;
	private JButton dialogActionButton, dialogCancelButton;
	private AbstractTableModel tm = null;
	private String buttonText, id;
	private ModifyBlackListPanel parent;
	
	public AddOffenceDialog(ElimuMaktabaMainDatabase db, ModifyBlackListPanel parent, String ID, AbstractTableModel tm) {

		this.setTitle("Add Offence");
        this.tm = tm;
        this.parent = parent;
        
		mainDB = db;
		id = ID;
		
		buildDialog(this.buttonText);
	}
	
	public AddOffenceDialog(ElimuMaktabaMainDatabase db, String ID) {
		this.setTitle("Add Offence");

		mainDB = db;
		id = ID;
		
		buildDialog(this.buttonText);
	}
    
	
	public void buildDialog(String buttontext) {
	    this.setLayout(dialogFrameGrid);
	    
	    dialogPanel = new JPanel(dialogFrameGrid);

		dialogFrameCons.gridx = 0;
		dialogFrameCons.gridy = 0;
		
		offenceLabel = new JLabel("Offence :  ");
		dialogPanel.add(offenceLabel, dialogFrameCons);
		
		dialogFrameCons.gridx = 1;
		dialogFrameCons.gridy = 0;
		
		offenceTF = new JTextField(25);
		dialogPanel.add(offenceTF, dialogFrameCons);
		
		dialogFrameCons.gridx = 0;
		dialogFrameCons.gridy = 1;
		dialogFrameCons.insets = new Insets(10, 0, 0, 0);
		
		penaltyLabel = new JLabel("Penalty :  ");
		dialogPanel.add(penaltyLabel, dialogFrameCons);
		
		dialogFrameCons.gridx = 1;
		dialogFrameCons.gridy = 1;
		dialogFrameCons.insets = new Insets(10, 0, 0, 0);
		
		penaltyTF = new JTextField(25);
		dialogPanel.add(penaltyTF, dialogFrameCons);
		
		dialogFrameCons.gridx = 0;
		dialogFrameCons.gridy = 2;
		dialogFrameCons.gridwidth = 2;
		dialogFrameCons.insets = new Insets(10, 0, 0, 0);
		
        dialogActionButton = new JButton("Add Offence");
        
        dialogActionButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int result = mainDB.addToBlacklist(id, offenceTF.getText(), penaltyTF.getText());
        		
        		if(result == 1) {
        			JOptionPane.showMessageDialog(null, "Record updated!", "Process Successful!", JOptionPane.PLAIN_MESSAGE);
        			
        			if(tm != null) {
        			   tm.fireTableStructureChanged();
        			}
        			
        			parent.setNull();
        			AddOffenceDialog.this.dispose();
        		} else {
        			JOptionPane.showMessageDialog(null, "Error updating Record!", "Process Failed!", JOptionPane.ERROR_MESSAGE);
        		}
        	}
        });
        
        dialogCancelButton = new JButton("Cancel");
        
        dialogCancelButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		AddOffenceDialog.this.dispose();
        	}
        });
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(dialogActionButton);
        buttonPanel.add(dialogCancelButton);
		
        dialogPanel.add(buttonPanel, dialogFrameCons);
        
        dialogFrameCons.gridx = 2;
        dialogFrameCons.gridy = 2;
        dialogFrameCons.gridwidth = 3;
        
        this.add(dialogPanel, dialogFrameCons);
        
	    this.setSize(350,155);
	    this.setVisible(true);
	}

	/*
	 * public static void main(String [] args) { ElimuMaktabaMainDatabase db =
	 * new ElimuMaktabaMainDatabase();
	 * 
	 * try { //UIManager.setLookAndFeel(new SubstanceLookAndFeel()); } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * new AddOffenceDialog(db, null, "23", null); }
	 */
}
