package algos.dp;

public class TowersOfHanoi {

   public void solve(int n, String start, String auxiliary, String end) {
       if (n == 1) {
           System.out.println("Disk 1 is moving from "+start + " -> " + end);
       } else {
           solve(n - 1, start, end, auxiliary);
           System.out.println("Disk "+n+" is moving from "+start + " -> " + end);
           solve(n - 1, auxiliary, start, end);
       }
   }

   public static void main(String[] args) {
       TowersOfHanoi towersOfHanoi = new TowersOfHanoi();
       int discs = 5;
       towersOfHanoi.solve(discs, "A", "B", "C");
   }
}