package grapher;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.data.XML;

public class Plot {
	private String type = "balance";
	private int color = 0xFFFFFFFF;

	private Domain xAxisDomain;
	private Domain yAxisDomain;
	
	protected HashMap<String,String> tags;

	public Plot(XML xml) {
		type = xml.getString("type");
		color = PApplet.unhex(xml.getString("color"));
		
		tags = new HashMap<String,String>();
		
		xAxisDomain = new Domain();
		yAxisDomain = new Domain();
	}

	/**
	 * Calculate the domain of this plot based on the data used
	 * @param data
	 */
	public void calculate(ArrayList<? extends GraphDatum> data) {

		xAxisDomain.reset();
		yAxisDomain.reset();

		// Determine bounds of graph
		int size = data.size();
		float balance = 0;
		for (int i = 0; i < size; i++) {
			GraphDatum d = data.get(i);
			if (type.equals("event")) {
				yAxisDomain.testExpansion(d.getYAxisValue());
			} else if (type.equals("balance")) {
				balance += d.getYAxisValue();
				yAxisDomain.testExpansion(balance);
			}

			xAxisDomain.testExpansion(d.getXAxisValue());
		}
	}

	/**
	 * Draw the plot with a particular set of domain values.
	 * Note these might not be the native domains of the plot
	 * @param g
	 * @param data
	 * @param xAxisDomain
	 * @param yAxisDomain
	 */
	public void draw(PGraphics g, ArrayList<? extends GraphDatum> data,
			Domain xAxisDomain, Domain yAxisDomain) {

		g.stroke(color);
		g.noFill();

		ArrayList<PVector> points = new ArrayList<PVector>();

		double balance = 0;
		
		Domain widthDomain = new Domain(0,512);
		Domain heightDomain = new Domain(0,512);
		
		for (GraphDatum d : data) {
			//TODO: Properly use tags here	
			double x = Domain.redomain(d.getXAxisValue(), xAxisDomain,widthDomain);
			double y = 0;

			if (type.equals("balance")) {
				balance += d.getYAxisValue();
				y = Domain.redomain(balance, yAxisDomain, heightDomain);
			} else if (type.equals("event")) {
				y = Domain.redomain(d.getYAxisValue(), yAxisDomain,heightDomain);
			}
			points.add(new PVector((float) x, (float) y));

		}

		g.beginShape();
		for (PVector v : points) {
			g.vertex(v.x, g.height - v.y);
		}
		g.endShape();
	}
	
	public Domain getXAxisDomain() {
		return(xAxisDomain);
	}
	
	public Domain getYAxisDomain() {
		return(yAxisDomain);
	}

}
