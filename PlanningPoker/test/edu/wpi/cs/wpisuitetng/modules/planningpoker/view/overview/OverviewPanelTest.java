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
		assertNotNull(new OverviewPanel(gm));
	}

}
