package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user.GetCurrentUser;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameSession;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.characteristics.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class ButtonPanel extends ToolbarGroupView{
	private final JPanel contentPanel = new JPanel();
	private LinkedList<JButton> buttonQueue = new LinkedList<JButton>();
	
	JButton newButton = new JButton("<html>New<br />Game</html>");
	JButton	settingButton = new JButton("<html>User<br />Setting</html>");
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
		readImg();
		newButton.setIcon(newImg);
		settingButton.setIcon(settingImg);
		this.setPreferredWidth(500);
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
		contentPanel.add(settingButton);
		this.add(contentPanel);
		super.setContent(contentPanel);
	}
	
	private void readImg(){
		try {
		    newImg = new ImageIcon(ImageIO.read(getClass().getResource("newgameimage.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    settingImg = new ImageIcon(ImageIO.read(getClass().getResource("optionsimage.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    editImg = new ImageIcon(ImageIO.read(getClass().getResource("edit.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    activateImg = new ImageIcon(ImageIO.read(getClass().getResource("activategame.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    archiveImg = new ImageIcon(ImageIO.read(getClass().getResource("archive.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    endImg = new ImageIcon(ImageIO.read(getClass().getResource("cancel.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}

		try {
		    playImg = new ImageIcon(ImageIO.read(getClass().getResource("vote.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
		
		try {
		    viewImg = new ImageIcon(ImageIO.read(getClass().getResource("view.png")));		    
		} catch (IOException ex) {
			System.out.println("IOException thrown in ButtonsPanel.");
		}
	}
	public void removeButtons(){
		for(JButton button:buttonQueue){
			contentPanel.remove(button);
		}
		contentPanel.updateUI();
		buttonQueue.clear();
	}
	public void showButton(final GameSession gameSelected){
		System.err.println("showButton function called");
    	final User currentUser = GetCurrentUser.getInstance().getCurrentUser();
    	final GameStatus status = gameSelected.getGameStatus();
    	final boolean hasCategory = false;
    	
    	
    	if(status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS) ){
    		playButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					ViewEventController.getInstance().playGameTab(gameSelected);
				}
    		});
    		System.err.println("add openButton called");
    		playButton.setIcon(playImg);
    		buttonQueue.add(playButton);
    		contentPanel.add(playButton);
    		contentPanel.updateUI();
    	}else if(status.equals(GameStatus.COMPLETED) || status.equals(GameStatus.ARCHIVED)){
    		viewButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					ViewEventController.getInstance().viewGameTab(gameSelected);					
				}
    		});
    		viewButton.setIcon(viewImg);
    		buttonQueue.add(viewButton);
    		contentPanel.add(viewButton);
    		contentPanel.updateUI();
    	}
    	
    	/*
    	if(currentUser.getIdNum() == gameSelected.getOwnerID()){
    		if(gameSelected.getGameStatus().equals(GameStatus.ACTIVE) || gameSelected.getGameStatus().equals(GameStatus.INPROGRESS)){
    			ViewEventController.getInstance().makeEndGameButtonVisible(gameSelected);
    			//hasCategory = true;
    		}else if(gameSelected.getGameStatus().equals(GameStatus.DRAFT)){
    			if(isValid(gameSelected)){
    				ViewEventController.getInstance().makeActivateGameButtonVisible(gameSelected);
    			}else{
    				System.out.println("disable the button");
    				ViewEventController.getInstance().makeActivateGameButtonDisable(gameSelected);
    			}
    			//hasCategory = true;
    		}else if(gameSelected.getGameStatus().equals(GameStatus.COMPLETED)){
    			ViewEventController.getInstance().makeArchiveGameButtonVisible(gameSelected);
    			//hasCategory = true;
    		}
    		
    		if(gameSelected.getGameStatus().equals(GameStatus.ACTIVE)||gameSelected.getGameStatus().equals(GameStatus.DRAFT)){
    			//ViewEventController.getInstance().makeEditGameButtonVisible(gameID);
    		}else{
    			ViewEventController.getInstance().makeEditGameButtonInVisible();
    		}
    	}else{
    		
    	}
    	
    	if(status.equals(GameStatus.ACTIVE) || status.equals(GameStatus.INPROGRESS)){
			ViewEventController.getInstance().makeVoteGameButtonVisible(gameSelected);
		}else if(status.equals(GameStatus.ARCHIVED) || status.equals(GameStatus.COMPLETED)){
			ViewEventController.getInstance().makeViewGameButtonVisible(gameSelected);
		}else {
			ViewEventController.getInstance().makeUserButtonInvisible();
		}
		*/
	}

	/**
	 * Checks if the GameSession is valid
	 * @param gs the GameSession to check
	 * @return true if valid, false otherwise
	 */
	private boolean isValid(GameSession gs) { // if all neccessary fields are filled out, returns true
		if (gs.getGameName().length()>0 && gs.getGameReqs().size()>0){
			if (gs.getEndDate()==null || gs.getEndDate().getTime()>System.currentTimeMillis()){
				return true;
			}
		}
		return false;
	}

}
