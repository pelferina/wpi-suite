/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;


import org.junit.Test;


public class testJTableModel{
	
	@Test
	public void testJTableModelCreate(){
		GameSession gs = new GameSession("t", "Test Description", "", 1, new Date(), new ArrayList<Integer>());
		GameSession[] games = {gs};
		assertNotNull(new JTableModel(games) );
	}

}
