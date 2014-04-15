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
	JButton createEditButton = new JButton("<html>Edit<br />Games</html>");
	final JButton createCancelButton = new JButton("<html>Cancel<br />Games</html>");
	private ImageIcon editImg = null;
	private ImageIcon saveImg = null;
	
	/**
	 *  disables the Edit Games/ActivateGames button 
	 */
	public void disableCreateEditButton() {
		createEditButton.setEnabled(false);
	}
	
	/**
	 *  enables the Edit Games/ActivateGames button 
	 */
	public void enableCreateEditButton() {
		createEditButton.setEnabled(true);
	}

	public EditButtonsPanel(){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(500);
		
		createEditButton.setPreferredSize(new Dimension(150,50));	
		createCancelButton.setVisible(false);
		
		try {
		    Image img = ImageIO.read(getClass().getResource("cancel.png"));
		    createCancelButton.setIcon(new ImageIcon(img));
		    
		    editImg = new ImageIcon(ImageIO.read(getClass().getResource("edit.png")));
		    createEditButton.setIcon(editImg);
		    saveImg = new ImageIcon(ImageIO.read(getClass().getResource("save.png")));
		    
		} catch (IOException ex) {}
		
		createEditButton.setVisible(true);
		// the action listener for the Edit Games button
		createEditButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// check to see if any other tab is currently open
				// if (ViewEventController.getInstance().getMainView().getTabCount() == 1) {
				
					// toggle the editing overview table mode
//					ViewEventController.getInstance().toggleEditingTable(false);
//					// edits the Edit Button text based on whether in editing overview table mode or not
//					if (ViewEventController.getInstance().getOverviewTable().getEditFlag()) {
//						ViewEventController.getInstance().getOverviewTable().repaint();
//						setButtonToActivate();
////					}	
//					else {
						setButtonToEdit();
//					}
				}
			//}
		});
		
		createCancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// toggle the editing overview table mode
//				ViewEventController.getInstance().toggleEditingTable(true);			
				setButtonToEdit();
				createEditButton.setEnabled(true);

			}
		});
		contentPanel.add(createEditButton);
		contentPanel.add(createCancelButton);
		contentPanel.setOpaque(false);
		
		this.add(contentPanel);
	}
	
	/**
	 * Method getEditButton.
	 * @return JButton 
	 * 
	 */
	public JButton getEditButton() {
		return this.createEditButton;
	}
	/**
	 * This method creates an edit button and enables it while disableing the cancel button
	 */
	public void setButtonToEdit(){
		if (editImg != null){
			createEditButton.setIcon(editImg);}
		createEditButton.setText("<html>Edit<br />Games</html>");
		createEditButton.setEnabled(true);
		createCancelButton.setVisible(false);
	}
	/**
	 * This method sets the button to read "activate"
	 */
	public void setButtonToActivate(){
		if (saveImg != null){
			createEditButton.setIcon(saveImg);}
		createEditButton.setText("<html>Activate<br />Games</html>");
		createEditButton.setEnabled(false);
		createCancelButton.setVisible(true);
	}
	/**
	 * This method enables the edit button.
	 * @param enabled whether the button should have enabled be true or false
	 */
	public void setActivateEnabled(boolean enabled){
		createEditButton.setEnabled(enabled);
	}
	class activateGameActionListener implements ActionListener{
		int gameID;
		public activateGameActionListener(int gameID){
			this.gameID = gameID;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			List<GameSession> games = GameModel.getInstance().getGames();
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
	class endGameActionListener implements ActionListener{
		int gameID;
		public endGameActionListener(int gameID){
			this.gameID = gameID;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			List<GameSession> games = GameModel.getInstance().getGames();
			for(GameSession g: games){
				if(g.getGameID() == gameID){
					g.setGameStatus(GameStatus.ARCHIVED);
					final Request request = Network.getInstance().makeRequest("planningpoker/planningpokergame", HttpMethod.POST); // POST == UPDATE
					request.setBody(g.toJSON()); // put the new session in the body of the request
					request.addObserver(new UpdateGameRequestObserver()); // add an observer to process the response
					request.send(); // send the request
				}
			}
			
		}
		
	}
}
