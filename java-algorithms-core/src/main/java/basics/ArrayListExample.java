package basics;
import java.util.ArrayList;
public class ArrayListExample {

    public static void main(String[] args) {

        ArrayList arrayList1 = new ArrayList(4);
        arrayList1.add("Violet");
        arrayList1.add("Indigo");
        arrayList1.add("Blue");
        arrayList1.add("Green");
        arrayList1.add("Green");

        ArrayList arrayList2 = new ArrayList(arrayList1);
        arrayList2.add("Red");

        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("Violet");
        arrayList3.add("Indigo");
        arrayList3.add("Blue");

        System.out.println("arrrayList1:"+arrayList1);
        System.out.println("arrrayList2:"+arrayList2);
        System.out.println("arrrayList3:"+arrayList3);

    }

}
