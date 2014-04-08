package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class PlanningPoker implements IJanewayModule{

	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	
	private MainView mainPanel;
	private JPanel buttonPanel;
	
	
	/**
	 * Construct a new PlanningPoker module
	 */
	public PlanningPoker() {
		
		// Setup button panel
		final GameModel gameModel = GameModel.getInstance();
		DeckModel deckModel = new DeckModel();
		mainPanel = new MainView(gameModel, deckModel, false);
		buttonPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton newGameButton = new JButton("New Game");
		buttonPanel.add(newGameButton);
		buttonPanel.add(new JButton("Options"));
		
		ViewEventController.getInstance().setMainView(mainPanel);
		
		newGameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				mainPanel.addNewGameTab(false);
			}
		});
		
		// Setup the main panel
		/*
		mainPanel.setLayout(new BorderLayout());
		JLabel label = new JLabel("Planning Poker");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(label, BorderLayout.PAGE_START);
		mainPanel.add(new JTextField(), BorderLayout.CENTER);
		mainPanel.add(new JTextField(), BorderLayout.CENTER);
		JPanel newGamePanel = new NewGameMainPanel();
		//mainPanel.add(newGamePanel);
		*/
		tabs = new ArrayList<JanewayTabModel>();
		JanewayTabModel tab = new JanewayTabModel("PlanningPoker", new ImageIcon(), buttonPanel, mainPanel);
		
		//JLabel lblNewLabel = new JLabel("New label");
		//mainPanel.add(lblNewLabel, BorderLayout.NORTH);
		tabs.add(tab);
	}
	
	/*
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return "Planning Poker";
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}
