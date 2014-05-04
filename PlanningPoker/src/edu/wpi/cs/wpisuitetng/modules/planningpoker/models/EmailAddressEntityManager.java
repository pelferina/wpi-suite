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

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

/**
 * This is the entity manager for the EmailAddressModel in the
 * PostBoard module.
 * 
 * @author Cosmic Latte
 * @version $Revision: 1.0 $
 */
public class EmailAddressEntityManager implements EntityManager<EmailAddressModel> {

	/** The database */
	static Data db;
	static String gmailUsername = "fff8e7.email@gmail.com";
	static String gmailPassword = "fff8e7team5";

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in
	 * the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */
	public EmailAddressEntityManager(Data db) {
		EmailAddressEntityManager.db = db;
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
		final EmailAddressModel newEmailAddress = EmailAddressModel.fromJson(content);
		
		final List<Model> emails = db.retrieveAll(newEmailAddress, s.getProject());

		for(Model e: emails){
			EmailAddressModel eModel = (EmailAddressModel)e;
			if(eModel.getUsername().equals(s.getUser().getUsername())){
				if(!newEmailAddress.getEnable()){
					System.out.println("Disable");
					db.update(EmailAddressModel.class, "Username", s.getUser().getUsername(), "Enable", false);
					return newEmailAddress;
				}else{
					System.out.println("Enable");
					db.update(EmailAddressModel.class, "Username", s.getUser().getUsername(), "Enable", true);
				}
				System.out.println(eModel.getAddress() + "|||" + newEmailAddress.getAddress());
				if((eModel.getAddress() == null && newEmailAddress.getAddress() != null) || (eModel.getAddress() != null && !eModel.getAddress().equals(newEmailAddress.getAddress()))){
					System.out.println("Update");
					db.update(EmailAddressModel.class, "Username", s.getUser().getUsername(), "Address", newEmailAddress.getAddress());
					sendEmail(newEmailAddress.getAddress(), "Update Email", ("Hi " + s.getUser().getUsername() + ",\r\n\tYou just updated your notification email address!\r\nsent by fff8e7"));
					System.out.println("update email");
				}
				return newEmailAddress;
			}
		}
		
		System.out.println("set your email");
		if (!db.save(newEmailAddress, s.getProject())) {
			throw new WPISuiteException();
		}
		if(!(newEmailAddress.getAddress() == null || newEmailAddress.getAddress().length() == 0)){
			sendEmail(newEmailAddress.getAddress(), "Set Email", ("Hi " + s.getUser().getUsername() + ",\r\n\tYou just set your notification email address!\r\nsent by fff8e7"));
		}


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
	public EmailAddressModel[] getAll(Session s){
			// Ask the database to retrieve all objects of the type
			// PostBoardMessage.
			// Passing a dummy PostBoardMessage lets the db know what type of object
			// to retrieve
			// Passing the project makes it only get messages from that project
		System.out.println("get one email address request");
		
			final EmailAddressModel[] emails = db.retrieveAll(new EmailAddressModel(null, null, false)).toArray(new EmailAddressModel[0]);
			final EmailAddressModel[] returnEmail = new EmailAddressModel[1];
			for(EmailAddressModel e: emails){
				if(e.getUsername().equals(s.getUser().getUsername())){
					returnEmail[0] = e;
					return returnEmail;
				}
			}
			returnEmail[0] = new EmailAddressModel(null, s.getUser().getUsername(), false);
			return returnEmail;
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
	 * @param send_to The person to send the email to
	 * @param send_subject The subject line of the email
	 * @param send_text The text body of the email.
	 */
	public static void sendEmail(String send_to, String send_subject, String send_text) {
 
		final Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		final javax.mail.Session session = javax.mail.Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmailUsername, gmailPassword);
			}
		  });
 
		try {
			final Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gmailUsername));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(send_to));
			message.setSubject(send_subject);
			message.setText(send_text);
 
			Transport.send(message);
 
			System.out.println("Email sent to " + send_to);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Sends an email to all related to the project
	 * @param send_subject The subject line of the email
	 * @param send_text The body of the email
	 * @param project The project to send email for
	 */
	public static void sendToALL(String send_subject, String send_text, Project project) {
		final EmailAddressModel[] emails = db.retrieveAll(new EmailAddressModel(null, null, false), project).toArray(new EmailAddressModel[0]);
		for(EmailAddressModel e: emails){
			if(e.getEnable()){
				sendEmail(e.getAddress(), send_subject, "Hi " + e.getUsername() + ",\r\n\t" + send_text + "\r\nsent by fff8e7");
			}
		}
	}


}