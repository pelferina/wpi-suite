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

/**
 * This interface states what methods a refreshable class needs
 * @author FFF8E7
 * @version 6
 */
public interface Refreshable {
	
	/**
	 * This method refreshes the requirements.
	 */
	void refreshRequirements();
	
	/**
	 * This method refreshes the games.
	 */

	void refreshGames();
	
	/**
	 * This method refreshes the decks.
	 */
	void refreshDecks();

}
