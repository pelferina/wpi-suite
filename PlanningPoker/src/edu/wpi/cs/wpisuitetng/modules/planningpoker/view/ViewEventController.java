/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.tree.DefaultMutableTreeNode;


import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;


/**
 * Provides an interface for interaction with the main GUI elements
 * All actions on GUI elements should be conducted through this controller.
 * @version $Revision: 1.0 $
 * @author justinhess
 */

public class ViewEventController {
	private static ViewEventController instance = null;
	private MainView main = null;
//	private OverviewTable overviewTable = null;
//	private OverviewTreePanel overviewTree = null;
//	private ArrayList<RequirementPanel> listOfEditingPanels = new ArrayList<RequirementPanel>();
//	private ArrayList<IterationPanel> listOfIterationPanels = new ArrayList<IterationPanel>();
//	private IterationOverviewPanel iterationOverview;
	
//	/**
//	 * Sets the OverviewTable for the controller
//	 * @param overviewTable a given OverviewTable
//	 */
//	public void setOverviewTable(OverviewTable overviewTable) {
//		this.overviewTable = overviewTable;
//	}

	/**
	 * Default constructor for ViewEventController.  Is protected to prevent instantiation.
	 */
	private ViewEventController() {}

	/**
	 * Returns the singleton instance of the vieweventcontroller.
	
	 * @return The instance of this controller. */
	public static ViewEventController getInstance() {
		if (instance == null) {
			instance = new ViewEventController();
		}
		return instance;
	}

	/**
	 * Sets the main view to the given view.
	
	 * @param mainview MainView
	 */
	public void setMainView(MainView mainview) {
		main = mainview;
	}


	/**
	 * Opens a new tab for the creation of a requirement.
	 */
	public void editGameTab(GameSession gameSession) {
		main.addEditGameTab(gameSession);
	}
}
