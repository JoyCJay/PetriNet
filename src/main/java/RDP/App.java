package RDP;
import java.util.*;  
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws IOException, InterruptedException
    {
        
        System.out.println("***Phase1: Initialize***");
        petriNet r = new petriNet();

        if (args.length==0) {
            System.out.println("Please chosse mode: deterministe,temporise or stochastique");
            System.exit(1);
        }

        switch (args[0]) {
            case "deterministe":
                deterministe(r);
                break;
            case "temporise":
                temporise(r);
                break;
            default:
                System.out.println("Spell Wrong.");
                break;
        }
               
        in.close();
    }

    private static void deterministe(petriNet r) {
        System.out.println("***Phase2: Simulate each step***");
        while( r.isFranchissable() ) {
            r.doAllTransition(); // deterministe
            in.nextLine();
        }
    }

    
    private static void temporise(petriNet r) {
        System.out.println("***Phase2: Simulate in time***");
        while( r.isFranchissable() ) {
            r.doTransitionInTime(); // temporise
            in.nextLine();
        }
    }

}
