public class A
 {
     private int i;
     
     public void display()
     {
         System.out.println(i);
     }
     
     public void setI(int i)
     {
         this.i = i;
     }
 }
 
 
 class B extends A
 {
     private int j;
     
     public void display()
     {
         System.out.println(j);
     }
     
     public void setJ(int j)
     {
         this.j = j;
     }
     
 }
 
 class inheritanceDemo
 {
     public static void main(String[] args)
     {
         B obj = new B();
         
         obj.setI(1);
         obj.setJ(2);
         obj.display();
     }
 }