package org.acme.processing;

import org.acme.pda.*;
import org.acme.statements.SingleVarStatement;
import org.acme.table.BooleanLine;
import org.acme.table.Evaluator;
import org.acme.table.TruthTable;

import java.util.*;

public class Testing {

    public static void main(String[] args) { //make this a test
        TruthTable table = new TruthTable("A and B and C");
        Evaluator evaluator = Evaluator.of(table);
        for (BooleanLine line : evaluator.getTruthFalsePermutations()){
            System.out.println(Arrays.toString(line.getValues()));
        }
        boolean[] test = {true, true, true};
        boolean[] test1 = {true, true, false};

        BooleanLine line1 = BooleanLine.of(test);
        BooleanLine line2 = BooleanLine.of(test1);

        System.out.println(line1.equals(line2));
        System.out.println(line1.hashCode() == line2.hashCode());
    }
}


