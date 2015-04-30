package matrix;

public interface matrix {
	public matrix multiple(matrix a);
	public void inflate(double factor);
	public void prune();
	public double eval();
	public boolean converge();
}
