package cz.zweistein.autoupdater.callback;

import java.util.ArrayList;
import java.util.List;

public class ProgressCallback implements IProgressCallback {
	
	private List<IProgressCallback> callbacks;
	
	public ProgressCallback() {
		super();
		
		callbacks = new ArrayList<IProgressCallback>();
	}

	public void changeFound(String filename) {
		for (IProgressCallback callback : callbacks) {
			callback.changeFound(filename);
			callback.tick();
		}
	}

	public void newFound(String filename) {
		for (IProgressCallback callback : callbacks) {
			callback.newFound(filename);
			callback.tick();
		}
	}

	public void deleted(String filename) {
		for (IProgressCallback callback : callbacks) {
			callback.deleted(filename);
			callback.tick();
		}
	}

	public void done() {
		for (IProgressCallback callback : callbacks) {
			callback.done();
			callback.tick();
		}
	}

	public void localParseStart() {
		for (IProgressCallback callback : callbacks) {
			callback.localParseStart();
			callback.tick();
		}
	}

	public void localParseDone() {
		for (IProgressCallback callback : callbacks) {
			callback.localParseDone();
			callback.tick();
		}
	}
	
	public void error(String message) {
		for (IProgressCallback callback : callbacks) {
			callback.error(message);
		}
	}
	
	public void downloadProgress(Long progress, Long total, String url) {
		for (IProgressCallback callback : callbacks) {
			callback.downloadProgress(progress, total, url);
			callback.tick();
		}
	}

	public void registerCallback(IProgressCallback callback) {
		this.callbacks.add(callback);
	}

	public void totalProgress(Long localSize, Long remoteSize) {
		for (IProgressCallback callback : callbacks) {
			callback.totalProgress(localSize, remoteSize);
			callback.tick();
		}
	}

	public void tick() {
		for (IProgressCallback callback : callbacks) {
			callback.tick();
		}
	}

	public void speed(Long bytesPerSec) {
		for (IProgressCallback callback : callbacks) {
			callback.speed(bytesPerSec);
		}
	}

}
