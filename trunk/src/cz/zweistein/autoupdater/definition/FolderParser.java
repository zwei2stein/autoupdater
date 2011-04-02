package cz.zweistein.autoupdater.definition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

import cz.zweistein.autoupdater.definition.vo.Directory;
import cz.zweistein.autoupdater.definition.vo.VersionedFile;

public class FolderParser {
	
	public static Directory parse(String path) {
		
		File dir = new File(path);
		
		if (dir.isDirectory()) {
			
			Directory directory = new Directory(dir.getName());
			
			File[] contents = dir.listFiles();
			
			for (File file : contents) {
				
				if (file.isDirectory()) {
					directory.getDirectories().add(parse(file.getPath()));
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
		
		else return null;
		
	}

}
