package cz.zweistein.autoupdater.callback;

public class DummyControllCallback implements IControllCallback {

	@Override
	public boolean deleteExistingFile(String filename) {
		return true;
	}

	@Override
	public boolean replaceExistingFile(String filename) {
		return true;
	}

}
