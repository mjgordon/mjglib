package grapher;

public interface GraphDatum extends Comparable<GraphDatum> {
	public abstract double getXAxisValue();
	public abstract double getYAxisValue();
	public abstract int compareTo(GraphDatum in);
}
