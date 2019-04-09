package RDP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * petriNet
 */
public class petriNet {

    int line=2;
    int col=2;

    int[][] pre = {
        {0,0,2},
        {0,0,1},
        {0,0,0}
    };
    int[][] post = {
        {1,0,0},
        {0,1,0},
        {0,0,1}
    };
    int[][] w = new int[line+1][col+1];

    int[]m = {1,1,0};

    List<Integer> transitionFranchissableIndex=new ArrayList<Integer>();

    petriNet(){
        for (int i = 0; i <= this.line; i++) {
            for (int j = 0; j <= this.col; j++) {
                this.w[i][j]=this.post[i][j]-this.pre[i][j];
            }
        }
        displayArray();
    }

    
    public void displayArray() {
        System.out.println("Pre:");
         for (int i = 0; i <=this.line; i++) {
             System.out.println ( Arrays.toString (this.pre[i]));
         }

         System.out.println("Post:");
         for (int i = 0; i <=this.line; i++) {
             System.out.println ( Arrays.toString (this.post[i]));
         }

         System.out.println("W:");
         for (int i = 0; i <=this.line; i++) {
             System.out.println ( Arrays.toString (this.w[i]));
         }
         System.out.println("M:");
         System.out.println(Arrays.toString (this.m));
    }

    boolean isFranchissable() {
        int nbFranchissable=0;
        for (int j = 0; j <= this.col; j++) {
            if (this.isFranchisableForTransition(j)) { nbFranchissable++; }
        }
        System.out.println("Franchissable transsition index:"+this.transitionFranchissableIndex.toString());
        return nbFranchissable>0? true:false;
    }

    boolean isFranchisableForTransition(int transitionIndex) {
        for (int i = 0; i <= this.line; i++) {
            if (this.m[i]<this.pre[i][transitionIndex]) {
                return false;
            }
        }
        this.transitionFranchissableIndex.add(transitionIndex);
        return true;
    }

	public void doAllTransition() {
        Iterator<Integer> iter = this.transitionFranchissableIndex.iterator();
        while (iter.hasNext()) {
            int transitionIndex = iter.next();
            this.doTransition(transitionIndex);
            iter.remove();
        }
        System.out.println("New M="+Arrays.toString (this.m));
	}

    public void doTransition(Integer transitionIndex) {
        for (int i = 0; i <= this.line; i++) {
            this.m[i] += this.w[i][transitionIndex];
        }
    }
}