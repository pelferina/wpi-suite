
/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.view;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks.DeckModel;

import javax.swing.JComboBox;

/**
 * Description
 *
 * @author Eric Faust; Dan Murray; Ayesha Fathima
 * @version Mar 24, 2014
 */
@SuppressWarnings({"serial"})
public class DeckPanel extends JPanel {

	private List<Integer> testData = new ArrayList<Integer>();

	private final JList deckList;
	private final DeckModel lstDeckModel;

	private JComboBox deckDropdown = new JComboBox();

	public DeckPanel(DeckModel deckModel) {
		
		lstDeckModel = deckModel;
		// Construct the decks to be displayed
		deckList = new JList(lstDeckModel);

		//default deck of 1,1,2,3,5,8,13
		testData = deckModel.getDeck(0).getCards();


		setPanel();
	}


	/**
	 * this function displays the deck given to deck panel
	 */
	private void setPanel(){
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		add(deckDropdown); //Adds the comboBox of decks

		addButtons();
		prepareDropdown();

		JButton submitButton = new JButton("Submit");
	}

	/**
	 * This function cycles through available cards and adds the appropriate buttons to the view
	 */
	private void addButtons(){
		//List of buttons associated with the cards. First element -> lowest card val
		List<JButton> cardButtons = new ArrayList<JButton>();

		Iterator<Integer> cardIterator = testData.iterator();
		Iterator<JButton> buttonIterator = cardButtons.iterator();

		System.out.println(testData);
		
		
		//This loop will cycle through the deck's values, and create buttons for them.
		//The buttons will have the value as its text
		while(cardIterator.hasNext()){
			cardButtons.add(new JButton(cardIterator.next().toString()));
		}
		System.out.println("CI:"+cardButtons);
		System.out.println("v2:"+testData);
		//This loop will cycle through all of the buttons that have been created and display them
		
		for(int i=0; i < cardButtons.size(); i++)
		{
		   JButton b = cardButtons.get(i);
		   add(b);
		}
		
	}

	/**
	 * This function creates and places the dropdown menu of available decks.
	 */
	private void prepareDropdown(){


		//Add all the decks to the dropdown
		Iterator<Deck> deckIterator = lstDeckModel.getDecks().iterator();
		while(deckIterator.hasNext()){
			//checks add's next deck.tostring to dropdown
			deckDropdown.addItem(lstDeckModel.getDeck(deckIterator.next().getId()).getCards().toString());
		}
	}
}











