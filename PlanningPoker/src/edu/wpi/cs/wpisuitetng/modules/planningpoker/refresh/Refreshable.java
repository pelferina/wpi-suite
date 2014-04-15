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
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 *
 */
public interface Refreshable {
	
	/**
	 * This method refreshes te requirements.
	 */
	public void refreshRequirements();
	
	/**
	 * This method refreshes the games.
	 */
	public void refreshGames();
}
