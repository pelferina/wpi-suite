/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import static org.junit.Assert.*;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.Test;

/**
 * @author Anthony
 *
 */
public class GameTreeTest {

	@Test
	public void createGameTreeTest() {
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Test Top");
		GameModel gm = new GameModel();
		assertNotNull(new GameTree(topNode, gm));
		assertNotNull((new GameTree(topNode, gm)).getTop());
	}

}
