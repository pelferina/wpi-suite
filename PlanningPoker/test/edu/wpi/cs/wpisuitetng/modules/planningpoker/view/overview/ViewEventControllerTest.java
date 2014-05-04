package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import static org.junit.Assert.assertNotNull;

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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetAllUsers;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class ViewEventControllerTest {
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
		GetAllUsers gau = GetAllUsers.getInstance();
		gau.enableTesting();
	}	
	
	@Test
	public void testViewEventController(){
		ViewEventController vec = ViewEventController.getInstance();

		MainView mv =  new MainView();
		assertNotNull(mv);
		vec.setMainView(mv);
		ToolbarView tv = new ToolbarView(true);
		vec.setToolBar(tv);
		
		mv.addTab("New Game", null);
		List <Integer> reqList =  new ArrayList<Integer>();
		reqList.add(1);
		GameSession gs = new GameSession("G","D", 0, 0, Calendar.getInstance().getTime(), reqList);
		db.save(gs);
		gs.setDeckId(-1);
		vec.makeActivateGameButtonDisable(gs);
		vec.makeActivateGameButtonVisible(gs);
		vec.makeArchiveGameButtonVisible(gs);
		vec.makeEditGameButtonInVisible();
		vec.makeEditGameButtonVisible(gs);
		vec.makeEndGameButtonVisible(gs);
		vec.makeOwnerButtonInvisible();
		vec.makeUserButtonInvisible();
		vec.makeViewGameButtonVisible(gs);
		vec.makeVoteGameButtonVisible(gs);
//		vec.playGameTab(gs);
//		vec.editGameTab(gs);
//		mv.addTab("Edit Game", gs);
	}
}
