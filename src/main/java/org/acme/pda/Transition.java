package org.acme.pda;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public enum Transition {
    //ID Transitions
    START_ID(Tuple.formTuple(
            Input.formInput(State.START, PushDown.VARS, StackItems.EMPTY),
            Output.formOutput(State.ID, StackItems.EMPTY))),
    OPERATOR_ID(Tuple.formTuple(
            Input.formInput(State.OPERATOR, PushDown.VARS, StackItems.EMPTY),
            Output.formOutput(State.ID, StackItems.EMPTY))),
    PARENTHESIS_ID(Tuple.formTuple(
            Input.formInput(State.START,PushDown.VARS, StackItems.PARENTHESIS),
            Output.formOutput(State.ID, StackItems.PARENTHESIS))
    ),
    OPERATOR_ID_P(Tuple.formTuple(
            Input.formInput(State.OPERATOR, PushDown.VARS, StackItems.PARENTHESIS),
            Output.formOutput(State.ID, StackItems.PARENTHESIS))),

    //Not transitions
    START_NOT(Tuple.formTuple(
            Input.formInput(State.START, Operator.NOT_SET, StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.EMPTY))
            ),
    OPERATOR_NOT(Tuple.formTuple(
            Input.formInput(State.OPERATOR, Operator.NOT_SET, StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.EMPTY))
            ),
    NOT_START_P(Tuple.formTuple(
            Input.formInput(State.START, Operator.NOT_SET, StackItems.PARENTHESIS),
            Output.formOutput(State.START, StackItems.PARENTHESIS))
    ),
    NOT_OPERATOR_P(Tuple.formTuple(
            Input.formInput(State.OPERATOR, Operator.NOT_SET, StackItems.PARENTHESIS),
            Output.formOutput(State.START, StackItems.PARENTHESIS))
    ),

    //Parenthesis Transitions
    PARENTHESIS_OPEN_E(Tuple.formTuple( // If you get an parenthesis at the beginning
            Input.formInput(State.START, Parenthesis.OPENING_P, StackItems.EMPTY),
            Output.formOutput(State.START, StackItems.PARENTHESIS))
            ),
    PARENTHESIS_CLOSE_ID(Tuple.formTuple( // Closing the parenthesis at ID, because you always close it after an ID
            Input.formInput(State.ID, Parenthesis.CLOSING_P, StackItems.PARENTHESIS),
            Output.formOutput(State.ID, StackItems.EMPTY))
            ),
    PARENTHESIS_OPEN_OP(Tuple.formTuple(
            Input.formInput(State.OPERATOR, Parenthesis.OPENING_P, StackItems.EMPTY),
            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS)
    )),
    PARENTHESIS_DOUBLE_OPEN_OP(Tuple.formTuple(
            Input.formInput(State.OPERATOR, Parenthesis.OPENING_P, StackItems.PARENTHESIS),
            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS)
    )),
    PARENTHESIS_DOUBLE_PARENTHESIS(Tuple.formTuple(
            Input.formInput(State.START, Parenthesis.OPENING_P, StackItems.PARENTHESIS),
            Output.formOutput(State.START, StackItems.PARENTHESIS)
    )),


    /*
    Operator Transitions with and without parenthesis
     */
    OP_E(Tuple.formTuple(
            Input.formInput(State.ID, Operator.NON_NOT_OPERATORS, StackItems.EMPTY),
            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
        ),
    OP_P(Tuple.formTuple(
            Input.formInput(State.ID, Operator.NON_NOT_OPERATORS, StackItems.PARENTHESIS),
            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS))
    );
//    OR_E(Tuple.formTuple(
//            Input.formInput(State.ID, Operator.OPERATORS, StackItems.EMPTY),
//            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
//        ),
//    OR_P(Tuple.formTuple(
//            Input.formInput(State.ID, Operator.OPERATORS, StackItems.PARENTHESIS),
//            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS))
//    ),
//    IFF_E(Tuple.formTuple(
//            Input.formInput(State.ID, Operator.OPERATORS, StackItems.EMPTY),
//            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
//        ),
//    IFF_P(Tuple.formTuple(
//            Input.formInput(State.ID, Operator.OPERATORS, StackItems.PARENTHESIS),
//            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS))
//    ),
//    IF_E(Tuple.formTuple(
//            Input.formInput(State.ID, Operator.OPERATORS, StackItems.EMPTY),
//            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
//        ),
//    IF_P(Tuple.formTuple(
//            Input.formInput(State.ID, Operator.OPERATORS, StackItems.PARENTHESIS),
//            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS))
//    ),
//    XOR_E(Tuple.formTuple(
//            Input.formInput(State.ID, Operator.OPERATORS, StackItems.EMPTY),
//            Output.formOutput(State.OPERATOR, StackItems.EMPTY))
//    ),
//    XOR_P(Tuple.formTuple(
//            Input.formInput(State.ID, Operator.OPERATORS, StackItems.PARENTHESIS),
//            Output.formOutput(State.OPERATOR, StackItems.PARENTHESIS))
//            );

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
        return INPUTS_TO_TRANSITION.containsValue(transition);
    }

    public static boolean containsInput(Input input) {
        return INPUTS_TO_OUTPUTS.containsKey(input);
    }

    public static Transition getTransition(Input input) {
        return INPUTS_TO_TRANSITION.get(input);
    }
}
