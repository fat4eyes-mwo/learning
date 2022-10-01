package hackerrank;

import java.util.Map;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


public class SuperMaxCostQueries {

	public static void main(String[] args) throws IOException {
		final String fileName = "SuperMaxCostQueries06";
        BufferedReader bufferedReader = new BufferedReader(
        		new InputStreamReader(
        				SuperMaxCostQueries.class.getResourceAsStream(fileName + ".in")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".out"));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int q = Integer.parseInt(firstMultipleInput[1]);

        List<List<Long>> tree = new ArrayList<>();

        IntStream.range(0, n - 1).forEach(i -> {
            try {
                tree.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Long::parseLong)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, q).forEach(i -> {
            try {
                queries.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.solve(tree, queries);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
	
	static class Result {

	    /*
	     * Complete the 'solve' function below.
	     *
	     * The function is expected to return an INTEGER_ARRAY.
	     * The function accepts following parameters:
	     *  1. 2D_INTEGER_ARRAY tree
	     *  2. 2D_INTEGER_ARRAY queries
	     */

	    static class Node {
	        int id;
	        Map<Integer, Long> connections;
	        
	        public Node(int id) {
	            this.id = id;
	            this.connections = new LinkedHashMap<>();
	        }
	        
	        @Override
	        public String toString() {
	            StringBuilder ret = new StringBuilder();
	            ret.append("(" + id + ":" + connections.toString() + ")");
	            return ret.toString();
	        }
	    }
	    
	    static class Graph {
	        private Map<Integer, Node> nodeMap = new LinkedHashMap<>();
	        
	        void addEdge(int from, int to, long weight) {
	            Node fromNode = nodeMap.get(from);
	            if (fromNode == null) {
	                fromNode = new Node(from);
	                nodeMap.put(from, fromNode);
	            }
	            Node toNode = nodeMap.get(to);
	            if (toNode == null) {
	                toNode = new Node(to);
	                nodeMap.put(to, toNode);
	            }
	            fromNode.connections.put(to, weight);
	            toNode.connections.put(from, weight);
	        }
	        
	        Node getNode(int id) {
	            return nodeMap.get(id);
	        }
	        
	        @Override
	        public String toString() {
	            StringBuffer ret = new StringBuffer();
	            
	            for (Node node : nodeMap.values()) {
	                ret.append(node.toString() + " ");
	            }
	            
	            return ret.toString();
	        }
	    }
	    
	    

	    public static List<Integer> solve(List<List<Long>> tree, List<List<Integer>> queries) {
	        int n = tree.size() + 1;
	        //is too big for max inputs (n = 100_000)
	        long[][] memo = new long[n+1][n+1]; //memo[i][j] = max edge for path from i to j
	        List<Integer> ret = new ArrayList<>();
	        Graph g = new Graph();
	        for (List<Long> edge : tree) {
	            long from = edge.get(0);
	            long to = edge.get(1);
	            long weight = edge.get(2);
	            g.addEdge((int)from, (int)to, weight);
	        }
//	        System.out.println(g);
	        for (int i = 1; i <=n; i++) {
	            computeMaxEdges(g, i, memo);    
	        }
//	        for (int i = 1; i <=n; i++) {
//	            System.out.println(Arrays.toString(memo[i]));
//	        }
	        for (List<Integer> query : queries) {
	            long min = query.get(0);
	            long max = query.get(1);
	            int pathCount = 0;
	            for (int i = 1; i <=n; i++) {
	                for (int j = i ; j <=n ; j++) {
	                    if (memo[i][j] >= min && memo[i][j] <= max) {
	                        pathCount++;
	                    }
	                }
	            }
	            ret.add(pathCount);
	        }
	        
	        System.out.println(ret);
	                
	        return ret;
	    }
	    
	    //compute max edges for all paths starting from node from.
	    private static void computeMaxEdges(Graph g, int from, long memo[][]) {
            LinkedHashSet<Node> visited = new LinkedHashSet<>();
            LinkedHashSet<Node> next = new LinkedHashSet<>();
            Node fromNode = g.getNode(from);
            next.add(fromNode);
            while (next.size() > 0) {
                Node currNode = next.iterator().next();
                next.remove(currNode);
                visited.add(currNode);
                for (long nextId : currNode.connections.keySet()) {
                    Node nextNode = g.getNode((int)nextId);
                    if (visited.contains(nextNode)) {
                        continue;
                    }
                    next.add(nextNode);
                    long weight = currNode.connections.get((int)nextId);
                    //immediate neighbors
                    memo[currNode.id][nextNode.id] = weight;
                    memo[nextNode.id][currNode.id] = weight;
                    //check if the weight of the current edge is higher than the currently computed max edge from the source to the current node
                    long maxWeight = Math.max(memo[from][currNode.id], weight);
                    memo[from][nextNode.id] = maxWeight;
                    memo[nextNode.id][from] = maxWeight;
                }
            }
            
            return;
        }

	}
	
}
