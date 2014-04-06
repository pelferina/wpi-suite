/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 * This class shows the requirements that are currently in the game
 * @author Anthony Dresser
 * @version March 23, 2014
 */
@SuppressWarnings("serial")
public class NewGameReqPanel extends JPanel {
	
	DefaultListModel<String> listValue = new DefaultListModel<String>();
	private final JList<String> reqList = new JList<String>(listValue);
	private final JPanel listPanel = new JPanel();
	private final JLabel gameReqs = new JLabel("Requirements in the game");
	private List<Requirement> selected = new ArrayList<Requirement>();
	private String[] columnName = {"Name", "Description"};
	private JTable table;
	private JTable table_1;
	private List<Requirement> reqs;
	
	public NewGameReqPanel() {
		reqs = RequirementModel.getInstance().getRequirements();
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel lblRequirementsAvailable = new JLabel("Requirements Available");
		springLayout.putConstraint(SpringLayout.WEST, lblRequirementsAvailable, 318, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblRequirementsAvailable, -108, SpringLayout.EAST, this);
		add(lblRequirementsAvailable);
		
		JButton button = new JButton("^");
		springLayout.putConstraint(SpringLayout.WEST, button, 10, SpringLayout.WEST, this);
		add(button);
		
		JButton btnV = new JButton("v");
		springLayout.putConstraint(SpringLayout.NORTH, btnV, 0, SpringLayout.NORTH, button);
		add(btnV);
		
		JButton button_1 = new JButton("^^");
		springLayout.putConstraint(SpringLayout.NORTH, button_1, 0, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.WEST, button_1, 6, SpringLayout.EAST, button);
		add(button_1);
		
		JButton btnVv = new JButton("vv");
		springLayout.putConstraint(SpringLayout.WEST, btnV, 6, SpringLayout.EAST, btnVv);
		springLayout.putConstraint(SpringLayout.NORTH, btnVv, 0, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.EAST, btnVv, -57, SpringLayout.EAST, this);
		add(btnVv);
		
		btnV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (table.getSelectedRow() != -1){
					int index = table.getSelectedRow();
					Requirement selectedReq = reqs.get(index);
					selected.add(selectedReq);
					reqs.remove(index);
					String[] data = {selectedReq.getName(), selectedReq.getDescription()};
					DefaultTableModel dtm = (DefaultTableModel)table.getModel();
					DefaultTableModel dtm_1 = (DefaultTableModel)table_1.getModel();
					dtm.setRowCount(reqs.size());
					for (int i = 0; i < reqs.size(); i++){
						dtm.setValueAt(reqs.get(i).getName(), i, 0);
						dtm.setValueAt(reqs.get(i).getDescription(), i, 1);
					}
					dtm_1.addRow(data);
				}
			}
		});
		
		btnVv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(reqs.size() != 0){
					DefaultTableModel dtm_1 = (DefaultTableModel)table_1.getModel();
					DefaultTableModel dtm = (DefaultTableModel)table.getModel();
					int size = reqs.size();
					for(int i=0; i < size; i++){
						String[] data = {reqs.get(i).getName(), reqs.get(i).getDescription()};
						dtm_1.addRow(data);
						selected.add(reqs.get(i));
					}
					reqs = new ArrayList<Requirement>();
					dtm.setRowCount(0);
				}
			}
		});
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (table_1.getSelectedRow() != -1){
					int index = table_1.getSelectedRow();
					Requirement selectedReq = selected.get(index);
					selected.remove(index);
					reqs.add(selectedReq);
					String[] data = {selectedReq.getName(), selectedReq.getDescription()};
					DefaultTableModel dtm = (DefaultTableModel)table.getModel();
					DefaultTableModel dtm_1 = (DefaultTableModel)table_1.getModel();
					dtm_1.setRowCount(selected.size());
					for (int i = 0; i < selected.size(); i++){
						dtm_1.setValueAt(selected.get(i).getName(), i, 0);
						dtm_1.setValueAt(selected.get(i).getDescription(), i, 1);
					}
					dtm.addRow(data);
				}
			}
		});
		
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (selected.size() != 0){
					DefaultTableModel dtm_1 = (DefaultTableModel)table_1.getModel();
					DefaultTableModel dtm = (DefaultTableModel)table.getModel();
					int size = selected.size();
					for(int i=0; i < size; i++){
						String[] data = {selected.get(i).getName(), selected.get(i).getDescription()};
						dtm.addRow(data);
						reqs.add(selected.get(i));
					}
					selected = new ArrayList<Requirement>();
					dtm_1.setRowCount(0);
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.SOUTH, lblRequirementsAvailable, -6, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 31, SpringLayout.NORTH, this);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Name", "Description"
			}
		));
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		dtm.setNumRows(reqs.size());
		dtm.setColumnCount(2);
		for (int i = 0; i < reqs.size(); i++){
			dtm.setValueAt(reqs.get(i).getName(), i, 0);
			dtm.setValueAt(reqs.get(i).getDescription(), i, 1);
		}
		scrollPane.setViewportView(table);
		//table.getColumnModel().getColumn(0).setPreferredWidth(50);
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 269, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, button, -3, SpringLayout.NORTH, scrollPane_1);
		add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Name", "Description"
			}
		));
		DefaultTableModel dtm_1 = (DefaultTableModel)table_1.getModel();
		dtm_1.setNumRows(0);
		dtm_1.setColumnCount(2);
		scrollPane_1.setViewportView(table_1);
		//table.getColumnModel().getColumn(0).setPreferredWidth(50);
		
		JLabel lblRequirementsSelected = new JLabel("Requirements Selected");
		springLayout.putConstraint(SpringLayout.NORTH, lblRequirementsSelected, 5, SpringLayout.NORTH, button);
		springLayout.putConstraint(SpringLayout.WEST, lblRequirementsSelected, 0, SpringLayout.WEST, lblRequirementsAvailable);
		add(lblRequirementsSelected);
	}
	
	public List<Requirement> getSelected(){
		return selected;

	}
	
	/**
	 * Takes a requirement and adds it to its reqList
	 * @param req
	 */
	public void addReq(Requirement req){
		selected.add(req);
		listValue.addElement(req.getName());
	}

	public Requirement removeSelected(){
		int tempIndex = reqList.getSelectedIndex();
		if (tempIndex == -1)
			return null;
		Requirement tempReq = selected.get(tempIndex);
		listValue.remove(tempIndex);
		selected.remove(tempIndex);
		return tempReq;

	}
}
