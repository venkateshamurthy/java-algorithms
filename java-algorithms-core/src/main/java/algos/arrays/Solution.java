package algos.arrays;

import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'maxHeight' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY wallPositions
     *  2. INTEGER_ARRAY wallHeights
     */

    public static int maxHeight(List<Integer> wallPositions, List<Integer> wallHeights) {
        if (wallPositions == null ||
                wallHeights == null )
            return Integer.MIN_VALUE;

        val posHeight = IntStream.range(0, wallPositions.size()).boxed()
                .filter(Objects::nonNull)
                .map(i -> Pair.<Integer, Integer>of(wallPositions.get(i), wallHeights.get(i)))
                .filter(p -> p.getLeft() > 0 && p.getRight() > 0)
                .sorted((p1, p2) -> Integer.compare(p1.getKey(), p2.getKey()))
                .collect(Collectors.toList());

        int max = 0;

        for (int i = 0; i < posHeight.size() - 1; i++) {
            val left = posHeight.get(i);
            val right = posHeight.get(i + 1);
            val posDiff = right.getLeft() - left.getLeft();
            if (posDiff <= 1) continue;
            val minHeight = Integer.min(right.getRight(), left.getRight());
            val maxHeight = Integer.max(right.getRight(), left.getRight());
            if (maxHeight - minHeight < 2)
                max = minHeight;
            else if (maxHeight - minHeight >= posDiff)
                max = Math.max(max, minHeight + posDiff - 1);
            else
                max = Math.max(max, maxHeight - 1);
        }
        return max;
    }


    public static class Solution {
        public static void main(String[] args) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

            int wallPositionsCount = Integer.parseInt(bufferedReader.readLine().trim());

            List<Integer> wallPositions = IntStream.range(0, wallPositionsCount).mapToObj(i -> {
                try {
                    return bufferedReader.readLine().replaceAll("\\s+$", "");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            })
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(toList());

            int wallHeightsCount = Integer.parseInt(bufferedReader.readLine().trim());

            List<Integer> wallHeights = IntStream.range(0, wallHeightsCount).mapToObj(i -> {
                try {
                    return bufferedReader.readLine().replaceAll("\\s+$", "");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            })
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(toList());

            int result = Result.maxHeight(wallPositions, wallHeights);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();

            bufferedReader.close();
            bufferedWriter.close();
        }
    }
}
