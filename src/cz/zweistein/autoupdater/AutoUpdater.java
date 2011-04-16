package cz.zweistein.autoupdater;

import cz.zweistein.autoupdater.callback.IProgressCallback;
import cz.zweistein.autoupdater.callback.ProgressCallback;
import cz.zweistein.autoupdater.definition.FolderParser;
import cz.zweistein.autoupdater.definition.Updater;
import cz.zweistein.autoupdater.definition.XMLParser;
import cz.zweistein.autoupdater.definition.vo.Directory;
import cz.zweistein.autoupdater.remote.Remote;

public class AutoUpdater {
	
	private static final String DFEAULT_DEFINITION_FILE = "autoUpdater.xml";
	
	private String localFolder;
	private String remoteDefinitionURL;
	private String definitionFile;
	
	private ProgressCallback callbackHolder;
	
	public AutoUpdater(String localFolder, String remoteDefinitionURL) {
		super();
		this.localFolder = localFolder;
		this.remoteDefinitionURL = remoteDefinitionURL;
		this.definitionFile = DFEAULT_DEFINITION_FILE;
		
		this.callbackHolder = new ProgressCallback();
	}

	public void start() {
		
		try {
		
			this.callbackHolder.localParseStart();
			Directory local = FolderParser.parse(localFolder);
			this.callbackHolder.localParseDone();
			
			Remote remote = new Remote(callbackHolder);
			
			Directory remoteDir = XMLParser.parse(remote.getUrlContent(this.remoteDefinitionURL+"/"+this.definitionFile));
			
			this.callbackHolder.totalProgress(local.getSize(), remoteDir.getSize());
			
			Updater updater = new Updater(callbackHolder, local.getSize(), remoteDir.getSize()); 
			updater.compareAndUpdate(local, remoteDir, localFolder, remoteDefinitionURL);
			
			this.callbackHolder.done();
		
		} catch (Exception e) {
			this.callbackHolder.error(e.toString());
			this.callbackHolder.done();
		}
	}
	
	public void registerCallback(IProgressCallback callback) {
		this.callbackHolder.registerCallback(callback);
	}

	public void setDefinitionFile(String definitionFile) {
		this.definitionFile = definitionFile;
	}
	
}
