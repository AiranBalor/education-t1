package ru.t1.education;

import java.util.ArrayList;
import java.util.List;

public class GraphConverter {
    
    public static List<int[]> adjacencyMatrixToEdgeList(int[][] adjacencyMatrix) {
        List<int[]> edgeList = new ArrayList<>();
        int n = adjacencyMatrix.length;
        
        if (n == 0) return edgeList;
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    edgeList.add(new int[]{i, j, adjacencyMatrix[i][j]});
                }
            }
        }
        
        return edgeList;
    }
}