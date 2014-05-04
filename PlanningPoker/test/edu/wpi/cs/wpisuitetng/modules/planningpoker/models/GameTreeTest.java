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
		final DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Test Top");
		assertNotNull(new GameTree(topNode));
		assertNotNull((new GameTree(topNode)).getTop());
	}

}
