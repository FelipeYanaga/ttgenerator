package org.acme;

import org.acme.pda.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class NLPTest {

    @Test
    public void splitMethod(){
        String input = "A iff B, A and B";
        String[] expected = {"A iff B", " A and B"};
        assert(Arrays.equals(expected, input.split(",")));
    }

    @Test
    public void splitAndTrim(){
        String input = "A iff B, A and B";
        String[] expected = {"A iff B", "A and B"};
        String [] splitInput = input.split(",");
        for (int i = 0; i < splitInput.length; i++) {
            splitInput[i] = splitInput[i].trim();
        }
        assert(Arrays.equals(expected, splitInput));
    }

    @Test
    public void splitStatement(){
        String statement = "not A and not B";
        String[] splitStatement = statement.split("\\s");
        String[] expected = {"not","A","and","not","B"};
        assert(Arrays.equals(splitStatement, expected));
    }

    @Test
    public void getVars(){
        String statement = "not A and not B";
        String[] splitStatement = statement.split("\\s");
        Set<String> operatorSet = new HashSet<>();
        operatorSet.add("not");
        operatorSet.add("and");
        operatorSet.add("iff");
        operatorSet.add("or");
        operatorSet.add("if");
        operatorSet.add("xor");
        List<String> statementOperators = new ArrayList<>();
        for (String s : splitStatement) {
            if (operatorSet.contains(s)) {
                statementOperators.add(s);
            }
        }
        String[] expected = {"not","and","not"};
        assert(Arrays.equals(expected, statementOperators.toArray()));
    }

    @Test
    public void containsInput(){
        Input input = Input.formInput(State.START,Operator.NOT_SET, StackItems.EMPTY);
        assert(Transition.containsInput(input));
    }

    @Test
    public void containsInputFalse(){
        Input input = Input.formInput(State.START, Operator.NON_NOT_OPERATORS, StackItems.PARENTHESIS);
        Assertions.assertFalse(Transition.containsInput(input));
    }

    @Test
    public void containsTransitionTrue(){
        assert(Transition.contains(Transition.START_ID));
    }

    @Test
    public void getTransition(){
        Input input = Input.formInput(State.START, PushDown.VARS, StackItems.EMPTY);
        assert(Transition.START_ID == Transition.getTransition(input));
    }

    @Test
    public void equals(){
        Input input = Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY);
        assert(Transition.START_ID.equals(Transition.getTransition(input)));
    }

    @Test
    public void inputEqualityRightSide(){
        Input input = Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY);
        assert(input.equals(Transition.START_ID.getBehavior().getInput()));
    }

    @Test
    public void inputEqualityLeftSide(){
        Input input = Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY);
        assert (input.equals(input1));
    }

    @Test
    public void inputEqualityOtherSide(){
        Input input = Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY);
        assert (input1.equals(input));
    }

    @Test
    public void inputEqualityFalse(){
        Input input = Input.formInput(State.ID,PushDown.VARS,StackItems.EMPTY);
        Input input1 = Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY);
        Assertions.assertFalse(input.equals(input1));
    }

    @Test
    public void objectEquality(){
        Input input = Input.formInput(State.ID,PushDown.VARS,StackItems.EMPTY);
        Input input1 = Input.formInput(State.ID,PushDown.VARS,StackItems.EMPTY);
        assert(input.equals(input1));
    }

    @Test
    public void readInputTest(){
        String s = "A and B";
    }

    @Test
    public void pushItem(){
        Stack<StackItems> stack = new Stack<>();
        stack.push(Transition.PARENTHESIS_OPEN_E.getBehavior().getOutput().getItem());
        assert(stack.peek().equals(StackItems.PARENTHESIS));
    }

    @Test
    public void popItem(){
        Stack<StackItems> stack = new Stack<>();
        stack.push(StackItems.EMPTY);
        stack.push(Transition.PARENTHESIS_OPEN_E.getBehavior().getOutput().getItem());
        stack.pop();
        assert(stack.peek().equals(StackItems.EMPTY));
    }

    @Test
    public void InputHashTrue(){
        assert(Input.formInput(State.START, PushDown.VARS, StackItems.EMPTY).hashCode() ==
                Input.formInput(State.START,PushDown.VARS,StackItems.EMPTY).hashCode()
        );
    }

    @Test
    public void InputHashFalse(){
        Assertions.assertFalse(Input.formInput(State.START, PushDown.VARS, StackItems.EMPTY).hashCode() ==
                Input.formInput(State.START,PushDown.VARS,StackItems.PARENTHESIS).hashCode());
    }

    @Test
    public void InputHashFalseId(){
        Assertions.assertFalse(Input.formInput(State.START, PushDown.VARS, StackItems.EMPTY).hashCode() ==
                Input.formInput(State.START,Operator.NON_NOT_OPERATORS,StackItems.PARENTHESIS).hashCode());
    }

    @Test
    public void InputHashFalseState(){
        Assertions.assertFalse(Input.formInput(State.START, PushDown.VARS, StackItems.EMPTY).hashCode() ==
                Input.formInput(State.ID,PushDown.VARS,StackItems.PARENTHESIS).hashCode());
    }

    private PushDown pda;

    private Set<SingleVarStatement> vars;

    @BeforeEach
    void beforePda(){
        this.pda = new PushDown();
        this.vars = new HashSet<SingleVarStatement>();
    }


    @Test
    public void testGetVars(){
        vars.add(SingleVarStatement.of("A"));
        vars.add(SingleVarStatement.of("B"));
        pda.getVars("A and B");
        assert(pda.getVars("A and B").equals(vars));
    }

    @Test
    public void testSetVars(){
        PushDown.setVars(pda.getVars("A and B"));
        assert(PushDown.VARS.contains(SingleVarStatement.of("A")));
    }

    @Test
    public void testNonNot(){
        assert(Operator.NON_NOT_OPERATORS.contains(Operator.AND));
    }
}

