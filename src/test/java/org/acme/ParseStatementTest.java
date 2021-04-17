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
        AllVars vars = AllVars.of("A and B");
        this.pda = new PushDown("A and B", vars);;
        assert(pda.getMainStatement() instanceof AndStatement);
    }

    @Test
    public void testRegularInputNot(){
        AllVars vars = AllVars.of("not A and B");
        this.pda = new PushDown("not A and B", vars);
        assert(pda.getMainStatement() instanceof AndStatement);
    }

    @Test
    public void testRegularInputNotOnly(){
        AllVars vars = AllVars.of("not A");
        this.pda = new PushDown("not A", vars);
        assert(pda.getMainStatement() instanceof NotStatement);
    }

    @Test
    public void testRegularInputVarOnly(){
        AllVars vars = AllVars.of(" A ");
        this.pda = new PushDown("A ", vars);
        assert(pda.getMainStatement() instanceof SingleVarStatement);
    }

    @Test
    public void testRegularInputLong(){
        AllVars vars = AllVars.of("A and B or C");
        this.pda = new PushDown("A and B or C", vars);
        assert(pda.getMainStatement() instanceof OrStatement);
    }

    @Test
    public void testParenthesisNormal(){
        String s = "(A or B)";
        AllVars vars = AllVars.of(s);
        this.pda = new PushDown(s, vars);
        assert(pda.getMainStatement() instanceof OrStatement);
    }

    @Test
    public void testParenthesisNot(){
        String s = "not (A or B)";
        AllVars vars = AllVars.of(s);
        this.pda = new PushDown(s, vars);
        assert(pda.getMainStatement() instanceof NotStatement);
    }

    @Test
    public void testParenthesisLong(){
        String s = "(A or B) iff (B and C)";
        AllVars vars = AllVars.of(s);
        this.pda = new PushDown(s, vars);
        assert(pda.getMainStatement() instanceof IffStatement);
    }

    @Test
    public void testParenthesisDouble() {
        String s = "((A and B) iff (B or C))";
        AllVars vars = AllVars.of(s);
        this.pda = new PushDown(s, vars);
        assert(pda.getMainStatement() instanceof IffStatement);
    }


    @Test
    public void testParenthesisTriple(){
        String s = "((not (A and B)) iff (A and (B and C)))";
        AllVars vars = AllVars.of(s);
        this.pda = new PushDown(s, vars);
        assert(pda.getMainStatement() instanceof IffStatement);
    }




}
