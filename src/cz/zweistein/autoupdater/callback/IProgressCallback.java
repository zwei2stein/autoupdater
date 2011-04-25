package cz.zweistein.autoupdater.callback;

public interface IProgressCallback {
	
	public void changeFound(String filename);
	
	public void newFound(String filename);
	
	public void deleted(String filename);
	
	public void done();
	
	public void localParseStart();
	
	public void localParseDone();
	
	public void error(String message);
	
	public void downloadProgress(Long progress, Long total, String url);
	
	public void totalProgress(Long localSize, Long remoteSize);
	
	public void tick();
	
}
