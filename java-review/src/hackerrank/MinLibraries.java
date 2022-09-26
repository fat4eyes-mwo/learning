package hackerrank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MinLibraries {

	
	static class Result {

	    /*
	     * Complete the 'roadsAndLibraries' function below.
	     *
	     * The function is expected to return a LONG_INTEGER.
	     * The function accepts following parameters:
	     *  1. INTEGER n
	     *  2. INTEGER c_lib
	     *  3. INTEGER c_road
	     *  4. 2D_INTEGER_ARRAY cities
	     */

	    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
	    // Write your code here
	        if (c_lib < c_road) {
	            return n * c_lib;
	        }
	        long[] network = findMSF(cities, n);
	        return c_lib * network[0] + c_road * network[1];
	    }
	    
	    static class Node {
	        int id;
	        HashSet<Integer> connections;
	        Node(int id) {
	            this.id = id;
	            connections = new LinkedHashSet<Integer>();
	        }
	        void addConnection(int toId) {
	            connections.add(toId);
	        }
	        void removeConnection(int toId) {
	            connections.remove(toId);
	        }
	        @Override
	        public String toString() {
	            return "id: " + id + " conns: " + connections.toString();
	        }
	    }
	    //returns [num trees, num edges]
	    private static long[] findMSF(List<List<Integer>> cities, int n) {
	        long trees = 0;
	        long edges = 0;
	        HashMap<Integer, Node> graph = new LinkedHashMap<>();

	        //initialize graph with no roads
	        for (int i = 1; i <= n; i++) {
	            if (graph.get(i) == null) {
	                graph.put(i, new Node(i));
	            }
	        }
	        //build graph from edges
	        for (List<Integer> edge : cities) {
	            int nodeId = edge.get(0);
	            int toId = edge.get(1);
	            Node node = graph.get(nodeId);
	            if (node == null) {
	                node = new Node(nodeId);
	                graph.put(nodeId, node);
	            }
	            node.addConnection(toId);
	            
	            Node toNode = graph.get(toId);
	            if (toNode == null) {
	                toNode = new Node(toId);
	                graph.put(toId, toNode);
	            }
	            toNode.addConnection(nodeId);
	        }
	        
	        Set<Integer> visited = new HashSet<>();
	        
	        while (visited.size() < n) {
	            boolean foundEdge = false;
	            for (Integer nodeId : visited) {
	                Node currNode = graph.get(nodeId);
	                if (currNode.connections.size() == 0) {
	                    continue;
	                }
	                Integer nextNodeId = currNode.connections.iterator().next();
	                Node nextNode = graph.get(nextNodeId);
	                currNode.removeConnection(nextNodeId);
	                nextNode.removeConnection(nodeId);
	                visited.add(nextNodeId);
	                edges++;
	                System.out.println("Edge: " + nodeId + "-" + nextNodeId);
	                foundEdge = true;
	                break;
	            }
	            if (!foundEdge) {
	                for (Integer nextStart : graph.keySet()) {
	                    if (!visited.contains(nextStart)) {
	                        visited.add(nextStart);
	                        trees++;
	                        break;
	                    }
	                }
	            }
	        }
	        System.out.println("trees: " + trees + " edges: " + edges);
	        return new long[] {trees, edges};
	    }

	}
}
