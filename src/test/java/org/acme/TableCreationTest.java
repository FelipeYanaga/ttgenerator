//package org.acme;
//
//import org.acme.table.Evaluator;
//import org.acme.pda.PushDown;
//import org.acme.table.TruthTable;
//import org.acme.statements.SingleVarStatement;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Arrays;
//
//public class TableCreationTest {
//
//    private PushDown pda;
//
//
//    @Test
//    public void testHeader(){ //simplify process of making the automaton work JESUS
//        String s = "A and B";
//        pda = new PushDown(s);
//        String [] correct = new String[3];
//        correct[0] = "A";
//        correct[1] = "B";
//        correct[2] = "A and B";
//        assert(Arrays.equals(correct,Evaluator.header(pda)));
//    }

import org.junit.jupiter.api.Test;

//@Test
//public void testEvaluate(){
//        Statement statement = new SingleVarStatement("A");
//        Assertions.assertFalse(statement.evaluate());
//        }
//
//@Test
//public void testEvaluateAndFalse(){
//        pda = new PushDown("A and B");
//        Assertions.assertFalse((pda.getMainStatement().evaluate()));
//        }
//
//@Test
//public void testEvaluateAndTrue(){
//        pda = new PushDown("A and B");
//        SingleVarStatement.setValues(Evaluator.getTruthFalsePermutations().get(0));
//        assert((pda.getMainStatement().evaluate()));
//        }
//
//@Test
//public void testEvaluateNotTrue(){
//        pda = new PushDown("not A");
//        SingleVarStatement.setValues(Evaluator.getTruthFalsePermutations().get(0));
//        Assertions.assertFalse((pda.getMainStatement().evaluate()));
//        }
//
//}
