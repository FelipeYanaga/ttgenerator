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
        Set<BooleanLine> lines = new LinkedHashSet<>();
        AllVars vars = table.getAllVars();

        //Set them equal to the two possible values
        // now multiply one by the other
        boolean[] line1 = new boolean[1];
        boolean[] line2 = new boolean[1];
        line1[0] = true;
        BooleanLine boolLine1 = BooleanLine.of(line1);
        BooleanLine boolLine2 = BooleanLine.of(line2);
        lines.add(boolLine1);
        lines.add(boolLine2); // don't need to set line2[0] to false, because it's default value

        for (int i = 1; i < vars.size(); i++){
            Set<BooleanLine> oneSet = new LinkedHashSet<>(lines); //prevent concurrency
            for (BooleanLine line : lines){
                boolean[] nextLine = Arrays.copyOf(line.getValues(), i + 1); //add one to size, b/c longer statement
                nextLine[i] = true;
                oneSet.add(BooleanLine.of(nextLine));
                boolean[] otherLine = Arrays.copyOf(line.getValues(), i + 1);
                otherLine[i] = false;
                oneSet.add(BooleanLine.of(otherLine));
            }
            lines = oneSet; //update lines
        }

        lines.removeIf(o -> (o.getValues().length != vars.size())); //take out all leftovers

        return new ArrayList<>(lines);

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
