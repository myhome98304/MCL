package mcl;

import graph.graph;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;



public class mcl {
	public static void main(String[] args){
		Path p1 = Paths.get("out.slashdot-threads");
		ArrayList<graph> graphSet = new ArrayList<>();
		graph init = new graph(p1);
		graphSet.add(init);
		int i=0;
		while(graphSet.get(i).size()>10000){
			graphSet.add(graphSet.get(i).Coarse());
			System.out.println(graphSet.get(i+1).size());
			i=i+1;	
		}	  
	}
}
