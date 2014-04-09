/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;

public class PlanningPoker implements IJanewayModule{

	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	
	private MainView mainPanel;
	private JPanel buttonPanel;
	
	
	/**
	 * Construct a new DummyModule for demonstration purposes
	 */
	public PlanningPoker() {
		
		// Setup button panel
		final GameModel gameModel = new GameModel();
		DeckModel deckModel = new DeckModel();
		mainPanel = new MainView(gameModel, deckModel, true);
		buttonPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton newGameButton = new JButton("New Game");
		buttonPanel.add(newGameButton);
		buttonPanel.add(new JButton("Options"));
		
		tabs = new ArrayList<JanewayTabModel>();
		JanewayTabModel tab = new JanewayTabModel("PlanningPoker", new ImageIcon(), buttonPanel, mainPanel);
		
		tabs.add(tab);
	}
	
	/*
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return "Planning Poker";
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}
