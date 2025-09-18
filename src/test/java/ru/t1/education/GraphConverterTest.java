package ru.t1.education;

import junit.framework.TestCase;
import java.util.List;

public class GraphConverterTest extends TestCase {
    
    public void testEmptyMatrix() {
        int[][] matrix = {};
        List<int[]> result = GraphConverter.adjacencyMatrixToEdgeList(matrix);
        assertTrue(result.isEmpty());
    }
    
    public void testSingleVertex() {
        int[][] matrix = {{0}};
        List<int[]> result = GraphConverter.adjacencyMatrixToEdgeList(matrix);
        assertTrue(result.isEmpty());
    }
    
    public void testUndirectedGraph() {
        int[][] matrix = {
            {0, 2, 0},
            {2, 0, 3},
            {0, 3, 0}
        };
        
        List<int[]> result = GraphConverter.adjacencyMatrixToEdgeList(matrix);
        assertEquals(2, result.size());
        assertArrayEquals(new int[]{0, 1, 2}, result.get(0));
        assertArrayEquals(new int[]{1, 2, 3}, result.get(1));
    }
    
    private void assertArrayEquals(int[] expected, int[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}