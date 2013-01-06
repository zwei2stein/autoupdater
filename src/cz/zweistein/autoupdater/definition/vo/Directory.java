package cz.zweistein.autoupdater.definition.vo;

import java.util.ArrayList;
import java.util.List;

public class Directory {
	
	private List<Directory> directories;
	private List<VersionedFile> files;
	
	private String name;
	private boolean ignore;
	
	public Directory(String name, boolean ignore) {
		super();
		
		this.directories = new ArrayList<Directory>();
		this.files = new ArrayList<VersionedFile>();
		
		this.name = name;
		this.ignore = ignore;
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
	
	public Long getSize() {
		long result = 0l;
		
		for (Directory d: this.directories) {
			result+=d.getSize();
		}
		for (VersionedFile f: this.files) {
			result+=f.getSize();
		}
		
		return result;
	}

	@Override
	public String toString() {
		return "Directory [directories=" + directories + ", files=" + files
				+ ", name=" + name + "]";
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public boolean getIgnore() {
		return ignore;
	}

}
