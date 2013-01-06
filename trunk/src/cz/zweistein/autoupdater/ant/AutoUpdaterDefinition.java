package cz.zweistein.autoupdater.ant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import cz.zweistein.autoupdater.callback.DummyProgressCallback;
import cz.zweistein.autoupdater.definition.FolderParser;
import cz.zweistein.autoupdater.definition.XMLProducer;
import cz.zweistein.autoupdater.definition.vo.Directory;

public class AutoUpdaterDefinition extends Task {
	
	private String srcdir;
	private String destfile;
	private String ignoredirs;
	
	public void setSrcdir(String srcdir) {
		this.srcdir = srcdir;
	}

	public void setDestfile(String destfile) {
		this.destfile = destfile;
	}
	
	public void setIgnoredirs(String ignoredirs) {
		this.ignoredirs = ignoredirs;
	}

	public void execute() throws BuildException {
		
		try {
			
			System.out.println("Staring");
			
			FolderParser folderParser = new FolderParser(new DummyProgressCallback());
			
			System.out.println("Parsing " + this.srcdir);
			
			Directory directory = folderParser.parse(this.srcdir, this.ignoredirs);
			
			System.out.println("Converting Config File");
			
			String content = XMLProducer.createXML(directory);
			
			System.out.println("Saving " + this.destfile);
			
			File xmlFile = new File(this.destfile);
	
	        FileWriter fileWriter = new FileWriter(xmlFile);
	        fileWriter.write(content);
	        fileWriter.close();
	        
	        System.out.println("Done");
	        
		} catch (IOException e) {
			throw new BuildException(e);
		} catch (ParserConfigurationException e) {
			throw new BuildException(e);
		} catch (TransformerException e) {
			throw new BuildException(e);
		}
        
	}

}
