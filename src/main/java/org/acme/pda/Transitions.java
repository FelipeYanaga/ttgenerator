package org.acme.pda;

import org.acme.statements.SingleVarStatement;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public enum Transitions {
    //ID Transitions
    START_ID(Tuple.of(
            Input.of(State.START, SingleVarStatement.VARS, StackItems.EMPTY),
            Output.of(State.ID, StackItems.EMPTY))),
    OPERATOR_ID(Tuple.of(
            Input.of(State.OPERATOR, SingleVarStatement.VARS, StackItems.EMPTY),
            Output.of(State.ID, StackItems.EMPTY))),
    PARENTHESIS_ID(Tuple.of(
            Input.of(State.START, SingleVarStatement.VARS, StackItems.PARENTHESIS),
            Output.of(State.ID, StackItems.PARENTHESIS))
    ),
    OPERATOR_ID_P(Tuple.of(
            Input.of(State.OPERATOR, SingleVarStatement.VARS, StackItems.PARENTHESIS),
            Output.of(State.ID, StackItems.PARENTHESIS))),

    //Not transitions
    START_NOT(Tuple.of(
            Input.of(State.START, Operator.NOT_SET, StackItems.EMPTY),
            Output.of(State.START, StackItems.EMPTY))
            ),
    OPERATOR_NOT(Tuple.of(
            Input.of(State.OPERATOR, Operator.NOT_SET, StackItems.EMPTY),
            Output.of(State.START, StackItems.EMPTY))
            ),
    NOT_START_P(Tuple.of(
            Input.of(State.START, Operator.NOT_SET, StackItems.PARENTHESIS),
            Output.of(State.START, StackItems.PARENTHESIS))
    ),
    NOT_OPERATOR_P(Tuple.of(
            Input.of(State.OPERATOR, Operator.NOT_SET, StackItems.PARENTHESIS),
            Output.of(State.START, StackItems.PARENTHESIS))
    ),

    //Parenthesis Transitions
    PARENTHESIS_OPEN_E(Tuple.of( // If you get an parenthesis at the beginning
            Input.of(State.START, Parenthesis.OPENING_P, StackItems.EMPTY),
            Output.of(State.START, StackItems.PARENTHESIS))
            ),
    PARENTHESIS_CLOSE_ID(Tuple.of( // Closing the parenthesis at ID, because you always close it after an ID
            Input.of(State.ID, Parenthesis.CLOSING_P, StackItems.PARENTHESIS),
            Output.of(State.ID, StackItems.EMPTY))
            ),
    PARENTHESIS_OPEN_OP(Tuple.of(
            Input.of(State.OPERATOR, Parenthesis.OPENING_P, StackItems.EMPTY),
            Output.of(State.OPERATOR, StackItems.PARENTHESIS)
    )),
    PARENTHESIS_DOUBLE_OPEN_OP(Tuple.of(
            Input.of(State.OPERATOR, Parenthesis.OPENING_P, StackItems.PARENTHESIS),
            Output.of(State.OPERATOR, StackItems.PARENTHESIS)
    )),
    PARENTHESIS_DOUBLE_PARENTHESIS(Tuple.of(
            Input.of(State.START, Parenthesis.OPENING_P, StackItems.PARENTHESIS),
            Output.of(State.START, StackItems.PARENTHESIS)
    )),


    /*
    Operator Transitions with and without parenthesis
     */
    OP_E(Tuple.of(
            Input.of(State.ID, Operator.NON_NOT_OPERATORS, StackItems.EMPTY),
            Output.of(State.OPERATOR, StackItems.EMPTY))
        ),
    OP_P(Tuple.of(
            Input.of(State.ID, Operator.NON_NOT_OPERATORS, StackItems.PARENTHESIS),
            Output.of(State.OPERATOR, StackItems.PARENTHESIS))
    );

    Transitions(Tuple tuple) {
        this.behavior = tuple;
    }

    private final Tuple behavior;

    public static EnumSet<Transitions> TRANSITIONS = EnumSet.allOf(Transitions.class);

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
    private static final Map<Input, Transitions> INPUTS_TO_TRANSITION = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(o -> o.getBehavior().getInput(), o -> o));


    public static boolean contains(Transitions transition) {
        return INPUTS_TO_TRANSITION.containsValue(transition);
    }

    public static boolean containsInput(Input input) {
        return INPUTS_TO_OUTPUTS.containsKey(input);
    }

    public static Transitions getTransition(Input input) {
        return INPUTS_TO_TRANSITION.get(input);
    }
}
