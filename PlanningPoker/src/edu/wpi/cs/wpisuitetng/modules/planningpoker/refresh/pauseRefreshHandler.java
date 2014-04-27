package edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh;

import java.util.ArrayList;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class pauseRefreshHandler implements AncestorListener {

	private static AncestorListener instance;
	private static ArrayList<RefreshManager> RefMan;

	/**
	 * private constructor for singleton class
	 */
	private pauseRefreshHandler(){
		this.RefMan = new ArrayList<RefreshManager>();
	}
	
	/** 
	 * Handles entering planning poker tab
	 * @see javax.swing.event.AncestorListener#ancestorAdded(javax.swing.event.AncestorEvent)
	 */
	@Override
	public void ancestorAdded(AncestorEvent event) {
		for(RefreshManager r : RefMan){
			r.startRefresh();
		}

	}

	/** 
	 * Unused, called during the setup
	 * @see javax.swing.event.AncestorListener#ancestorMoved(javax.swing.event.AncestorEvent)
	 */
	@Override
	public void ancestorMoved(AncestorEvent event) {}

	/**
	 * Handles exiting the planning poker tab
	 * @see javax.swing.event.AncestorListener#ancestorRemoved(javax.swing.event.AncestorEvent)
	 */
	@Override
	public void ancestorRemoved(AncestorEvent event) {
		for(RefreshManager r : RefMan){
			r.stopRefresh();
		}
	}

	/**
	 * @return the instance
	 */
	public static AncestorListener getInstance() {
		if(instance == null) {
			instance = new pauseRefreshHandler();
		}
		return instance;
	}
	
	public static void addRefreshManager(RefreshManager NewRefManager){
		pauseRefreshHandler.getInstance(); // ensure that refmanager gets created
		RefMan.add(NewRefManager);
	}

}
