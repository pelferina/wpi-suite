package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class GameSessionTest {

	@Before
	public void setUp() throws Exception {
//		Network.initNetwork(new MockNetwork());
//		Network.getInstance().setDefaultNetworkConfiguration(
//				new NetworkConfiguration("http://wpisuitetng"));

	}	
	
	// Tests whether a gamesession can be created successfully.
	@Test
	public void testGameSessionCreate(){
		GameSession gs = new GameSession("Test Game", 0, 1, Calendar.getInstance().getTime(), new ArrayList<Integer>());
		assertNotNull(gs);
		assertEquals("Test Game", gs.getGameName());
		assertEquals(0,gs.getOwnerID());
		assertEquals(1,gs.getGameID());
		assertNotNull(gs.toString());
		
	}
	
	@Test
	public void testSetDeadline(){
		GameSession gs = new GameSession("Test Game", 0, 1, Calendar.getInstance().getTime(), new ArrayList<Integer>());
		Date deadline = Calendar.getInstance().getTime();
		
		gs.setEndDate(deadline);
		assertTrue(gs.getEndDate().equals(deadline));
	}
	
	
	
}
