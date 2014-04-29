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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;

/**
 * Button for ending a game
 * @author FFF8E7
 * @version 6
 */
@SuppressWarnings("serial")
public class OwnerButtonPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	private final JButton ownerButton = new JButton();
	private final JButton editButton = new JButton("<html>Edit<br />Game</html>");
	private ActionListener listener = null;
	private ImageIcon endImg;
	private ImageIcon activateImg;
	private ImageIcon archiveImg;
	private ImageIcon editImg;
	private Timer expireTimer = null;
	Component spacer = Box.createRigidArea(new Dimension(15,0));
	
	public OwnerButtonPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(315);
		
		ownerButton.setPreferredSize(new Dimension(150,50));	
		editButton.setPreferredSize(new Dimension(150,50));
		
		try {
		    endImg= new ImageIcon(ImageIO.read(getClass().getResource("cancel.png")));
		    activateImg = new ImageIcon(ImageIO.read(getClass().getResource("activategame.png")));
		    archiveImg =new ImageIcon( ImageIO.read(getClass().getResource("archive.png")));
		    editImg = new ImageIcon(ImageIO.read(getClass().getResource("edit.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in EndGameButtonPanel");
		}

		editButton.setIcon(editImg);
		editButton.setVisible(false);
		ownerButton.setVisible(false);
		
		contentPanel.add(editButton);
		contentPanel.add(ownerButton);

		contentPanel.setOpaque(false);
		

		this.add(contentPanel);
	}
	
	/**
	 *  disables the end game button 
	 */
	public void makeOwnerButtonInvisible() {
		ownerButton.setEnabled(false);
		ownerButton.setVisible(false);
		determineButtonSize();
	}
	
	/**
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param game The GameSession to add a listener to
	 */
	public void makeEndGameButtonVisible(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(true);
		ownerButton.setIcon(endImg);
		ownerButton.setText("<html>End<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new EndGameActionListener(game);
		ownerButton.addActionListener(listener);
		if (game.getEndDate()!=null){
			expireThisButtonIn((int)(game.getEndDate().getTime()-Calendar.getInstance().getTime().getTime()));
		}
		determineButtonSize();

	}
	/**
	 * Enables the activate game button, and add a action listener
	 * to this game
	 * @param game the GameSession to add a listener to 
	 */
	public void makeActivateGameButtonVisible(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(true);
		ownerButton.setIcon(activateImg);
		ownerButton.setText("<html>Activate<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new ActivateGameActionListener(game);
		ownerButton.addActionListener(listener);
		
		if (game.getEndDate()!=null){
			expireThisButtonIn((int)(game.getEndDate().getTime()-Calendar.getInstance().getTime().getTime()));
		}
		determineButtonSize();
	}
	
	/**
	 * Disable the activate game button, and add a action listener
	 * to this game
	 * @param game The game to add a listener to
	 */
	public void makeActivateGameButtonDisable(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(false);
		ownerButton.setIcon(activateImg);
		ownerButton.setText("<html>Activate<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new ActivateGameActionListener(game);
		ownerButton.addActionListener(listener);
		
		if (game.getEndDate()!=null){
			expireThisButtonIn((int)(game.getEndDate().getTime()-Calendar.getInstance().getTime().getTime()));
		}
		determineButtonSize();
	}
	/**
	 * Disable the activate game button, and add a action listener
	 * to this game
	 */
	public void makeActivateGameButtonDisable(){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(false);
		ownerButton.setIcon(activateImg);
		ownerButton.setText("<html>Activate<br />Game</html>");
		determineButtonSize();
	}

	/**
	 * Enables the archive game button, and add a action listener
	 * to this game
	 * @param game The GameSession to add a listener to
	 */
	public void makeArchiveGameButtonVisible(GameSession game){
		ownerButton.setVisible(true);
		ownerButton.setEnabled(true);
		ownerButton.setIcon(archiveImg);
		ownerButton.setText("<html>Archive<br />Game</html>");
		
		
		if(listener != null){
			ownerButton.removeActionListener(listener);
		}
		listener = new ArchiveGameActionListener(game);
		ownerButton.addActionListener(listener);
		determineButtonSize();
	}
	
	private void expireThisButtonIn(int expireTime) {
		if (expireTimer!=null){
			expireTimer.stop();
		}
		
		final ActionListener al = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ownerButton.removeActionListener(listener);
				makeActivateGameButtonDisable();
				System.out.println("Expired");
			}
			
		}; // make it expire 		
		
		expireTimer = new Timer(expireTime, al);
		expireTimer.setRepeats(false);
		expireTimer.start();
		System.out.println("Expiring in "+expireTime);
	}
	
	/**
	 * This method sets the button to read "activate"
	 *
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void makeEditGameButtonVisible(int gameID){
		editButton.setVisible(true);
		editButton.setEnabled(true);
		if(listener != null){
			editButton.removeActionListener(listener);
		}
		listener = new EditGameActionListener(gameID);
		editButton.addActionListener(listener);
		determineButtonSize();
	}
	
	/**
	 *  disables the end game button 
	 */
	public void makeEditGameButtonInvisible() {
		editButton.setEnabled(false);
		editButton.setVisible(false);
		determineButtonSize();
	}
	
	/**
	 * This function determines and sets button size based on their visibility
	 */
	public void determineButtonSize()
	{
		if ( ownerButton.isVisible() && editButton.isVisible() )
		{
			this.setPreferredWidth(315);
			contentPanel.add(spacer,1);
		}
		else
		{
			this.setPreferredWidth(150);
			contentPanel.remove(spacer);
		}
	}
}
