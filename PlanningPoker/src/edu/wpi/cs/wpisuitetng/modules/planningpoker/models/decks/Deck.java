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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh.Refreshable;

/**
 * A deck in a game. Decks can be assigned to a game.
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
*/
public class Deck extends AbstractModel {

	/** the ID of the deck */
	private int id;
	private int UserID = -1;
	private final String name;
	private List<Integer> cards;
	private boolean isDeleted;

	private boolean isSingleSelection;
	
	/**
	 * Constructs a Deck with default cards
	 */
	public Deck()
	{
		id = 0;
		name = "Default Deck";
		cards = Arrays.asList(0,1,1,2,3,5,8,13); 
		isDeleted = false;
		isSingleSelection = false;
	}
	
	/**
	 * Constructs a deck with the given cards that has the given id
	 * @param name the deck's name
	 * @param cards the cards that should be in the deck
	 */
	public Deck(String name, List<Integer> cards)
	{
		this.id = -1;
		this.name = name;
		this.cards = cards;
		this.isDeleted = false;
		this.isSingleSelection = false;
	}
	/**
	 * compares two decks for equality based on name
	 * 
	 * @param that deck to compare to
	 * 
	 * @return boolean for equality */
	
	@Override
	public boolean equals(Object that) {
		if (that instanceof Deck){
			Deck o = (Deck) that;
			return (this.id == o.getId() && this.cards.equals(o.getCards()));
		}
		else return false; // if it's not a deck, it's not equal.
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
	 * @param id to be set
	 * @return this Deck object
	 */
	public Deck setId(int id){
		this.id = id;
		return this;
	}
	
	/**
	 * @return the name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return status of the deck (isDeleted)
	 */
	public boolean getStatus(){
		return isDeleted;
	}
	
	public Deck setStatus(boolean isDeleted){
		this.isDeleted = isDeleted;
		return this;
	}
	
	/**
	 * return the String representation of this deck
	 */
	public String toString(){
		final String s = "Deck ID: " + id + "; name: " + name + "; cards:" + cards.toString();
		return s;
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

	public boolean isSingleSelection() {
		return isSingleSelection;
	}

	public Deck setSingleSelection(boolean isSingleSelection) {
		this.isSingleSelection = isSingleSelection;
		return this;
	}
	/**
	 * set the card list
	 * @param cards the card list
	 */
	public void setCards(List<Integer> cards) {
		this.cards = cards;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

}
