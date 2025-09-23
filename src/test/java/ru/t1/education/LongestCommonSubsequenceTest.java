package ru.t1.education;

import junit.framework.TestCase;
import java.util.List;

public class LongestCommonSubsequenceTest extends TestCase {
    
    public void testLcsLengthEmptyStrings() {
        assertEquals(0, LongestCommonSubsequence.lcsLength("", ""));
        assertEquals(0, LongestCommonSubsequence.lcsLength("abc", ""));
        assertEquals(0, LongestCommonSubsequence.lcsLength("", "xyz"));
        assertEquals(0, LongestCommonSubsequence.lcsLength(null, "test"));
        assertEquals(0, LongestCommonSubsequence.lcsLength("test", null));
    }
    
    public void testLcsLengthBasic() {
        assertEquals(3, LongestCommonSubsequence.lcsLength("ABCDGH", "AEDFHR"));
        assertEquals(4, LongestCommonSubsequence.lcsLength("AGGTAB", "GXTXAYB"));
        assertEquals(0, LongestCommonSubsequence.lcsLength("ABC", "XYZ")); // Должно быть 0!
    }
    
    public void testLcsLengthSameStrings() {
        String str = "Hello World";
        assertEquals(str.length(), LongestCommonSubsequence.lcsLength(str, str));
    }
    
    public void testLcsStringBasic() {
        String result = LongestCommonSubsequence.lcs("ABCDGH", "AEDFHR");
        assertEquals("ADH", result);
        
        result = LongestCommonSubsequence.lcs("AGGTAB", "GXTXAYB");
        assertEquals("GTAB", result);
    }
    
    public void testLcsStringNoCommon() {
        String result = LongestCommonSubsequence.lcs("ABC", "XYZ");
        assertEquals("", result);
    }
    
    public void testLcsStringEdgeCases() {
        assertEquals("", LongestCommonSubsequence.lcs("", "TEST"));
        assertEquals("A", LongestCommonSubsequence.lcs("A", "A"));
        assertEquals("", LongestCommonSubsequence.lcs("A", "B"));
    }
    
    public void testFindAllLCSBasic() {
        List<String> result = LongestCommonSubsequence.findAllLCS("ABCBDAB", "BDCAB");
        assertTrue(result.size() >= 1);
        assertEquals(4, result.get(0).length());
    }
    
    public void testFindAllLCSMultipleSolutions() {
        List<String> result = LongestCommonSubsequence.findAllLCS("ABCD", "ACBD");
        assertTrue(result.size() >= 1);
        for (String lcs : result) {
            assertEquals(3, lcs.length());
        }
    }
    
    public void testFindAllLCSNoCommon() {
        List<String> result = LongestCommonSubsequence.findAllLCS("ABC", "XYZ");
        assertTrue(result.isEmpty());
    }
    
    public void testLcsWithSpecialCharacters() {
        String result = LongestCommonSubsequence.lcs("Hello 123!", "Hello World!");
        assertTrue(result.length() >= 5);
        assertTrue(result.startsWith("Hello"));
    }
    
    public void testLcsPerformance() {
        String str1 = "A".repeat(100) + "B".repeat(100);
        String str2 = "A".repeat(100) + "C".repeat(100);
        
        assertEquals(100, LongestCommonSubsequence.lcsLength(str1, str2));
        assertEquals("A".repeat(100), LongestCommonSubsequence.lcs(str1, str2));
    }
    
    public void testFindAllLCSSimple() {
        List<String> result = LongestCommonSubsequence.findAllLCS("ABC", "AC");
        assertFalse(result.isEmpty());
        assertEquals(2, result.get(0).length());
    }
}