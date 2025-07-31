package Question_1;
import java.util.*;

public class BankPINSecurity {
    
    public static int strongPINChanges(String pinCode) {
        int n = pinCode.length();
        
        // Check what character types are missing
        boolean hasLower = false, hasUpper = false, hasDigit = false;
        
        for (char c : pinCode.toCharArray()) {
            if (c >= 'a' && c <= 'z') hasLower = true;
            else if (c >= 'A' && c <= 'Z') hasUpper = true;
            else if (c >= '0' && c <= '9') hasDigit = true;
        }
        
        // Count missing character types
        int missingTypes = 0;
        if (!hasLower) missingTypes++;
        if (!hasUpper) missingTypes++;
        if (!hasDigit) missingTypes++;
        
        // Case 1: PIN is too short (< 6 characters)
        if (n < 6) {
            return Math.max(6 - n, missingTypes);
        }
        
        // Case 2: PIN is too long (> 20 characters)
        if (n > 20) {
            int deletions = n - 20;
            
            // Count consecutive repeating sequences that need to be fixed
            int consecutiveReplacements = countConsecutiveReplacements(pinCode);
            
            // Try to reduce consecutive replacements by strategic deletions
            consecutiveReplacements = Math.max(0, consecutiveReplacements - deletions);
            
            return deletions + Math.max(consecutiveReplacements, missingTypes);
        }
        
        // Case 3: PIN length is within range (6-20 characters)
        int consecutiveReplacements = countConsecutiveReplacements(pinCode);
        
        // The number of replacements needed is the maximum of:
        // - replacements for consecutive characters
        // - missing character types
        return Math.max(consecutiveReplacements, missingTypes);
    }
    
    // Helper method to count replacements needed for consecutive repeating characters
    private static int countConsecutiveReplacements(String s) {
        int replacements = 0;
        int i = 0;
        
        while (i < s.length()) {
            int j = i;
            
            // Find the length of current repeating sequence
            while (j < s.length() && s.charAt(j) == s.charAt(i)) {
                j++;
            }
            
            int sequenceLength = j - i;
            
            // If sequence length >= 3, we need replacements
            if (sequenceLength >= 3) {
                replacements += sequenceLength / 3;
            }
            
            i = j;
        }
        
        return replacements;
    }
    
    public static void main(String[] args) {
        // Test cases
        testCase("X1!", 3, "Too short, missing lowercase");
        testCase("123456", 2, "Missing uppercase and lowercase");
        testCase("Aa1234!", 0, "Already strong");
        testCase("aaa111", 2, "Consecutive repeating chars");
        testCase("AAAAAA", 2, "All same char, missing types");
        testCase("Aa1", 3, "Too short");
        testCase("Aa1234567890123456789012", 4, "Too long");
        testCase("AAAaaa111", 3, "Multiple consecutive sequences");
    }
    
    private static void testCase(String pin, int expected, String description) {
        int result = strongPINChanges(pin);
        System.out.printf("PIN: %-25s | Expected: %d | Got: %d | %s%n", 
                         "\"" + pin + "\"", expected, result, 
                         result == expected ? "✓ PASS" : "✗ FAIL");
        System.out.println("Description: " + description);
        System.out.println();
    }
}

// Alternative simpler approach for better understanding
class SimplePINSecurity {
    
    public static int strongPINChangesSimple(String pinCode) {
        int n = pinCode.length();
        int changes = 0;
        
        // Step 1: Handle length issues
        if (n < 6) {
            changes += (6 - n); // Need to insert characters
            n = 6; // Assume we've made it 6 characters
        } else if (n > 20) {
            changes += (n - 20); // Need to delete characters
            n = 20; // Assume we've made it 20 characters
        }
        
        // Step 2: Check for missing character types
        boolean hasLower = false, hasUpper = false, hasDigit = false;
        
        for (char c : pinCode.toCharArray()) {
            if (c >= 'a' && c <= 'z') hasLower = true;
            else if (c >= 'A' && c <= 'Z') hasUpper = true;
            else if (c >= '0' && c <= '9') hasDigit = true;
        }
        
        int missingTypes = 0;
        if (!hasLower) missingTypes++;
        if (!hasUpper) missingTypes++;
        if (!hasDigit) missingTypes++;
        
        // Step 3: Handle consecutive repeating characters
        int consecutiveReplacements = 0;
        for (int i = 0; i < pinCode.length(); ) {
            int j = i;
            while (j < pinCode.length() && pinCode.charAt(j) == pinCode.charAt(i)) {
                j++;
            }
            
            int sequenceLength = j - i;
            if (sequenceLength >= 3) {
                consecutiveReplacements += sequenceLength / 3;
            }
            i = j;
        }
        
        // The additional changes needed
        int additionalChanges = Math.max(consecutiveReplacements, missingTypes);
        
        return changes + additionalChanges;
    }
}

// For reference (Similar QUE) : https://leetcode.com/problems/strong-password-checker/description/