package cz.zweistein.autoupdater.callback;

public class DummyCallback implements IProgressCallback {

	@Override
	public void changeFound(String filename) {
	}

	@Override
	public void newFound(String filename) {
	}

	@Override
	public void deleted(String filename) {
	}

	@Override
	public void done() {
	}

	@Override
	public void localParseStart() {
	}

	@Override
	public void localParseDone() {
	}

	@Override
	public void error(String message) {
	}

	@Override
	public void downloadProgress(Long progress, Long total, String url) {
	}

	@Override
	public void totalProgress(Long localSize, Long remoteSize) {
	}

	@Override
	public void tick() {
	}

}
