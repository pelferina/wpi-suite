/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view.DeckPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.currentgame.CurrentGamePanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGameMainPanel;

/**
 * Description
 *
 * @author Xi Wen; Anthony Dresser; Nathan Bryant
 * @version Mar 24, 2014
 */
@SuppressWarnings("serial")
public class MainView extends JTabbedPane {
	private final JPanel currentGame;
	private final JPanel pastGames;
	private final JPanel deckPanel;
	private GameModel gameModel;
	

	public MainView(GameModel gameModel, DeckModel deckModel, boolean hasNewGame) {
		this.gameModel = gameModel;
		currentGame = new CurrentGamePanel(gameModel);
		pastGames = new JPanel();
		deckPanel = new DeckPanel(deckModel);
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//		if (hasNewGame == true){
//			addTab("New Game", newGame);
//		}
		addTab("Current Game", currentGame);
		addTab("Past Game", pastGames);
		addTab("Deck", deckPanel);
	}
	
	public void addNewGameTab()
	{
		MyCloseActionHandler myCloseActionHandler = new MyCloseActionHandler("New Game");
		NewGameMainPanel newGame = new NewGameMainPanel(gameModel);
		add(newGame, 0);
		JPanel pnlTab = new JPanel(new GridBagLayout());
		pnlTab.setOpaque(false);
		JLabel lblTitle = new JLabel("New Game");
		JButton btnClose = new JButton("x");

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;

		pnlTab.add(lblTitle, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		pnlTab.add(btnClose, gbc);

		setTabComponentAt(0, pnlTab);

		btnClose.addActionListener(myCloseActionHandler);
		setSelectedIndex(0);
	}
	
	public class MyCloseActionHandler implements ActionListener {

	    private String tabName;

	    public MyCloseActionHandler(String tabName) {
	        this.tabName = tabName;
	    }

	    public String getTabName() {
	        return tabName;
	    }

	    public void actionPerformed(ActionEvent evt) {

	        int index = indexOfTab(getTabName());
	        if (index >= 0) {

	            removeTabAt(index);
	            // It would probably be worthwhile getting the source
	            // casting it back to a JButton and removing
	            // the action handler reference ;)

	        }

	    }

	}   


}
