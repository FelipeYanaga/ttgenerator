package org.acme.pda;

public class Input {
    private State state;
    private String id;
    private StackItems item;

    public Input(State state, String id, StackItems item) {
        this.state = state;
        this.id = id;
        this.item = item;
    }

    public static Input formInput(State state, String id, StackItems item) { return new Input(state, id, item); }

    public void setState(State state) {this.state = state;}

    public void setId(String id) {this.id = id;}

    public void setItem(StackItems item) {this.item = item;}

    public Input setValues(State state, String id, StackItems item) {
        setId(id);
        setItem(item);
        setState(state);
        return this;
    }

    public StackItems getItem(){return this.item;}

    public String getId(){return this.id;}

    public State getState(){return this.state;}

    public boolean equals(Input input){
        return this.state.equals(input.getState()) &&
        this.id.equals(input.getId()) &&
        this.item.equals(input.getItem());
    }
}
