package mcl;

import graph.graph;

import java.nio.file.Path;
import java.nio.file.Paths;

import matrix.graph_matrix;
import matrix.matrix;

public class mlr_mcl {
	public static void main(String[] args){
		
		Path p1 = Paths.get("soc-Epinions1.txt");//args[0]
		
		int coarse = Integer.parseInt(args[1]);
		int iterate = Integer.parseInt(args[2]);
		double inf_factor = Double.parseDouble(args[3]);
		
		graph g = new graph(p1);
		
		for(int i=0;i<coarse;i++){
			g.Coarse();
		}
		
		matrix m;
		matrix n;
		
		for(int i=coarse;i>0;i--){
			m = regularize_matrix(new graph_matrix(g));
			n = regularize_matrix(new graph_matrix(g));	
			for(int j = 0;j<iterate;j++){
				n.multiple(m);
				n.inflate(inf_factor);
				n.prune();
			}
			g.projectFlow();
		}
		
		m = regularize_matrix(new graph_matrix(g));
		n = regularize_matrix(new graph_matrix(g));
		
		while(!n.converge()){
			n.multiple(m);
			n.inflate(inf_factor);
			n.prune();
		}
		
	}
	
	public static matrix regularize_matrix(matrix m){
		return m;
	}
}
