package org.acme.pda;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public enum Transition {
    //ID Transitions
    START_ID(Tuple.formTuple(
            Input.formInput(State.START, "id", StackItems.EMPTY),
            Output.formOutput(State.ID, StackItems.EMPTY))),
    OPERATOR_ID(Tuple.formTuple(
            Input.formInput(State.OPERATOR, "id", StackItems.EMPTY),
            Output.formOutput(State.ID, StackItems.EMPTY))),
    PARENTHESIS_ID(Tuple.formTuple(
            Input.formInput(State.START,"id", StackItems.PARENTHESIS),
            Output.formOutput(State.ID, StackItems.PARENTHESIS))
    ),
    OPERATOR_ID_P(Tuple.formTuple(
            Input.formInput(State.OPERATOR, "id", StackItems.PARENTHESIS),
            Output.formOutput(State.ID, StackItems.PARENTHESIS))),

    //Not transitions
    START_NOT(Tuple.formTuple(
            Input.formInput(State.START, "not", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.EMPTY))
            ),
    OPERATOR_NOT(Tuple.formTuple(
            Input.formInput(State.OPERATOR, "not", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.EMPTY))
            ),

    //Parenthesis Transitions
    PARENTHESIS_OPEN_E(Tuple.formTuple( // If you get an parenthesis at the beginning
            Input.formInput(State.START, "(", StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.PARENTHESIS))
            ),
    PARENTHESIS_CLOSE_ID(Tuple.formTuple( // Closing the parenthesis at ID, because you always close it after an ID
            Input.formInput(State.ID, ")", StackItems.PARENTHESIS),
            Output.formOutput(State.ID, StackItems.EMPTY))
            ),
    PARENTHESIS_OPEN_OP(Tuple.formTuple(
            Input.formInput(State.OPERATOR, "(", StackItems.EMPTY),
            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS)
    )),

    //Operator Transitions
    AND_E(Tuple.formTuple(
            Input.formInput(State.ID, "and", StackItems.EMPTY),
            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
        ),
    AND_P(Tuple.formTuple(
            Input.formInput(State.ID, "and", StackItems.PARENTHESIS),
            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS))
    ),
    OR_E(Tuple.formTuple(
            Input.formInput(State.ID, "or", StackItems.EMPTY),
            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
        ),
    IFF_E(Tuple.formTuple(
            Input.formInput(State.ID, "iff", StackItems.EMPTY),
            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
        ),
    IF_E(Tuple.formTuple(
            Input.formInput(State.ID, "if", StackItems.EMPTY),
            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
        ),
    XOR_E(Tuple.formTuple(
            Input.formInput(State.ID, "xor", StackItems.EMPTY),
            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
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
