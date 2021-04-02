package org.acme;

import org.acme.pda.PushDown;
import org.acme.pda.StackItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReadTest {

    private PushDown pushDown;

    @BeforeEach
    void before() {
        this.pushDown = new PushDown();
    }

    @Test
    public void readInput(){
        PushDown pda = new PushDown();
        assert(pda.validInput("id and id"));
    }

    @Test
    public void readInputSimpleP(){
        PushDown pda = new PushDown();
        assert(pda.validInput("(id and id)"));
    }

    @Test
    public void pushParenthesis(){ //See everything through with the parenthesis stuff
        PushDown pda = new PushDown();
        pda.validInput("(");
        assert(pda.getAutomataStack().peek().equals(StackItems.PARENTHESIS));
    }

    @Test
    public void popParenthesis(){
        PushDown pda = new PushDown();
        assert(pda.validInput("(id)"));
    }

    @Test
    public void readInputXor(){
        PushDown pda = new PushDown();
        assert(pda.validInput("(id xor id)"));
    }

    @Test
    public void readNot(){
        PushDown pda = new PushDown();
        assert(pda.validInput("not (id or id)"));
    }

    @Test
    public void readNotOperator(){
        PushDown pda = new PushDown();
        assert(pda.validInput("not (id iff not id)"));
    }

    @Test
    public void readTwoParenthesis(){
        assert(pushDown.validInput("(id or not id) iff (not id xor id)"));
    }

    @Test
    public void testReadDoubleP(){
        assert (pushDown.validInput("((id or not id))"));
    }

    @Test
    public void testReadDouble(){
        pushDown.validInput("((id))");
        assert(pushDown.getAutomataStack().peek() == StackItems.EMPTY);
    }

    @Test
    public void readNestedP(){
        assert (pushDown.validInput("(id or not (id and id))"));
    }

    @Test
    public void testDoubleNot(){
        assert(pushDown.validInput("not not id"));
    }

    @Test
    public void testDoubleNotAndP(){
        assert(pushDown.validInput("not (not id)"));
    }

    @Test
    public void testDoubleNotAndPUnconventional(){
        assert(pushDown.validInput("not (id and (id or id))"));
    }

    @Test
    public void testDoublePTough(){
        assert(pushDown.validInput("not (id and (id or id))"));
    }

    @Test
    public void readTripleP(){
        assert(pushDown.validInput("(((id)))"));
    }
}
