package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TriangleMinPath {
    public static class Result {
        public final int sum;
        public final List<Integer> values;
        public final List<Integer> indices;

        public Result(int sum, List<Integer> values, List<Integer> indices) {
            this.sum = sum;
            this.values = values;
            this.indices = indices;
        }

        @Override
        public String toString() {
            return "sum=" + sum + ", values=" + values + ", indices=" + indices;
        }
    }

    public static Result minPathSumWithPath(List<List<Integer>> triangle) {
        if (triangle == null || triangle.isEmpty()) {
            return new Result(0, Collections.emptyList(), Collections.emptyList());
        }

        int n = triangle.size();
        List<Integer> lastRow = triangle.get(n - 1);
        int mLast = lastRow.size();
        int[] dp = new int[mLast];
        for (int i = 0; i < mLast; i++) dp[i] = lastRow.get(i);

        int[][] choices = new int[Math.max(0, n - 1)][];
        for (int i = n - 2; i >= 0; i--) {
            List<Integer> row = triangle.get(i);
            int m = row.size();
            int[] newDp = new int[m];
            int[] choiceRow = new int[m];
            for (int j = 0; j < m; j++) {
                int down = dp[j];
                int downRight = dp[j + 1];
                if (down <= downRight) {
                    newDp[j] = row.get(j) + down;
                    choiceRow[j] = j;
                } else {
                    newDp[j] = row.get(j) + downRight;
                    choiceRow[j] = j + 1;
                }
            }
            dp = newDp;
            choices[i] = choiceRow;
        }

        int minSum = dp[0];

        List<Integer> indices = new ArrayList<>(Collections.nCopies(n, 0));
        int cur = 0;
        indices.set(0, 0);
        for (int i = 0; i < n - 1; i++) {
            cur = choices[i][cur];
            indices.set(i + 1, cur);
        }

        List<Integer> values = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            values.add(triangle.get(i).get(indices.get(i)));
        }

        return new Result(minSum, values, indices);
    }

    // Демонстрация на примерах
    public static void main(String[] args) {
        List<List<Integer>> tri1 = Arrays.asList(
                Arrays.asList(2),
                Arrays.asList(3, 4),
                Arrays.asList(6, 5, 7),
                Arrays.asList(4, 1, 8, 3)
        );

        List<List<Integer>> tri2 = Arrays.asList(
                Arrays.asList(-1),
                Arrays.asList(2, 3),
                Arrays.asList(1, -1, -3),
                Arrays.asList(4, 2, 1, 3)
        );

        Result r1 = minPathSumWithPath(tri1);
        System.out.println("Первый пример: " + r1);
        // Ожидается sum=11, values=[2,3,5,1]

        Result r2 = minPathSumWithPath(tri2);
        System.out.println("Второй пример: " + r2);
        // Ожидается sum=0, values=[-1,3,-3,1]
    }
}
