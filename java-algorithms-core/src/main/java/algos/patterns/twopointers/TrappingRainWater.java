package algos.patterns.twopointers;

public class TrappingRainWater {

    public static int trap(int[] height) {
        if (height == null || height.length == 0) return 0;

        int left = 0;
        int right = height.length - 1;
        int lMax = 0;
        int rMax = 0;
        int res = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                lMax = Integer.max(lMax, height[left]);
                res += (lMax - height[left]);
                left++;
            } else { //height[left] >= height[right]
                rMax = Integer.max(rMax, height[right]);
                res += (rMax - height[right]);
                right--;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println("Trapped water: " + trap(height)); // Output: 6
    }
}
