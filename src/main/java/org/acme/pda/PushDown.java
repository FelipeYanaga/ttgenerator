package org.acme.pda;

import java.util.Scanner;
import java.util.Stack;

public class PushDown {

    /* One stack will be used to keep track of statements,
    the other will be used to keep track of the automata.
     */
    private Stack<Integer> statementStack;
    private Stack<StackItems> automataStack = new Stack<>();
    private State state = State.START;

    public PushDown(){
        automataStack.push(StackItems.EMPTY);
    }

    public boolean readInput(String input) {
        //Get statement and split it due to the parenthesis
        String[] splitStatement = input.split("(?<=\\()|(?=\\))"); // Breaks (A into ( A, or, B) into B )

        //Create a string that can be read by a Scanner
        String splitString = "";
        for (String s : splitStatement) {
            splitString = splitString + " " + s;
        }

        //PDA starts to read the input
        Scanner scanner = new Scanner(splitString);
        Input transitionInput = Input.formInput(this.state, "",automataStack.peek());
        while (scanner.hasNext()){
            if (Transition.containsInput(transitionInput.setValues(this.state, scanner.next(), automataStack.peek()))){
                makeTransition(Transition.getTransition(transitionInput));
            }
            else {
                return false;
            }
        }

        return true;
    }

    private void setStates(State state) { this.state = state;}

    private State getState(State state) { return this.state;}


    /*
    Now, you have to think about the states as well, it only ends if it's an accepting state and the stack is empty
    Simply having transitions for all of the Strings is not enough, but this does show that the transitions are being
    applied correctly.
     */
    private void makeTransition(Transition transition) {
        Output output = transition.getBehavior().getOutput();
        setStates(output.getState());
        updateStack(transition);
    }

    private void updateStack(Transition transition) {
        if (transition.getBehavior().getInput().getItem() != transition.getBehavior().getOutput().getItem()){
            if (transition.getBehavior().getInput().getItem() == StackItems.EMPTY) {
                this.automataStack.push(transition.getBehavior().getOutput().getItem());
            }
            else {
                this.automataStack.pop();
            }
        }
    }

}
