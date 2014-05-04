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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.Timer;

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
@SuppressWarnings("serial")
public class ButtonPanel extends ToolbarGroupView{
	private final JPanel contentPanel = new JPanel();
	private final LinkedList<JButton> buttonQueue = new LinkedList<JButton>();
	private int spacerCount = 0;
	
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
	
	private Timer expireTimer = null;
	
	public ButtonPanel() {
		super("");
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		
		//Set up image icons
		readImg();
		contentPanel.setOpaque(false);
		this.add(contentPanel);
		super.setContent(contentPanel);
	}
	
	private void readImg(){
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
		//contentPanel.add(newButton);
		//contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
		//contentPanel.add(settingButton);
		//contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
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
    	if(status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS) ){
    		removeActionListeners(playButton);
    		playButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					ViewEventController.getInstance().playGameTab(gameSelected);
				}
    			
    		});
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
    		buttonQueue.add(viewButton);
    		contentPanel.add(viewButton);
    		contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
    		contentPanel.updateUI();
    	}
    	
    	if(currentUser.getIdNum() == gameSelected.getOwnerID()){
    		if(gameSelected.getGameStatus().equals(GameStatus.ACTIVE)||gameSelected.getGameStatus().equals(GameStatus.DRAFT)){
    			removeActionListeners(editButton);
    			editButton.addActionListener(new EditGameActionListener(gameSelected));
    			buttonQueue.add(editButton);
    			contentPanel.add(editButton);
    			contentPanel.add(Box.createRigidArea(new Dimension(15,0)));
    			contentPanel.updateUI();
    		}	
    		
    		if(gameSelected.getGameStatus().equals(GameStatus.ACTIVE) || gameSelected.getGameStatus().equals(GameStatus.INPROGRESS)){
    			removeActionListeners(endButton);
    			endButton.addActionListener(new EndGameActionListener(gameSelected));
        		buttonQueue.add(endButton);
        		contentPanel.add(endButton);
        		contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        		contentPanel.updateUI();
    		}else if(gameSelected.getGameStatus().equals(GameStatus.DRAFT)){
    			if(isValid(gameSelected)){
    				removeActionListeners(activateButton);
    				if(gameSelected.getEndDate() != null)
    					expireThisButtonIn(gameSelected);
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
    			archiveButton.addActionListener(new ArchiveGameActionListener(gameSelected));
    			buttonQueue.add(archiveButton);
    			contentPanel.add(archiveButton);
    			contentPanel.add(Box.createRigidArea(new Dimension(15, 0)));
    			contentPanel.updateUI();
    		}
    		
    	}
    	
    	for (JButton button : buttonQueue)
    	{
    		button.setPreferredSize(new Dimension(150, 60));
    		button.setMinimumSize(new Dimension(150, 60));
    		button.setMaximumSize(new Dimension(150, 60));
    	}
    	
    	final int buttonNum = buttonQueue.size();
//    	contentPanel.setPreferredSize(new Dimension(150*buttonNum + 15*(buttonNum),100));
//    	contentPanel.setMinimumSize(new Dimension(150*buttonNum + 15*(buttonNum),100));
//    	contentPanel.setMaximumSize(new Dimension(150*buttonNum + 15*(buttonNum),100));
    	this.setPreferredSize(new Dimension(150*buttonNum + 15*(buttonNum),100));
    	this.setMinimumSize(new Dimension(150*buttonNum + 15*(buttonNum),100));
    	this.setMaximumSize(new Dimension(150*buttonNum + 15*(buttonNum),100));

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
	
	private void expireThisButtonIn(final GameSession gameSelected) {
		int expireTime =(int) (gameSelected.getEndDate().getTime() - Calendar.getInstance().getTime().getTime());
		if (expireTimer != null){
			expireTimer.stop();
		}
		
		final ActionListener al = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().removeButtons();
		    	ViewEventController.getInstance().changeButton(gameSelected);
				System.out.println("Expired");
			}
			
		}; // make it expire 		
		
		expireTimer = new Timer(expireTime, al);
		expireTimer.setRepeats(false);
		expireTimer.start();
		System.out.println("Expiring in " + expireTime);
	}
}
