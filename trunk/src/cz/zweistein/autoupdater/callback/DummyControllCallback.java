package cz.zweistein.autoupdater.callback;

public class DummyControllCallback implements IControllCallback {

	public boolean deleteExistingFile(String filename) {
		return true;
	}

	public boolean replaceExistingFile(String filename) {
		return true;
	}

}
