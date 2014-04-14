/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Anthony
 *
 */
public class OverviewPanelTest {

	@Test
	public void test() {
		OverviewPanel op = new OverviewPanel();
		op.updateTable("Drafts");
		assertNotNull(op);
	}

}
