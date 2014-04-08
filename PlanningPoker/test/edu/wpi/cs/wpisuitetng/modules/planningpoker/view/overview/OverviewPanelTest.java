/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.GameModel;

/**
 * @author Anthony
 *
 */
public class OverviewPanelTest {

	@Test
	public void test() {
		GameModel gm = new GameModel();
		OverviewPanel op = new OverviewPanel(gm);
		op.updateTable("Drafts");
		assertNotNull(op);
	}

}
