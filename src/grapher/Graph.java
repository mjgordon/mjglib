package grapher;

import grapher.util.*;

import java.util.ArrayList;


import java.util.Collections;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.data.XML;

public class Graph extends GrapherBridge {
	
	protected PGraphics g;
	
	protected Domain xAxisDomain;
	protected Domain yAxisDomain;
	
	protected int step;
	
	protected int heightCutoff = 12;
	
	protected int backgroundColor = 0;
	
	protected ArrayList<Plot> plots;
	
	protected int leftGutter = 36;
	protected int rightGutter = 36;
	protected int lowerGutter = 24;
	
	protected int width = 512;
	protected int height= 512;
	
	protected ArrayList<String> xTags;
	protected ArrayList<String> yTags;
	
	public Graph(XML graphXML) {
		
		xAxisDomain = new Domain();
		yAxisDomain = new Domain();
		
		plots = new ArrayList<Plot>();

		for (XML xml : graphXML.getChildren()) {
			if (xml.getName().equals("settings")) {
				backgroundColor = PApplet.unhex(xml.getString("bgcolor"));
			}
			else if (xml.getName().equals("plot")) {
				plots.add(new Plot(xml));
			}
		}
		
		g = p.createGraphics(leftGutter + width + rightGutter, height + lowerGutter);
		
		xTags = new ArrayList<String>();
		yTags = new ArrayList<String>();
	}
	
	public void draw(ArrayList<? extends GraphDatum> data) {
		// First setup the data and bounds
		
		Collections.sort(data);

		xAxisDomain.reset();
		yAxisDomain.reset();

		for (Plot p : plots) {
			p.calculate(data);
			xAxisDomain.testExpansion(p.getXAxisDomain());
			yAxisDomain.testExpansion(p.getYAxisDomain());
		}
		
		int digits = ((int) yAxisDomain.getRange() + "").length();
		step = (int) Math.pow(10, digits - 2);
		yAxisDomain.setMinimum(Math.floor(yAxisDomain.getMinimum() / step) * step);
		yAxisDomain.setMaximum(Math.ceil (yAxisDomain.getMaximum() / step) * step);
		
		// Actually get to drawing
		g.beginDraw();
		g.background(backgroundColor);
		g.stroke(255, 255, 255, 80);
		
		int xTicks = xTags.size();
		int yTicks = yTags.size();
		for (int i = 0; i < xTicks; i++) {
			float xPos = (float)Domain.redomain(i, 0, xTicks,0,width);
			g.line(leftGutter + xPos, 0, leftGutter + xPos, height);
			
			String xText = xTags.get(i);
			g.text(xText, leftGutter + xPos - 24, height + 14);
		}

		for (int i = 0; i < yTicks; i++) {
			float yPos = (float)Domain.redomain(i, 0, yTicks,0,height);
			g.line(leftGutter, yPos, leftGutter + width, yPos);
			
			String yText = yTags.get(i);
			g.text(yText, 0, yPos);
		}
		
		// Draw data
		g.pushMatrix();
		g.translate(leftGutter, -lowerGutter);
		for (Plot p : plots) {
			p.draw(g, data, xAxisDomain, yAxisDomain);
		}
		g.popMatrix();

		g.text(xAxisDomain.toString(),0,height + 12);
		g.text(yAxisDomain.toString(),0,height + 24);
		
		g.endDraw();
	}
	
	
	
	public void drawToScreen(int x, int y) {
		p.image(g,x,y);
		p.stroke(255);
		p.noFill();
		p.rect(x + leftGutter,y - 1,width + 1,height + 1);
	}


}
