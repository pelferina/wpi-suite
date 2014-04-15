package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.user;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class GetCurrentUser {
	private static GetCurrentUser instance = null;
	private static User user = null;
	
	public static GetCurrentUser getInstance(){
		if (instance == null){
			instance = new GetCurrentUser();
		}
		return instance;
	}
	public User getCurrentUser(){
		if(user == null){
			final Request request = Network.getInstance().makeRequest("core/user", HttpMethod.PUT); 
			request.setBody("who am I?");
			request.addObserver(new GetCurrentUserRequestObserver()); 
			// add an observer to process the response
			request.send(); // send the request	
			while(user == null){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return user;
		}
		else 
			return user;
	}
	public void setUser(User user){
		this.user = user;
	}
}
