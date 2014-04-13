package edu.wpi.cs.wpisuitetng.modules.planningpoker.refresh;

import java.util.ArrayList;
import java.util.List;

public abstract class RefreshableController {
	protected List<Refreshable> refreshables = new ArrayList<Refreshable>();
	
	public void addRefreshable(Refreshable r){
		refreshables.add(r);
	}
	
	public void removeRefreshable(Refreshable r){
		refreshables.remove(r);
	}
	
	public abstract void refresh();
}
