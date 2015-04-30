package graph;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class graph {
	private HashMap<Integer, node> map;
	HashMap<Integer, Integer> NodeMap1;

	HashMap<Integer, Integer> NodeMap2;

	private int[] match;
	private int[] coarse;
	private int size;
	private boolean initial = false;

	public graph(Path file) {
		map = new HashMap<>();

		this.initial = true;
		StringTokenizer st;
		node a, b;
		int index1, index2;
		int max = 0;
		try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
			for (String line : (Iterable<String>) lines::iterator) {
				if (line.startsWith("#") || line.startsWith("%"))
					continue;
				st = new StringTokenizer(line, " \t,");
				index1 = Integer.parseInt(st.nextToken());
				index2 = Integer.parseInt(st.nextToken());
				if (map.containsKey(index1)) {
					a = map.get(index1);
				} else {
					a = new node(index1);
					a.setWeight(1);
					map.put(index1, a);

				}
				if (map.containsKey(index2)) {
					b = map.get(index2);
				} else {
					b = new node(index2);
					b.setWeight(1);
					map.put(index2, b);

				}
				if (index1 == index2)
					new edge(a, a);
				else
					new edge(a, b);

				max = max > (index1 > index2 ? index1 : index2) ? max
						: (index1 > index2 ? index1 : index2);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		size = max + 1;

	}

	public graph() {
		this.map = new HashMap<>();
		this.initial = false;
	}


	public HashMap<Integer, node> getMap() {
		return map;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int size() {
		return size;
	}

	public void buildMatching() {
		match = new int[this.size()];
		for (int i = 0; i < size; i++)
			match[i] = -2;

		this.getMap().values().forEach(v -> {
			v.matchingNode();
			match[v.getIndex()]=-1;
		});
		
		for (int i = 0; i < size; i++){
			if(match[i]==-1){
				for(node v : this.getMap().get(i).superNode){
					match[v.getIndex()]=i;
				};
			}
		}
	}

	public graph Coarse() {
		graph g = new graph();
		coarse = new int[this.size()];

		for (int i = 0; i < coarse.length; i++)
			coarse[i] = -1;

		buildMatching();

		NodeMap1 = new HashMap<>();
		NodeMap2 = new HashMap<>();

		int num = 0;

		for (int i = 0; i < this.size(); i++) {
			if (match[i] != -2) {
				if (coarse[match[i]] == -1) {
					coarse[match[i]] = num;
					coarse[i] = num;
					g.getMap().put(num,
							new node(num, this.getMap().get(i).getWeight()));
					num++;
				} else {
					coarse[i] = coarse[match[i]];
					g.getMap().get(coarse[match[i]])
							.addWeight(this.getMap().get(i));
				}
			}
		}

		node v, proj_v;
		for (int i = 0; i < this.size(); i++) {
			if (match[i] != -2) {
				
				v = this.getMap().get(i);
				proj_v = g.getMap().get(coarse[i]);
				
				for (edge e : v.getAdj_list().values()) {
					if(e.isLoop()){
						proj_v.insertEdge(null, e.getWeight());
					}
					
					else if(coarse[e.getFrom().getIndex()]!=coarse[e.getTo().getIndex()]){
						proj_v.insertEdge(g.getMap().get(coarse[match[e.incident(v).getIndex()]]),
								e.getWeight());
					}
						
				}
			}

		}

		g.setSize(g.getMap().size());

		return g;

	}

	public boolean isInitial() {
		return initial;
	}

	public void projectFlow() {

	}
}
