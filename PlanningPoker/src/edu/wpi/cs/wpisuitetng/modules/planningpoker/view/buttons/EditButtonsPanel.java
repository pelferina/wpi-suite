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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGameRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
/**
 *
 * @version $Revision: 1.0 $
 * @author justinhess
 */
@SuppressWarnings("serial")
public class EditButtonsPanel extends ToolbarGroupView{
	
	private final JPanel contentPanel = new JPanel();
	JButton editButton = new JButton("<html>Edit<br />Games</html>");
	final JButton createCancelButton = new JButton("<html>Cancel<br />Games</html>");
	private ActionListener listener = null;
	private ImageIcon editImg = null;
	
	/**
	 *  disables the Edit Games/ActivateGames button 
	 */
	public void disableCreateEditButton() {
		editButton.setEnabled(false);
	}
	
	/**
	 *  enables the Edit Games/ActivateGames button 
	 */
	public void enableCreateEditButton() {
		editButton.setEnabled(true);
	}

	public EditButtonsPanel(){
		super("");
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(500);
		
		editButton.setPreferredSize(new Dimension(150,50));	
		
		try {
		    editImg = new ImageIcon(ImageIO.read(getClass().getResource("edit.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in EditButtonsPanel.");
		}
		
		editButton.setVisible(true);
		
		contentPanel.add(editButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	
	/**
	 * Method getEditButton.
	 * @return JButton 
	 * 
	 */
	public JButton getEditButton() {
		return editButton;
	}
	/**
	 * This method creates an edit button and enables it while disableing the cancel button
	 */
	public void setButtonToEdit(){
		if (editImg != null){
			editButton.setIcon(editImg);}
		editButton.setText("<html>Edit<br />Games</html>");
		createCancelButton.setEnabled(false);
		createCancelButton.setVisible(false);
	}
	/**
	 * This method sets the button to read "activate"
	 *
	 * Enables the end game button, and add a action listener
	 * to this game
	 * @param gameID 
	 */
	public void setEditGameButtonVisible(int gameID){
		editButton.setVisible(true);
		editButton.setEnabled(true);
		if(listener != null){
			editButton.removeActionListener(listener);
		}
		listener = new EditGameActionListener(gameID);
		editButton.addActionListener(listener);
	}
	
	/**
	 *  disables the end game button 
	 */
	public void setEditGameButtonInvisible() {
		editButton.setEnabled(false);
		editButton.setVisible(false);
	}

	/**
	 * This listener watches for when a game is activated
	 * @author Cosmic Latte
	 * @version $Revision: 1.0 $
	 */
	class activateGameActionListener implements ActionListener{
		int gameID;
		/**
		 * Constructor to poulate gameID
		 * @param gameID
		 */
		private activateGameActionListener(int gameID){
			this.gameID = gameID;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			final List<GameSession> games = GameModel.getInstance().getGames();
			for(GameSession g: games){
				if(g.getGameID() == gameID){
					g.setGameStatus(GameStatus.ACTIVE);
					final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.POST); // POST == UPDATE
					request.setBody(g.toJSON()); // put the new session in the body of the request
					request.addObserver(new UpdateGameRequestObserver()); // add an observer to process the response
					request.send(); // send the request
				}
			}
			
		}
	}

}
