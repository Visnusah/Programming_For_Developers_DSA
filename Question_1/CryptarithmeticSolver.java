package Question_1;

import java.util.*;

public class CryptarithmeticSolver {
    
    // Main method to solve cryptarithmetic equations
    public static boolean solveCryptarithmetic(String word1, String word2, String result) {
        // Get all unique letters from the equation
        Set<Character> uniqueLetters = new HashSet<>();
        for (char c : word1.toCharArray()) uniqueLetters.add(c);
        for (char c : word2.toCharArray()) uniqueLetters.add(c);
        for (char c : result.toCharArray()) uniqueLetters.add(c);
        
        // Convert to list for easier indexing
        List<Character> letters = new ArrayList<>(uniqueLetters);
        
        // Check if we have too many letters (max 10 digits available)
        if (letters.size() > 10) {
            System.out.println("Too many unique letters! Maximum 10 allowed.");
            return false;
        }
        
        // Create mapping array: letterToDigit[i] = digit assigned to letters[i]
        Map<Character, Integer> letterToDigit = new HashMap<>();
        boolean[] usedDigits = new boolean[10]; // Track which digits are used
        
        System.out.println("Solving: " + word1 + " + " + word2 + " = " + result);
        System.out.println("Unique letters: " + letters);
        System.out.println();
        
        // Start backtracking
        boolean found = backtrack(letters, 0, letterToDigit, usedDigits, word1, word2, result);
        
        if (!found) {
            System.out.println("No valid solution exists for this equation.");
        }
        
        return found;
    }
    
    // Backtracking function to try all possible digit assignments
    private static boolean backtrack(List<Character> letters, int index, 
                                   Map<Character, Integer> letterToDigit, 
                                   boolean[] usedDigits, 
                                   String word1, String word2, String result) {
        
        // Base case: all letters have been assigned digits
        if (index == letters.size()) {
            return isValidEquation(word1, word2, result, letterToDigit);
        }
        
        char currentLetter = letters.get(index);
        
        // Try assigning each available digit (0-9) to current letter
        for (int digit = 0; digit <= 9; digit++) {
            if (usedDigits[digit]) continue; // Skip if digit already used
            
            // Check for leading zeros constraint
            if (digit == 0 && isLeadingLetter(currentLetter, word1, word2, result)) {
                continue; // Skip assigning 0 to leading letters
            }
            
            // Assign digit to current letter
            letterToDigit.put(currentLetter, digit);
            usedDigits[digit] = true;
            
            // Recursively try to assign remaining letters
            if (backtrack(letters, index + 1, letterToDigit, usedDigits, word1, word2, result)) {
                return true; // Found valid solution
            }
            
            // Backtrack: remove assignment
            letterToDigit.remove(currentLetter);
            usedDigits[digit] = false;
        }
        
        return false; // No valid assignment found for current letter
    }
    
    // Check if a letter is the first letter of any word (leading letter)
    private static boolean isLeadingLetter(char letter, String word1, String word2, String result) {
        return (word1.charAt(0) == letter) || 
               (word2.charAt(0) == letter) || 
               (result.charAt(0) == letter);
    }
    
    // Convert word to number using letter-to-digit mapping
    private static long wordToNumber(String word, Map<Character, Integer> letterToDigit) {
        long number = 0;
        for (char c : word.toCharArray()) {
            number = number * 10 + letterToDigit.get(c);
        }
        return number;
    }
    
    // Check if current digit assignment satisfies the equation
    private static boolean isValidEquation(String word1, String word2, String result, 
                                         Map<Character, Integer> letterToDigit) {
        long num1 = wordToNumber(word1, letterToDigit);
        long num2 = wordToNumber(word2, letterToDigit);
        long numResult = wordToNumber(result, letterToDigit);
        
        boolean isValid = (num1 + num2 == numResult);
        
        if (isValid) {
            System.out.println("✅ SOLUTION FOUND!");
            System.out.println("Letter to Digit Mapping:");
            
            // Sort letters for consistent output
            List<Character> sortedLetters = new ArrayList<>(letterToDigit.keySet());
            Collections.sort(sortedLetters);
            
            for (char letter : sortedLetters) {
                System.out.println(letter + " = " + letterToDigit.get(letter));
            }
            
            System.out.println();
            System.out.println("Verification:");
            System.out.println("\"" + word1 + "\" → " + num1);
            System.out.println("\"" + word2 + "\" → " + num2);
            System.out.println("\"" + result + "\" → " + numResult);
            System.out.println();
            System.out.println("Check: " + num1 + " + " + num2 + " = " + (num1 + num2));
            System.out.println("Expected: " + numResult);
            System.out.println("Valid: " + isValid);
        }
        
        return isValid;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Cryptarithmetic Puzzle Solver ===");
        System.out.println();
        
        // Test Case 1: SEND + MORE = MONEY (classic example)
        System.out.println("--- Test Case 1 ---");
        solveCryptarithmetic("SEND", "MORE", "MONEY");
        System.out.println();
        
        // Test Case 2: STAR + MOON = NIGHT (from your example)
        System.out.println("--- Test Case 2 ---");
        solveCryptarithmetic("STAR", "MOON", "NIGHT");
        System.out.println();
        
        // Test Case 3: CODE + BUG = DEBUG (should be impossible)
        System.out.println("--- Test Case 3 ---");
        solveCryptarithmetic("CODE", "BUG", "DEBUG");
        System.out.println();
        
        // Test Case 4: Simple example
        System.out.println("--- Test Case 4 ---");
        solveCryptarithmetic("AB", "CD", "EFG");
        System.out.println();
    }
}

// Simplified version for educational purposes
class SimpleCryptarithmeticSolver {
    
    public static void solvePuzzleManually() {
        System.out.println("=== Manual Solution Example ===");
        System.out.println("Equation: STAR + MOON = NIGHT");
        System.out.println();
        
        // Manual assignment from your example
        Map<Character, Integer> mapping = new HashMap<>();
        mapping.put('S', 8);
        mapping.put('T', 4);
        mapping.put('A', 2);
        mapping.put('R', 5);
        mapping.put('M', 7);
        mapping.put('O', 1);
        mapping.put('N', 9);
        mapping.put('I', 6);
        mapping.put('G', 3);
        mapping.put('H', 0);
        
        System.out.println("Given mapping:");
        for (Map.Entry<Character, Integer> entry : mapping.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
        System.out.println();
        
        // Convert words to numbers
        String word1 = "STAR", word2 = "MOON", result = "NIGHT";
        
        long num1 = wordToNumber(word1, mapping);
        long num2 = wordToNumber(word2, mapping);
        long numResult = wordToNumber(result, mapping);
        
        System.out.println("Conversion:");
        System.out.println(word1 + " → " + num1);
        System.out.println(word2 + " → " + num2);
        System.out.println(result + " → " + numResult);
        System.out.println();
        
        System.out.println("Verification:");
        System.out.println(num1 + " + " + num2 + " = " + (num1 + num2));
        System.out.println("Expected: " + numResult);
        System.out.println("Valid: " + (num1 + num2 == numResult));
    }
    
    private static long wordToNumber(String word, Map<Character, Integer> mapping) {
        long number = 0;
        for (char c : word.toCharArray()) {
            number = number * 10 + mapping.get(c);
        }
        return number;
    }
    
    public static void main(String[] args) {
        solvePuzzleManually();
    }
}