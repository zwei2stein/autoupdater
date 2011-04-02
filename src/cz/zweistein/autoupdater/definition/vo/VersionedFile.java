package cz.zweistein.autoupdater.definition.vo;

public class VersionedFile {
	
	private String name;
	private String sha1;
	private Long size;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSha1() {
		return sha1;
	}
	
	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	public Long getSize() {
		return size;
	}

	@Override
	public String toString() {
		return "VersionedFile [name=" + this.name + ", sha1=" + this.sha1
				+ ", size=" + this.size + "]";
	}

}
