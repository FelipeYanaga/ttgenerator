package org.acme;

import org.acme.pda.*;
import org.acme.statements.*;
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
        pda.setInput("A and B");
        Statement statement = new AndStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputNot(){
        pda.setInput("not A and B");
        Statement statement = new AndStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputNotOnly(){
        pda.setInput("not A");
        Statement statement = new NotStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputVarOnly(){
        pda.setInput("A");
        Statement statement = new SingleVarStatement("a");
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputLong(){
        pda.setInput("A and B or C");
        Statement statement = new OrStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisNormal(){
        pda.setInput("(A and B)");
        Statement statement = new AndStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisNot(){
        pda.setInput("not (A and B)");
        Statement statement = new NotStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisLong(){
        pda.setInput("(A and B) iff (B or C)");
        Statement statement = new IffStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisDouble(){
        pda.setInput("((A and B) iff (B or C))");
        Statement statement = new IffStatement();
        assert(pda.parseStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisTriple(){
        pda.setInput("((not (A and B) iff (A and (B and C)))");
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
        pda.setInput("A and B");
        Assertions.assertFalse((pda.parseStatement().evaluate()));
    }

    @Test
    public void testEvaluateAndTrue(){
        pda.setInput("A and B");
        SingleVarStatement.setVars(pda.getVars("A and B"));
        SingleVarStatement.setValues(Evaluator.getPerm().get(0));
        assert((pda.parseStatement().evaluate()));
    }

    @Test
    public void testEvaluateNotTrue(){
        pda.setInput("not A");
        SingleVarStatement.setVars(pda.getVars("A"));
        SingleVarStatement.setValues(Evaluator.getPerm().get(0));
        Assertions.assertFalse((pda.parseStatement().evaluate()));
    }





}
