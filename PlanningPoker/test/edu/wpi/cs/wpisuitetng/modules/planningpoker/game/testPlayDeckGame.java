/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.PlayDeckGame;

public class testPlayDeckGame {

	
	@Before
	public void setUp() throws Exception {
		final User admin = new User("admin", "admin", "1234", 27);
		final User bob = new User("bob", "bob", "1234", 28);
		admin.setRole(Role.ADMIN);
		

	}
	@Test
	public void testDeadlineCheck() throws Exception{
		final GameSession gs = new GameSession("G", "D", 0, 0, Calendar.getInstance().getTime(), new ArrayList());
		final GameView gv = new GameView(gs);
		final PlayDeckGame pdg = new PlayDeckGame(gs, gv);
		pdg.clear();
		pdg.checkCanSubmit();
		
		assertNotNull(pdg);
		
	}

}
