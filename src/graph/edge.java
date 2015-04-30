package graph;


public class edge {
	private node from;
	private node to;
	private double weight;

	public edge(node from, node to) {
		this.from = from;
		this.to = to;
		this.weight = 1;
		from.addEdge(this);
		to.addEdge(this);
		from.modifySumofEdge(1);
		to.modifySumofEdge(1);

	}

	public edge(node from, node to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
		from.addEdge(this);
		to.addEdge(this);
		from.modifySumofEdge(weight);
		to.modifySumofEdge(weight);

	}

	public edge(node from) {
		this.from = from;
		this.to = from;
		this.weight = 1;
		from.addEdge(this);
		from.modifySumofEdge(1);

	}

	public edge(node from, double weight) {
		this.from = from;
		this.to = from;
		this.weight = weight;
		from.addEdge(this);
		from.modifySumofEdge(weight);

	}

	public boolean isLoop() {
		return (from.getIndex() == to.getIndex());
	}

	public node getFrom() {
		return from;
	}

	public node getTo() {
		return to;
	}

	public void setFrom(node from) {
		this.from = from;
	}

	public void setTo(node to) {
		this.to = to;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		from.modifySumofEdge(-this.weight);
		to.modifySumofEdge(-this.weight);
		this.weight = weight;
		from.modifySumofEdge(this.weight);
		to.modifySumofEdge(this.weight);
	}

	public void addWeight(double weight) {
		this.weight += weight;
		from.modifySumofEdge(weight);
		to.modifySumofEdge(weight);
	}

	public node incident(node v) {
		return from.getIndex() != v.getIndex() ? from : to;
	}

	public void remove() {
		from.modifySumofEdge(-this.weight);
		to.modifySumofEdge(-this.weight);
		from.getAdj_list().remove(this);
		to.getAdj_list().remove(this);
	}

}
