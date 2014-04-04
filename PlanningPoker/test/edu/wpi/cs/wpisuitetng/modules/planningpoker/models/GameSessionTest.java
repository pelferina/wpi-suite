package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

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
		GameSession gs = new GameSession("Test Game", 0, 1, Calendar.getInstance(), new ArrayList<Requirement>());
		assertNotNull(gs);
		
	}
	
	@Test
	public void testGameSessionToString(){
		GameSession gs = new GameSession("Test Game", 0, 1, Calendar.getInstance(), new ArrayList<Requirement>());
		assertTrue(gs.toString().length()>0);
	}

	
	
}
