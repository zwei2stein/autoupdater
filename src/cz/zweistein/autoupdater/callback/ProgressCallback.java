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
			callback.tick();
		}
	}

	@Override
	public void newFound(String filename) {
		for (IProgressCallback callback : callbacks) {
			callback.newFound(filename);
			callback.tick();
		}
	}

	@Override
	public void deleted(String filename) {
		for (IProgressCallback callback : callbacks) {
			callback.deleted(filename);
			callback.tick();
		}
	}

	@Override
	public void done() {
		for (IProgressCallback callback : callbacks) {
			callback.done();
			callback.tick();
		}
	}

	@Override
	public void localParseStart() {
		for (IProgressCallback callback : callbacks) {
			callback.localParseStart();
			callback.tick();
		}
	}

	@Override
	public void localParseDone() {
		for (IProgressCallback callback : callbacks) {
			callback.localParseDone();
			callback.tick();
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
			callback.tick();
		}
	}

	public void registerCallback(IProgressCallback callback) {
		this.callbacks.add(callback);
	}

	@Override
	public void totalProgress(Long localSize, Long remoteSize) {
		for (IProgressCallback callback : callbacks) {
			callback.totalProgress(localSize, remoteSize);
			callback.tick();
		}
	}

	@Override
	public void tick() {
		for (IProgressCallback callback : callbacks) {
			callback.tick();
		}
	}

	@Override
	public void speed(Long bytesPerSec) {
		for (IProgressCallback callback : callbacks) {
			callback.speed(bytesPerSec);
		}
	}

}
