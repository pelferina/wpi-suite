/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors: Team Cosmic Latte
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;
/**
 * The ButtonPanel class
 * @author FFF8E7
 * @version 6
 */
public class ButtonPanel extends ToolbarGroupView{
	private final JPanel contentPanel = new JPanel();
	private final LinkedList<JButton> buttonQueue = new LinkedList<JButton>();
	Component spacer = Box.createRigidArea(new Dimension(15, 0));
	
	JButton newButton = new JButton("<html>New<br />Game</html>");
	JButton settingButton = new JButton("<html>User<br />Setting</html>");
	JButton editButton = new JButton("<html>Edit<br />Game</html>");
	JButton activateButton = new JButton("<html>Activate<br />Game</html>");
	JButton archiveButton = new JButton("<html>Archive<br />Game</html>");
	JButton endButton = new JButton("<html>End<br />Game</html>");
	JButton playButton = new JButton("<html>Play<br />Game</html>");
	JButton viewButton = new JButton("<html>View<br />Game</html>");
	
	JButton createCancelButton = new JButton("<html>Cancel<br />Games</html>");
	
	private ImageIcon newImg = null;
	private ImageIcon settingImg = null;
	private ImageIcon editImg = null;
	private ImageIcon activateImg = null;
	private ImageIcon archiveImg = null;
	private ImageIcon endImg = null;
	private ImageIcon playImg = null;
	private ImageIcon viewImg = null;
	
	
	public ButtonPanel() {
		super("");
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		readImg();
		newButton.setIcon(newImg);
		settingButton.setIcon(settingImg);
		this.setPreferredWidth(165*2);
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().createGame();
			}
		});
		settingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().options();
			}
		});
		contentPanel.add(newButton);
		contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
		contentPanel.add(settingButton);
		contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
		contentPanel.setOpaque(false);
		this.add(contentPanel);
		super.setContent(contentPanel);
	}
	
	private void readImg(){
		try {
		    newImg = new ImageIcon(ImageIO.read(getClass().getResource("newgameimage.png")));
		    newButton.setIcon(newImg);
		    newButton.setPreferredSize(new Dimension(150, 50));
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    settingImg = new ImageIcon(ImageIO.read(getClass().getResource("optionsimage.png")));
		    settingButton.setIcon(settingImg);
		    settingButton.setPreferredSize(new Dimension(150, 50));
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    editImg = new ImageIcon(ImageIO.read(getClass().getResource("edit.png")));
		    editButton.setIcon(editImg);
		    editButton.setPreferredSize(new Dimension(150, 50));
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    activateImg = new ImageIcon(ImageIO.read(getClass().getResource("activategame.png")));
		    activateButton.setIcon(activateImg);
		    activateButton.setPreferredSize(new Dimension(150, 50));
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    archiveImg = new ImageIcon(ImageIO.read(getClass().getResource("archive.png")));
		    archiveButton.setIcon(archiveImg);
		    archiveButton.setPreferredSize(new Dimension(150, 50));
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    endImg = new ImageIcon(ImageIO.read(getClass().getResource("cancel.png")));
		    endButton.setIcon(endImg);
		    archiveButton.setPreferredSize(new Dimension(150, 50));
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}

		try {
		    playImg = new ImageIcon(ImageIO.read(getClass().getResource("vote.png")));
		    playButton.setIcon(playImg);
		    playButton.setPreferredSize(new Dimension(150, 50));
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    viewImg = new ImageIcon(ImageIO.read(getClass().getResource("view.png")));
		    viewButton.setIcon(viewImg);
		    viewButton.setPreferredSize(new Dimension(150, 50));
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
	}
	/**
	 * Removes all the buttons from the buttonPanel
	 */
	public void removeButtons(){
		for(JButton button:buttonQueue){
			contentPanel.remove(button);
		}
		contentPanel.removeAll();
		contentPanel.add(newButton);
		contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
		contentPanel.add(settingButton);
		contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
		this.setPreferredWidth(165*2);
		contentPanel.updateUI();
		buttonQueue.clear();
	}
	/**
	 * Shows all buttons for a selected game
	 * @param gameSelected The selected game
	 */
	public void showButton(final GameSession gameSelected){
    	final User currentUser = GetCurrentUser.getInstance().getCurrentUser();
    	final GameStatus status = gameSelected.getGameStatus();
    	final boolean hasCategory = false;
    	
    	if(status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS) ){
    		removeActionListeners(playButton);
    		playButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					ViewEventController.getInstance().playGameTab(gameSelected);
				}
    			
    		});
    		playButton.setPreferredSize(new Dimension(150, 50));
    		buttonQueue.add(playButton);
    		contentPanel.add(playButton);
    		contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
    		contentPanel.updateUI();
    	}else if(status.equals(GameStatus.COMPLETED) || status.equals(GameStatus.ARCHIVED)){
    		removeActionListeners(viewButton);
    		viewButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					ViewEventController.getInstance().viewGameTab(gameSelected);					
				}
    		});
    		viewButton.setPreferredSize(new Dimension(150, 50));
    		buttonQueue.add(viewButton);
    		contentPanel.add(viewButton);
    		contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
    		contentPanel.updateUI();
    	}
    	
    	if(currentUser.getIdNum() == gameSelected.getOwnerID()){
    		if(gameSelected.getGameStatus().equals(GameStatus.ACTIVE)||gameSelected.getGameStatus().equals(GameStatus.DRAFT)){
    			removeActionListeners(editButton);
    			editButton.setPreferredSize(new Dimension(150,50));
    			editButton.addActionListener(new EditGameActionListener(gameSelected));
    			buttonQueue.add(editButton);
    			contentPanel.add(editButton);
    			contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
    			contentPanel.updateUI();
    		}	
    		
    		if(gameSelected.getGameStatus().equals(GameStatus.ACTIVE) || gameSelected.getGameStatus().equals(GameStatus.INPROGRESS)){
    			removeActionListeners(endButton);
    			endButton.addActionListener(new EndGameActionListener(gameSelected));
    			endButton.setPreferredSize(new Dimension(150, 50));
        		buttonQueue.add(endButton);
        		contentPanel.add(endButton);
        		contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        		contentPanel.updateUI();
    		}else if(gameSelected.getGameStatus().equals(GameStatus.DRAFT)){
    			if(isValid(gameSelected)){
    				removeActionListeners(activateButton);
    				activateButton.setPreferredSize(new Dimension(150, 50));
    				activateButton.addActionListener(new ActivateGameActionListener(gameSelected));
    				buttonQueue.add(activateButton);
    				contentPanel.add(activateButton);
    				contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
    				contentPanel.updateUI();
    			}else{
    				//ViewEventController.getInstance().makeActivateGameButtonDisable(gameSelected);
    			}
    		}else if(gameSelected.getGameStatus().equals(GameStatus.COMPLETED)){
    			removeActionListeners(archiveButton);
    			archiveButton.setPreferredSize(new Dimension(150, 50));
    			archiveButton.addActionListener(new ArchiveGameActionListener(gameSelected));
    			buttonQueue.add(archiveButton);
    			contentPanel.add(archiveButton);
    			contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
    			contentPanel.updateUI();
    		}
    		
    	}
    	final int buttonNum = buttonQueue.size();
		this.setPreferredWidth(165*(buttonNum+2));

		this.updateUI();
	}

	/**
	 * Checks if the GameSession is valid
	 * @param gs the GameSession to check
	 * @return true if valid, false otherwise
	 */
	private boolean isValid(GameSession gs) { // if all neccessary fields are filled out, returns true
		if (gs.getGameName().length() > 0 && gs.getGameReqs().size() > 0){
			if (gs.getEndDate() == null || gs.getEndDate().getTime() > System.currentTimeMillis()){
				return true;
			}
		}
		return false;
	}

	private void removeActionListeners(JButton button){
		if(button.getActionListeners() != null){
			for(ActionListener a: button.getActionListeners())
				button.removeActionListener(a);
		}
	}
}
