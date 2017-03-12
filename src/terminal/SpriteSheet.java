package terminal;

import grapher.util.GrapherBridge;

import java.util.HashMap;

import processing.core.*;



public class SpriteSheet extends GrapherBridge {
    public static HashMap<String,PImage> sheets;

    public PImage src;
    public String sheetName;
    public int spriteWidth;
    public int spriteHeight;

    public SpriteSheet(String name,int width,int height) {
		this.sheetName = name;
		this.spriteWidth = width;
		this.spriteHeight = height;
		src = loadSheet(name);
    }

    public static PImage loadSheet(String name) {
    	PImage out = sheets.get(name);
    	if (out == null) {
    		out = p.loadImage("data/graphics/" + name);
    		sheets.put(name,out);
    	}
    	return(out);
    }

    public void render(int x, int y, int id) {
    	render(src,x,y,spriteWidth,spriteHeight,id);
    }

    public void render(int x, int y, int id,int foreColor, int backColor) {
        render(src,x,y,spriteWidth,spriteHeight,id,foreColor,backColor);
    }

    public static void render(String name, int x, int y, int id) {
    	PImage src = loadSheet(name);
    	render(src,x,y,32,32,id);
    }

    public static void render(String name, int x, int y, int id,int foreColor,int backColor) {
        PImage src = loadSheet(name);
        render(src,x,y,32,32,id,foreColor,backColor);
    }

    public static void render(PImage src,int x, int y,int spriteWidth,int spriteHeight,int id) {
		int idX = id % (src.width/spriteWidth) * spriteWidth;
		int idY = id / (src.width/spriteWidth) * spriteHeight;
		for (int i = 0; i< spriteWidth * spriteHeight; i++) {
		    int x1 = i % spriteWidth + idX;
		    int y1 = i / spriteWidth + idY;

		    int x2 = i % spriteWidth + x;
		    int y2 = i / spriteWidth + y;

		    int c = src.pixels[y1 * src.width + x1];
		    if (p.alpha(c) == 0) continue;

	  	  p.pixels[y2 * p.width + x2] = c; 
		}
    }

    public static void render(PImage src, int x, int y, int spriteWidth, int spriteHeight, int id, int foreColor, int backColor) {
            int idX = id % (src.width/spriteWidth) * spriteWidth;
            int idY = id / (src.width/spriteWidth) * spriteHeight;
            for (int i = 0; i< spriteWidth * spriteHeight; i++) {
                int x1 = i % spriteWidth + idX;
                int y1 = i / spriteWidth + idY;

                int x2 = i % spriteWidth + x;
                int y2 = i / spriteWidth + y;

                int c = src.pixels[y1 * src.width + x1];

                if (p.alpha(c) == 0) {
                    p.pixels[y2 * p.width + x2] = backColor;
                }
                else {
                    p.pixels[y2 * p.width + x2] = foreColor;
                }
            }
    }

    public static void initialize(String file) {
            sheets = new HashMap<String,PImage>();
            loadSheet(file);
            System.out.println("Loaded " + sheets.size() + " sprite sheets");
    }

    public static void initialize(String[] files) {
    	sheets = new HashMap<String,PImage>();
    	for(String s : files) loadSheet(s);
        System.out.println("Loaded " + sheets.size() + " sprite sheets");
    }
}