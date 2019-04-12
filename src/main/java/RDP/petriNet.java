package RDP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * petriNet
 */
public class petriNet {
    /**
     *  General variables
     */
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

    /**
     *  Temporise variables
     */
    int time = 0;
    int infini = 9999;
    int[] transitionDuration={5,3,1};
    int[] finishTimes=new int[col+1];
    boolean isFirstTime=true;
    
    petriNet(){
        // calculate W[]
        for (int i = 0; i <= this.line; i++) {
            for (int j = 0; j <= this.col; j++) {
                this.w[i][j]=this.post[i][j]-this.pre[i][j];
            }
        }
        // initialize finishTimes[]
        for (int j = 0; j <= this.col; j++) {
            this.finishTimes[j]=this.infini;
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
        if (!this.transitionFranchissableIndex.contains(transitionIndex)){
            this.transitionFranchissableIndex.add(transitionIndex);
        }
        return true;
    }

    public void doTransition(Integer transitionIndex) {
        for (int i = 0; i <= this.line; i++) {
            this.m[i] += this.w[i][transitionIndex];
        }
    }

 /**
 *  Immediate major function
 */
	public void doAllTransition() {
        Iterator<Integer> iter = this.transitionFranchissableIndex.iterator();
        while (iter.hasNext()) {
            int transitionIndex = iter.next();
            this.doTransition(transitionIndex);
            iter.remove();
        }
        System.out.println("New M="+Arrays.toString (this.m));
	}

/**
 *  Temporise major function
 */
	public void doTransitionInTime() {
        calculateFinishTime();
        this.time = minValueInArray(this.finishTimes);
        // there may be serveral minimal value
        ArrayList<Integer> minTransitions = new ArrayList<Integer>();
        minTransitions = minIndexesInArray(this.finishTimes);
        for (Integer whichTransition : minTransitions) {
            doTransition(whichTransition); //min index in finishTimes[] == index of transition to do
            this.transitionFranchissableIndex.remove(whichTransition);
            this.finishTimes[whichTransition]=this.infini;
        }
    
        System.out.println("t | TD |  P[line]  |  Td[col]"); // TD=Transition has been done
        System.out.println(this.time+" | "+minTransitions.toString()+" | "+Arrays.toString (this.m)+" | "+Arrays.toString (this.finishTimes));
	}

    private void calculateFinishTime() {
        // reset to now
        for (int fIndex : this.transitionFranchissableIndex) {
            if (this.finishTimes[fIndex]==this.infini) {
                this.finishTimes[fIndex] = this.time;
            }
        }

        if (this.isFirstTime) {
            this.isFirstTime = false;
            for (int fIndex : this.transitionFranchissableIndex) {
                this.finishTimes[fIndex]+= this.transitionDuration[fIndex];
            }
        }

        for (int fIndex : this.transitionFranchissableIndex) {
            if (this.time == this.finishTimes[fIndex]) {
                this.finishTimes[fIndex]+= this.transitionDuration[fIndex];
            }
        }
        
        System.out.println("finishTimes="+Arrays.toString (this.finishTimes));
    }

    private ArrayList<Integer> minIndexesInArray(int[] arr) {
        ArrayList<Integer> minTransitions = new ArrayList<Integer>();
        int min = arr[0];
        for (int index = 0; index < arr.length; index++) {
            if (arr[index]<min) { min=arr[index];}
        }
        for (int index = 0; index < arr.length; index++) {
            if (arr[index]==min) { minTransitions.add(index);}
        }
        return minTransitions;
    }

    private int minValueInArray(int[] arr) {
        int min = this.infini;
        for (int value : arr) {
            min = min>value? value:min;
        }
        return min;
    }

    /**
     *  Temporise stochastique major function
     */
	public void stochastique(double lambda) {
        calculateFinishTime();
        this.time = minValueInArray(this.finishTimes);
        ArrayList<Integer> minTransitions = new ArrayList<Integer>();
        minTransitions = minIndexesInArray(this.finishTimes);
        for (Integer whichTransition : minTransitions) {
            // difference with temporise
            if (this.isPossibleToDo(lambda)) {
                System.out.println("Do: T"+whichTransition);
                doTransition(whichTransition);
            }
            else {
                System.out.println("Not do: T"+whichTransition);
            }
            this.transitionFranchissableIndex.remove(whichTransition);
            this.finishTimes[whichTransition]=this.infini;
        }
        System.out.println("t |  P[line]  |  Td[col]");
        System.out.println(this.time+" | "+Arrays.toString (this.m)+" | "+Arrays.toString (this.finishTimes));
	}

    boolean isPossibleToDo(double lambda){
        /**
         * p obey the index distribution(loi expotentielle)
         * In time t，the possibility to do
         * 0<=p<=1
         */
        double p = 1 - Math.exp(-1.0 * lambda * this.time);
        Random random = new Random();
        double ref = random.nextDouble(); // ref is random to compare with p
        System.out.println("In time:"+this.time+",  p="+p+"  |  ref="+ref);
        return p>ref?true:false;
    }
}