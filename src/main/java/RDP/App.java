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
            System.out.println("Please chosse mode: immediate,temporise or stochastique");
            System.exit(1);
        }

        switch (args[0]) {
            case "immediate":
                immediate(r);
                break;
            case "temporise":
                temporise(r);
                break;
            case "stochastique":
                stochastique(r);
                break;
            default:
                System.out.println("Spell Wrong.Please check: immediate,temporise or stochastique");
                break;
        }
               
        in.close();
    }

    // Use TP boulangerie Example
    private static void immediate(petriNet r) {
        System.out.println("***Phase2: Simulate each step***");
        while( r.isFranchissable() ) {
            r.doAllTransition(); // immediate
            in.nextLine();
        }
    }

    // Use TP boulangerie Example
    private static void temporise(petriNet r) {
        System.out.println("***Phase2: Simulate in time***");
        while( r.isFranchissable() ) {
            r.doTransitionInTime(); // temporise deterministe
            in.nextLine();
        }
    }

    // Use TP boulangerie Example
    private static void stochastique(petriNet r) {
        System.out.println("***Phase2: Simulate final state***");
        while (r.isFranchissable()) {
            r.stochastique(0.1); //lambda=0.1
            in.nextLine();
        }
    }

}
