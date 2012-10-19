package org.matheusdev.util;

import java.io.File;

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
	public static boolean hasLibrary(String path) {
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
	public static ProcessBuilder checkLibrary(String libPath, String command, String... arguments) {
		if (!hasLibrary(libPath)) {
			ProcessBuilder p = null;
			String[] cmd = new String[arguments.length+1];
			cmd[0] = command;
			for (int i = 1; i < cmd.length; i++) {
				cmd[i] = arguments[i-1];
			}
			p = new ProcessBuilder(cmd);
			p.redirectError(ProcessBuilder.Redirect.PIPE);
			p.redirectOutput(ProcessBuilder.Redirect.PIPE);
			p.redirectInput(ProcessBuilder.Redirect.PIPE);
			return p;
		}
		return null;
	}

}
