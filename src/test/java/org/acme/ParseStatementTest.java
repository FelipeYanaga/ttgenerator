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
    }

    @Test
    public void testRegularInput(){
        this.pda = new PushDown("A and B");
        Statement statement = new AndStatement();
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputNot(){
        this.pda = new PushDown("not A and B");
        Statement statement = new AndStatement();
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputNotOnly(){
        this.pda = new PushDown("not A");
        Statement statement = new NotStatement();
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputVarOnly(){
        this.pda = new PushDown("A");
        Statement statement = new SingleVarStatement("a");
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testRegularInputLong(){
        this.pda = new PushDown("A and B or C");
        Statement statement = new OrStatement();
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisNormal(){
        this.pda = new PushDown("(A and B)");
        Statement statement = new AndStatement();
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisNot(){
        this.pda = new PushDown("not (A and B)");
        Statement statement = new NotStatement();
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisLong(){
        this.pda = new PushDown("(A and B) iff (B or C)");
        Statement statement = new IffStatement();
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisDouble(){
        this.pda = new PushDown("((A and B) iff (B or C))");
        Statement statement = new IffStatement();
        assert(pda.getMainStatement().getClass() == statement.getClass());
    }

    @Test
    public void testParenthesisTriple(){
        this.pda = new PushDown("((not (A and B) iff (A and (B and C)))");
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
        pda = new PushDown("A and B");
        Assertions.assertFalse((pda.getMainStatement().evaluate()));
    }

    @Test
    public void testEvaluateAndTrue(){
        pda = new PushDown("A and B");
        SingleVarStatement.setValues(Evaluator.getPerm().get(0));
        assert((pda.getMainStatement().evaluate()));
    }

    @Test
    public void testEvaluateNotTrue(){
        pda = new PushDown("not A");
        SingleVarStatement.setValues(Evaluator.getPerm().get(0));
        Assertions.assertFalse((pda.getMainStatement().evaluate()));
    }





}
