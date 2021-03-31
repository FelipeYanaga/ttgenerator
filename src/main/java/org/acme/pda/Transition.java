package org.acme.pda;

import java.util.EnumSet;

public enum Transition {
    ID_E(Tuple.formTuple(
            Input.formInput(State.START, "id", StackItems.EMPTY),
            Output.formOutput(State.ID, StackItems.EMPTY))),
    ID_O(Tuple.formTuple(
            Input.formInput(State.START, "id", StackItems.OPERATOR),
            Output.formOutput(State.ID, StackItems.EMPTY))),
    NOT_E(Tuple.formTuple(
            Input.formInput(State.START, "not", StackItems.EMPTY),
            Output.formOutput(State.NOT_STATE, StackItems.NOT_OPERATOR))
            ),
    NOT_O(Tuple.formTuple(
            Input.formInput(State.START, "not", StackItems.OPERATOR),
            Output.formOutput(State.NOT_STATE, StackItems.OPERATOR))
            ),
    PARENTHESIS_E(Tuple.formTuple(
            Input.formInput(State.START, "(", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.PARENTHESIS))
            ),
    PARENTHESIS_P(Tuple.formTuple(
            Input.formInput(State.START, ")", StackItems.PARENTHESIS),
            Output.formOutput(State.START, StackItems.EMPTY))
            ),
    NOT_N(Tuple.formTuple(
            Input.formInput(State.NOT_STATE, "not", StackItems.NOT_OPERATOR),
            Output.formOutput(State.NOT_STATE, StackItems.NOT_OPERATOR))
            ),
    ID_N(Tuple.formTuple(
            Input.formInput(State.NOT_STATE, "id", StackItems.NOT_OPERATOR),
            Output.formOutput(State.ID, StackItems.EMPTY))
            ),
    AND_E(Tuple.formTuple(
            Input.formInput(State.ID, "and", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.OPERATOR))
        ),
    OR_E(Tuple.formTuple(
            Input.formInput(State.ID, "or", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.OPERATOR))
        ),
    IFF_E(Tuple.formTuple(
            Input.formInput(State.ID, "iff", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.OPERATOR))
        ),
    IF_E(Tuple.formTuple(
            Input.formInput(State.ID, "if", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.OPERATOR))
        ),
    XOR_E(Tuple.formTuple(
            Input.formInput(State.ID, "xor", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.OPERATOR))
    );

    Transition(Tuple tuple) {
        this.behavior = tuple;
    }

    private final Tuple behavior;

    public static EnumSet<Transition> TRANSITIONS = EnumSet.allOf(Transition.class);

    public Tuple getBehavior(){ return this.behavior;}

    /*
    Implement a map to get the values in O(1) and not O(n) time, but this might be acceptable
    to create later.
     */
    public static boolean contains(Transition transition) {
        for (Transition t : TRANSITIONS) {
            if (t.equals(transition)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsInput(Input input) {
        for (Transition t : TRANSITIONS) {
            if (t == Transition.getTransition(input)){
                return true;
            }
        }
        return false;
    }

    public static Transition getTransition(Input input) {
        Transition value = null;
        for (Transition t : TRANSITIONS) {
            if (input.equals(t.getBehavior().getInput())) {
                value = t;
            }
        }
        return value;
    }
}
