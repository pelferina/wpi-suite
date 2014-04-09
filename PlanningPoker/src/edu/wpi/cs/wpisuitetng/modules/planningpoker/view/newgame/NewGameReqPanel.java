/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import javax.swing.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
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
	private JTable unselectedTable;
	private JTable selectedTable;
	private List<Requirement> reqs;
	private boolean editMode = false;
	private Timer refresh;
	
	/**
	 * @wbp.parser.constructor
	 */
	public NewGameReqPanel(List<Requirement> requirements) {
		reqs = requirements;
		System.out.println(reqs.size());
		unselectedTable = new JTable();
		selectedTable = new JTable();
		init();
	}

	public NewGameReqPanel(List<Requirement> requirements, GameSession gameSession) {
		unselectedTable = new JTable();
		selectedTable = new JTable();
		this.editMode  = true;
		reqs = new ArrayList<Requirement>(requirements);
		List<Integer> selectedIDs = gameSession.getGameReqs();
		for (Requirement req: reqs){
			for (int selectedReqID: selectedIDs) {
				if (req.getId() == selectedReqID) {
					this.selected.add(requirements.remove(selectedReqID));
					System.out.println(selected.get(0));
				}
			}	
		}
		reqs = requirements;
		System.out.println(reqs.size());
		init();
	}
	
	private void init()
	{
/*		refresh = new Timer(3000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				GetRequirementsController.getInstance().retrieveRequirements();
				updateReqs = RequirementModel.getInstance().getRequirements();
				for (int i = 0; i < selected.size(); i++){
					updateReqs.remove(selected.get(i).getId());
				}
				DefaultTableModel dtm = (DefaultTableModel) unselectedTable.getModel();
				dtm.setRowCount(reqs.size());
				for (int i = 0; i < reqs.size(); i++){
					dtm.setValueAt(reqs.get(i).getName(), i, 0);
					dtm.setValueAt(reqs.get(i).getDescript0ion(), i, 1);
				}
				unselectedTable.repaint();
				reqs = updateReqs;
			}
			
		});
		refresh.start();
*/
		
		// Sets up Swing layout
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		// Declarations and initializations
		JLabel lblRequirementsAvailable = new JLabel("Requirements Available");
		JButton btnAddReq = new JButton("Add New Requirement");
		JButton btnRemoveOne = new JButton("\u2191");
		JLabel lblRequirementsSelected = new JLabel("Requirements Selected");
		JButton btnAddOne = new JButton("\u2193");
		JButton btnRemoveAll = new JButton("\u21c8");
		JButton btnAddAll = new JButton("\u21ca");
		JScrollPane unselected_table = new JScrollPane();
		JScrollPane selected_table = new JScrollPane();
		
		// Observers
		
		btnAddReq.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
			}
		});
		
		btnAddOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (unselectedTable.getSelectedRow() != -1){
					int index = unselectedTable.getSelectedRow();
					Requirement selectedReq = reqs.get(index);
					selected.add(selectedReq);
					reqs.remove(index);
					String[] data = {selectedReq.getName(), selectedReq.getDescription()};
					DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
					DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
					dtm.setRowCount(reqs.size());
					for (int i = 0; i < reqs.size(); i++){
						dtm.setValueAt(reqs.get(i).getName(), i, 0);
						dtm.setValueAt(reqs.get(i).getDescription(), i, 1);
					}
					dtm_1.addRow(data);
				}
			}
		});
		
		btnAddAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(reqs.size() != 0){
					DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
					DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
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
		
		btnRemoveOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (selectedTable.getSelectedRow() != -1){
					int index = selectedTable.getSelectedRow();
					Requirement selectedReq = selected.get(index);
					selected.remove(index);
					reqs.add(selectedReq);
					String[] data = {selectedReq.getName(), selectedReq.getDescription()};
					DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
					DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
					dtm_1.setRowCount(selected.size());
					for (int i = 0; i < selected.size(); i++){
						dtm_1.setValueAt(selected.get(i).getName(), i, 0);
						dtm_1.setValueAt(selected.get(i).getDescription(), i, 1);
					}
					dtm.addRow(data);
				}
			}
		});
		
		btnRemoveAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (selected.size() != 0){
					DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
					DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
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
		
		unselectedTable.setModel(new DefaultTableModel(
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
			
		
		// Layout configuration
		
		// Spring Layout of lblRequirementsAvailable
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lblRequirementsAvailable, 0, SpringLayout.HORIZONTAL_CENTER, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblRequirementsAvailable, 10, SpringLayout.NORTH, this);
		
		// Spring Layout of unselected_table
		springLayout.putConstraint(SpringLayout.WEST, unselected_table, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, unselected_table, -10, SpringLayout.NORTH, btnAddReq);
		springLayout.putConstraint(SpringLayout.EAST, unselected_table, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, unselected_table, 10, SpringLayout.SOUTH, lblRequirementsAvailable);
		
		// Spring Layout of Buttons
		//springLayout.putConstraint(SpringLayout.NORTH, btnAddReq, 10, SpringLayout.SOUTH, unselected_table);
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, btnAddReq, 0, SpringLayout.VERTICAL_CENTER, this);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnAddReq, 0, SpringLayout.HORIZONTAL_CENTER, this);
		springLayout.putConstraint(SpringLayout.NORTH, btnRemoveOne, 0, SpringLayout.NORTH, btnAddReq); // Move one up
		springLayout.putConstraint(SpringLayout.WEST, btnRemoveOne, 15, SpringLayout.EAST, btnAddReq); 
		springLayout.putConstraint(SpringLayout.NORTH, btnRemoveAll, 0, SpringLayout.NORTH, btnRemoveOne); // Move all up
		springLayout.putConstraint(SpringLayout.WEST, btnRemoveAll, 0, SpringLayout.EAST, btnRemoveOne);
		springLayout.putConstraint(SpringLayout.NORTH, btnAddAll, 0, SpringLayout.NORTH, btnAddReq); // Move all down
		springLayout.putConstraint(SpringLayout.EAST, btnAddAll, -15, SpringLayout.WEST, btnAddReq);
		springLayout.putConstraint(SpringLayout.NORTH, btnAddOne, 0, SpringLayout.NORTH, btnAddReq); // Move one down
		springLayout.putConstraint(SpringLayout.EAST, btnAddOne, 0, SpringLayout.WEST, btnAddAll);
		
		// Spring Layout of lblRequirementsSelected
		springLayout.putConstraint(SpringLayout.NORTH, lblRequirementsSelected, 10, SpringLayout.SOUTH, btnRemoveOne);
		springLayout.putConstraint(SpringLayout.WEST, lblRequirementsSelected, 0, SpringLayout.WEST, lblRequirementsAvailable);
		
		// Spring Layout of selected_table
		springLayout.putConstraint(SpringLayout.NORTH, selected_table, 10, SpringLayout.SOUTH, lblRequirementsSelected);
		springLayout.putConstraint(SpringLayout.WEST, selected_table, 0, SpringLayout.WEST, unselected_table);
		springLayout.putConstraint(SpringLayout.SOUTH, selected_table, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, selected_table, -0, SpringLayout.EAST, unselected_table);
		
		// Adds elements to the panel
		add(lblRequirementsAvailable);
		add(unselected_table);
		add(btnAddReq);
		add(btnRemoveOne);
		add(btnRemoveAll);
		add(btnAddAll);
		add(btnAddOne);
		add(lblRequirementsSelected);
		add(selected_table);
		
		DefaultTableModel dtm = (DefaultTableModel)unselectedTable.getModel();
		dtm.setNumRows(reqs.size());
		dtm.setColumnCount(2);
		
		unselectedTable.getColumnModel().getColumn(0).setMinWidth(100);
		unselectedTable.getColumnModel().getColumn(0).setMaxWidth(200);
		unselectedTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		for (int i = 0; i < reqs.size(); i++){
			dtm.setValueAt(reqs.get(i).getName(), i, 0);
			dtm.setValueAt(reqs.get(i).getDescription(), i, 1);
		}
		unselected_table.setViewportView(unselectedTable);
		
		selectedTable.setModel(new DefaultTableModel(
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
		DefaultTableModel dtm_1 = (DefaultTableModel)selectedTable.getModel();
		dtm_1.setNumRows(selected.size());
		dtm_1.setColumnCount(2);
		selected_table.setViewportView(selectedTable);
		//unselectedTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		
		selectedTable.getColumnModel().getColumn(0).setMinWidth(100);
		selectedTable.getColumnModel().getColumn(0).setMaxWidth(200);
		selectedTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		for (int i = 0; i < selected.size(); i++){
			dtm_1.setValueAt(selected.get(i).getName(), i, 0);
			dtm_1.setValueAt(selected.get(i).getDescription(), i, 1);
		}
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

	public void refresh() {
		reqs = RequirementModel.getInstance().getRequirements();
		for (int i = 0; i < selected.size(); i++){
			reqs.remove(selected.get(i));
		}
		DefaultTableModel dtm = (DefaultTableModel) unselectedTable.getModel();
		dtm.setRowCount(reqs.size());
		for (int i = 0; i < reqs.size(); i++){
			dtm.setValueAt(reqs.get(i).getName(), i, 0);
			dtm.setValueAt(reqs.get(i).getDescription(), i, 1);
		}
		unselectedTable.repaint();
	}
}

