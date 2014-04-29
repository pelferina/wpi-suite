/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

/**
 * This enum contains standard distances for placement of GUI objects
 * @author fff8e7
 *
 */
public enum GuiStandards {
	// distances between GUI elements and the edge of the screen
	TOP_MARGIN(34),
	BOTTOM_MARGIN(10),
	LEFT_MARGIN(23),
	RIGHT_MARGIN(23),
	
	// distance between GUI elements and a divider
	DIVIDER_MARGIN(10),
	
	// margins for text areas
	TEXT_AREA_MARGINS(5),
	
	// left margin for single line text boxes
	TEXT_BOX_MARGIN(5),
	
	/* 
	 * height offset for single line text boxes. This is applied to both
	 * the top and bottom of the box, so the change in hieght is 
	 * actually double this value
	 */
	TEXT_HIEGHT_OFFSET(2),
	
	// distance between a label and its text box/area bellow it
	LABEL_TEXT_OFFSET(7),
	
	// distance between a label of a text field/area and the previous text field/area above it
	NEXT_LABEL_OFFSET(15),
	
	// distance between in-line buttons
	BUTTON_OFFSET(10),
	;
	
	
	private int value;
	
	private GuiStandards(int value) {
        this.value = value;
	}

	public int getValue() {
		return value;
	}
}
