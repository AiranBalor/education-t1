package ru.t1.education;

import junit.framework.TestCase;
import java.util.List;

public class TopologicalSortTest extends TestCase {
    
    public void testEmptyGraph() {
        int[][] matrix = {};
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        assertTrue(result.isEmpty());
    }
    
    public void testSingleVertex() {
        int[][] matrix = {{0}};
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        assertEquals(1, result.size());
        assertEquals(0, (int) result.get(0));
    }
    
    public void testLinearGraph() {
        // 0 -> 1 -> 2
        int[][] matrix = {
            {0, 1, 0},
            {0, 0, 1},
            {0, 0, 0}
        };
        
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        assertEquals(3, result.size());
        assertEquals(0, (int) result.get(0));
        assertEquals(1, (int) result.get(1));
        assertEquals(2, (int) result.get(2));
    }
    
    public void testGraphWithMultiplePaths() {
        // 0 -> 1 -> 3
        // 0 -> 2 -> 3
        int[][] matrix = {
            {0, 1, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };
        
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        assertEquals(4, result.size());
        assertEquals(0, (int) result.get(0));
        assertTrue(result.indexOf(3) > result.indexOf(1));
        assertTrue(result.indexOf(3) > result.indexOf(2));
    }
    
    public void testGraphWithCycle() {
        // 0 -> 1 -> 2 -> 0 (цикл)
        int[][] matrix = {
            {0, 1, 0},
            {0, 0, 1},
            {1, 0, 0}
        };
        
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        assertTrue(result.isEmpty()); // Должен вернуть пустой список из-за цикла
    }
    
    public void testComplexDAG() {
        int[][] matrix = new int[8][8];
        // Вершины: 0-рубашка, 1-галстук, 2-пиджак, 3-ремень, 4-носки, 5-туфли, 6-трусы, 7-брюки 
        matrix[0][1] = 1; // рубашка -> галстук
        matrix[0][3] = 1; // рубашка -> ремень
        matrix[1][2] = 1; // галстук -> пиджак
        matrix[3][2] = 1; // ремень -> пиджак
        matrix[4][5] = 1; // носки -> туфли
        matrix[6][7] = 1; // трусы -> брюки
        matrix[7][5] = 1; // брюки -> туфли
        matrix[7][3] = 1; // брюки -> ремень
        
        List<Integer> result = TopologicalSort.topologicalSort(matrix);
        assertEquals(8, result.size());
        
        // Проверяем корректность порядка
        assertTrue(result.indexOf(0) < result.indexOf(1)); // рубашка before галстук
        assertTrue(result.indexOf(0) < result.indexOf(3)); // рубашка before ремень
        assertTrue(result.indexOf(1) < result.indexOf(2)); // галстук before пиджак
        assertTrue(result.indexOf(3) < result.indexOf(2)); // ремень before пиджак
        assertTrue(result.indexOf(4) < result.indexOf(5)); // носки before туфли
        assertTrue(result.indexOf(6) < result.indexOf(7)); // трусы before брюки
        assertTrue(result.indexOf(7) < result.indexOf(5)); // брюки before туфли
        assertTrue(result.indexOf(7) < result.indexOf(3)); // брюки before ремень
    }
    
    public void testDFSMethod() {
        int[][] matrix = {
            {0, 1, 0},
            {0, 0, 1},
            {0, 0, 0}
        };
        
        List<Integer> result = TopologicalSort.topologicalSortDFS(matrix);
        assertEquals(3, result.size());
        assertEquals(0, (int) result.get(0));
        assertEquals(1, (int) result.get(1));
        assertEquals(2, (int) result.get(2));
    }
    
    public void testDFSWithCycle() {
        int[][] matrix = {
            {0, 1, 0},
            {0, 0, 1},
            {1, 0, 0}
        };
        
        List<Integer> result = TopologicalSort.topologicalSortDFS(matrix);
        assertTrue(result.isEmpty());
    }
}