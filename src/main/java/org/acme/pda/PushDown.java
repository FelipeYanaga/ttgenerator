package org.acme.pda;

import java.util.*;

public class PushDown {

    /* One stack will be used to keep track of statements,
    the other will be used to keep track of the automata.
     */
    private Stack<Integer> statementStack;
    private Stack<StackItems> automataStack = new Stack<>();
    private State state;
    private Statement placeHolder;
    private Statement pStatement;

    public PushDown(){
        this.state = State.START;
        automataStack.push(StackItems.EMPTY);
    }

    public Statement getPlaceHolder(){
        return placeHolder;
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
            Set word = classify(scanner.next());
            Input input = Input.of(this.state, word ,automataStack.peek());
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
        && transition.getBehavior().getInput().getId().equals(Parenthesis.OPENING_P)) {
            this.automataStack.push(StackItems.PARENTHESIS);
        }
        else if (transition.getBehavior().getInput().getItem() == transition.getBehavior().getOutput().getItem() &&
        transition.getBehavior().getInput().getItem() == StackItems.PARENTHESIS
        && transition.getBehavior().getInput().getId().equals(Parenthesis.CLOSING_P)) {
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

        boolean isOperator = false;
        Statement currStatement;
        Scanner scanner = new Scanner(splitString);
        String word;
        while (scanner.hasNext()) {
            word = scanner.next();
            if (word.equalsIgnoreCase("(") || word.equalsIgnoreCase(")")){
                word = scanner.next();
            }
            if (isVar(word)) {
                currStatement = SingleVarStatement.getStatement(word);
                if (isOperator) {
                    placeHolder.addStatement(currStatement);
                }
                else {
                    placeHolder = currStatement;
                    isOperator = false;
                }
            }
            else {
                if (word.equalsIgnoreCase(Operator.NOT.toString())){
                    placeHolder = new NotStatement.Builder().build();
                    isOperator = true;
                }
                else if (word.equalsIgnoreCase(Operator.OR.toString())){
                    placeHolder = new OrStatement.Builder(placeHolder).build();
                    isOperator = true;
                }
                else if (word.equalsIgnoreCase(Operator.XOR.toString())){
                    placeHolder = new XorStatement.Builder(placeHolder).build();
                    isOperator = true;
                }
                else if (word.equalsIgnoreCase(Operator.AND.toString())){
                    placeHolder = new AndStatement.Builder(placeHolder).build();
                    isOperator = true;
                }
                else if (word.equalsIgnoreCase(Operator.IFF.toString())){
                    placeHolder = new IffStatement.Builder(placeHolder).build();
                    isOperator = true;
                }
                else {
                    placeHolder = new IfStatement.Builder(placeHolder).build();
                    isOperator = true;
                }
            }
        }

        return true;
    }

    private boolean isOperator(String s){
        return Operator.contains(s);
    }

    public boolean isVar(String s){
        return SingleVarStatement.VARS.contains(SingleVarStatement.of(s));
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

    public Set classify(String s) {
        if (Operator.NON_NOT_OPERATORS.contains(Operator.fromString(s))) {
            return Operator.NON_NOT_OPERATORS;
        }
        else if (Operator.NOT_SET.contains(Operator.fromString(s))) {
            return Operator.NOT_SET;
        }
        else if (Parenthesis.OPENING_P.contains(Parenthesis.fromString(s))){
            return Parenthesis.OPENING_P;
        }
        else if (Parenthesis.CLOSING_P.contains(Parenthesis.fromString(s))){
            return Parenthesis.CLOSING_P;
        }
        else {
            return SingleVarStatement.VARS;
        }
    }

}
