package org.acme.pda;

public class Output {
    private State state;
    private StackItems item;

    public Output(State state, StackItems item){
        this.state = state;
        this.item = item;
    }

    public static Output formOutput(State state, StackItems item) { return new Output(state, item);}

    public State getState(){return this.state;}

    public StackItems getItem(){return this.item;}
}
