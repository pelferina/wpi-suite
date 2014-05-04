package edu.wpi.cs.wpisuitetng.modules.planningpoker.views;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockData;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.mock.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckManagingPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class ManageDeckTest {
	final JButton btnClose = new JButton("x");
	MockData db;
	String mockSsid;
	DeckEntityManager d_manager;
	AddDeckController dController;
	Session adminSession;
	Project testProject;
	List<Integer> testCards1; 
	Deck d1;
	List<Integer> testCards2;
	Deck d2;
	
	@Before
	public void setUp() throws Exception {
		final User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		db = new MockData(new HashSet<Object>());
		db.save(admin);
		d_manager = new DeckEntityManager(db);
		dController = AddDeckController.getInstance();
		
		testCards1 = Arrays.asList(0, 1, 2, 3, 4, 5);
		d1 = new Deck("Test Deck1", testCards1);
		d1.setId(1);
		testCards2 = Arrays.asList(6, 7, 8, 9, 10);
		d2 = new Deck("Test Deck2", testCards2);
		d2.setId(2);
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
//		dController.addDeck(d1);
//		dController.addDeck(d2);
	}
	
	
	@Test
	public void testManageDeck(){
		DeckModel.getInstance().addDeck(d1);
		DeckModel.getInstance().addDeck(d2);
		DeckManagingPanel dmp = new DeckManagingPanel(btnClose);
		assertNotNull(dmp);
		assertEquals(0, dmp.getDecksComboBox().getSelectedIndex());
		dmp.getNameField().setText("Default Deck");
		dmp.getNameField().setText("Test1");
		dmp.getNumberField().setText("abc");
		dmp.getNumberField().setText("-5");
		dmp.getNumberField().setText("10");
		dmp.getBtnAddCard().doClick();
		dmp.getBtnSave().doClick();
		dmp.getDecksComboBox().setSelectedIndex(1);
		dmp.getCardsToBeRemoved().add(1);
		dmp.getBtnRmvSelected().doClick();
		dmp.getBtnRmvAll().doClick();
		dmp.getBtnSingleSelection().doClick();
		dmp.getBtnMultipleSelection().doClick();
	}

}
