package org.acme;

import org.acme.pda.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatementCreationTest {

    private PushDown pda;

    @BeforeEach
    void beforeEach(){
        this.pda = new PushDown();
    }

    @Test
    public void testSingleVar(){
        String word = "A";
        SingleVarStatement.setVars(pda.getVars("A"));
        pda.createStatements(word);
        SingleVarStatement var = new SingleVarStatement("A");
        assert(pda.getPlaceHolder().getClass() == var.getClass());
    }

    @Test
    public void testAndStatement(){
        String word = "A and B";
        SingleVarStatement.setVars(pda.getVars("A and B"));
        pda.createStatements(word);
        AndStatement statement = new AndStatement();
        assert(pda.getPlaceHolder().getClass() == statement.getClass());
    }

    @Test
    public void testOrStatement(){
        String word = "A or B";
        SingleVarStatement.setVars(pda.getVars("A or B"));
        pda.createStatements(word);
        OrStatement statement = new OrStatement();
        assert(pda.getPlaceHolder().getClass() == statement.getClass());
    }

    @Test
    public void testLongStatement(){
        String word = "A and B iff C";
        SingleVarStatement.setVars(pda.getVars("A and B iff C"));
        pda.createStatements(word);
        IffStatement statement = new IffStatement();
        assert(pda.getPlaceHolder().getClass() == statement.getClass());
    }

    @Test
    public void testNotStatement(){
        String word = "not A";
        SingleVarStatement.setVars(pda.getVars("not A"));
        pda.createStatements(word);
        NotStatement statement = new NotStatement();
        assert(pda.getPlaceHolder().getClass() == statement.getClass());
    }



}
