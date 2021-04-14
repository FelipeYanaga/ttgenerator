package org.acme.pda;

import org.acme.statements.SingleVarStatement;
import org.acme.statements.Statement;

import java.util.*;

public class Evaluator {
    public static int varNumber; //Number of vars

    /*
    Get a better name for this
    @return List with all of the permutations of T and F.
     */
    public static List<boolean []> getPerm(){
        varNumber = SingleVarStatement.VARS.size();
        Set<boolean[]> lines = new LinkedHashSet<>();

        //Set them equal to the two possible values
        // now multiply one by the other
        boolean[] line1 = new boolean[1];
        boolean[] line2 = new boolean[1];
        line1[0] = true;
        lines.add(line1);
        lines.add(line2); // don't need to set line2[0] to false, because it's default value

        for (int i = 1; i < varNumber; i++){
            Set<boolean []> oneSet = new LinkedHashSet<>(lines); //prevent concurrency
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

    public static List<boolean []> makeTable(PushDown pda){
        List<boolean []> lines = getPerm();
        Statement statement = pda.getMainStatement();
        List<boolean []> result = new ArrayList<>();
        for (boolean [] line : lines){
            SingleVarStatement.setValues(line);
            boolean [] currLine = Arrays.copyOf(line, line.length + 1);
            currLine[currLine.length - 1] = statement.evaluate();
            result.add(currLine);
        }
        return result;
    }

    public static String [] header(PushDown pda){
        String [] header = new String[SingleVarStatement.VARS.size() + 1]; //+ 1 because we are adding a statement at the end
        int i = 0;
        for (SingleVarStatement var : SingleVarStatement.VARS){
            header[i] = var.getString().trim();
            i += 1;
        }
        header[header.length - 1] = pda.getMainStatement().getString();
        return header;
    }

}
