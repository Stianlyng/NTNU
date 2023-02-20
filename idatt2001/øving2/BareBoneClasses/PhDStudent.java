public class PhDStudent extends Student {
    
    private String advisor;

    public PhDStudent(String name, int age, String major,String advisor) {
        super(name, age, major);
        this.advisor = advisor;
    }

    public String getAdvisor() { return advisor; }
}
