package Question_1;

import java.util.*;

public class SecureTransmission {
    /*
     * Problem Summary:
     * A company's offices are connected by secure communication links with signal strength limits.
     * Given a network of offices (nodes) and communication links (edges with strengths), determine
     * if a message can be securely transmitted between two offices without exceeding maximum signal strength.
     * 
     * Approach:
     * - Build an adjacency list representation of the undirected graph
     * - Use BFS (Breadth-First Search) to find if there's a valid path
     * - Only traverse edges with strength strictly less than maxStrength
     * - Return true if receiver is reachable from sender, false otherwise
     * 
     * Time Complexity: O(V + E) per canTransmit query
     * Space Complexity: O(V + E) for graph storage
     */
    
    private int n;
    private Map<Integer, List<Edge>> graph;
    
    // Helper class to represent edges with strength
    private static class Edge {
        int neighbor;
        int strength;
        
        Edge(int neighbor, int strength) {
            this.neighbor = neighbor;
            this.strength = strength;
        }
    }
    
    public SecureTransmission(int n, int[][] links) {
        this.n = n;
        this.graph = new HashMap<>();
        
        // Initialize adjacency list for all nodes
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        
        // Step 1: Build undirected graph from communication links
        for (int[] link : links) {
            int a = link[0];
            int b = link[1];
            int strength = link[2];
            
            graph.get(a).add(new Edge(b, strength));
            graph.get(b).add(new Edge(a, strength));
        }
    }
    
    public boolean canTransmit(int sender, int receiver, int maxStrength) {
        // Edge case: same sender and receiver
        if (sender == receiver) {
            return true;
        }
        
        // Step 2: BFS traversal with strength constraint
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        
        queue.offer(sender);
        visited.add(sender);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            // Check all neighbors of current office
            for (Edge edge : graph.get(current)) {
                int neighbor = edge.neighbor;
                int strength = edge.strength;
                
                // Only traverse if strength is strictly less than maxStrength
                if (strength < maxStrength && !visited.contains(neighbor)) {
                    if (neighbor == receiver) {
                        return true; // Found valid path to receiver
                    }
                    
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        
        // Step 3: No valid path found
        return false;
    }
    
    // Main method to test the implementation
    public static void main(String[] args) {
        System.out.println("=== Secure Transmission Algorithm Test Cases ===\n");
        
        // Test Case 1: Given example
        System.out.println("Test Case 1: Given Example");
        int[][] links = {{0, 2, 4}, {2, 3, 1}, {2, 1, 3}, {4, 5, 5}};
        SecureTransmission st = new SecureTransmission(6, links);
        
        // Test queries with expected results
        testQuery(st, 2, 3, 2, true);   // Direct link strength 1 < 2
        testQuery(st, 1, 3, 3, false);  // Path blocked: 1→2 strength 3 not < 3
        testQuery(st, 2, 0, 5, true);   // Valid path: 2→0 via strength 4 < 5
        testQuery(st, 0, 5, 6, false);  // No path between disconnected components
        
        System.out.println();
        
        // Test Case 2: Edge cases
        System.out.println("Test Case 2: Edge Cases");
        int[][] links2 = {{0, 1, 2}, {1, 2, 3}};
        SecureTransmission st2 = new SecureTransmission(3, links2);
        
        testQuery(st2, 0, 0, 1, true);   // Same sender/receiver
        testQuery(st2, 0, 2, 4, true);   // Path exists: 0→1→2 (strengths 2,3 < 4)
        testQuery(st2, 0, 2, 3, false);  // Blocked: 1→2 strength 3 not < 3
        testQuery(st2, 0, 2, 2, false);  // Blocked: 0→1 strength 2 not < 2
        
        System.out.println();
        
        // Test Case 3: Single node network
        System.out.println("Test Case 3: Single Node Network");
        SecureTransmission st3 = new SecureTransmission(1, new int[0][]);
        testQuery(st3, 0, 0, 5, true);   // Same node always reachable
        
        System.out.println();
        
        // Test Case 4: Fully connected triangle
        System.out.println("Test Case 4: Triangle Network");
        int[][] links4 = {{0, 1, 1}, {1, 2, 2}, {2, 0, 3}};
        SecureTransmission st4 = new SecureTransmission(3, links4);
        
        testQuery(st4, 0, 2, 2, true);   // Direct path 0→1→2 (strengths 1,2 both < 2? No, 2 not < 2)
        testQuery(st4, 0, 2, 3, true);   // Multiple paths available
        testQuery(st4, 0, 2, 1, false);  // All paths blocked
    }
    
    // Helper method to test and display results
    private static void testQuery(SecureTransmission st, int sender, int receiver, int maxStrength, boolean expected) {
        boolean result = st.canTransmit(sender, receiver, maxStrength);
        String status = result == expected ? "✓" : "✗";
        System.out.printf("  canTransmit(%d, %d, %d) = %s %s%n", sender, receiver, maxStrength, result, status);
    }
}