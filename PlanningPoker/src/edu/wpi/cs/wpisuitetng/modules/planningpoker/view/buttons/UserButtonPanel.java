/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * Button for ending a game
 * @author FFF8E7
 * @version 6
 */
public class UserButtonPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	private final JButton userButton = new JButton();
	private ActionListener listener = null;
	private ImageIcon viewImg;
	private ImageIcon voteImg;
	private final Timer expireTimer = null;
	
	public UserButtonPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(150);
		
		userButton.setPreferredSize(new Dimension(150,50));	
		userButton.setVisible(false);
		
		try {
			viewImg= new ImageIcon(ImageIO.read(getClass().getResource("searchimage.png")));
		    voteImg = new ImageIcon(ImageIO.read(getClass().getResource("vote.png")));
		    
		} catch (IOException ex){
			System.out.println("IOException thrown in EndGameButtonPanel");
		}
		
		contentPanel.add(userButton);
		contentPanel.setOpaque(false);
		this.add(contentPanel);
		super.setContent(contentPanel);
	}
	
	/**
	 *  disables the end game button 
	 */
	public void makeUserButtonInvisible() {
		userButton.setEnabled(false);
		userButton.setVisible(false);
	}

	/**
	 * Enables the vote game button, and add a action listener
	 * to this game
	 * @param game The GameSession to add a listener to
	 */
	public void makeVoteGameButtonVisible(final GameSession game){
		userButton.setVisible(true);
		userButton.setEnabled(true);
		userButton.setIcon(voteImg);
		userButton.setText("<html>Play<br />Game</html>");
		
		
		if(listener != null){
			userButton.removeActionListener(listener);
		}
		listener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().playGameTab(game);
			}
			
		};
		userButton.addActionListener(listener);
	}

	/**
	 * Enables the view game button, and add a action listener
	 * to this game
	 * @param game the GameSession to add a listener to
	 */
	public void makeViewGameButtonVisible(final GameSession game){
		userButton.setVisible(true);
		userButton.setEnabled(true);
		userButton.setIcon(viewImg);
		userButton.setText("<html>View<br />Game</html>");
		
		
		if(listener != null){
			userButton.removeActionListener(listener);
		}
		if(game.getGameStatus().equals(GameStatus.ARCHIVED) || game.getGameStatus().equals(GameStatus.COMPLETED)){
			listener = new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().viewGameTab(game);
				}
			};
		}else if(game.getGameStatus().equals(GameStatus.DRAFT)){
			listener = new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().editGameTab(game);
				}
			};
		}
		userButton.addActionListener(listener);
	}
	
}
