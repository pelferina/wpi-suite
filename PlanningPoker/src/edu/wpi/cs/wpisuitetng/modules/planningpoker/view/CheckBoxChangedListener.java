package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddEmailAddressObserver;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.EmailAddressModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class CheckBoxChangedListener implements ItemListener{
	private PreferencesPanel panel;
	public CheckBoxChangedListener(PreferencesPanel panel){
		this.panel = panel;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		EmailAddressModel eModel = panel.getEmailModel();
		if(e.getStateChange() == ItemEvent.SELECTED){
			eModel.setEnable(true);
			panel.makeEmailEnable();
		}else{
			eModel.setEnable(false);
			panel.makeEmailDisable();
		}
		panel.setEmailModel(eModel);
		
		// Send a request to the core to save this email
		final Request request = Network.getInstance().makeRequest("planningpoker/emailmodel", HttpMethod.PUT); // PUT == create
		//request.setBody(new EmailAddressModel(address).toJSON()); // put the new message in the body of the request
		request.setBody(eModel.toJSON());
		request.addObserver(new AddEmailAddressObserver()); // add an observer to process the response
		request.send(); // send the request
		
	}

}
