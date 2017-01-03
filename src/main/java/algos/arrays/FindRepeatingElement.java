package algos.arrays;

public class FindRepeatingElement {
	 
	// Returns index of second appearance of a repeating element
	// The function assumes that array elements are in range from
	// 1 to n-1.
	int findRepeatingElement(int arr[], int low, int high)
	{
	    // low = 0 , high = n-1;
	    if (low > high)
	        return -1;
	 
	    int mid = (low + high) / 2;
	 
	    // Check if the mid element is the repeating one
	    if (arr[mid] != mid + 1)
	    {
	        if (mid > 0 && arr[mid]==arr[mid-1])
	            return mid;
	 
	        // If mid element is not at its position that means
	        // the repeated element is in left
	        return  findRepeatingElement(arr, low, mid-1);
	    }
	 
	    // If mid is at proper position then repeated one is in
	    // right.
	    return findRepeatingElement(arr, mid+1, high);
	}
}

