package cz.zweistein.autoupdater.definition;

import java.io.IOException;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import cz.zweistein.autoupdater.definition.vo.Directory;
import cz.zweistein.autoupdater.definition.vo.VersionedFile;

public class XMLParser {
	
	public static Directory parse(String xmlContent) throws SAXException, IOException {
		
		DOMParser parser = new DOMParser();
		
		parser.parse(new InputSource(new StringReader(xmlContent)));
		
		Document doc = parser.getDocument();
		
		NodeList rootNode = doc.getElementsByTagName("autoUpdater");
		Node autoUpdater = rootNode.item(0);
		
		Directory output = null;
		
		for (int i = 0; i < autoUpdater.getChildNodes().getLength(); i++) {
			if ("directory".equals(autoUpdater.getChildNodes().item(i).getNodeName())) {
				output = parseDirectoryNode(autoUpdater.getChildNodes().item(i));
				break;
			}
		}
			
		return output;
	}

	private static Directory parseDirectoryNode(Node item) {
		Directory dir = new Directory(item.getAttributes().getNamedItem("name").getNodeValue());
		
		for (int i = 0; i < item.getChildNodes().getLength(); i++) {
			Node child = item.getChildNodes().item(i);
			
			if ("directory".equals(child.getNodeName())) {
				dir.getDirectories().add(parseDirectoryNode(child));
			}
			
			if ("file".equals(child.getNodeName())) {
				dir.getFiles().add(parseFileNode(child));
			}
		
		}
		
		return dir;
	}

	private static VersionedFile parseFileNode(Node item) {
		VersionedFile file = new VersionedFile();
		
		file.setName(item.getAttributes().getNamedItem("name").getNodeValue());
		file.setSha1(item.getAttributes().getNamedItem("sha1").getNodeValue());
		
		return file;
	}

}
