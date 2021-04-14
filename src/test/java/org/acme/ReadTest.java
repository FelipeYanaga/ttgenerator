package org.acme;

import org.acme.pda.PushDown;
import org.acme.pda.StackItems;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class ReadTest {


    @Test
    public void readInput(){
        PushDown pda = new PushDown("id and id");
        assert(pda.validInput());
    }

    @Test
    public void readInputSimpleP(){
        PushDown pda = new PushDown("(id and id)");
        assert(pda.validInput());
    }

    @Test
    public void pushParenthesis(){ //See everything through with the parenthesis stuff
        PushDown pda = new PushDown("(");
        pda.validInput();
        assert(pda.getAutomataStack().peek().equals(StackItems.PARENTHESIS));
    }

    @Test
    public void popParenthesis(){
        PushDown pda = new PushDown("(id)");
        assert(pda.validInput());
    }

    @Test
    public void readInputXor(){
        PushDown pda = new PushDown("(id xor id)");
        assert(pda.validInput());
    }

    @Test
    public void readNot(){
        PushDown pda = new PushDown("not (id or id)");
        assert(pda.validInput());
    }

    @Test
    public void readNotOperator(){
        PushDown pda = new PushDown("not (id iff not id)");
        assert(pda.validInput());
    }

    @Test
    public void readTwoParenthesis(){
        PushDown pda = new PushDown("(id or not id) iff (not id xor id)");
        assert(pda.validInput());
    }

    @Test
    public void testReadDoubleP(){
        PushDown pda = new PushDown("((id or not id))");
        assert (pda.validInput());
    }

    @Test
    public void testReadDouble(){
        PushDown pda = new PushDown("((id))");
        assert(pda.getAutomataStack().peek() == StackItems.EMPTY);
    }

    @Test
    public void readNestedP(){
        PushDown pda = new PushDown("(id or not (id and id))");
        assert (pda.validInput());
    }

    @Test
    public void testDoubleNot(){
        PushDown pda = new PushDown("not not id");
        assert(pda.validInput());
    }

    @Test
    public void testDoubleNotAndP(){
        PushDown pda = new PushDown("not (not id)");
        assert(pda.validInput());
    }

    @Test
    public void testDoubleNotAndPUnconventional(){
        PushDown pda = new PushDown("not (id and (id or id))");
        assert(pda.validInput());
    }

    @Test
    public void testDoublePTough(){
        PushDown pda = new PushDown("not (id and (id or id))");
        assert(pda.validInput());
    }

    @Test
    public void readTripleP(){
        PushDown pda = new PushDown("(((id)))");
        assert(pda.validInput());
    }

    @Test
    public void readVars(){
        PushDown pda = new PushDown("A and B and C");
        assert(pda.validInput());
    }

    @Test
    public void readInvalidInput(){
        PushDown pda = new PushDown("A and");
        Assertions.assertFalse(pda.validInput());
    }

    @Test
    public void readInvalidInputNot(){
        PushDown pda = new PushDown("not");
        Assertions.assertFalse(pda.validInput());
    }

    @Test
    public void readInvalidInputDoubleP(){
        PushDown pda = new PushDown("(A and B");
        Assertions.assertFalse(pda.validInput());
    }
}
