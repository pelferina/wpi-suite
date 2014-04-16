/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.deckcontroller.AddDeckController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;


/**
 * List of Decks being pulled from the server
 * 
 * 
 * @version $Revision: 1.0 $
 * @author Cosmic Latte
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class DeckModel extends AbstractListModel {

	/**
	 * The list in which all the Decks for a single project are contained
	 */
	private List<Deck> listOfDecks;
	private int nextID; // the next available ID number for the Decks that
						// are added.
	
	private final Deck defaultDeck;
	
	// the static object to allow the Deck model to be
	private static DeckModel instance;

	/**
	 * Constructs an list of Decks with a default deck for game creation
	 */
	public DeckModel() {
		defaultDeck = new Deck();
		listOfDecks = new ArrayList<Deck>(Arrays.asList(defaultDeck));
		nextID = 1;
	}
	
	/**
	 * @return the instance of the Deck model singleton. 
	 */
	public static DeckModel getInstance() {
		if (instance == null) {
			instance = new DeckModel();
		}
		return instance;
	}


	/**
	 * Adds a single Deck to the Decks of game creation
	 * 
	 * @param newDeck Deck
	 */
	public void addDeck(Deck newDeck) {
		// add the Deck
		listOfDecks.add(newDeck);
		try {
			AddDeckController.getInstance().addDeck(newDeck);
		} catch (Exception e) {
			System.out.println("Exception thrown in DeckModel");
		}
		//TODO:Change to Deck View
		ViewEventController.getInstance().refreshTree();
	}

	/**
	 * Provides the number of elements in the list of Decks for the
	 * project. This function is called internally by the JList in
	 * NewDeckPanel. Returns elements in reverse order, so the newest
	 * Deck is returned first.
	 * 
	 * @return the number of Decks in the project * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize() * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return listOfDecks.size();
	}

	/**
	 * 
	 * Provides the next ID number that should be used for a new Deck that
	 * is created.
	 * 
	 * @return the next open id number */
	public int getNextID() {
		return nextID++;
	}

	/**
	 * This function takes an index and finds the Deck in the list of
	 * Decks for the project. Used internally by the JList in
	 * NewDeckModel.
	 * 
	 * @param index
	 *            The index of the Deck to be returned
	 * @return the Deck associated with the provided index * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int) * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Deck getElementAt(int index) {
		return listOfDecks.get(listOfDecks.size() - 1 - index);
	}

	/**
	 * Removes all Decks from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of the model, because
	 * other classes in this module have references to it. Hence, we manually
	 * remove each Deck from the model.
	 */
	public void emptyModel() {
		final int oldSize = getSize();
		Iterator<Deck> iterator = listOfDecks.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
		ViewEventController.getInstance().refreshTable();
		ViewEventController.getInstance().refreshTree();
	}

	/**
	 * Adds the given array of Decks to the list
	 * 
	 * @param Decks
	 *            the array of Decks to add
	 */
	public void addDecks(Deck[] Decks) {
		System.out.println("Got decks.." + Decks.length);
		for (int i = 0; i < Decks.length; i++) {
			listOfDecks.add(Decks[i]);
			if (Decks[i].getId() >= nextID) nextID = Decks[i].getId() + 1;
		}
		
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
		ViewEventController.getInstance().refreshTree();
	}

	/**
	 * Returns the list of the Decks
	 * 
	 * @return the Decks held within the Deckmodel. */
	public List<Deck> getDecks() {
		return listOfDecks;
	}
	
	/**
	 * Return the deck with the specified id
	 * @param id id of the deck
	 * @return the deck */
	public Deck getDeck(int id)
	{
		if(id == 0) return defaultDeck;
		for(Deck deck : listOfDecks)
		{
			if(deck.getId() ==id) return deck;
		}
		return null;
	}
}