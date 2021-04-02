package org.acme.pda;

import java.util.*;

public class PushDown {

    /* One stack will be used to keep track of statements,
    the other will be used to keep track of the automata.
     */
    private Stack<Integer> statementStack;
    private Stack<StackItems> automataStack = new Stack<>();
    private State state;
    private boolean insideP;

    public static Set<SingleVarStatement> VARS = new HashSet<>();

    public static void setVars(Set<SingleVarStatement> vars) {
        VARS = vars;
    }

    public PushDown(){
        this.state = State.START;
        automataStack.push(StackItems.EMPTY);
    }

    public Set<SingleVarStatement> getVars(String s){

        Set<SingleVarStatement> vars = new HashSet<SingleVarStatement>();

        String[] splitStatement = s.split("(?<=\\()|(?=\\))");

        String splitString = "";
        for (String s1 : splitStatement) {
            splitString = splitString + " " + s1;
        }

        Scanner scanner = new Scanner(splitString);
        while (scanner.hasNext()) {
            String s1 = scanner.next();
            if (!Operator.contains(s1) && !s1.equalsIgnoreCase(")")
            && !s1.equalsIgnoreCase("(")) {
                vars.add(SingleVarStatement.of(s1));
            }
        }


        return vars;
    }

    public boolean validInput(String s) {
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
                System.out.println(input.getState() + " " + input.getItem() + " " + input.getId());
                makeTransition(input);
            }
            else {
                throw new RuntimeException(String.format("Transition does not exist: %s %s %s", input.getId(), input.getState(), input.getItem()));
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
        else if (transition.getBehavior().getInput().getItem() == transition.getBehavior().getOutput().getItem() &&
        transition.getBehavior().getInput().getItem() == StackItems.PARENTHESIS
        && transition.getBehavior().getInput().getId().equalsIgnoreCase("(")) {
            this.automataStack.push(StackItems.PARENTHESIS);
        }
        else if (transition.getBehavior().getInput().getItem() == transition.getBehavior().getOutput().getItem() &&
        transition.getBehavior().getInput().getItem() == StackItems.PARENTHESIS
        && transition.getBehavior().getInput().getId().equalsIgnoreCase(")")) {
            this.automataStack.pop();
        }
    }

    public boolean createStatements(String s){
        //Get statement and split it due to the parenthesis
        String[] splitStatement = s.split("(?<=\\()|(?=\\))"); // Breaks (A into ( A, or, B) into B )

        //Create a string that can be read by a Scanner
        String splitString = "";
        for (String s1 : splitStatement) {
            splitString = splitString + " " + s1;
        }

        Scanner scanner = new Scanner(splitString);
        while (scanner.hasNext()) {
            Statement currStatement;
            String current = scanner.next();
            if (isOperator(current)) {
                currStatement = createStatement(current);
            }
            else if (!isOperator(current) && !s.equalsIgnoreCase(")") && !s.equalsIgnoreCase("(")){
                currStatement = SingleVarStatement.of(current);
            }
            else if (current.equalsIgnoreCase("(")){
                insideP = true;
            }
            else {
                insideP = false;
            }
        }


        return true;
    }

    private boolean isOperator(String s){
        return Operator.contains(s);
    }

    private Statement createStatement(String s){
        if (s.equalsIgnoreCase(Operator.NOT.toString())){
            return NotStatement.of();
        }
        else if (s.equalsIgnoreCase(Operator.AND.toString())){
            return AndStatement.of();
        }
        else if (s.equalsIgnoreCase(Operator.OR.toString())){
            return OrStatement.of();
        }
        else if (s.equalsIgnoreCase(Operator.XOR.toString())){
            return XorStatement.of();
        }
        else if (s.equalsIgnoreCase(Operator.IFF.toString())){
            return IffStatement.of();
        }
        else {
            return IfStatement.of();
        }
    }

}
