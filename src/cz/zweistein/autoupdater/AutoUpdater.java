package cz.zweistein.autoupdater;

import java.util.List;

import cz.zweistein.autoupdater.callback.ControllCallback;
import cz.zweistein.autoupdater.callback.IControllCallback;
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
	private List<String> remoteDefinitionURLs;
	private String definitionFile;
	
	private ProgressCallback progressCallbackHolder;
	private ControllCallback controllCallbackHolder;
	
	public AutoUpdater(String localFolder, List<String> remoteDefinitionURLs) {
		super();
		this.localFolder = localFolder;
		this.remoteDefinitionURLs = remoteDefinitionURLs;
		this.definitionFile = DFEAULT_DEFINITION_FILE;
		
		this.progressCallbackHolder = new ProgressCallback();
		this.controllCallbackHolder = new ControllCallback();
	}

	public void start() {
		
		try {
		
			this.progressCallbackHolder.localParseStart();
			
			FolderParser folderParser = new FolderParser(progressCallbackHolder);
			Directory local = folderParser.parse(localFolder, null);
			this.progressCallbackHolder.localParseDone();
			
			Remote remote = new Remote(progressCallbackHolder);
			
			Directory remoteDir = XMLParser.parse(remote.getUrlContent(this.remoteDefinitionURLs.get(0) +"/"+this.definitionFile));
			
			this.progressCallbackHolder.totalProgress(local.getSize(), remoteDir.getSize());
			
			Updater updater = new Updater(progressCallbackHolder, controllCallbackHolder, local.getSize(), remoteDir.getSize()); 
			updater.compareAndUpdate(local, remoteDir, localFolder, remoteDefinitionURLs, "");
			
			this.progressCallbackHolder.done();
		
		} catch (Exception e) {
			this.progressCallbackHolder.error(e.toString());
			this.progressCallbackHolder.done();
		}
	}
	
	public void registerProgressCallback(IProgressCallback callback) {
		this.progressCallbackHolder.registerCallback(callback);
	}
	
	public void registerControllCallback(IControllCallback callback) {
		this.controllCallbackHolder.registerCallback(callback);
	}

	public void setDefinitionFile(String definitionFile) {
		this.definitionFile = definitionFile;
	}
	
}
