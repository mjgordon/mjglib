package terminal;

import java.util.ArrayList;

public class Tab {
	
	private int x = 0;
	private int width;
	private String name = " ";
	
	public boolean selected = false;
	
	public static ArrayList<Tab> tabs;
	
	public static int selectedId = 0;
	
	public Tab(String name) {
		this.name = name;
		width = name.length() + 2;
	}
	
	private void draw() {
		Terminal.writeRect(x, 0, width, 3);
		Terminal.writeString(name, x + 1, 1);
		if (selected) {
			Terminal.writeCharacter(Terminal.LR, x, 2);
			Terminal.writeCharacter(Terminal.LL, x + width - 1, 2);
			for (int i = 0; i < width - 2; i++) Terminal.writeCharacter(' ' , x + i + 1, 2);
		}
		else {
			Terminal.writeCharacter(Terminal.HOR, x, 2);
			Terminal.writeCharacter(Terminal.HOR, x + width - 1, 2);
		}
	}
	
	public static void initialize() {
		tabs = new ArrayList<Tab>();
	}
	
	public static void reposition() {
		int x = 0;
		for (Tab t : tabs) {
			t.x = x;
			x += t.width;
		}
	}
	
	public static void drawTabs() {
		Terminal.writeHorizontalLine(2);
		for (Tab t : tabs) t.draw();
	}
	
	public static void add(Tab in) {
		tabs.add(in);
		reposition();
	}
	
	public static void advance() {
		select(selectedId + 1);
	}
	
	public static void select(int id) {
		tabs.get(selectedId).selected = false;
		selectedId = id;
		selectedId %=  tabs.size();
		tabs.get(selectedId).selected = true;
	}
}
