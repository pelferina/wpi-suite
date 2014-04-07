/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;
import javax.swing.tree.*;

/**
 * @author Anthony
 *
 */
@SuppressWarnings("serial")
public class GameTree extends DefaultMutableTreeNode {
	
	private DefaultMutableTreeNode top;

	public GameTree(DefaultMutableTreeNode top) {
        this.top = top;
        createNodes(top);
	}

	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode drafts = new DefaultMutableTreeNode("Drafts");
		DefaultMutableTreeNode draftGame1 = new DefaultMutableTreeNode("Game 1");
		
		drafts.add(draftGame1);
		
		DefaultMutableTreeNode activeGames = new DefaultMutableTreeNode("Active Games");
		DefaultMutableTreeNode activeGame1 = new DefaultMutableTreeNode("Game 1");
		
		activeGames.add(activeGame1);
		
		DefaultMutableTreeNode pastGames = new DefaultMutableTreeNode("Past Games");
		DefaultMutableTreeNode pastGame1 = new DefaultMutableTreeNode("Game 1");
		
		pastGames.add(pastGame1);
		
		top.add(drafts);
		top.add(activeGames);
		top.add(pastGames);
	}
	
	public DefaultMutableTreeNode getTop(){
		return top;
	}
}
