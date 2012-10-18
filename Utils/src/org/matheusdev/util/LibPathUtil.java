package org.matheusdev.util;

import java.io.File;
import java.io.IOException;

/**
 * @author matheusdev
 * @author Nikk
 *
 */
public class LibPathUtil {

	/**
	 * @author Nikk
	 * @param path
	 * @return
	 */
	protected static boolean hasLibrary(String path) {
		String[] libraries = System.getProperty("java.library.path").split(File.pathSeparator);

		for (String lib : libraries) {
			if (lib.equals(path)) return true;
		}
		return false;
	}

	/**
	 * @author Nikk
	 * @param libraryPath
	 * @param jarName
	 * @return
	 */
	public static boolean checkLibrary(String libraryPath, String jarName) {
		if (!hasLibrary(libraryPath)) {
			try {
				Runtime.getRuntime().exec(
						"java -Djava.library.path=\"" + libraryPath + "\" -jar " + jarName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

}
