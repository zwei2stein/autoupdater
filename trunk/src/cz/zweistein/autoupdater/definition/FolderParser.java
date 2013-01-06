package cz.zweistein.autoupdater.definition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

import cz.zweistein.autoupdater.callback.IProgressCallback;
import cz.zweistein.autoupdater.definition.vo.Directory;
import cz.zweistein.autoupdater.definition.vo.VersionedFile;

public class FolderParser {
	
	private IProgressCallback callbackHolder;
	
	public FolderParser(IProgressCallback callbackHolder) {
		super();
		this.callbackHolder = callbackHolder;
	}

	public Directory parse(String path, String ignoredirs) {
		
		File dir = new File(path);
		
		callbackHolder.tick();
		
		if (dir.isDirectory()) {
			
			if (dir.getName().equalsIgnoreCase(ignoredirs) ) {
				
				return new Directory(dir.getName(), true);
				
			} else {
			
				Directory directory = new Directory(dir.getName(), false);
				
				File[] contents = dir.listFiles();
				
				for (File file : contents) {
					
					if (file.isDirectory()) {
						directory.getDirectories().add(parse(file.getPath(), ignoredirs));
					} else if (file.isFile()) {
						VersionedFile versionedFile = new VersionedFile();
						versionedFile.setName(file.getName());
						versionedFile.setSize(file.length());
						try {
							FileInputStream is = new FileInputStream(file);
							versionedFile.setSha1(DigestUtils.shaHex(is));
							is.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						directory.getFiles().add(versionedFile);
					}
					
				}
				
				return directory;
			
			}
			
		} else {
			throw new IllegalArgumentException("Directory " + path + " not found."); 
		}
		
	}

}
