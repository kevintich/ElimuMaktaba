package elimu_maktabaLibrarianScreen;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import elimu_maktabaDatabaseConnection.ElimuMaktabaMainDatabase;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class BlacklistPanel extends JPanel {

	BlackListTableModel removeTableModel, modifyTableModel;
	AddOffenceDialog addOffenceDialog;
	AddToBlackListPanel addMemberPanel;
	ModifyBlackListPanel modifyMemberPanel;

	ElimuMaktabaMainDatabase mainDB = null;

	private JRadioButton addToBlackListRB, modifyBlackListRB,
			removeBlackListRB;
	private ButtonGroup buttonGroupBlackList;
	private JPanel mainRadioButton, displayBlacklistPanel;
	private RemoveFromBlackListPanel removeMemberPanel;

	public BlacklistPanel(ElimuMaktabaMainDatabase db) {
		mainDB = db;

		buildUI();
	}

	public void buildUI() {
		setLayout(new BorderLayout());

		displayBlacklistPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		addToBlackListRB = new JRadioButton("Add Member to BlackList");
		modifyBlackListRB = new JRadioButton("Modify BlackList Member Record");
		removeBlackListRB = new JRadioButton("Remove BlackList Member");
		buttonGroupBlackList = new ButtonGroup();
		addToBlackListRB.setSelected(true);

		buttonGroupBlackList.add(addToBlackListRB);
		buttonGroupBlackList.add(modifyBlackListRB);
		buttonGroupBlackList.add(removeBlackListRB);

		mainRadioButton = new JPanel();
		mainRadioButton.setLayout(new BoxLayout(mainRadioButton,
				BoxLayout.PAGE_AXIS));
		mainRadioButton.add(addToBlackListRB);
		mainRadioButton.add(modifyBlackListRB);
		mainRadioButton.add(removeBlackListRB);

		addToBlackListRB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				addMemberPanel.setVisible(true);
				modifyMemberPanel.setVisible(false);
				removeMemberPanel.setVisible(false);
			}
		});

		modifyBlackListRB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				addMemberPanel.setVisible(false);
				modifyMemberPanel.setVisible(true);
				removeMemberPanel.setVisible(false);
			}
		});

		removeBlackListRB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				addMemberPanel.setVisible(false);
				modifyMemberPanel.setVisible(false);
				removeMemberPanel.setVisible(true);
			}
		});

		add(mainRadioButton, BorderLayout.WEST);

		addMemberPanel = new AddToBlackListPanel(mainDB);
		addMemberPanel.setVisible(true);

		modifyMemberPanel = new ModifyBlackListPanel(mainDB,
				addMemberPanel.getOffencesHashMap());
		modifyMemberPanel.setVisible(false);

		removeMemberPanel = new RemoveFromBlackListPanel(mainDB);
		removeMemberPanel.setVisible(false);

		displayBlacklistPanel.add(addMemberPanel);
		displayBlacklistPanel.add(modifyMemberPanel);
		displayBlacklistPanel.add(removeMemberPanel);

		add(displayBlacklistPanel, BorderLayout.CENTER);
	}
}