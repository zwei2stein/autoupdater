package cz.zweistein.autoupdater.callback;

import java.util.ArrayList;
import java.util.List;

public class ControllCallback implements IControllCallback {
	
	private List<IControllCallback> callbacks;
	
	public ControllCallback() {
		super();
		
		callbacks = new ArrayList<IControllCallback>();
	}

	public void registerCallback(IControllCallback callback) {
		this.callbacks.add(callback);
	}

	@Override
	public boolean deleteExistingFile(String filename) {
		if (callbacks.size() == 0 ) return true;
		boolean takeAction = false;
		for (IControllCallback callback : callbacks) {
			takeAction |= callback.deleteExistingFile(filename);
		}
		return takeAction;
	}

	@Override
	public boolean replaceExistingFile(String filename) {
		if (callbacks.size() == 0 ) return true;
		boolean takeAction = false;
		for (IControllCallback callback : callbacks) {
			takeAction |= callback.replaceExistingFile(filename);
		}
		return takeAction;
	}

}
