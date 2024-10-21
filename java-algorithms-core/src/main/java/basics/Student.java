package basics;

public class Student {
    String name;
    String branch;
    int semester;

    /* Constructor*/
    public Student (String nm, String br, int sem) {
        this.name = nm;
        this.branch = br;
        this.semester = sem;
    }

    public String toString() {
        return "Name:"+name+" branch:"+branch+"  semester:"+semester;
    }

    public static void main (String[] args) {
        Student s1 = new Student("Vydya", "AI&ML", 3);
        Student s2 = new Student("Yajna", "CS", 1);
        System.out.println(s1.toString());
        System.out.println(s2.toString());
    }
}
