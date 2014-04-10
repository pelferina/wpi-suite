package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.Test;


public class testJTableModel{
	
	@Test
	public void testJTableModelCreate(){
		GameSession gs = new GameSession("t", "Test Description", 0, 1, new Date(), new ArrayList<Integer>());
		GameSession[] games = {gs};
		assertNotNull(new JTableModel(games) );
	}

}
