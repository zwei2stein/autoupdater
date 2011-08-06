package cz.zweistein.autoupdater.callback;

public interface IControllCallback {
	
	public boolean deleteExistingFile(String filename);
	
	public boolean replaceExistingFile(String filename);

}
