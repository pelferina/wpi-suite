package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PlayGame extends JPanel{

	private List<Integer> gameReqs;
	private final JLabel reqName = new JLabel("Requirement Name:");
	private final JLabel reqDesc = new JLabel("Requirement Description:");
	private final JLabel estimateLabel = new JLabel("Input Estimate");
	private JTextField estimateTextField = new JTextField();
	private JTextField reqNameTextField = new JTextField();
	private JTextArea reqDescTextArea = new JTextArea();
	private final JButton submit = new JButton("Submit Estimate");
	private Requirement currentReq;
	private GameView gv;
	
	public PlayGame(GameSession gameToPlay, GameView agv){
		submit.setEnabled(false);
		this.gameReqs = gameToPlay.getGameReqs();
		this.gv = agv;
		List<Requirement> allReqs = RequirementModel.getInstance().getRequirements();
		
		//Finds the requirement that is first in the to estimate table. The play game screen will default to displaying the first requirement
		//in the estimates pending table
		for (Requirement r: allReqs){
			if (r.getId() == gameReqs.get(0)){
				currentReq = r;
			}
		}
		
		//Sets the description and name text fields to the first requirement in the to estimate table
		reqNameTextField.setText(currentReq.getName());
		reqDescTextArea.setText(currentReq.getDescription());
		reqNameTextField.setEditable(false);
		reqDescTextArea.setEditable(false);
		
		//This document listener will enable the submit button when something is inputted into the estimate text field
		estimateTextField.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				if (estimateTextField.getText().length() > 0){
					submit.setEnabled(true);
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e){
				if (estimateTextField.getText().length() > 0){
					submit.setEnabled(true);
				}
			}
			@Override 
			public void insertUpdate(DocumentEvent e){
				if (estimateTextField.getText().length() > 0){
					submit.setEnabled(true);
				}
			}
		});

		
		SpringLayout springLayout = new SpringLayout();
		
		//Spring layout placement for submit button
		springLayout.putConstraint(SpringLayout.NORTH, submit, 6, SpringLayout.SOUTH, estimateTextField);
		springLayout.putConstraint(SpringLayout.WEST, submit, 132, SpringLayout.WEST, this);
		
		//Spring layout placement for estimateTextField
		springLayout.putConstraint(SpringLayout.NORTH, estimateTextField, 6, SpringLayout.SOUTH, estimateLabel);
		springLayout.putConstraint(SpringLayout.WEST, estimateTextField, 0, SpringLayout.WEST, estimateLabel);
		springLayout.putConstraint(SpringLayout.EAST, estimateTextField, 0, SpringLayout.EAST, estimateLabel);
		
		//Spring layout for placement of reqNameTextField
		springLayout.putConstraint(SpringLayout.WEST, reqNameTextField, 32, SpringLayout.EAST, reqName);
		springLayout.putConstraint(SpringLayout.EAST, reqNameTextField, -116, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqNameTextField, -3, SpringLayout.NORTH, reqName);
		
		//Spring layout for estimateLabel
		springLayout.putConstraint(SpringLayout.NORTH, estimateLabel, 47, SpringLayout.SOUTH, reqDescTextArea);
		springLayout.putConstraint(SpringLayout.WEST, estimateLabel, 155, SpringLayout.WEST, this);
		
		//Spring layout for reqDescTextArea
		springLayout.putConstraint(SpringLayout.NORTH, reqDescTextArea, 0, SpringLayout.NORTH, reqDesc);
		springLayout.putConstraint(SpringLayout.WEST, reqDescTextArea, 6, SpringLayout.EAST, reqDesc);
		springLayout.putConstraint(SpringLayout.SOUTH, reqDescTextArea, 113, SpringLayout.SOUTH, reqName);
		springLayout.putConstraint(SpringLayout.EAST, reqDescTextArea, 438, SpringLayout.WEST, this);
		
		//Spring layout for reqDesc label
		springLayout.putConstraint(SpringLayout.NORTH, reqDesc, 16, SpringLayout.SOUTH, reqName);
		springLayout.putConstraint(SpringLayout.WEST, reqDesc, 0, SpringLayout.WEST, reqName);
		
		//Spring layout for reqName label
		springLayout.putConstraint(SpringLayout.NORTH, reqName, 35, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, reqName, 25, SpringLayout.WEST, this);
		setLayout(springLayout);
	
		add(submit);
		add(reqName);
		add(reqDesc);
		add(estimateLabel);
		add(estimateTextField);
		add(reqNameTextField);
		add(reqDescTextArea);
	}
	
	//This function is used when a requirement is double clicked in one of the two requirement tables, and it sets the name and description fields to the 
	//selected requirement
	public void chooseReq(Requirement reqToEstimate){
		currentReq = reqToEstimate;
		reqNameTextField.setText(reqToEstimate.getName());
		reqDescTextArea.setText(reqToEstimate.getDescription());
		estimateTextField.setText("");
	}
	
	//This function will be used when the user submits an estimate for a requirement, and it will notify GameRequirements to move the requirement from
	//toestimate table to the completed estimates table
	public void sendEstimatetoGameView(Requirement r){
		gv.updateReqTables(r);
	}
	

	
}
