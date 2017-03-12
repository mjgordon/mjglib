package terminal;

import java.util.HashMap;

public class Colors {
	private static HashMap<String,Integer> colorMap;
	
	public static void initialize() {
		colorMap = new HashMap<String,Integer>();
	}
	
	public static void setColor(String name, int value) {
		colorMap.put(name, value);
	}
	
	public static Integer getColor(String name) {
		Integer n = colorMap.get(name);
		return(n);
	}
	
	public static int updateColor(String name, int input) {
		Integer n = getColor(name);
		if (n == null) {
			return(input);
		}
		else return(n);
	}
}
