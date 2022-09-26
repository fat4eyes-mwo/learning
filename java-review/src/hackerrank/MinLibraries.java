package hackerrank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MinLibraries {
	public static void main(String[] args) throws IOException {
		String filename = "MinLibraries.Test07";
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(MinLibraries.class.getResourceAsStream(filename + ".in")));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename + ".out"));

		int q = Integer.parseInt(bufferedReader.readLine().trim());

		IntStream.range(0, q).forEach(qItr -> {
			try {
				String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

				int n = Integer.parseInt(firstMultipleInput[0]);

				int m = Integer.parseInt(firstMultipleInput[1]);

				int c_lib = Integer.parseInt(firstMultipleInput[2]);

				int c_road = Integer.parseInt(firstMultipleInput[3]);

				List<List<Integer>> cities = new ArrayList<>();

				IntStream.range(0, m).forEach(i -> {
					try {
						cities.add(Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
								.map(Integer::parseInt).collect(toList()));
					} catch (IOException ex) {
						throw new RuntimeException(ex);
					}
				});

				long result = Result.roadsAndLibraries(n, c_lib, c_road, cities);

				bufferedWriter.write(String.valueOf(result));
				bufferedWriter.newLine();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});

		bufferedReader.close();
		bufferedWriter.close();
	}

	static class Result {

		/*
		 * Complete the 'roadsAndLibraries' function below.
		 *
		 * The function is expected to return a LONG_INTEGER. The function accepts
		 * following parameters: 1. INTEGER n 2. INTEGER c_lib 3. INTEGER c_road 4.
		 * 2D_INTEGER_ARRAY cities
		 */

		public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
			System.out.println("n: " + n + " c_lib:" + c_lib + " c_road:" + c_road + " csize: " + cities.size());
			if (c_lib < c_road) {
				long ret = (long) n * (long) c_lib;
				System.out.println("Non-MSF return: " + ret);
				return ret;
			}
			long[] network = findMSF(cities, n);
			long ret = (long) c_lib * network[0] + (long) c_road * network[1];
			System.out.println("MSF return: " + ret);
			return ret;
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

		// returns [num trees, num edges]
		private static long[] findMSF(List<List<Integer>> cities, int n) {
			long trees = 0;
			long edges = 0;
			HashMap<Integer, Node> graph = new LinkedHashMap<>();

			// initialize graph with no roads
			for (int i = 1; i <= n; i++) {
				if (graph.get(i) == null) {
					graph.put(i, new Node(i));
				}
			}
			// build graph from edges
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

			//prims algo
			Set<Integer> visited = new HashSet<>();
			Set<Integer> unvisited = new HashSet<>(graph.keySet());
			Set<Integer> visitedToRemove = new HashSet<>();
			while (unvisited.size() > 0) {
				boolean foundEdge = false;
				for (Integer nodeId : visited) {
					Node currNode = graph.get(nodeId);
					if (currNode.connections.size() == 0) {
						visitedToRemove.add(nodeId);
						continue;
					}
					Integer nextNodeId = null;
					Set<Integer> connectionsToRemove = new HashSet<>();
					for (Integer nextId : currNode.connections) {
						if (!visited.contains(nextId)) {
							nextNodeId = nextId;
							break;
						} else {
							connectionsToRemove.add(nextId);
						}
					}
					currNode.connections.removeAll(connectionsToRemove);
					if (currNode.connections.size() == 0) {
						visitedToRemove.add(currNode.id);
					}
					if (nextNodeId == null) {
						continue;
					}
					Node nextNode = graph.get(nextNodeId);
					currNode.removeConnection(nextNodeId);
					nextNode.removeConnection(nodeId);
					visited.add(nextNodeId);
					unvisited.remove(nextNodeId);
					edges++;
//					System.out.println("Edge: " + nodeId + "-" + nextNodeId);
					foundEdge = true;
					break;
				}
				for (Integer toRemove : visitedToRemove) {
					visited.remove(toRemove);
				}
				visitedToRemove.clear();

				if (!foundEdge) {
					visited.clear();
					Integer nextStartToRemove = null;
					for (Integer nextStart : unvisited) {
						visited.add(nextStart);
						nextStartToRemove = nextStart;
						trees++;
						break;
					}
					unvisited.remove(nextStartToRemove);
				}
			}
			System.out.println("trees: " + trees + " edges: " + edges);
			return new long[] { trees, edges };
		}

	}
}
