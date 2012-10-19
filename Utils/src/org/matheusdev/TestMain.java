package org.matheusdev;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.matheusdev.util.LibPathUtil;


/**
 * @author matheusdev
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Is the path existing before starting a proc? " + LibPathUtil.hasLibrary("test"));
		ProcessBuilder pb = LibPathUtil.checkLibrary("test", "java", "-Djava.library.path=\"test\"", "-jar", "Utils.jar");
		if (pb != null) {
			Process proc = null;
			try {
				proc = pb.start();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println(proc);
			System.out.println("Old Proc: Erm... I need to start up again... I already created a Process.");
			try {
				System.out.println("Old Proc: Return value of recently created process: " + proc.exitValue());
			} catch (IllegalThreadStateException e) {
				System.out.println("Old Proc: Eeeeh... Getting that return value didn't work, the Process is executing at the moment.");
			}
			System.out.println("Old Proc: Let's see, what the Process is writing:");
		} else {
			JOptionPane.showMessageDialog(null, "Should be new Proc: I'm the new proces... Did it work?");
			JOptionPane.showMessageDialog(null, "Should be new Proc: Have I got that path? " + LibPathUtil.hasLibrary("test"));
		}
	}

}
