package terminal;

import grapher.util.GrapherBridge;


public class CommandLine extends GrapherBridge {
	
	public boolean active = false;
	
	public String text = "";
	
	public void keyPressed(char key) {
		if (key == p.DELETE || key == p.BACKSPACE) {
			if (text.length() > 0) text = text.substring(0, text.length() - 1);
		}
		else {
			text += key;
		}
	}
	
	public void draw(int x, int y) {
		if (active) {
			Terminal.writeString("> ", x, y);
			Terminal.writeString(text,x + 2,y);
		}
		
	}
	
	public void clear() {
		text = "";
	}
	
	public void addText(String text) {
		this.text += text;
	}
}
