/**
 * 
 */
package org.matheusdev.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author matheusdev
 *
 */
public final class TextFileReader {
	
	// Invisible Constructor.
	private TextFileReader() {}
	
	public String readTextFile(File file) throws IOException {
		return readText(new FileInputStream(file));
	}
	
	public String readText(InputStream stream) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(stream));
		StringBuilder str = new StringBuilder();
		String line;
		while ((line = read.readLine()) != null) {
			str.append(line + "\n");
		}
		return str.toString();
	}
	
}
