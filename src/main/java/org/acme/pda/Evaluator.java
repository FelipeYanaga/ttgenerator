package org.acme.pda;

import org.acme.statements.SingleVarStatement;

import java.util.*;

public class Evaluator {
    public static int varNumber = SingleVarStatement.VARS.size(); //Number of vars

    /*
    Get a better name for this
    @return List with all of the permutations of T and F.
     */
    public static List<boolean []> getPerm(){
        Set<boolean []> lines = new HashSet<>();

        //Set them equal to the two possible values
        // now multiply one by the other
        boolean[] line1 = new boolean[1];
        boolean[] line2 = new boolean[1];
        line1[0] = true;
        lines.add(line1);
        lines.add(line2); // don't need to set line2[0] to false, because it's default value

        for (int i = 1; i < varNumber; i++){
            Set<boolean []> oneSet = new HashSet<>(lines); //prevent concurrency
            for (boolean [] line : lines){
                boolean[] nextLine = Arrays.copyOf(line, i + 1); //add one to size, b/c longer statement
                nextLine[i] = true;
                oneSet.add(nextLine);
                boolean[] otherLine = Arrays.copyOf(line, i + 1);
                otherLine[i] = false;
                oneSet.add(otherLine);
            }
            lines = oneSet; //update lines
        }

        lines.removeIf(o -> (o.length != varNumber)); //take out all leftovers

        return new ArrayList<>(lines); //Return an array because it's easier to deal with
    }

}
