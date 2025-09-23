package ru.t1.education;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubsequence {
    
    public static int lcsLength(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        
        int m = str1.length();
        int n = str2.length();
        
        if (m == 0 || n == 0) {
            return 0;
        }
        
        int[][] dp = new int[m + 1][n + 1];
        
        // Заполняем таблицу DP. Циклы в цикле, мда
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    public static String lcs(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return "";
        }
        
        int m = str1.length();
        int n = str2.length();
        
        if (m == 0 || n == 0) {
            return "";
        }
        
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Восстанавливаем LCS по таблице
        return buildLCS(str1, str2, dp, m, n);
    }
    
    public static List<String> findAllLCS(String str1, String str2) {
        List<String> result = new ArrayList<>();
        if (str1 == null || str2 == null) {
            return result;
        }
        
        int m = str1.length();
        int n = str2.length();
        
        if (m == 0 || n == 0) {
            return result;
        }
        
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Находим все LCS
        int lcsLength = dp[m][n];
        if (lcsLength == 0) {
            return result; // нет общих подпоследовательностей
        }
        
        findAllLCSHelper(str1, str2, dp, m, n, new StringBuilder(), result, lcsLength);
        return result;
    }
    
    private static String buildLCS(String str1, String str2, int[][] dp, int i, int j) {
        StringBuilder lcs = new StringBuilder();
        
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.append(str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        
        return lcs.reverse().toString();
    }
    
    private static void findAllLCSHelper(String str1, String str2, int[][] dp, 
                                       int i, int j, StringBuilder current, 
                                       List<String> result, int targetLength) {
        // Если достигли начала или текущая длина превышает целевую
        if (i == 0 || j == 0) {
            if (current.length() == targetLength) {
                String lcs = current.reverse().toString();
                if (!result.contains(lcs)) {
                    result.add(lcs);
                }
                current.reverse();
            }
            return;
        }
        
        // Если символы совпадают
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
            current.append(str1.charAt(i - 1));
            findAllLCSHelper(str1, str2, dp, i - 1, j - 1, current, result, targetLength);
            current.deleteCharAt(current.length() - 1);
        } else {
            // Двигаемся в направлении с максимальным значением
            if (dp[i - 1][j] == dp[i][j]) {
                findAllLCSHelper(str1, str2, dp, i - 1, j, current, result, targetLength);
            }
            if (dp[i][j - 1] == dp[i][j]) {
                findAllLCSHelper(str1, str2, dp, i, j - 1, current, result, targetLength);
            }
        }
    }
}