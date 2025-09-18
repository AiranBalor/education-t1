package ru.t1.education;

import java.util.*;

public class TopologicalSort {
    
    /**
     * @param adjacencyMatrix матрица смежности
     * @return список вершин в топологическом порядке (или пустой список, на случай, если граф содержит цикл)
     */
    public static List<Integer> topologicalSort(int[][] adjacencyMatrix) {
        int n = adjacencyMatrix.length;
        if (n == 0) return new ArrayList<>();
        
        // Проверяем, что матрица квадратная
        for (int[] row : adjacencyMatrix) {
            if (row.length != n) {
                throw new IllegalArgumentException("Матрица смежности должна быть квадратной!");
            }
        }
        
        int[] inDegree = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    inDegree[j]++;
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        int count = 0;
        
        // Алгоритм Кана
        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.add(u);
            count++;
            
            for (int v = 0; v < n; v++) {
                if (adjacencyMatrix[u][v] != 0) {
                    inDegree[v]--;
                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }
                }
            }
        }

        if (count != n) {
            return new ArrayList<>(); // Граф таки содержит цикл
        }
        
        return result;
    }
    
    /**
     * используем DFS
     * @param adjacencyMatrix матрица смежности
     * @return список вершин в топологическом порядке или опять пустой список при наличии цикла
     */
    public static List<Integer> topologicalSortDFS(int[][] adjacencyMatrix) {
        int n = adjacencyMatrix.length;
        if (n == 0) return new ArrayList<>();
        
        boolean[] visited = new boolean[n];
        boolean[] recursionStack = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (dfs(i, adjacencyMatrix, visited, recursionStack, stack)) {
                    return new ArrayList<>(); // Обнаружен цикл
                }
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        
        return result;
    }
    
    private static boolean dfs(int u, int[][] graph, boolean[] visited, 
                             boolean[] recursionStack, Stack<Integer> stack) {
        if (recursionStack[u]) {
            return true; // Обнаружен цикл
        }
        
        if (visited[u]) {
            return false;
        }
        
        visited[u] = true;
        recursionStack[u] = true;
        
        for (int v = 0; v < graph.length; v++) {
            if (graph[u][v] != 0) {
                if (dfs(v, graph, visited, recursionStack, stack)) {
                    return true;
                }
            }
        }
        
        recursionStack[u] = false;
        stack.push(u);
        return false;
    }
}
