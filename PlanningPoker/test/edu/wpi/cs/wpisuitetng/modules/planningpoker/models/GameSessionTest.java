package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
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
	
	
	
	@Test
	public void testDefaultGameStatus(){
		GameSession gs = new GameSession("Test Game", 0, 1, new Date(), new ArrayList<Integer>());
		assertEquals(GameStatus.DRAFT, gs.getGameStatus());
	}
	
	@Test
	public void testGetDraftGameSessionList(){
		GameModel gm = new GameModel();
		GameSession gs1 = new GameSession("Test Game1", 0, 1, new Date(), new ArrayList<Integer>());
		GameSession gs2 = new GameSession("Test Game2", 0, 2, new Date(), new ArrayList<Integer>());
		GameSession gs3 = new GameSession("Test Game3", 0, 3, new Date(), new ArrayList<Integer>());
		gs2.setGameStatus(GameStatus.ACTIVE);
		gs3.setGameStatus(GameStatus.COMPLETED);
		gm.addMessage(gs1);
		gm.addMessage(gs2);
		gm.addMessage(gs3);
		List<GameSession> draftGameSessionList = new ArrayList<GameSession>();
		draftGameSessionList.add(gs1);
		int i = 0;
		for (GameSession expectedGameSession: draftGameSessionList){
			assertTrue(expectedGameSession.equals(gm.getDraftGameSessions().get(i)));
			i++;
		}
		System.out.println(gm.getDraftGameSessions().toString());
		System.out.println(gm.getActiveGameSessions().toString());
	}
	
	@Test
	public void testGetActiveGameSessionList(){
		GameModel gm = new GameModel();
		GameSession gs1 = new GameSession("Test Game1", 0, 1, new Date(), new ArrayList<Integer>());
		GameSession gs2 = new GameSession("Test Game2", 0, 2, new Date(), new ArrayList<Integer>());
		GameSession gs3 = new GameSession("Test Game3", 0, 3, new Date(), new ArrayList<Integer>());
		gs2.setGameStatus(GameStatus.ACTIVE);
		gs3.setGameStatus(GameStatus.COMPLETED);
		gm.addMessage(gs1);
		gm.addMessage(gs2);
		gm.addMessage(gs3);
		List<GameSession> activeGameSessionList = new ArrayList<GameSession>();
		activeGameSessionList.add(gs2);
		int i = 0;
		for (GameSession expectedGameSession: activeGameSessionList){
			assertTrue(expectedGameSession.equals(gm.getActiveGameSessions().get(i)));
			i++;
		}
		System.out.println(gm.getActiveGameSessions().toString());
	}
	
	@Test
	public void testGetPastGameSessionList(){
		GameModel gm = new GameModel();
		GameSession gs1 = new GameSession("Test Game1", 0, 1, new Date(), new ArrayList<Integer>());
		GameSession gs2 = new GameSession("Test Game2", 0, 2, new Date(), new ArrayList<Integer>());
		GameSession gs3 = new GameSession("Test Game3", 0, 3, new Date(), new ArrayList<Integer>());
		gs2.setGameStatus(GameStatus.ACTIVE);
		gs3.setGameStatus(GameStatus.COMPLETED);
		gm.addMessage(gs1);
		gm.addMessage(gs2);
		gm.addMessage(gs3);
		List<GameSession> pastGameSessionList = new ArrayList<GameSession>();
		pastGameSessionList.add(gs3);
		int i = 0;
		for (GameSession expectedGameSession: pastGameSessionList){
			assertTrue(expectedGameSession.equals(gm.getCompletedGameSessions().get(i)));
			i++;
		}
		System.out.println(gm.getCompletedGameSessions().toString());
	}
	
	
}
