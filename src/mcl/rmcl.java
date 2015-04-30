package mcl;

import graph.graph;

import java.nio.file.Path;
import java.nio.file.Paths;

import matrix.graph_matrix;
import matrix.matrix;

public class rmcl {
	
	public rmcl(Path p){
		
	}
	public static void main(String[] args){
		Path p1 = Paths.get("soc-Epinions1.txt");
		graph init = new graph(p1);
		matrix m = regularize_matrix(new graph_matrix(init));
		matrix n = regularize_matrix(new graph_matrix(init));
		while(!m.converge()){
			n=n.multiple(m);
			n.inflate(2);
			n.prune();
		}
	}
	
	public static matrix regularize_matrix(matrix m){
		return m;
	}
}
