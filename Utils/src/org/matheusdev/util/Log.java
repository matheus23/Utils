package org.matheusdev.util;

import java.io.PrintStream;

public class Log {

	public static boolean PRINT_TIME = true;  // The time like this: [01:33 ...] example { ... } where the time is 1 min and 33 sec
	public static boolean PRINT_TRACE = true; // The trace line: [..:.. ...] ... { org.my.package.MyClass.method(MyClass.java:130) }
	public static boolean PRINT_CLASS = true; // The class: [TI:ME ThisIsTheClass] ... { ... }

	private static Log instance;
	private PrintStream err;
	private PrintStream out;
	private long startupTime;

	private Log() {
		err = System.err;
		out = System.out;
		startupTime = System.currentTimeMillis();
	}

	private String pref() {
		if (!PRINT_TIME && !PRINT_TRACE && ! PRINT_CLASS) return "";
		String filename = (Thread.currentThread().getStackTrace()[4].getFileName());
		String classname = filename.substring(0, filename.length()-5);

		return
				"[" +
				((PRINT_TIME) ? (getTimeString()) : ("")) +
				((PRINT_CLASS) ? (" " + classname) : ("")) +
				"] ";
	}

	private String suff() {
		return ((PRINT_TRACE) ? (
				" { " + getTraceString(Thread.currentThread().getStackTrace()[4]) + " }"
				) : (""));
	}

	private void errInst(Object s) {
		err.printf("%-25s%-75s%s\n", pref(), s.toString(), suff());
	}

	private void outInst(Object s) {
		out.printf("%-25s%-75s%s\n", pref(), s.toString(), suff());
	}

	private void errfInst(String s, Object ... objs) {
		String str = String.format(s, objs);
		err.printf("%-25s%-75s%s\n", pref(), str, suff());
	}

	private void outfInst(String s, Object ... objs) {
		String str = String.format(s, objs);
		out.printf("%-25s%-75s%s\n", pref(), str, suff());
	}

	private void setOutInst(PrintStream out) {
		this.out = out;
	}

	private void setErrInst(PrintStream err) {
		this.err = err;
	}

	private String getTimeString() {
		long delta = System.currentTimeMillis() - startupTime;
		long deltaSec = delta/1000L;
		return String.format("%02d:%02d", deltaSec/60L, deltaSec%60L);
	}

	private static String getTraceString(StackTraceElement elem) {
		return String.format("%s.%s(%s:%s)",
				elem.getClassName(), elem.getMethodName(), elem.getFileName(), elem.getLineNumber());
	}

	private static Log instance() {
		if (instance == null) {
			return instance = new Log();
		}
		return instance;
	}

	public static void err(Object s) {
		instance().errInst(s);
	}

	public static void out(Object s) {
		instance().outInst(s);
	}

	public static void errf(String s, Object ... objs) {
		instance().errfInst(s, objs);
	}

	public static void out(String s, Object ... objs) {
		instance().outfInst(s, objs);
	}

	public static void setOut(PrintStream out) {
		instance().setOutInst(out);
	}

	public static void setErr(PrintStream err) {
		instance().setErrInst(err);
	}

}
