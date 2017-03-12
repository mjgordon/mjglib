package terminal;

public class ColorString {
	public String text;
	public int[] colorsFG;
	public int[] colorsBG;
	
	public boolean bg = false;

	public ColorString(String text) {
		this.text = text;
		colorsFG = new int[text.length()];
		colorsBG = new int[text.length()];
		for (int i = 0; i < colorsFG.length; i++) {
			colorsFG[i] = 0xFFFFFF;
			colorsBG[i] = 0x000000;
		}
	}

	public ColorString(String text, int colorFG) {
		this.text = text;
		colorsFG = new int[text.length()];
		colorsBG = new int[text.length()];
		for (int i = 0; i < colorsFG.length; i++) {
			colorsFG[i] = colorFG;
			colorsBG[i] = 0x000000;
		}
	}
	
	

	public ColorString(String text, int[] colors) {
		this.text = text;
		this.colorsFG = colors;
	}
	
	public void setColorBG(int color) {
		for (int i = 0; i < colorsBG.length; i++) {
			colorsBG[i] = color;
		}
		bg = true;
	}

	public void setColorFG(int id, int color) {
		colorsFG[id] = color;
	}
	
	public void setColorBG(int id, int color) {
		colorsBG[id] = color;
		bg = true;
	}

	public void setColorFG(int id, int color, int width) {
		for (int i = 0; i < width; i++) {
			colorsFG[id + i] = color;
		}
	}
	
	public void setColorBG(int id, int color, int width) {
		for (int i = 0; i < width; i++) {
			colorsBG[id + i] = color;
		}
		bg = true;
	}

	public ColorString append(ColorString in) {
		text += in.text;
		int[] newFG = new int[colorsFG.length + in.colorsFG.length];
		for (int i = 0; i < colorsFG.length; i++) newFG[i] = colorsFG[i];
		for (int i = 0; i < in.colorsFG.length; i++) newFG[i + colorsFG.length] = in.colorsFG[i];
		
		int[] newBG = new int[colorsBG.length + in.colorsBG.length];
		for (int i = 0; i < colorsBG.length; i++) newBG[i] = colorsBG[i];
		for (int i = 0; i < in.colorsBG.length; i++) newBG[i + colorsBG.length] = in.colorsBG[i];
		
		colorsFG = newFG;
		colorsBG = newBG;
		if (in.bg) {
			bg = true;
		}
		return(this);
	}

	public int length() {
		return(text.length());
	}

}