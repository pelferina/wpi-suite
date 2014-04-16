/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 *    Andrew Hurle
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
 * This is the entity manager for the EmailAddressModel in the
 * PostBoard module.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class EmailAddressEntityManager implements EntityManager<EmailAddressModel> {

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
	public EmailAddressEntityManager(Data db) {
		this.db = db;
	}

	/*
	 * Saves a EmailAddressModel when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	
	
	// content is not a json string, is just the address for the user.
	@Override
	public EmailAddressModel makeEntity(Session s, String content)
			throws WPISuiteException {
		
		// Parse the message from JSON
		final EmailAddressModel newEmailAddress = new EmailAddressModel(content);
		
		if(content.contains("endGame")){
			System.err.println("Game Ended");
			List<Model> emails = db.retrieveAll(newEmailAddress, s.getProject());
			for(Model e: emails){
				sendEmail(((EmailAddressModel)e).getAddress(), "A game ended", content.substring(7) + " just ended");
			}
			return newEmailAddress;
		}
		
		else if(content.contains("newGame")){
			System.err.println("Game Ended");
			List<Model> emails = db.retrieveAll(newEmailAddress, s.getProject());
			for(Model e: emails){
				sendEmail(((EmailAddressModel)e).getAddress(), "A game started", content.substring(7) + " just started");
			}
			return newEmailAddress;
		} 

		
		newEmailAddress.setUserID(s.getUser().getIdNum());
		List<Model> emails = db.retrieveAll(newEmailAddress, s.getProject());

		for(Model e: emails){			
			if(((EmailAddressModel)e).getUserID() == s.getUser().getIdNum()){
				if(!((EmailAddressModel)e).getAddress().equals(content)){
					db.update(EmailAddressModel.class, "UserID", s.getUser().getIdNum(), "Address", newEmailAddress.getAddress());
					sendEmail(content, "Update Email", ("Hi " + s.getUser().getUsername() + ", You just updated your notification email address!"));
					System.out.println("update your email");
					return newEmailAddress;
				}
				System.out.println("not update your email because the email address is not changed");
				return newEmailAddress;
			}
		}
		
		System.out.println("set your email");
		if (!db.save(newEmailAddress, s.getProject())) {
			throw new WPISuiteException();
		}
		sendEmail(content, "Set Email", ("Hi " + s.getUser().getUsername() + ", You just set your notification email address!"));


		// Return the newly created email (this gets passed back to the client)
		return newEmailAddress;
	}

	/*
	 * Individual Email cannot be retrieved. This message always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public EmailAddressModel[] getEntity(Session s, String id)
			throws WPISuiteException {
		throw new WPISuiteException();
	}

	/* 
	 * Returns all of the emails that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public EmailAddressModel[] getAll(Session s) throws WPISuiteException {
		throw new WPISuiteException();
	}

	/*
	 * Emails cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public EmailAddressModel update(Session s, String content)
			throws WPISuiteException {
		throw new WPISuiteException();
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng.Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, EmailAddressModel model){

		// Save the given defect in the database
		db.save(model);
	}

	/*
	 * Emails cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {

		// This module does not allow EmailAddressModels to be deleted, so throw an exception
		throw new WPISuiteException();
	}

	/*
	 * Emails cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng.Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {

		// This module does not allow EmailAddressModels to be deleted, so throw an exception
		throw new WPISuiteException();
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count(){
		// Return the number of EmailAddressModels currently in the database
		return db.retrieveAll(EmailAddressModel.class).size();
	}

	@Override
	public String advancedGet(Session s, String[] args){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content){
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method sends an email.
	 * @param sent_to The person to send the email to
	 * @param send_subject The subject line of the email
	 * @param send_text The text body of the email.
	 */
	public void sendEmail(String sent_to, String send_subject, String send_text) {
		 
		final String username = "fff8e7.email@gmail.com";
		final String password = "fff8e7team5";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		javax.mail.Session session = javax.mail.Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("fff8e7.email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(sent_to));
			message.setSubject(send_subject);
			message.setText(send_text);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}


}