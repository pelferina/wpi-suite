/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team FFF8E7
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh;

import java.util.ArrayList;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
/**
 * Manages pausing the refresh code when we are not in the planning poker tab
 * @author FFF8E7
 * @version 6
 *
 */
public class pauseRefreshHandler implements AncestorListener {

	private static AncestorListener instance;
	private static final ArrayList<RefreshManager> RefMan = new ArrayList<RefreshManager>();;

	/**
	 * private constructor for singleton class
	 */
	private pauseRefreshHandler(){
	}
	
	/** 
	 * Handles entering planning poker tab
	 * @see javax.swing.event.AncestorListener#ancestorAdded(javax.swing.event.AncestorEvent)
	 */
	@Override
	public void ancestorAdded(AncestorEvent event) {
		for(RefreshManager r : RefMan){
			r.startRefresh();
		}

	}

	/** 
	 * Unused, called during the setup
	 * @see javax.swing.event.AncestorListener#ancestorMoved(javax.swing.event.AncestorEvent)
	 */
	@Override
	public void ancestorMoved(AncestorEvent event) {}

	/**
	 * Handles exiting the planning poker tab
	 * @see javax.swing.event.AncestorListener#ancestorRemoved(javax.swing.event.AncestorEvent)
	 */
	@Override
	public void ancestorRemoved(AncestorEvent event) {
		for(RefreshManager r : RefMan){
			r.stopRefresh();
		}
	}

	/**
	 * @return the instance
	 */
	public static AncestorListener getInstance() {
		if(instance == null) {
			instance = new pauseRefreshHandler();
		}
		return instance;
	}
	
	/**
	 * This function adds a manager to the handler
	 * @param NewRefManager the refresh manager to add, as NewRefManager
	 */
	public static void addRefreshManager(RefreshManager NewRefManager){
		pauseRefreshHandler.getInstance(); // ensure that refmanager gets created
		RefMan.add(NewRefManager);
	}

}
