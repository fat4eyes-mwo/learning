package hackerrank;

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

public class QueenAttack2 {
	public static void main(String[] args) throws IOException {
		final String fileName = "QueenAttack2_7";
		
        BufferedReader bufferedReader = new BufferedReader(
        		new InputStreamReader(QueenAttack2.class.getResourceAsStream(fileName + ".in")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".out"));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String[] secondMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int r_q = Integer.parseInt(secondMultipleInput[0]);

        int c_q = Integer.parseInt(secondMultipleInput[1]);

        List<List<Integer>> obstacles = new ArrayList<>();

        IntStream.range(0, k).forEach(i -> {
            try {
                obstacles.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.queensAttack(n, k, r_q, c_q, obstacles);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
	
	static class Result {

	    /*
	     * Complete the 'queensAttack' function below.
	     *
	     * The function is expected to return an INTEGER.
	     * The function accepts following parameters:
	     *  1. INTEGER n
	     *  2. INTEGER k
	     *  3. INTEGER r_q
	     *  4. INTEGER c_q
	     *  5. 2D_INTEGER_ARRAY obstacles
	     */

	    //idea: for each attack line, store the closest obstacle to the queen (diagonal distance i think is enough)
	    //then compute the number of reachable squares from the queen to the closest obstacle in the attack line
	    //complexity is k the number of obstacles (the computation for reachable squares is O(1) for each attack line once the closest obstacle is known)
	    //still failing some tests, probably some off by one error somewhere.
	    public static int queensAttack(int n, int k, int r_q, int c_q, List<List<Integer>> obstacles) {
	    // Write your code here
	        int qx = c_q;
	        int qy = r_q;
	        
	        Pair queen = new Pair(qx, qy);
	        System.out.println("Queen: " + queen + " n: " + n);
	        AttackLine[] attackLines = {
	            new AttackLine(queen, new Pair(1, 0)),
	            new AttackLine(queen, new Pair(1, 1)),
	            new AttackLine(queen, new Pair(0, 1)),
	            new AttackLine(queen, new Pair(-1, 1)),
	            new AttackLine(queen, new Pair(-1, 0)),
	            new AttackLine(queen, new Pair(-1, -1)),
	            new AttackLine(queen, new Pair(0, -1)),
	            new AttackLine(queen, new Pair(1, -1)),
	        };
	        
	        for (List<Integer> obstaclePair: obstacles) {
	            int oX = obstaclePair.get(1);
	            int oY = obstaclePair.get(0);
	            Pair obstacle = new Pair(oX, oY);
	            // System.out.println("obtacle: " + obstacle);
	            for (AttackLine attack : attackLines) {
	                if (attack.isOnAttackLine(obstacle) &&
	                    attack.obstacleDistance(obstacle) < attack.closestObstacleDistance()) {
	                    attack.setClosestObstacle(obstacle);
	                }
	            }
	        }
	        int reachable = 0;
	        for (AttackLine attack : attackLines) {
	            reachable+= attack.countReachable(n);
	        }
	        System.out.println(reachable);
	        return reachable;
	    }
	    
	    static class Pair {
	        int x;
	        int y;
	        Pair(int x, int y) {
	            this.x = x;
	            this.y = y;
	        }
	        @Override
	        public String toString() {
	            return "(" + y + ", " + x + ")";
	        }
	    }
	    
	    static class AttackLine {
	        Pair queen;
	        Pair delta; //x, y elem {-1,0,1}
	        Pair closestObstacle;
	        AttackLine(Pair queen, Pair delta) {
	            this.queen = queen;
	            this.delta = delta;
	        }
	        boolean isOnAttackLine(Pair obstacle) {
	            int dx = obstacle.x - queen.x;
	            int dy = obstacle.y - queen.y;
	            if (delta.x == 0 && dx != 0) return false;
	            if (delta.y == 0 && dy != 0) return false;
	            if (Math.signum(dx) != Math.signum(delta.x)) return false;
	            if (Math.signum(dy) != Math.signum(delta.y)) return false;
	            if (delta.x != 0 && delta.y != 0) {
	                return dx / delta.x == dy / delta.y;
	            } else if (delta.x == 0) {
	                return dx == 0 && dy / delta.y > 0;
	            } else if (delta.y == 0) {
	                return dy == 0 && dx / delta.x > 0;
	            }
	            return false;
	        }
	        double obstacleDistance(Pair obstacle) {
	            return distance(obstacle, queen);
	        }
	        double closestObstacleDistance() {
	            if (closestObstacle == null) {
	                return Integer.MAX_VALUE;
	            } else {
	                // return Math.abs(closestObstacle.x - queen.x) + Math.abs(closestObstacle.y - queen.y);
	                return distance(closestObstacle, queen);
	            }
	        }
	        Pair computeEnd(int n) {
	            Pair end = new Pair(queen.x, queen.y);
	            if (delta.x != 0 && delta.y != 0) {
	                int maxDx = delta.x > 0 ? n + 1 - queen.x : queen.x;
	                int maxDy = delta.y > 0 ? n + 1 - queen.y : queen.y;
	                int maxMove = Math.min(maxDx, maxDy);
	                end.x = queen.x + maxMove * delta.x;
	                end.y = queen.y + maxMove * delta.y;
	            } else if (delta.x == 0) {
	                end.y = delta.y > 0 ? n + 1 : 0;
	            } else if (delta.y == 0) {
	                end.x = delta.x > 0 ? n + 1 : 0;
	            }
	             System.out.println("AttackLine: " + this + " computeEnd: " + end);
	            return end;
	        }
	        int countReachable(int n) {
	            Pair end = closestObstacle;
	            if (end == null) {
	                end = computeEnd(n);
	            }
	            int reachable = 0;
	            if (delta.x != 0 && delta.y != 0) {
	                reachable = Math.abs(end.x - queen.x) - 1;
	            } else {
	                if (delta.x != 0) {
	                    reachable = Math.abs(end.x - queen.x) - 1;
	                } else if (delta.y != 0) {
	                    reachable = Math.abs(end.y - queen.y) - 1;
	                }
	            }
	             System.out.println("countReachable: " + reachable + " " + this + " end: " + end);
	            return reachable;
	        }
	        public void setClosestObstacle(Pair closestObstacle) {
	            // System.out.println(this + " new closest: " + closestObstacle);
	            this.closestObstacle = closestObstacle;
	        }
	        @Override
	        public String toString() {
	            return "Queen: " + queen + " delta: " + delta;
	        }
	    }
	    
	    static double distance(Pair a, Pair b) {
	        double dx2 = Math.pow(a.x - b.x, 2);
	        double dy2 = Math.pow(a.y - b.y, 2);
	        return Math.sqrt(dx2 + dy2);
	    }

	}
}
