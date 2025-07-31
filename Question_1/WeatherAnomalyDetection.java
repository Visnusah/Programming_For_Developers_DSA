package Question_1;

import java.util.*;


public class WeatherAnomalyDetection {
    
    // Simple Brute Force Approach - Easy to understand
    public static int countAnomalyPeriods(int[] temperatureChanges, int lowThreshold, int highThreshold) {
        int n = temperatureChanges.length;
        int count = 0;
        
        // Check all possible subarrays
        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            
            for (int j = i; j < n; j++) {
                currentSum += temperatureChanges[j];
                
                // Check if current subarray sum is within threshold range
                if (currentSum >= lowThreshold && currentSum <= highThreshold) {
                    count++;
                    System.out.println("Valid period: Day " + i + " to Day " + j + 
                                     " → Total change = " + currentSum);
                }
            }
        }
        
        return count;
    }
    
    // Optimized approach using prefix sums
    public static int countAnomalyPeriodsOptimized(int[] temperatureChanges, int lowThreshold, int highThreshold) {
        int n = temperatureChanges.length;
        int count = 0;
        
        // For each starting position
        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            
            // Extend the subarray from position i
            for (int j = i; j < n; j++) {
                currentSum += temperatureChanges[j];
                
                // Check if sum is within range
                if (currentSum >= lowThreshold && currentSum <= highThreshold) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    public static void main(String[] args) {
        // Test Case 1
        System.out.println("=== Test Case 1 ===");
        int[] temp1 = {3, -1, -4, 6, 2};
        int low1 = 2, high1 = 5;
        
        System.out.println("Temperature changes: " + java.util.Arrays.toString(temp1));
        System.out.println("Threshold range: [" + low1 + ", " + high1 + "]");
        System.out.println();
        
        int result1 = countAnomalyPeriods(temp1, low1, high1);
        System.out.println("Total valid periods: " + result1);
        System.out.println("Expected: 3");
        System.out.println();
        
        // Test Case 2
        System.out.println("=== Test Case 2 ===");
        int[] temp2 = {-2, 3, 1, -5, 4};
        int low2 = -1, high2 = 2;
        
        System.out.println("Temperature changes: " + java.util.Arrays.toString(temp2));
        System.out.println("Threshold range: [" + low2 + ", " + high2 + "]");
        System.out.println();
        
        int result2 = countAnomalyPeriods(temp2, low2, high2);
        System.out.println("Total valid periods: " + result2);
        System.out.println("Expected: 4");
        System.out.println();
        
        // Additional test case **************(FOR better undersand)
        System.out.println("=== Test Case 3 ===");
        int[] temp3 = {1, 2, 3};
        int low3 = 3, high3 = 6;
        
        System.out.println("Temperature changes: " + java.util.Arrays.toString(temp3));
        System.out.println("Threshold range: [" + low3 + ", " + high3 + "]");
        System.out.println();
        
        int result3 = countAnomalyPeriods(temp3, low3, high3);
        System.out.println("Total valid periods: " + result3);
        System.out.println();
    }
}

// Alternative implementation with detailed step-by-step explanation
class WeatherAnomalyDetailed {
    
    public static int countAnomalyPeriodsDetailed(int[] temperatureChanges, int lowThreshold, int highThreshold) {
        int n = temperatureChanges.length;
        int validCount = 0;
        
        System.out.println("Checking all possible continuous periods:");
        System.out.println("Temperature changes: " + java.util.Arrays.toString(temperatureChanges));
        System.out.println("Valid range: [" + lowThreshold + ", " + highThreshold + "]");
        System.out.println();
        
        // Check all possible subarrays
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                // Calculate sum of subarray from start to end
                int sum = 0;
                for (int k = start; k <= end; k++) {
                    sum += temperatureChanges[k];
                }
                
                // Check if sum is within threshold range
                boolean isValid = (sum >= lowThreshold && sum <= highThreshold);
                
                System.out.printf("Day %d to Day %d → Sum = %d %s%n", 
                                start, end, sum, 
                                isValid ? "✅ (valid)" : "❌ (invalid)");
                
                if (isValid) {
                    validCount++;
                }
            }
        }
        
        return validCount;
    }
}