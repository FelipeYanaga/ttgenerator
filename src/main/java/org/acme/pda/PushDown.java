package org.acme.pda;

import org.acme.statements.*;

import java.util.*;

public class PushDown {

    /*
    Note: still have to figure out where to put the parenthesis, with regards to statement
    precedent.
     */
    private Stack<StackItems> automataStack = new Stack<>(); //Automata Stack
    private State state; //Automata State
    private Parser parser; //Scanner
    private String userInput; //String given by the user. Could be throw off if written properly
    private Statement mainStatement; //Parsed Statement
    private StatementVars statementVars; //Variables in this statement
    private AllVars allVars; //Variables in this statement

    public PushDown(String s, AllVars allVars){ //add set input to this and add string as a parameter
        this.state = State.START;
        automataStack.push(StackItems.EMPTY);
        this.userInput = s;
        this.allVars = allVars;
        this.statementVars = getStatementVars(s);
        this.parser = Parser.of(s);
        setMainStatement();
    }

    private boolean setMainStatement(){
        if (validInput()){
            this.mainStatement = parseStatement();
            return true;
        }
        else {
            return false;
        }
    }


    /*
    Get statement
     */
    public Statement getMainStatement(){
        return mainStatement;
    }

    /*
    Change the state
     */
    private void setState(State newState) { this.state = newState;}

    /*
    Get what's on the stack
     */
    public Stack<StackItems> getAutomataStack() { return this.automataStack;}

    public StatementVars getStatementVars(String s){
        return StatementVars.ofStatement(s,this.allVars);
    }


    public boolean validInput() {
        //Get statement and split it due to the parenthesis


        Parser parser1 = Parser.of(Parser.splitStatement(userInput));
        while (parser1.hasNext()){
            Set word = classify(parser1.next());
            Input input = Input.of(this.state, word ,automataStack.peek());
            if (Transitions.containsInput(input)){
                makeTransition(input);
            }
            else {
                throw new RuntimeException(String.format("Transition does not exist: %s %s %s", input.getId(), input.getState(), input.getItem()));
            }
        }

        return isAccepted();
    }

    /*
    Operate on the transition
    Change the state.
    Call to change stack.
    @param first part of the transition
     */

    private void makeTransition(Input input) {
        if (Transitions.containsInput(input)) {
            setState(Transitions.getTransition(input).getBehavior().getOutput().getState());
            Transitions transition = Transitions.getTransition(input);
            updateStack(transition);
        }
    }

    /*
    Sees if the automaton ended up in a final state and empty stack
    Meaning it's accepted
     */
    public boolean isAccepted() {
        return this.state == State.ID &&
                this.automataStack.peek() == StackItems.EMPTY;
    }

    /*
    You don't need to do anything with transitions that have the same stack items, because they don't change anything
     */
    private void updateStack(Transitions transition) {
        if (transition.getBehavior().getInput().getItem() != transition.getBehavior().getOutput().getItem()){
            if (transition.getBehavior().getInput().getItem() == StackItems.EMPTY) {
                this.automataStack.push(transition.getBehavior().getOutput().getItem());
            }
            else {
                this.automataStack.pop();
            }
        }  //Very hard to read, probably needs to change
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

    /*
    Classify statements into types
     */
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
            return Identifier.IDS;
        }
    }


    /*
    Parses the statement into the Parse Tree
    Should probably move this to the parser.
     */
    public Statement parseStatement(){

        boolean isOperator = false;
        Statement previousStatement = null;
        String word;
        while(parser.hasNext()){
            word = parser.next();
            if (statementVars.contains(word)){
                if (isOperator){
                    previousStatement = previousStatement.addStatement(statementVars.getVar(word));
                }
                else {
                    previousStatement = statementVars.getVar(word);
                    isOperator = false;
                }
            }
            else if(Operator.contains(word)){
                if (word.equalsIgnoreCase(Operator.NOT.toString())){
                    previousStatement = new NotStatement.Builder().build();
                    isOperator = true;
                }
                else if (word.equalsIgnoreCase(Operator.OR.toString())){
                    previousStatement = new OrStatement.Builder(previousStatement).build();
                    isOperator = true;
                }
                else if (word.equalsIgnoreCase(Operator.XOR.toString())){
                    previousStatement = new XorStatement.Builder(previousStatement).build();
                    isOperator = true;
                }
                else if (word.equalsIgnoreCase(Operator.AND.toString())){
                    previousStatement = new AndStatement.Builder(previousStatement).build();
                    isOperator = true;
                }
                else if (word.equalsIgnoreCase(Operator.IFF.toString())){
                    previousStatement = new IffStatement.Builder(previousStatement).build();
                    isOperator = true;
                }
                else {
                    previousStatement = new IfStatement.Builder(previousStatement).build();
                    isOperator = true;
                }
            }
            else if (word.equalsIgnoreCase("(")) {
                if (isOperator){
                    previousStatement = previousStatement.addStatement(parseStatement());
                }
                else {
                    previousStatement = parseStatement();
                }
            }
            else { // case in which we hit the final parenthesis
                return previousStatement;
            }
        }
        return previousStatement;
    }


    /*
    Might not be the ideal place to put this
     */
//
//    public List<boolean []> evaluate(){
//        List<boolean []> result = new ArrayList<>();
//        for (boolean [] line : Evaluator.getTruthFalsePermutations()){
//            boolean [] stat = new boolean[1];
//            SingleVarStatement.setValues(line);
//            stat[0] = mainStatement.evaluate();
//            result.add(stat);
//        }
//        return result;
//    }

}
