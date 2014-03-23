package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameMainPanel;

import javax.swing.SwingConstants;

public class PlanningPoker implements IJanewayModule{

	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	
	/**
	 * Construct a new DummyModule for demonstration purposes
	 */
	public PlanningPoker() {
		
		// Setup button panel
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton button = new JButton("New Game");
		buttonPanel.add(button);
		buttonPanel.add(new JButton("Current Game"));
		
		// Setup the main panel
		MainView mainPanel = new MainView();
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
