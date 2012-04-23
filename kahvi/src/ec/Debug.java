package ec;

public class Debug {
	
	static boolean debug = true;
	
	public static void log(String str) {
		if (debug) System.out.println(str);
	}
	
	public static void log(Object o) {
		if (debug) System.out.println(o.toString());
	}
}
