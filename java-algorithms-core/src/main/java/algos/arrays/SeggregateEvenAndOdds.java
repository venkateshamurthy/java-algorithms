package algos.arrays;

import static algos.utils.Utils.swap;

public class SeggregateEvenAndOdds {
    final int[] data;
    public SeggregateEvenAndOdds(int[] data) {
         this.data = data;
    }
    public void seggregateEvenAndOdds() {
        int i=0,j=data.length-1 ;
        while(i<j) {
            if (data[i] % 2 == 0) {
                i++;
            } else  if (data[j] % 2 != 0){
                j--;
            } else {
                swap(data, i++, j--);
            }
        }
    }
}
