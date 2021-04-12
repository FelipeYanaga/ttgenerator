package org.acme;

import org.acme.pda.Evaluator;
import org.acme.pda.PushDown;
import org.acme.processing.TruthTable;
import org.acme.statements.SingleVarStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TableCreationTest {

    private PushDown pda;

    @BeforeEach
    public void beforeEach(){
        this.pda = new PushDown();
    }


    @Test
    public void testHeader(){ //simplify process of making the automaton work JESUS
        String s = "A and B";
        SingleVarStatement.setVars(pda.getVars(s));
        pda.setInput(s);
        pda.parseStatement();
        pda.createMainStatement();
        String [] correct = new String[3];
        correct[0] = "A";
        correct[1] = "B";
        correct[2] = "A and B";
        assert(Arrays.equals(correct,Evaluator.header(pda)));
    }

}
