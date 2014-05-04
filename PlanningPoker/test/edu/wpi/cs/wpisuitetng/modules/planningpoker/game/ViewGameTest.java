package edu.wpi.cs.wpisuitetng.modules.planningpoker.game;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.GameView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game.ViewGame;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class ViewGameTest {
	private Requirement req1;
	private Requirement req2;
	private Requirement req3;
	User admin;
	MockData db;
	String mockSsid;
	GameEntityManager p_manager;
	Session adminSession;
	Project testProject;
	/**
	 * Setting up using Network and Iteration
	
	 * @throws Exception */
	
	@Before
	public void setUp() throws Exception {
		admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		req1 = new Requirement(1,"one","Desc");
		req2 = new Requirement(2,"two","Desc");
		req3 = new Requirement(3,"three","Desc");
		RequirementModel.getInstance().addRequirement(req1);
		RequirementModel.getInstance().addRequirement(req2);
		RequirementModel.getInstance().addRequirement(req3);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		db = new MockData(new HashSet<Object>());
		db.save(admin);
		p_manager = new GameEntityManager(db);
		GetCurrentUser gcu =  GetCurrentUser.getInstance();
		assertNotNull(gcu);
		gcu.enableTesting();
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
	}		
	
	@Test
	public void testViewGame(){
		List <Integer> reqList =  new ArrayList<Integer>();
		reqList.add(1);
		reqList.add(2);
		//reqList.add(3);
		GameSession gs = new GameSession("G","D", 0, 0, Calendar.getInstance().getTime(), reqList);
		gs.setDeckId(-1);
		assertEquals(GameStatus.DRAFT, gs.getGameStatus());
		gs.setGameStatus(GameStatus.ACTIVE);
		GameView gv = new GameView(gs);
		ViewGame vg = new ViewGame(gs, gv);
		assertNotNull(vg);
		vg.getEstimateTextField().setText("-5");
		vg.getEstimateTextField().setText("10");
		vg.getVoteButton().doClick();
		vg.chooseReq(req2);
		vg.getEstimateTextField().setText("10");
		vg.getVoteButton().doClick();
		assertTrue(vg.getSubmit().isEnabled());
	}
}
