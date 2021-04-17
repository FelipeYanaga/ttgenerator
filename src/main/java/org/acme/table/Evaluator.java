package org.acme.table;

import org.acme.pda.AllVars;
import org.acme.pda.PushDown;
import org.acme.pda.SetOfVars;
import org.acme.statements.SingleVarStatement;
import org.acme.statements.Statement;

import java.util.*;
import java.util.stream.Collectors;

public class Evaluator {
    private final TruthTable table;

    private Evaluator(TruthTable table){
        this.table = table;
    }

    public static Evaluator of(TruthTable table){
        return new Evaluator(table);
    }

    /*
    Get a better name for this
    @return List with all of the permutations of T and F.
     */
    public List<BooleanLine> getTruthFalsePermutations(){
//        varNumber = SingleVarStatement.VARS.size();
        Set<boolean[]> lines = new LinkedHashSet<>();
        AllVars vars = table.getAllVars();

        //Set them equal to the two possible values
        // now multiply one by the other
        boolean[] line1 = new boolean[1];
        boolean[] line2 = new boolean[1];
        line1[0] = true;
        lines.add(line1);
        lines.add(line2); // don't need to set line2[0] to false, because it's default value

        for (int i = 1; i < vars.size(); i++){
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

        lines.removeIf(o -> (o.length != vars.size())); //take out all leftovers

        List<BooleanLine> returnArray = new ArrayList<>();
        for (boolean [] line : lines){
            returnArray.add(BooleanLine.of(line));
        }

        return returnArray;
    }

    public List<BooleanLine> getLines(){
        List<BooleanLine> lines = getTruthFalsePermutations();
        List<BooleanLine> result = new ArrayList<>();
        AllVars vars = table.getAllVars();
        Statement[] statements = table.getStatements();
        for (BooleanLine line : lines){
            vars.setValues(line.getValues());
            for (Statement statement : statements){
                boolean [] currLine = Arrays.copyOf(line.getValues(),line.size() + 1);
                currLine[currLine.length - 1] = statement.evaluate();
                line = BooleanLine.of(currLine);
            }
            result.add(line);
        }
        return result;
    }


}
