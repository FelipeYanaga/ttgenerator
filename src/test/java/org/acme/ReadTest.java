package org.acme;

import org.acme.pda.AllVars;
import org.acme.pda.PushDown;
import org.acme.pda.StackItems;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ReadTest {

    private AllVars vars;

    @BeforeEach
    public void beforeEach(){
        vars = AllVars.of(("id and A and B and C"));
    }


    @Test
    public void readInput(){ // if input is invalid, then the main statement is null
        PushDown pda = new PushDown("A and ", vars);
        assert(pda.getMainStatement() == null);
    }

    @Test
    public void readInputSimpleP(){
        PushDown pda = new PushDown("(id and id)", vars);
        assert(pda.isAccepted());
    }

    @Test
    public void pushParenthesis(){ //See everything through with the parenthesis stuff
        PushDown pda = new PushDown("(",vars);
        assert(pda.getAutomataStack().peek().equals(StackItems.PARENTHESIS));
    }

    @Test
    public void popParenthesis(){
        PushDown pda = new PushDown("(id)",vars);
        assert(pda.isAccepted());
    }

    @Test
    public void readInputXor(){
        PushDown pda = new PushDown("(id xor id)",vars);
        assert(pda.isAccepted());
    }

    @Test
    public void readNot(){
        PushDown pda = new PushDown("not (id or id)",vars);
        assert(pda.isAccepted());
    }

    @Test
    public void readNotOperator(){
        PushDown pda = new PushDown("not (id iff not id)",vars);
        assert(pda.isAccepted());
    }

    @Test
    public void readTwoParenthesis(){
        PushDown pda = new PushDown("(id or not id) iff (not id xor id)",vars);
        assert(pda.isAccepted());
    }

    @Test
    public void testReadDoubleP(){
        PushDown pda = new PushDown("((id or not id))",vars);
        assert (pda.isAccepted());
    }

    @Test
    public void testReadDouble(){
        PushDown pda = new PushDown("((id))",vars);
        assert(pda.getAutomataStack().peek() == StackItems.EMPTY);
    }

    @Test
    public void readNestedP(){
        PushDown pda = new PushDown("(id or not (id and id))",vars);
        assert (pda.isAccepted());
    }

    @Test
    public void testDoubleNot(){
        PushDown pda = new PushDown("not not id",vars);
        assert(pda.isAccepted());
    }

    @Test
    public void testDoubleNotAndP(){
        PushDown pda = new PushDown("not (not id)",vars);
        assert(pda.isAccepted());
    }

    @Test
    public void testDoubleNotAndPUnconventional(){
        PushDown pda = new PushDown("not (id and (id or id))",vars);
        assert(pda.isAccepted());
    }

    @Test
    public void testDoublePTough(){
        PushDown pda = new PushDown("not (id and (id or id))", vars);
        assert(pda.isAccepted());
    }

    @Test
    public void readTripleP(){
        PushDown pda = new PushDown("(((id)))", vars);
        assert(pda.isAccepted());
    }

    @Test
    public void readVars(){
        PushDown pda = new PushDown("A and B and C", vars);
        assert(pda.isAccepted());
    }

    @Test
    public void readInvalidInput(){
        PushDown pda = new PushDown("A and", vars);
        Assertions.assertFalse(pda.isAccepted());
    }

    @Test
    public void readInvalidInputNot(){
        PushDown pda = new PushDown("not", vars);
        Assertions.assertFalse(pda.isAccepted());
    }

    @Test
    public void readInvalidInputDoubleP(){
        PushDown pda = new PushDown("(A and B", vars);
        Assertions.assertFalse(pda.isAccepted());
    }
}
