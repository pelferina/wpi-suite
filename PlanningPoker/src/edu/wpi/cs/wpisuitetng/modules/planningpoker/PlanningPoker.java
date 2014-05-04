/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;



import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetAllUsers;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.RefreshManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.pauseRefreshHandler;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;

/**
 * This is the PlanningPoker module class.
 * @author FFF8E7
 * @version 6
 */
public class PlanningPoker implements IJanewayModule{

	/** The tabs used by this module */
	private final List<JanewayTabModel> tabs;


	private MainView mainPanel;
	private JPanel buttonPanel;
	private final RefreshManager refresh;
	Timer getUserTimer;

	/**
	 * Construct a new PlanningPoker module
	 */
	public PlanningPoker() {
		//Setup refreshing
		refresh = new RefreshManager();
		tabs = new ArrayList<JanewayTabModel>();

		final MainView mainPanel = new MainView();
		final ToolbarView toolBar = new ToolbarView(true);

		ViewEventController.getInstance().setMainView(mainPanel);
		ViewEventController.getInstance().setToolBar(toolBar);

		final JanewayTabModel tab = new JanewayTabModel("PlanningPoker", new ImageIcon(), toolBar, mainPanel);
		tabs.add(tab);


		getUserTimer = new Timer(1000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(GetCurrentUser.getInstance().getCurrentUser() != null &&
						GetAllUsers.getInstance().getAllUsers() != null){
					getUserTimer.stop();
				}
			}
		});
		getUserTimer.start();
		mainPanel.addAncestorListener(pauseRefreshHandler.getInstance());
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
