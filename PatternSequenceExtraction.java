package Question_1;

import java.util.*;

public class PatternSubsequenceChecker {
    /*
     * Problem Summary:
     * Given two strings `p1` and `p2`, and two integers `t1` and `t2`, this program determines
     * the maximum number of times pattern p2 can be extracted as a subsequence from the sequence
     * formed by repeating p1 exactly t1 times.
     * 
     * Approach:
     * - Construct the full sequence A by repeating p1 t1 times.
     * - Use a greedy pointer approach to scan the full sequence and extract p2 repeatedly.
     * - Count how many times p2 is fully matched in order inside A.
     * - Return the minimum of (extracted count, t2) as the final answer.
     */
    
    public static int getMaxPatternRepeats(String p1, int t1, String p2, int t2) {
        StringBuilder seqA = new StringBuilder();
        
        // Step 1: Build the full sequence A by repeating p1 t1 times
        for (int i = 0; i < t1; i++) {
            seqA.append(p1);
        }
        
        int extractedCount = 0; // Total number of p2 patterns extracted
        int p2Index = 0;        // Pointer in p2 pattern
        
        // Step 2: Scan through seqA to extract p2 repeatedly
        for (int i = 0; i < seqA.length(); i++) {
            if (seqA.charAt(i) == p2.charAt(p2Index)) {
                p2Index++; // Match one character
                
                if (p2Index == p2.length()) {
                    extractedCount++; // Completed one p2 extraction
                    p2Index = 0;      // Reset pointer for next p2
                }
            }
        }
        
        // Step 3: Return minimum of extracted count and requested t2
        return Math.min(extractedCount, t2);
    }
    
    // Main method to test the function
    public static void main(String[] args) {
        // Test Case 1: Given example
        String p1 = "bca";
        int t1 = 6;
        String p2 = "ba";
        int t2 = 3;
        int result1 = getMaxPatternRepeats(p1, t1, p2, t2);
        System.out.println("Test 1 - Output: " + result1); // Expected: 3
        
        // Test Case 2: More extractions possible than requested
        String p1_2 = "abc";
        int t1_2 = 4;
        String p2_2 = "a";
        int t2_2 = 2;
        int result2 = getMaxPatternRepeats(p1_2, t1_2, p2_2, t2_2);
        System.out.println("Test 2 - Output: " + result2); // Expected: 2
        
        // Test Case 3: Less extractions possible than requested
        String p1_3 = "xy";
        int t1_3 = 3;
        String p2_3 = "xyz";
        int t2_3 = 2;
        int result3 = getMaxPatternRepeats(p1_3, t1_3, p2_3, t2_3);
        System.out.println("Test 3 - Output: " + result3); // Expected: 0
        
        // Test Case 4: Edge case - single character
        String p1_4 = "a";
        int t1_4 = 5;
        String p2_4 = "a";
        int t2_4 = 3;
        int result4 = getMaxPatternRepeats(p1_4, t1_4, p2_4, t2_4);
        System.out.println("Test 4 - Output: " + result4); // Expected: 3
    }
}