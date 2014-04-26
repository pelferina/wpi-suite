package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;
import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;


	
	public class testTabName {
		GameSession game = new GameSession(null, null, 0, 0, null, null);
		@Test
		public void testNewGameName(){	
			MainView testTabName = new MainView();	
			String tabType = "New Game";
			String result = testTabName.tabLabler(tabType, game);
			assertEquals("New Game",result);
			
		}
		@Test
		public void testPlayGameName(){	
			MainView testTabName = new MainView();	
			String tabType = "Play Game";
			String result = testTabName.tabLabler(tabType, game);
			assertEquals(game.getGameName(),result);
		}
		@Test
		public void testEditGameName(){
			MainView testTabName = new MainView();	
			String tabType = "Edit Game";
			String result = testTabName.tabLabler(tabType, game);
			assertEquals(game.getGameName(),result);
		}
		@Test
		public void testReqTabName(){
			MainView testTabName = new MainView();	
			String tabType ="Req Tab";
			String result = testTabName.tabLabler(tabType, game);
			assertEquals("New Requirement",result);
		}

	}