/**
 * 
 */
package org.matheusdev.util.javatemplates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Use this Templater to create Java file with templates.
 * 
 * You can write "&lt;template&gt;" anywhere into the .java file to
 * be templated, and the templater will generate templates for all 
 * types: byte, char, short, int, long, boolean.
 * The generated file will have generated Suffixes:
 * byte (b), char (c), short (s), int (i), long (l), boolean (bl).
 * 
 * Generated Files can include bugs, so don't use the Templater without
 * checking the files later by yourself.
 * 
 * @author matheusdev
 *
 */
public class Templater {
	
	static abstract class Template {
		public abstract String getTemplateName();
		public abstract String getTemplateSuffix();
	}
	
	static class GeneratedJavaFile {
		public String content;
		public String name;
		
		public void writeToFile(String directory) throws IOException {
			PrintWriter write = null;
			try {
				write = new PrintWriter(new File(directory + name));
				write.append(content);
			} finally {
				if (write != null) {
					write.close();
				}
			}
		}
	}
	
	public static final Template intTemplate = new Template() {
		public String getTemplateName() { return "int"; }
		public String getTemplateSuffix() { return "i"; }
	};
	
	public static final Template longTemplate = new Template() {
		public String getTemplateName() { return "long"; }
		public String getTemplateSuffix() { return "l"; }
	};
	
	public static final Template byteTemplate = new Template() {
		public String getTemplateName() { return "byte"; }
		public String getTemplateSuffix() { return "b"; }
	};
	
	public static final Template shortTemplate = new Template() {
		public String getTemplateName() { return "short"; }
		public String getTemplateSuffix() { return "s"; }
	};
	
	public static final Template charTemplate = new Template() {
		public String getTemplateName() { return "char"; }
		public String getTemplateSuffix() { return "c"; }
	};
	
	public static final Template booleanTemplate = new Template() {
		public String getTemplateName() { return "boolean"; }
		public String getTemplateSuffix() { return "bl"; }
	};
	
	public static final Template floatTemplate = new Template() {
		public String getTemplateName() { return "float"; }
		public String getTemplateSuffix() { return "f"; }
	};
	
	public static final Template doubleTemplate = new Template() {
		public String getTemplateName() { return "double"; }
		public String getTemplateSuffix() { return "d"; }
	};
	
	private List<GeneratedJavaFile> javafiles = new ArrayList<GeneratedJavaFile>();
	
	public Templater() throws IOException {
	}
	
	public void doTemplate(File file, String classname) throws IOException {
		javafiles.add(doTemplate(file, intTemplate, classname));
		javafiles.add(doTemplate(file, longTemplate, classname));
		javafiles.add(doTemplate(file, byteTemplate, classname));
		javafiles.add(doTemplate(file, charTemplate, classname));
		javafiles.add(doTemplate(file, shortTemplate, classname));
		javafiles.add(doTemplate(file, booleanTemplate, classname));
		javafiles.add(doTemplate(file, floatTemplate, classname));
		javafiles.add(doTemplate(file, doubleTemplate, classname));
	}
	
	public GeneratedJavaFile doTemplate(File file, Template template, String classname) throws IOException {
		if (template == null) throw new NullPointerException("template == null");
		return genFileFor(template, new FileInputStream(file), classname);
	}
	
	public List<GeneratedJavaFile> getJavaFiles() {
		return javafiles;
	}
	
	public GeneratedJavaFile genFileFor(Template template, InputStream stream, String className) throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuffer genFile = new StringBuffer();
		String outputName = className + template.getTemplateSuffix();
		
		while ((line = read.readLine()) != null) {
			line = line.replace(className, outputName);
			line = line.replace("<template>", template.getTemplateName());
			genFile.append(line + "\n");
		}
		
		GeneratedJavaFile gjf = new GeneratedJavaFile();
		gjf.name = outputName + ".java";
		gjf.content = genFile.toString();
		
		return gjf;
	}
	
	public static void main(String[] args) throws IOException {
		Templater templater = new Templater();
		templater.doTemplate(
				new File("src/org/matheusdev/util/matrix/matrix3/MatrixN3.java"), 
				"MatrixN3");
		List<GeneratedJavaFile> files = templater.getJavaFiles();
		for (int i = 0; i < files.size(); i++) {
			System.out.println("File: " + files.get(i).name);
			System.out.println("\n" + files.get(i).content);
			files.get(i).writeToFile("src/org/matheusdev/util/matrix/matrix3/");
		}
	}
	
}
