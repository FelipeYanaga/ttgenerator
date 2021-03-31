package org.acme.pda;

public class Tuple {
    private Input input;
    private Output output;

    public Tuple(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public static Tuple formTuple(Input input, Output output) {return new Tuple(input, output);}

    public Input getInput(){return this.input;}

    public Output getOutput(){return this.output;}
}
