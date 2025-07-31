package Question_1;

import java.util.*;

public class TechStartupRevenue {
    
    public static int maximizeCapital(int k, int c, int[] revenues, int[] investments) {
        int n = revenues.length;
        
        // Min-heap to store projects sorted by investment cost (ascending)
        // Each element is an array [investment, revenue]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        
        // Max-heap to store affordable projects sorted by revenue (descending)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        // Add all projects to min-heap
        for (int i = 0; i < n; i++) {
            minHeap.offer(new int[]{investments[i], revenues[i]});
        }
        
        int currentCapital = c;
        
        // Complete at most k projects
        for (int i = 0; i < k; i++) {
            // Move all affordable projects from min-heap to max-heap
            while (!minHeap.isEmpty() && minHeap.peek()[0] <= currentCapital) {
                int[] project = minHeap.poll();
                maxHeap.offer(project[1]); // Add revenue to max-heap
            }
            
            // If no affordable projects, break
            if (maxHeap.isEmpty()) {
                break;
            }
            
            // Pick the most profitable project
            int bestRevenue = maxHeap.poll();
            currentCapital += bestRevenue;
        }
        
        return currentCapital;
    }
    
    public static void main(String[] args) {
        // Test Case 1
        int k1 = 3, c1 = 0;
        int[] revenues1 = {1, 2, 3};
        int[] investments1 = {0, 1, 2};
        
        System.out.println("Test Case 1:");
        System.out.println("k = " + k1 + ", c = " + c1);
        System.out.println("revenues = " + Arrays.toString(revenues1));
        System.out.println("investments = " + Arrays.toString(investments1));
        System.out.println("Maximum Capital: " + maximizeCapital(k1, c1, revenues1, investments1));
        System.out.println("Expected: 7\n");
        
        // Test Case 2
        int k2 = 3, c2 = 1;
        int[] revenues2 = {3, 6, 10};
        int[] investments2 = {1, 3, 5};
        
        System.out.println("Test Case 2:");
        System.out.println("k = " + k2 + ", c = " + c2);
        System.out.println("revenues = " + Arrays.toString(revenues2));
        System.out.println("investments = " + Arrays.toString(investments2));
        System.out.println("Maximum Capital: " + maximizeCapital(k2, c2, revenues2, investments2));
        System.out.println("Expected: 19");
    }
}

// Alternative simpler approach for better understanding
class SimpleApproach {
    
    public static int maximizeCapitalSimple(int k, int c, int[] revenues, int[] investments) {
        int n = revenues.length;
        boolean[] used = new boolean[n]; // Track which projects are completed
        int currentCapital = c;
        
        // Complete at most k projects
        for (int project = 0; project < k; project++) {
            int bestProject = -1;
            int bestRevenue = -1;
            
            // Find the best affordable project
            for (int i = 0; i < n; i++) {
                if (!used[i] && investments[i] <= currentCapital) {
                    if (revenues[i] > bestRevenue) {
                        bestRevenue = revenues[i];
                        bestProject = i;
                    }
                }
            }
            
            // If no affordable project found, break
            if (bestProject == -1) {
                break;
            }
            
            // Complete the best project
            used[bestProject] = true;
            currentCapital += bestRevenue;
        }
        
        return currentCapital;
    }
}