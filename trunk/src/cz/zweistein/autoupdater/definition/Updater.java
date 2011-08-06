package cz.zweistein.autoupdater.definition;

import java.io.File;
import java.io.IOException;

import cz.zweistein.autoupdater.callback.IControllCallback;
import cz.zweistein.autoupdater.callback.IProgressCallback;
import cz.zweistein.autoupdater.definition.vo.Directory;
import cz.zweistein.autoupdater.definition.vo.VersionedFile;
import cz.zweistein.autoupdater.remote.Remote;

public class Updater {

	private IProgressCallback progressCallbackHolder;
	private IControllCallback controllCallbackHolder;
	private Remote remote;
	
	private Long total;
	private Long progress;

	public Updater(IProgressCallback progressCallbackHolder, IControllCallback controllCallbackHolder, Long progress, Long total) {
		this.progressCallbackHolder = progressCallbackHolder;
		this.controllCallbackHolder = controllCallbackHolder;
		this.total = total;
		this.progress = progress;
		this.remote = new Remote(progressCallbackHolder);
	}

	public void compareAndUpdate(Directory local, Directory remote, String localPath, String remotePath) throws IOException {
		
		for (VersionedFile localFile : local.getFiles()) {
			boolean found = false;
			for (VersionedFile remoteFile : remote.getFiles()) {
				if (localFile.getName().equals(remoteFile.getName())) {
					if (localFile.getSha1().equals(remoteFile.getSha1())) {
						//file is up-to-date
					} else {
						if (this.controllCallbackHolder.replaceExistingFile(localPath+"/"+localFile.getName())) {
							this.progressCallbackHolder.changeFound(localFile.getName());
							//file is changed
							String localFilename = localPath+"/"+localFile.getName();
							new File(localFilename).delete();
							this.remote.downloadFile(localFilename, remotePath+"/"+remoteFile.getName(), remoteFile.getSize());
						}
						this.progress+=remoteFile.getSize();
						this.progressCallbackHolder.totalProgress(this.progress, this.total);
						
					}
					found = true;
					break;
				}
			}
			if (!found) {
				if (this.controllCallbackHolder.deleteExistingFile(localPath+"/"+localFile.getName())) {
					this.progressCallbackHolder.deleted(localFile.getName());
					//file was removed
					new File(localPath+"/"+localFile.getName()).delete();
				}
			}
		}
		
		for (VersionedFile remoteFile : remote.getFiles()) {
			boolean found = false;
			for (VersionedFile localFile : local.getFiles()) {
				if (localFile.getName().equals(remoteFile.getName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				this.progressCallbackHolder.newFound(remoteFile.getName());
				//file was added
				String localFilename = localPath+"/"+remoteFile.getName();
				this.remote.downloadFile(localFilename, remotePath+"/"+remoteFile.getName(), remoteFile.getSize());
				this.progress+=remoteFile.getSize();
				this.progressCallbackHolder.totalProgress(this.progress, this.total);
			}
		}
		
		for (Directory localDirectory : local.getDirectories()) {
			boolean found = false;
			for (Directory remoteDirectory : remote.getDirectories()) {
				if (localDirectory.getName().equals(remoteDirectory.getName())) {
					found = true;
					compareAndUpdate(localDirectory, remoteDirectory, localPath+"/"+localDirectory.getName(), remotePath+"/"+remoteDirectory.getName());
				}
			}
			if (!found) {
				if (this.controllCallbackHolder.deleteExistingFile(localPath+"/"+localDirectory.getName())) {
					//directory was removed
					deleteDirectoryTree(new File(localPath+"/"+localDirectory.getName()));
				}
			}
		}
		
		for (Directory remoteDirectory : remote.getDirectories()) {
			boolean found = false;
			for (Directory localDirectory : local.getDirectories()) {
				if (localDirectory.getName().equals(remoteDirectory.getName())) {
					found = true;
				}
			}
			if (!found) {
				//directory was added
				Directory newDir = new Directory(remoteDirectory.getName());
				local.getDirectories().add(newDir);
				
				String createdDirPath = localPath+"/"+newDir.getName(); 
				new File(createdDirPath).mkdir();
				
				compareAndUpdate(newDir, remoteDirectory, createdDirPath, remotePath+"/"+remoteDirectory.getName());
			}
		}
		
	}

	private void deleteDirectoryTree(File file) {
		for(File child: file.listFiles()) {
			if (child.isFile()) {
				this.progressCallbackHolder.deleted(child.getName());
				this.progress-=child.length();
				this.progressCallbackHolder.totalProgress(this.progress, this.total);
				child.delete();
			} else if (child.isDirectory()) {
				deleteDirectoryTree(child);
			}
		}
		file.delete();
		this.progressCallbackHolder.deleted(file.getName());
	}
	
}
