/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Cosmic Latte
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
/**
 * UpdateDeckRequestObserver class
 * @author FFF8E7
 * @version 6
 */
public class UpdateDeckRequestObserver implements RequestObserver{
	private static UpdateDeckRequestObserver instance = null;

	/**
	 * private constructor in order to ensure singleton nature
	 */
	private UpdateDeckRequestObserver(){
	}
	/** (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		System.out.println("Sucessfully updated a vote!");
	}

	/** (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to update a gamemodel failed. (responseError)");
		
	}

	/** (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to update a gamemodel failed. (fail)");
		
	}

	/**
	 * @return the instance
	 */
	public static UpdateDeckRequestObserver getInstance() {
		if(instance == null){
			instance = new UpdateDeckRequestObserver();
		}
		return instance;
	}

}
