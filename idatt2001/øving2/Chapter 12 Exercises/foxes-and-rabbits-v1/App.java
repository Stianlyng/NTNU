public class App {
    
    public static void main(String[] args) {
        //Simulator s = new Simulator();
        Simulator s = new Simulator(100, 100);
        s.simulate(400);
        
        Simulator a = new Simulator(50, 50);
        a.simulate(400);

        Simulator b = new Simulator(50, 50);
        b.simulate(400);
        //s.runLongSimulation();
     }
}

//      depth:50, width:50         depth: 100, width: 100









//      depth:50, width:50