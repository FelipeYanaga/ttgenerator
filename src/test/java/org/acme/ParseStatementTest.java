package org.acme;

import org.acme.pda.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParseStatementTest {

    private PushDown pda;

    @BeforeEach
    void beforeEach(){
        this.pda = new PushDown();
        SingleVarStatement.setVars(pda.getVars("A and B and C"));
    }

    @Test
    public void testRegularInput(){
        pda.createParser("A and B");
        Statement statement = new AndStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputNot(){
        pda.createParser("not A and B");
        Statement statement = new AndStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputNotOnly(){
        pda.createParser("not A");
        Statement statement = new NotStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputVarOnly(){
        pda.createParser("A");
        Statement statement = new SingleVarStatement("a");
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputLong(){
        pda.createParser("A and B or C");
        Statement statement = new OrStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisNormal(){
        pda.createParser("(A and B)");
        Statement statement = new AndStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisNot(){
        pda.createParser("not (A and B)");
        Statement statement = new NotStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisLong(){
        pda.createParser("(A and B) iff (B or C)");
        Statement statement = new IffStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisDouble(){
        pda.createParser("((A and B) iff (B or C))");
        Statement statement = new IffStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisTriple(){
        pda.createParser("((not (A and B) iff (A and (B and C)))");
        Statement statement = new IffStatement();
        assert(pda.parseStatement() instanceof IffStatement);
    }

    @Test
    public void testEvaluate(){
        Statement statement = new SingleVarStatement("A");
        Assertions.assertFalse(statement.evaluate());
    }

    @Test
    public void testEvaluateAndFalse(){
        pda.createParser("A and B");
        Assertions.assertFalse((pda.parseStatement().evaluate()));
    }

    @Test
    public void testEvaluateAndTrue(){
        pda.createParser("A and B");
        SingleVarStatement.setValues();
        assert((pda.parseStatement().evaluate()));
    }




}
