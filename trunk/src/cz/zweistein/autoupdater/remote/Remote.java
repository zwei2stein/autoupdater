package cz.zweistein.autoupdater.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import cz.zweistein.autoupdater.callback.IProgressCallback;

public class Remote {
	
	IProgressCallback progressCallback;
	
	private int bufferSize = 1024;
	
	public Remote(IProgressCallback progressCallback) {
		super();
		this.progressCallback = progressCallback;
	}

	public String getUrlContent(String url) throws IOException {
		
		URL remoteDefinition = new URL(url);

		InputStream is = remoteDefinition.openStream();
		Writer writer = new StringWriter();
		Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		char[] buffer = new char[bufferSize];
		try {
			long count = 0;;
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
				count += n;
				progressCallback.downloadProgress(count, null, url);
			}
		} finally {
			reader.close();
			writer.close();
		}
		
		return writer.toString();
		
	}
	
	public void downloadFile(String localFilename, String url, Long expectedSize) throws IOException {
		
		String replacedUrl = url.replaceAll(" ", "%20");
		
		URL remoteDefinition = new URL(replacedUrl);

		InputStream reader = new BufferedInputStream(remoteDefinition.openStream());
		OutputStream writer = new BufferedOutputStream(new FileOutputStream(localFilename));
		
		byte[] buffer = new byte[bufferSize];
		try {
			long count = 0;;
			int n;
			while ((n = reader.read(buffer, 0, bufferSize)) != -1) {
				writer.write(buffer, 0, n);
				count += n;
				progressCallback.downloadProgress(count, expectedSize, url);
			}
		} finally {
			reader.close();
			writer.close();
		}
		
	}

}
