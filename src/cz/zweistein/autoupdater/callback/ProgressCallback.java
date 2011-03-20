package cz.zweistein.autoupdater.callback;

import java.util.ArrayList;
import java.util.List;

public class ProgressCallback implements IProgressCallback {
	
	private List<IProgressCallback> callbacks;
	
	public ProgressCallback() {
		super();
		
		callbacks = new ArrayList<IProgressCallback>();
	}

	@Override
	public void changeFound(String filename) {
		for (IProgressCallback callback : callbacks) {
			callback.changeFound(filename);
		}
	}

	@Override
	public void newFound(String filename) {
		for (IProgressCallback callback : callbacks) {
			callback.newFound(filename);
		}
	}

	@Override
	public void deleted(String filename) {
		for (IProgressCallback callback : callbacks) {
			callback.deleted(filename);
		}
	}

	@Override
	public void done() {
		for (IProgressCallback callback : callbacks) {
			callback.done();
		}
	}

	@Override
	public void localParseStart() {
		for (IProgressCallback callback : callbacks) {
			callback.localParseStart();
		}
	}

	@Override
	public void localParseDone() {
		for (IProgressCallback callback : callbacks) {
			callback.localParseDone();
		}
	}
	
	@Override
	public void error(String message) {
		for (IProgressCallback callback : callbacks) {
			callback.error(message);
		}
	}
	
	@Override
	public void downloadProgress(Long progress, Long total, String url) {
		for (IProgressCallback callback : callbacks) {
			callback.downloadProgress(progress, total, url);
		}
	}

	public void registerCallback(IProgressCallback callback) {
		this.callbacks.add(callback);
	}

}
