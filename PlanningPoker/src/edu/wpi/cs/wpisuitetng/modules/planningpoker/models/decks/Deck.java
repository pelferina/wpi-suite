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

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * A deck in a game. Decks can be assigned to a game.
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
*/
public class Deck extends AbstractModel {

	/** the ID of the deck */
	private final int id;
	private final List<Integer> cards;
	
	/**
	 * Constructs a Deck with default cards
	 */
	public Deck()
	{
		super();
		id = 0;
		cards = Arrays.asList(0,1,1,2,3,5,8,13); 
	}
	
	/**
	 * Constructs a deck with the given cards that has the given id
	 * @param id the deck id number
	 * @param cards the cards that should be in the deck
	 */
	public Deck(int id, List<Integer> cards)
	{
		super();
		this.id = id;
		this.cards = cards;
	}
	/**
	 * compares two decks for equality based on name
	 * 
	 * @param that deck to compare to
	 * 
	 * @return boolean for equality */
	// TODO: implement two decks being equal
	public boolean equals(Deck that) {
		return (id == that.id);
	}

	/**
	 * @return the defaultDeck
	 */
	public List<Integer> getCards() {
		return cards;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns an array of Decks parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param body	string containing a JSON-encoded array of Decks
	
	 * @return an array of Requirement deserialized from the given JSON string */
	public static Deck[] fromJsonArray(String body) {
		final Gson parser = new Gson();
		return parser.fromJson(body, Deck[].class);
	}

	/**
	 * Method toJSON.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	public String toJSON() {
		return new Gson().toJson(this, Deck.class);
	}

	/**
	 * Returns an instance of Iteration constructed using the given
	 * Iteration encoded as a JSON string.
	 * 
	 * @param body the
	 *            JSON-encoded Iteration to deserialize
	
	 * @return the Iteration contained in the given JSON */
	public static Deck fromJson(String body) {
		final Gson parser = new Gson();
		final Deck test = parser.fromJson(body, Deck.class);

		return test;
	}

	/**
	 * Method identify.
	 * @param o Object
	
	
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}

	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {

	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {

	}
}
