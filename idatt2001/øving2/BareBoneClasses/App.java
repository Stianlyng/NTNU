public class App {

    public static void main(String[] args) {
        Student s2 = new Student("John", 20, "Computer Science");
        Person p1 = new Student("John", 20, "Computer Science");
        Person p2 = new PhDStudent("John", 20, "Computer Science", "frank");
        PhDStudent phd1 = new Student("John", 20, "Computer Science");
        //Teacher t1 = new Person("Stian", 0);
        //Student s1 = new PhDStudent("John", 20, "Computer Science", "frank");
        System.out.println(s2.toString());
    }
    
}
