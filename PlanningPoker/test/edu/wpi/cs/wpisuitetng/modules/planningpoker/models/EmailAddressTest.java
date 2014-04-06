package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockRequest;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementEntityManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class EmailAddressTest {

	MockData db;
	User existingUser;
	Requirement req1;
	Session defaultSession;
	String mockSsid;
	RequirementEntityManager r_manager;
	PlanningPokerEntityManager p_manager;
	EmailAddressEntityManager e_manager;
	Requirement req3;
	User bob;
	Requirement goodUpdatedRequirement;
	Session adminSession;
	Project testProject;
	Project otherProject;
	Requirement req2;
	AddEmailAddressController eController;
	
	@Before
	public void setUp() throws Exception {
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("joe", "joe", "1234", 2);
		req1 = new Requirement(1, "Bob", "1.0", RequirementStatus.NEW, RequirementPriority.BLANK, "Desc", 1, 1);
		
		req2 = new Requirement(2, "Joe", "2.0", RequirementStatus.NEW, RequirementPriority.LOW, "Description", 2, 2);
				
		defaultSession = new Session(existingUser, testProject, mockSsid);
		req3 = new Requirement(3, "Jim", "3.0", RequirementStatus.NEW, RequirementPriority.HIGH, "Desc", 1, 2);
		
		db = new MockData(new HashSet<Object>());
		db.save(req1, testProject);
		db.save(existingUser);
		db.save(req2, otherProject);
		db.save(admin);
		r_manager = new RequirementEntityManager(db);
		p_manager = new PlanningPokerEntityManager(db);
		e_manager = new EmailAddressEntityManager(db);
		eController = new AddEmailAddressController();
		
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));

	}	
	
	@Test
	public void testSendEmail() throws UnsupportedEncodingException{
		GameSession gs = new GameSession("Test Game", 0, 1, Calendar.getInstance(), new ArrayList<Requirement>());
		//eController.saveEmail("rhhayne@wpi.edu");
		
		EmailAddressModel newEmailAddress = new EmailAddressModel("rhhayne@wpi.edu");
		db.save(newEmailAddress, adminSession.getProject());
		
		//e_manager.makeEntity(adminSession, "rhhayne@wpi.edu");
		
		try {
			p_manager.endGame(gs.getGameID(), adminSession);
		} catch (WPISuiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
