/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Cosmic Latte
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.completedgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddVoteController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetUsersController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.VoteModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class VoteData extends JPanel{

	private final JLabel reqNameLabel = new JLabel("Requirement Name:");
	private final JLabel reqDescriptionLabel = new JLabel("Requirement Description:");
	private final JLabel meanLabel = new JLabel("Mean:");
	private final JLabel medianLabel = new JLabel("Median:");
	private final JLabel estimatesLabel = new JLabel("Estimates:");
	private final JLabel finalEstimateLabel = new JLabel ("Final Estimate:");
	private JLabel finalEstimateText = new JLabel();
	private JButton	finalSubmitButton = new JButton("Submit");
	private JLabel reqNameText = new JLabel();
	private JLabel meanTextField;
	private JLabel medianTextField;
	private JLabel descriptionTextArea = new JLabel();
	private JScrollPane estimatesPane;
	private JTable estimatesTable;
	private GameSession completedGame;
	private CompleteView completeView;
	private List<Integer> gameReqIDs;
	private List<Requirement> gameReqs;
	private Requirement currentReq;
	private int	reqIndex;
	private Vote finalVote;
	
	/**
	 * The constructor for the VoteData class
	 * @param gs, the completed game session to be viewed
	 * @param cv, the CompleteView that called the constructor for VoteData
	 */
	public VoteData(GameSession gs, CompleteView cv){
		completeView = cv;
		completedGame = gs;
		gameReqs = cv.getGameRequirements();
		ArrayList<Integer> estimates = new ArrayList<Integer>();
		for (int i = 0; i < gameReqs.size(); i++){
			estimates.add(-1);
		}
		finalVote = new Vote(estimates, completedGame.getGameID());
		currentReq = gameReqs.get(0);
		reqIndex = 0;
		
		//Enables the submit button and text box for final estimate if the user is the owner of the game
		if (completedGame.getOwnerID() == GetCurrentUser.getInstance().getCurrentUser().getIdNum()){
			finalEstimateText.setEnabled(true);
			finalSubmitButton.setEnabled(true);
		}
		else {
			finalEstimateText.setEnabled(false);
			finalSubmitButton.setEnabled(false);
		}
		
		//Sets the name and description text fields to the first requirements
		reqNameText.setText(currentReq.getName());
		reqNameText.setEnabled(false);
		descriptionTextArea.setVerticalAlignment(SwingConstants.TOP);
		descriptionTextArea.setText(currentReq.getDescription());
		//descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setEnabled(false);
		gs.calculateStats();
		
		//Sets the statistic text fields to the stats of the first requirement in the game, and disables user edits
		float mean = completedGame.getMean().get(reqIndex);
		meanTextField = new JLabel(String.format("%.2f", mean));
		float median = completedGame.getMedian().get(reqIndex);
		medianTextField = new JLabel(String.format("%.2f", median));
		init();
		
		//Action listener for the submit button that will save the final estimate for the requirement
		finalSubmitButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				boolean allVotes = true;
				int finalEstimate = Integer.parseInt(finalEstimateText.getText());
				finalVote.getVote().set(reqIndex, finalEstimate);
				for (int i: finalVote.getVote()){
					if (i == -1){
						allVotes = false;
					}
				}
				if (allVotes){
					AddVoteController msgr = new AddVoteController(VoteModel.getInstance());
					msgr.sendVote(finalVote);
				}
				completeView.nextRequirement();
			}
		});
	}
	
	/**
	 * Initializes the GUI components of VoteData class, and fills the table with the user names and estimates for the first 
	 * requirement in the game
	 */
	private void init(){
		
		estimatesTable = new JTable() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all -cells false
		       return false;
		    }
		};
		
		estimatesTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"User Name", "Estimate"}));
		DefaultTableModel estimatesModel = (DefaultTableModel) estimatesTable.getModel();
		estimatesModel.setRowCount(completedGame.getVotes().size());
		
		int i = 0;
		for (Vote v: completedGame.getVotes()){
			for (User u: GetUsersController.getInstance().getUsers()){
				if (u.getIdNum() == v.getUID()){
					estimatesModel.setValueAt(u.getName(), i, 0);
				}
			}
			estimatesModel.setValueAt(v.getVote().get(reqIndex), i, 1);
			i++;
		}
		
		estimatesPane = new JScrollPane(estimatesTable);
		estimatesPane.setViewportView(estimatesTable);
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.WEST, reqNameText, 6, SpringLayout.EAST, reqNameLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, reqNameText, -6, SpringLayout.NORTH, reqDescriptionLabel);
		springLayout.putConstraint(SpringLayout.EAST, reqNameText, -18, SpringLayout.EAST, medianTextField);
		springLayout.putConstraint(SpringLayout.NORTH, descriptionTextArea, 6, SpringLayout.SOUTH, reqDescriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, descriptionTextArea, 0, SpringLayout.WEST, reqDescriptionLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, descriptionTextArea, -169, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, descriptionTextArea, -71, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.WEST, estimatesPane, 80, SpringLayout.EAST, finalSubmitButton);
		springLayout.putConstraint(SpringLayout.EAST, estimatesPane, -61, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqDescriptionLabel, 32, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, reqNameLabel, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, reqNameLabel, 0, SpringLayout.WEST, reqDescriptionLabel);
		springLayout.putConstraint(SpringLayout.WEST, medianLabel, 214, SpringLayout.WEST, this);
		
		//Spring layout constraints for meanTextField
		springLayout.putConstraint(SpringLayout.NORTH, meanTextField, 150, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, meanTextField, 61, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, meanTextField, -80, SpringLayout.WEST, medianLabel);
		
		//Spring layout constraints for medianTextField
		springLayout.putConstraint(SpringLayout.NORTH, medianTextField, 150, SpringLayout.NORTH, this);
		
		//Spring layout constraints for meanLabel
		springLayout.putConstraint(SpringLayout.NORTH, meanLabel, 153, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, finalEstimateLabel, 27, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, meanLabel, 27, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.WEST, reqDescriptionLabel, 0, SpringLayout.WEST, meanLabel);
		
		//Spring layout constraints for finalSubmitButton
		springLayout.putConstraint(SpringLayout.NORTH, finalSubmitButton, 6, SpringLayout.SOUTH, finalEstimateLabel);
		springLayout.putConstraint(SpringLayout.EAST, finalSubmitButton, 0, SpringLayout.EAST, meanTextField);
		
		//Spring layout constraints for finalEstimateText
		springLayout.putConstraint(SpringLayout.NORTH, finalEstimateText, -2, SpringLayout.NORTH, estimatesPane);
		springLayout.putConstraint(SpringLayout.WEST, finalEstimateText, 6, SpringLayout.EAST, finalEstimateLabel);
		springLayout.putConstraint(SpringLayout.EAST, finalEstimateText, -38, SpringLayout.WEST, estimatesPane);
		
		//Spring layout constraints for finalEstimateLabel
		springLayout.putConstraint(SpringLayout.NORTH, finalEstimateLabel, 1, SpringLayout.NORTH, estimatesPane);
		
		//Spring layout constraints for estimatesPane
		springLayout.putConstraint(SpringLayout.NORTH, estimatesPane, 6, SpringLayout.SOUTH, estimatesLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, estimatesPane, -10, SpringLayout.SOUTH, this);
		
		//Spring layout constraints for estimatesLabel
		springLayout.putConstraint(SpringLayout.NORTH, estimatesLabel, 6, SpringLayout.SOUTH, medianTextField);
		springLayout.putConstraint(SpringLayout.WEST, estimatesLabel, 0, SpringLayout.WEST, medianLabel);
		springLayout.putConstraint(SpringLayout.WEST, medianTextField, 6, SpringLayout.EAST, medianLabel);
		springLayout.putConstraint(SpringLayout.EAST, medianTextField, 79, SpringLayout.EAST, medianLabel);
		
		//Spring layout constraints for medianLabel
		springLayout.putConstraint(SpringLayout.NORTH, medianLabel, 3, SpringLayout.NORTH, meanTextField);
		
		setLayout(springLayout);
		
		add(estimatesPane);
		add(descriptionTextArea);
		add(medianTextField);
		add(meanTextField);
		add(reqNameText);
		add(estimatesLabel);
		add(reqDescriptionLabel);
		add(medianLabel);
		add(meanLabel);
		add(reqNameLabel);
		add(finalEstimateLabel);
		add(finalEstimateText);
		add(finalSubmitButton);
	}
	
	/**
	 * Receives a new requirement to view, and displays the name, description, mean and median in the appropriate text fields, as well 
	 * as filling the table with the user IDs and votes.
	 * @param req, the requirement to view (sent from GameData class)
	 */
	public void receiveNewReq(Requirement req) {
		currentReq = req;
		reqIndex = completeView.getIndex(currentReq.getId());
		int i = 0;
		DefaultTableModel estimatesModel = (DefaultTableModel) estimatesTable.getModel();
		for (Vote v: completedGame.getVotes()){
			for (User u: GetUsersController.getInstance().getUsers()){
				if (u.getIdNum() == v.getUID()){
					estimatesModel.setValueAt(u.getName(), i, 0);
				}
			}
			estimatesModel.setValueAt(v.getVote().get(reqIndex), i, 1);
			i++;
		}
		if (finalVote.getVote().get(reqIndex) != -1){
			finalEstimateText.setText(Integer.toString(finalVote.getVote().get(reqIndex)));
		}
		else {
			finalEstimateText.setText("");
		}
		reqNameText.setText(req.getName());
		descriptionTextArea.setText(req.getDescription());
		meanTextField.setText(Float.toString(completedGame.getMean().get(reqIndex)));
		medianTextField.setText(Float.toString(completedGame.getMedian().get(reqIndex)));	
	}
}
