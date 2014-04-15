/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that handles all of the refreshable classes
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public abstract class RefreshableController {
	protected List<Refreshable> refreshables = new ArrayList<Refreshable>();
	
	/**
	 * This method adds a refreshable class to the list of refreshables
	 * @param r refreshable class to add
	 */
	public void addRefreshable(Refreshable r){
		refreshables.add(r);
	}
	
	/**
	 * This method removes a refreshable class from the list of refreshables
	 * @param r refreshable class to remove
	 */
	public void removeRefreshable(Refreshable r){
		refreshables.remove(r);
	}
	
	/**
	 * This method refreshes the list of refreshables
	 */
	public abstract void refresh();
}
