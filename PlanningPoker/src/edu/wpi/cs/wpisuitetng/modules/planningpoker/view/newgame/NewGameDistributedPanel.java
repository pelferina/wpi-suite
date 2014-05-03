/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors: Team Cosmic Latte
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Dimension;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckBuildingPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.reqpanel.NewRequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.GetDecksController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;



/**
 * The panel for creating a new live Planning Poker game, along with fields
 * that have the parameters for what a new game should have.
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")

public class NewGameDistributedPanel extends JSplitPane {
	
	public boolean isNew = true;
	public NewGameInputDistributedPanel newGameInputPanel;
	public NewGameReqPanel newGameReqPanel;
	public NewRequirementPanel newRequirement;
	private DeckBuildingPanel deckPanel;
	public JButton close;
	private boolean isDeckOpen = false;
	private boolean isRequirementOpen = false;
	
	private final int DIVIDER_LOCATION = 450;
	
	/**
	 * Constructor for NewGameDistributedPanel
	 * @param gameSession the session to display
	 * @param close the close button to close the panel
	 */
	public NewGameDistributedPanel(GameSession gameSession, JButton close)
	{
		this.close = close;
		newGameReqPanel = new NewGameReqPanel(this, gameSession);
		GetRequirementsController.getInstance().addRefreshable(newGameReqPanel); /** TODO this adds a refreshable whenever a new game tab is created.  We need to clean up refreshables when the tab is closed*/
		newGameInputPanel = new NewGameInputDistributedPanel(this, gameSession);
		GetDecksController.getInstance().addRefreshable(newGameInputPanel);
		panelSetup();
	}
	/**
	 * Constructor for NewGameDistributedPanel that gets requirements
	 * @param reqs a List<Requirement> of requirements
	 * @param close button to close the panel
	 */
	public NewGameDistributedPanel(List<Requirement> reqs, JButton close) {
		newGameReqPanel = new NewGameReqPanel(this);
		newGameInputPanel = new NewGameInputDistributedPanel(this);
		GetDecksController.getInstance().addRefreshable(newGameInputPanel);
		this.close = close;
		
		setEnabled(false);
		panelSetup();
	}
	/**
	 * This sets up the panels
	 * Must be private so that it cannot be set up more than once
	 * Only to be called in the constructor
	 * 
	 */
	private void panelSetup(){
		newGameInputPanel.focusNameText();
		newRequirement = new NewRequirementPanel(this);
		deckPanel = new DeckBuildingPanel(this);
		addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
		final Dimension minimumSize = new Dimension(DIVIDER_LOCATION, 200);
		leftComponent.setMinimumSize(minimumSize);
		addImpl(newGameReqPanel, JSplitPane.RIGHT, 2);
		newGameReqPanel.setVisible(true);
		newRequirement.setVisible(false);
		deckPanel.setVisible(false);
		setDividerLocation(DIVIDER_LOCATION);
		
		
		this.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
            return new BasicSplitPaneDivider(this) {
                public void setBorder(Border b) {
                }
            };
            }
        });
        this.setBorder(null);
        this.setEnabled( false );
		
		
	}
	
	public List<Requirement> getSelected(){
		return newGameReqPanel.getSelected();
	}
	/**
	 * SetUp method for making a newReq panel
	 */
	public void newReq() {
		addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
		addImpl(newRequirement, JSplitPane.RIGHT, 3);
		setDividerLocation(DIVIDER_LOCATION);
		newRequirement.setVisible(true);
		deckPanel.setVisible(false);
		newGameReqPanel.setVisible(false);
		newRequirement.focusOnName();
		isRequirementOpen = true;
	}
	
	/**
	 * Setup method for the deck creation panel
	 */
	public void newDeck() {
		addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
		addImpl(deckPanel, JSplitPane.RIGHT, 4);
		setDividerLocation(DIVIDER_LOCATION);
		deckPanel.setVisible(true);
		deckPanel.focusOnName();
		newRequirement.setVisible(false);
		newGameReqPanel.setVisible(false);
		newRequirement.focusOnName();
		isDeckOpen = true;
	}
	
	/**
	 * This closes the deck panel
	 */
	public void closeDeck(){
		if (!isRequirementOpen){
			addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
			addImpl(newGameReqPanel, JSplitPane.RIGHT, 2);
			setDividerLocation(DIVIDER_LOCATION);
			newGameReqPanel.setVisible(true);
		}
		else {
			addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
			addImpl(newRequirement, JSplitPane.RIGHT, 3);
			setDividerLocation(DIVIDER_LOCATION);
			newRequirement.setVisible(true);
		}
		isDeckOpen = false;
	}
	/**
	 * Sends the created Req
	 * @param r The Requirement to send
	 */
	public void sendCreatedReq(Requirement r){
		newGameReqPanel.receiveCreatedReq(r);
	}
	/**
	 * Closes the create requirement panel
	 */
	public void closeReq(){
		if (!isDeckOpen){
			addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
			addImpl(newGameReqPanel, JSplitPane.RIGHT, 2);
			setDividerLocation(DIVIDER_LOCATION);
			newGameReqPanel.setVisible(true);
		}
		else{
			addImpl(newGameInputPanel, JSplitPane.LEFT, 1);
			addImpl(deckPanel, JSplitPane.RIGHT, 4);
			setDividerLocation(DIVIDER_LOCATION);
			deckPanel.setVisible(true);
		}
		newRequirement.clearFields();
		newRequirement.setVisible(false);
		isRequirementOpen = false;
	}
	/**
	 * Stops the timer in newGameInputPanel
	 */
	public void stopTimer(){
		newGameInputPanel.stopTimer();
	}
	/**
	 * Makes the newGameReqPanel refresh the requirements
	 */
	public void refreshRequirements() {
		newGameReqPanel.refreshRequirements();
	}
}
