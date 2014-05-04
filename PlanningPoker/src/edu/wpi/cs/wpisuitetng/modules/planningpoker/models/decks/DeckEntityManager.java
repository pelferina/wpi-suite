/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models.decks;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Vote;

/**
 * This is the entity manager for the Deck in the
 * DeckManager module.
 *
 * @version $Revision: 1.0 $
 * @author Cosmic Latte
 */
public class DeckEntityManager implements EntityManager<Deck> {

	/** The database */
	Data db;
	
	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public DeckEntityManager(Data db) {
		this.db = db; 
	}

	/**
	 * Saves a Deck when it is received from a client
	 * 
	
	 * @param s the session
	 * @param content a deck that has been converted to Json
	
	
	
	 * @return the deck passed into the method converted from Json to a deck * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String) */
	@Override
	public Deck makeEntity(Session s, String content) throws WPISuiteException {
		final Deck importedDeck = Deck.fromJson(content);
		final Deck[] decks = getAll(s);
		final Deck newDeck =  new Deck(importedDeck.getName(), importedDeck.getCards());
		
		newDeck.setIsDeleted(importedDeck.getIsDeleted());
		newDeck.setUserID(importedDeck.getUserID());
		newDeck.setIsSingleSelection(importedDeck.getIsSingleSelection());
		newDeck.setId(decks.length + 1);
		
		if(!db.save(newDeck, s.getProject())) {
			System.err.println("Deck not saved");
			throw new WPISuiteException();
		}
		System.err.println("Deck saved");
		System.out.println("new deck being saved is deck " + newDeck.getId());
		return newDeck;
	}
	
	/**
	 * Retrieves a single Deck from the database
	 * @param s the session
	 * @param id the id number of the Deck to retrieve
	
	
	
	
	 * @return the Deck matching the given id * @throws NotFoundException * @throws NotFoundException * @throws NotFoundException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String) */
	/*TODO: for the future, when decks have been added, when they are selected in the drop-down,
	 * the database will be notified with this function. It will call this function using the ID
	 * of the deck selected, and this will return the deck selected.*/
	@Override
	public Deck[] getEntity(Session s, String id) throws NotFoundException {
		final int intId = Integer.parseInt(id);
		if(intId < 0) {
			throw new NotFoundException();
		}
		Deck[] Decks = null;
		try {
			Decks = db.retrieve(Deck.class, "id", intId, s.getProject()).toArray(new Deck[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(Decks.length < 1 || Decks[0] == null) {
			throw new NotFoundException();
		}
		return Decks;
	}

	/**
	 * Retrieves all Decks from the database
	 * @param s the session
	
	
	
	 * @return array of all stored Decks * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	@Override
	public Deck[] getAll(Session s) {
		return db.retrieveAll(new Deck(), s.getProject()).toArray(new Deck[0]);
	}

	/**
	 * Saves a data model to the database
	 * @param s the session
	 * @param model the model to be saved
	 */
	@Override
	public void save(Session s, Deck model) {
		db.save(model);
	}
	
	/**
	 * Deletes a Deck from the database
	 * @param s the session
	 * @param id the id of the Deck to delete
	
	
	
	
	 * @return true if the deletion was successful * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String) */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		//TODO: make it so that any deck but the default deck can be deleted
		//return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
		throw new WPISuiteException();
	}
	
	/**
	 * Deletes all Decks from the database
	 * @param s the session
	
	
	 * @throws WPISuiteException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		//TODO: make it so that every deck except the default deck is deleted
		//db.deleteAll(new Deck(), s.getProject());
		throw new WPISuiteException();
	}
	
	/**
	 * Returns the number of Decks in the database
	
	
	
	
	 * @return number of Decks stored * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count() */
	@Override
	public int Count(){
		return db.retrieveAll(new Deck()).size();
	}

	/**
	 * Updates the given deck in the database
	 * @param session the session the deck to be updated is in
	 * @param content the updated deck as a Json string
	
	
	
	
	 * @return the old deck prior to updating * @throws WPISuiteException * @throws WPISuiteException * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String) */
	@Override
	public Deck update(Session session, String content)
			throws WPISuiteException {
		final Deck newDeck = Deck.fromJson(content);
		db.update(Deck.class, "Id", newDeck.getId(), "Cards", newDeck.getCards());
		db.update(Deck.class, "Id", newDeck.getId(), "Name", newDeck.getName());
		db.update(Deck.class, "Id", newDeck.getId(), "IsDeleted", newDeck.getIsDeleted());
		db.update(Deck.class, "Id", newDeck.getId(), "IsSingleSelection", newDeck.getIsSingleSelection());
		return newDeck;
	}

	/**
	 * Method advancedGet.
	 * @param arg0 Session
	 * @param arg1 String[]
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[]) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedGet(Session, String[])
	 */
	@Override
	public String advancedGet(Session arg0, String[] arg1) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPost.
	 * @param arg0 Session
	 * @param arg1 String
	 * @param arg2 String
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String)
	 */
	@Override
	public String advancedPost(Session arg0, String arg1, String arg2) throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPut.
	 * @param arg0 Session
	 * @param arg1 String[]
	 * @param arg2 String
	
	
	
	 * @return String * @throws NotImplementedException * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String) * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	@Override
	public String advancedPut(Session arg0, String[] arg1, String arg2) throws NotImplementedException {
		throw new NotImplementedException();
	}

}
