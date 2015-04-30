package graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class node {
	private int index;
	private HashMap<Integer, edge> adj_list;
	private node matched_node = null;
	private int weight;
	private int matrix_index = -1;
	private int sumofEdge = 0;
	ArrayList<node> superNode;

	public node(int index) {
		this.index = index;
		adj_list = new HashMap<>();
		this.weight = 0;
	}

	public node(int index, int weight) {
		this.index = index;
		adj_list = new HashMap<>();
		this.weight = weight;
	}

	public node getMatched_node() {
		return matched_node;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setMatched_node(node matched_node) {
		this.matched_node = matched_node;
	}

	public int getSumofEdge() {
		return sumofEdge;
	}

	public void modifySumofEdge(double weight) {
		sumofEdge += weight;
	}

	public int getMatrix_index() {
		return matrix_index;
	}

	public void setMatrix_index(int matrix_index) {
		this.matrix_index = matrix_index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void insertEdge(node to, double weight) {
		if (to == null) {
			if (this.getAdj_list().containsKey(this.getIndex())) {
				return;
			} else {
				new edge(this, weight);
			}

		} else {
			if (this.getAdj_list().containsKey(to.getIndex())) {
				return;
			} else {
				new edge(this, to, weight);
			}
		}
	}

	public void addEdge(edge e) {
		this.getAdj_list().put(e.incident(this).getIndex(), e);
	}

	public HashMap<Integer, edge> getAdj_list() {
		return adj_list;
	}

	public void matchingNode() {

		edge e = this.getAdj_list().values().stream().max(compFC).get();
		node to = e.incident(this);

		if (this.superNode == null && to.superNode == null) {
			superNode = new ArrayList<>();
			superNode.add(this);
			superNode.add(to);
			to.superNode = this.superNode;
		} 
		
		else if (this.superNode == null && to.superNode != null) {
			to.superNode.add(this);
			this.superNode = to.superNode;
		} 
		
		else if (this.superNode != null && to.superNode == null) {
			this.superNode.add(to);
			to.superNode = this.superNode;
		} 
		
		else {
			to.superNode.addAll(this.superNode);
		}

		if (e.isLoop() && this.getAdj_list().size() != 1)
			e.remove();
	}

	public boolean isMatchable() {
		return (this.matched_node == null);
	}

	public node getNeighbor(edge e) {
		return e.getFrom().getIndex() == index ? e.getTo() : e.getFrom();
	}

	public int degree() {
		return adj_list.size();
	}

	public void addWeight(node v) {
		this.weight += v.getWeight();
	}

	final Comparator<edge> compFC = (e1, e2) -> {
		if (e1.getWeight() > e2.getWeight())
			return 1;
		else if (e1.getWeight() == e2.getWeight()) {
			node v1 = e1.incident(this);
			node v2 = e2.incident(this);

			if (v1.getWeight() > v2.getWeight()) {
				return 0;
			} else if (v1.getWeight() < v2.getWeight()) {
				return 1;
			} else {
				return (int) (Math.random() * 2);
			}
		} else {
			return 0;
		}
	};
}
