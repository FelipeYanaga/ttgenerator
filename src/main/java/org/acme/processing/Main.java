package org.acme.processing;

import org.acme.pda.*;
import org.acme.statements.SingleVarStatement;

import java.util.*;

public class Main {

    public static void main(String[] args) {
//        PushDown pda = new PushDown();
//        SingleVarStatement.setVars(pda.getVars("A iff B"));
//        pda.setInput("A if B");
//        pda.createMainStatement();
//        List<boolean []> results = pda.evaluate();
//        for (boolean[] line : results){
//            System.out.println(Arrays.toString(line));
//        }
        PushDown pda = new PushDown();
        String s = "A and B";
        SingleVarStatement.setVars(pda.getVars(s));
        pda.setInput(s);
        pda.parseStatement();
        pda.createMainStatement();
        String [] correct = new String[3];
        correct[0] = "A";
        correct[1] = "B";
        correct[2] = "A and B";
        System.out.println(Arrays.toString(correct));
        System.out.println(Arrays.toString(Evaluator.header(pda)));

    }
}


