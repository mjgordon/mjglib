package grapher;

public class Domain {
	private double minimum;
	private double maximum;
	private double range;
	
	/**
	 * Create a new domain with inverted min/max at the limits for testing
	 */
	public Domain() {
		reset();
	}
	
	/**
	 * Create a new domain with defined minimum and maximum
	 * @param minimum
	 * @param maximum
	 */
	public Domain(double minimum, double maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
		this.range = maximum - minimum;
	}
	
	public void reset() {
		this.minimum = Double.MAX_VALUE;
		this.maximum = -Double.MAX_VALUE;
		this.range = maximum - minimum;
	}
	
	/**
	 * Expand the range if the input Domain is larger
	 * @param in
	 */
	public void testExpansion(Domain in) {
		if (in.minimum < minimum) setMinimum(in.minimum);
		if (in.maximum > maximum) setMaximum(in.maximum);
	}
	
	/**
	 * Expand the range if the input lies outside of it
	 * @param in
	 */
	public void testExpansion(double in) {
		if (in < minimum) setMinimum(in);
		if (in > maximum) setMaximum(in);
	}
	
	public static double redomain(double value, Domain oldDomain,Domain newDomain) {
		return (redomain(value, oldDomain.getMinimum(), oldDomain.getMaximum(),
				newDomain.getMinimum(), newDomain.getMaximum()));
	}
	
	public static double redomain(double value, double oldMin, double oldMax, double newMin, double newMax) {
		double temp = (value - oldMin) / (oldMax - oldMin);
		double out = (temp * (newMax - newMin)) + newMin;
		return(out);
	}

	/*
	 * === Getters and Setters ===
	 */
	public double getMinimum() {
		return minimum;
	}

	public void setMinimum(double minimum) {
		this.minimum = minimum;
		this.range = this.maximum - this.minimum;
	}
	
	public double getMaximum() {
		return maximum;
	}

	public void setMaximum(double maximum) {
		this.maximum = maximum;
		this.range = this.maximum - this.minimum;
	}
	
	public double getRange() {
		return range;
	}
	
	@Override
	public String toString() {
		return("Min: " + minimum + " Max: " + maximum + " R: " + range);
	}
	
	
	
	
	
}
