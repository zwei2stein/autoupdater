package cz.zweistein.autoupdater.definition.vo;

import java.util.ArrayList;
import java.util.List;

public class Directory {
	
	private List<Directory> directories;
	private List<VersionedFile> files;
	
	private String name;
	
	public Directory(String name) {
		super();
		
		this.directories = new ArrayList<Directory>();
		this.files = new ArrayList<VersionedFile>();
		
		this.name = name;
	}

	public List<Directory> getDirectories() {
		return directories;
	}
	
	public List<VersionedFile> getFiles() {
		return files;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Directory [directories=" + directories + ", files=" + files
				+ ", name=" + name + "]";
	}

}
