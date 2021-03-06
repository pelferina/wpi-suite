/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.game;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * A split pane for when a game's information is being viewed
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class GameView extends JSplitPane{

	GameRequirements gameReqs;
	PlayGame playGame;
	PlayDeckGame playDeckGame;
	ViewGame viewGame;
	boolean isDeckGame = false;
	public boolean isNew = true;
	JScrollPane playGameScrollPane;
	
	/**
	 * Constructor to make a gameview with a given session
	 * @param gameToPlay the active game to be viewing
	 */
	public GameView (GameSession gameToPlay){
		gameReqs = new GameRequirements(gameToPlay, this);
		System.out.println("DeckID is " + gameToPlay.getDeckId());
		
		if(gameToPlay.getGameStatus() == GameStatus.COMPLETED)
		{
			viewGame = new ViewGame(gameToPlay, this);
		}
		else{
			if (gameToPlay.getDeckId() == -1){
				isDeckGame = false;
				playGame = new PlayGame(gameToPlay, this);
				playGameScrollPane = new JScrollPane(playGame);
				playDeckGame = null;
			}
			else {
				isDeckGame = true;
				playDeckGame = new PlayDeckGame(gameToPlay, this);
				playGameScrollPane = new JScrollPane(playDeckGame);
				playGame = null;
			}
		}
		addImpl(gameReqs, JSplitPane.LEFT, 1);
	
		if(gameToPlay.getGameStatus() == GameStatus.COMPLETED)
		{
			addImpl(viewGame, JSplitPane.RIGHT, 2);
		}
		else
		{
			if (isDeckGame){
				addImpl(playGameScrollPane, JSplitPane.RIGHT, 2);
			}
			else {
				addImpl(playGameScrollPane, JSplitPane.RIGHT, 2);
			}
		}
		leftComponent.setMinimumSize(new Dimension(200, 200));
		rightComponent.setMinimumSize(new Dimension(1000, 3000));
		setResizeWeight(0.1);
		
		
		this.setUI(new BasicSplitPaneUI() {
            public BasicSplitPaneDivider createDefaultDivider() {
            return new BasicSplitPaneDivider(this) {
                public void setBorder(Border b) {
                }
            };
            }
        });
        this.setBorder(null);
        this.setEnabled( false );
	}

	/**
	 * This method chooses a requirement
	 * @param r the requirement
	 */
	public void sendReqToPlay(Requirement r) {
		if (isDeckGame){
			playDeckGame.chooseReq(r);
		}
		else {
			playGame.chooseReq(r);
		}
	}
	
	/**
	 * This sends a requirement to view of play game
	 * @param r the Requirement to send
	 */
	public void sendReqToView(Requirement r) {
		if (isDeckGame){
			playDeckGame.chooseReq(r);
		}
		else {
			playGame.chooseReq(r);
		}
	}

	/**
	 * This method updates the requirement tables with a requirement
	 * @param r the requirement
	 * @param estimate The estimate to be updated
	 */
	public void updateReqTables(Requirement r, int estimate) {
		gameReqs.updateTables(r, estimate);
	}
	/**
	 * clears the game view's boxes
	 */
	public void clearBoxes() {
		if (isDeckGame){
			playDeckGame.clear();
		}
		else {
			playGame.clear();
		}
	}
	
	/**
	 * 
	 */
	public void unselectAllCards(){
		if (playDeckGame != null){
			playDeckGame.unselectAll();
		}
	}
}
