package org.acme.pda;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public enum Transition {
    //ID Transitions
    ID_E(Tuple.formTuple(
            Input.formInput(State.START, "id", StackItems.EMPTY),
            Output.formOutput(State.ID, StackItems.EMPTY))),
    ID_O(Tuple.formTuple(
            Input.formInput(State.START, "id", StackItems.OPERATOR),
            Output.formOutput(State.ID, StackItems.EMPTY))),

    //Not transitions
    NOT_E(Tuple.formTuple(
            Input.formInput(State.START, "not", StackItems.EMPTY),
            Output.formOutput(State.NOT_STATE, StackItems.NOT_OPERATOR))
            ),
    NOT_O(Tuple.formTuple(
            Input.formInput(State.START, "not", StackItems.OPERATOR),
            Output.formOutput(State.NOT_STATE, StackItems.OPERATOR))
            ),
    NOT_N(Tuple.formTuple(
            Input.formInput(State.NOT_STATE, "not", StackItems.EMPTY),
            Output.formOutput(State.NOT_STATE, StackItems.EMPTY))
    ),
    ID_N(Tuple.formTuple(
            Input.formInput(State.NOT_STATE, "id", StackItems.NOT_OPERATOR),
            Output.formOutput(State.ID, StackItems.EMPTY))
    ),

    //Parenthesis Transitions
    PARENTHESIS_OPEN_E(Tuple.formTuple( // If you get an parenthesis at the beginning
            Input.formInput(State.START, "(", StackItems.EMPTY),
            Output.formOutput(State.ID, StackItems.PARENTHESIS))
            ),
    PARENTHESIS_CLOSE_ID(Tuple.formTuple( // Closing the parenthesis at ID, because you always close it after an ID
            Input.formInput(State.ID, ")", StackItems.PARENTHESIS),
            Output.formOutput(State.START, StackItems.EMPTY))
            ),
    PARENTHESIS_OPEN_N(Tuple.formTuple(
            Input.formInput(State.NOT_STATE, "(", StackItems.EMPTY),
            Output.formOutput(State.NOT_STATE, StackItems.PARENTHESIS)
    )),

    //Operator Transitions
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
    Map of the inputs that should return the output.
     */
    private static final Map<Input, Output> INPUTS_TO_OUTPUTS = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(o -> o.getBehavior().getInput(), o -> o.getBehavior().getOutput()));

    /*
    Map of the inputs to the transitions.
    Maybe having both of these at the same time might be a little bit of a stretch
     */
    private static final Map<Input, Transition> INPUTS_TO_TRANSITION = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(o -> o.getBehavior().getInput(), o -> o));

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
        return INPUTS_TO_OUTPUTS.containsKey(input);
    }

    public static Transition getTransition(Input input) {
        return INPUTS_TO_TRANSITION.get(input);
    }
}
