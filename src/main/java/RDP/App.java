package RDP;
import java.util.*;  
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException, InterruptedException
    {
        Scanner in=new Scanner(System.in); 
        System.out.println("***Phase1: Initialize***");
        petriNet r = new petriNet();

        System.out.println("***Phase2: Simulate each step***");
        while( r.isFranchissable() ) {
            r.doAllTransition();
            in.nextLine();
        }
        
        in.close();
    }
    
}
