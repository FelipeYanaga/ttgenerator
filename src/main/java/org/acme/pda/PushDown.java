package org.acme.pda;

import java.util.Scanner;
import java.util.Stack;

public class PushDown {

    /* One stack will be used to keep track of statements,
    the other will be used to keep track of the automata.
     */
    private Stack<Integer> statementStack;
    private Stack<StackItems> automataStack = new Stack<>();
    private State state;

    public PushDown(){
        this.state = State.START;
        automataStack.push(StackItems.EMPTY);
    }

    public boolean readInput(String s) {
        //Get statement and split it due to the parenthesis
        String[] splitStatement = s.split("(?<=\\()|(?=\\))"); // Breaks (A into ( A, or, B) into B )

        //Create a string that can be read by a Scanner
        String splitString = "";
        for (String s1 : splitStatement) {
            splitString = splitString + " " + s1;
        }

        //PDA starts to read the input
        Scanner scanner = new Scanner(splitString);
        while (scanner.hasNext()){
            Input input = Input.formInput(this.state, scanner.next(),automataStack.peek());
            if (Transition.containsInput(input)){
                System.out.println(this.state);
                makeTransition(input);
            }
            else {
                throw new RuntimeException(String.format("Transition does not exist: %s %s", input.getId(), input.getState()));
            }
        }

        return isAccepted();
    }

    private void makeTransition(Input input) {
        if (Transition.containsInput(input)) {
            setState(Transition.getTransition(input).getBehavior().getOutput().getState());
            Transition transition = Transition.getTransition(input);
            updateStack(transition);
        }
    }

    private void setState(State newState) { this.state = newState;}

    private State getState(State state) { return this.state;}

    public Stack<StackItems> getAutomataStack() { return this.automataStack;}

    private boolean isAccepted() {
        return this.state == State.ID &&
                this.automataStack.peek() == StackItems.EMPTY;
    }
    /*
    Other point to consider is that may not be necessarily empty for state to continue.
     */


    /*
    You don't need to do anything with transitions that have the same stack items, because they don't change anything
     */
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
