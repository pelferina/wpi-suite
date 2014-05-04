package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.EmailAddressEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.EmailAddressModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.PreferencesPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class PreferencesPanelTest {
	MockData db;
	String mockSsid;
	GameEntityManager p_manager;
	EmailAddressEntityManager e_manager;
	Session adminSession;
	Session bobSession;
	Project testProject;
	AddEmailAddressController eController;
	EmailAddressModel eModel;
	
	@Before
	public void setUp() throws Exception {
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		db = new MockData(new HashSet<Object>());
		admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		db.save(admin);
		p_manager = new GameEntityManager(db);
		e_manager = new EmailAddressEntityManager(db);
		eController = new AddEmailAddressController(null);
		e_manager = new EmailAddressEntityManager(db);
		eController = new AddEmailAddressController(null);
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		eModel = new EmailAddressModel(null, "admin", false);
	}

	@Test
	public void testPreferencesPanel(){
		GetCurrentUser gcu =  GetCurrentUser.getInstance();
		assertNotNull(gcu);
		gcu.enableTesting();
		PreferencesPanel pp = new PreferencesPanel(new JButton(), 1);
		assertNotNull(pp);
		pp.makeEmailDisable();
		pp.makeEmailEnable();
	}
}
