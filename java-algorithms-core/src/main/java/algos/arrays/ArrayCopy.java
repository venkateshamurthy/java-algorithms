package algos.arrays;

import java.util.Arrays;

//                 |
//                 v
//12 | 23|32 |45 | 98|87 | 69| | | |
//0  | 1 | 2| 3  | 4 | 5 | 6 |7  |8  | 9 |
public class ArrayCopy {
    int capacity = 7;
    int[] data = new int[capacity];
    int n;

    public ArrayCopy(int[] input) {
        for(int i = 0; i< input.length; i++) {
            data[i] = input[i];
        }
        this.n = input.length;
        // start = 0, end = 9, length = 10
    }

    public void insertAt(int index, int value) {
        if (n >= capacity) throw new RuntimeException("Array is full");
        // input =             {12, 23, 32, 45, value, 98, 87, 69}
        for (int i = n - 1; i >= index; i-- ){
            data[i+1] = data[i];
        }
        data[index] = -1;
        System.out.println("Before:"+Arrays.toString(data));
        data[index] = value;
        System.out.println("After: "+Arrays.toString(data));
    }

    public static void main(String[] args) {
        int[] input = new int[]{12, 23, 32, 45, 98, 87 , 69};
        System.out.println("Input: "+Arrays.toString(input));
        ArrayCopy arrayCopy = new ArrayCopy(input);
        arrayCopy.insertAt(4, 67);
    }
}
