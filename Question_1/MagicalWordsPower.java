package Question_1;

import java.util.*;

public class MagicalWordsPower {
    /*
     * Problem Summary:
     * Find two non-overlapping magical words (odd-length palindromes) from a manuscript string
     * that maximize their power combination (product of their lengths).
     * 
     * Approach:
     * 1. Find all odd-length palindromes in the string with their positions
     * 2. For each pair of non-overlapping palindromes, calculate their power product
     * 3. Return the maximum power combination found
     */
    
    public static int maxMagicalPower(String manuscript) {
        int n = manuscript.length();
        int maxPower = 0;
        
        // Find all odd-length palindromes and check pairs
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // Check if substring from i to j is an odd-length palindrome
                if ((j - i + 1) % 2 == 1 && isPalindrome(manuscript, i, j)) {
                    int firstLength = j - i + 1;
                    
                    // Look for second non-overlapping palindrome
                    // Search before first palindrome
                    for (int k = 0; k < i; k++) {
                        for (int l = k; l < i; l++) {
                            if ((l - k + 1) % 2 == 1 && isPalindrome(manuscript, k, l)) {
                                int secondLength = l - k + 1;
                                maxPower = Math.max(maxPower, firstLength * secondLength);
                            }
                        }
                    }
                    
                    // Search after first palindrome
                    for (int k = j + 1; k < n; k++) {
                        for (int l = k; l < n; l++) {
                            if ((l - k + 1) % 2 == 1 && isPalindrome(manuscript, k, l)) {
                                int secondLength = l - k + 1;
                                maxPower = Math.max(maxPower, firstLength * secondLength);
                            }
                        }
                    }
                }
            }
        }
        
        return maxPower;
    }
    
    // Helper method to check if substring is palindrome
    private static boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
    
    // Main method to test the function
    public static void main(String[] args) {
        // Test Case 1: Given example
        String M1 = "xyzyxabc";
        int result1 = maxMagicalPower(M1);
        System.out.println("Test 1 - Input: \"" + M1 + "\"");
        System.out.println("Output: " + result1); // Expected: 5
        
        // Test Case 2: Given example
        String M2 = "levelwowracecar";
        int result2 = maxMagicalPower(M2);
        System.out.println("Test 2 - Input: \"" + M2 + "\"");
        System.out.println("Output: " + result2); // Expected: 35
        
        // Test Case 3: Simple case
        String M3 = "abacaba";
        int result3 = maxMagicalPower(M3);
        System.out.println("Test 3 - Input: \"" + M3 + "\"");
        System.out.println("Output: " + result3); // Expected: 21 (aba * abacaba = 3*7)
        
        // Test Case 4: Only single characters
        String M4 = "abcd";
        int result4 = maxMagicalPower(M4);
        System.out.println("Test 4 - Input: \"" + M4 + "\"");
        System.out.println("Output: " + result4); // Expected: 1 (any two single chars = 1*1)
        
        // Test Case 5: No valid pairs
        String M5 = "a";
        int result5 = maxMagicalPower(M5);
        System.out.println("Test 5 - Input: \"" + M5 + "\"");
        System.out.println("Output: " + result5); // Expected: 0 (need two palindromes)
    }
}