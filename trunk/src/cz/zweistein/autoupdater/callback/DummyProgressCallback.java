package cz.zweistein.autoupdater.callback;

public class DummyProgressCallback implements IProgressCallback {

	public void changeFound(String filename) {
	}

	public void newFound(String filename) {
	}

	public void deleted(String filename) {
	}

	public void done() {
	}

	public void localParseStart() {
	}

	public void localParseDone() {
	}

	public void error(String message) {
	}

	public void downloadProgress(Long progress, Long total, String url) {
	}

	public void totalProgress(Long localSize, Long remoteSize) {
	}

	public void tick() {
	}

	public void speed(Long bytesPerSec) {
	}

}
