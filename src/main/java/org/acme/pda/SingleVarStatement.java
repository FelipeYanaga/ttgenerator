package org.acme.pda;

public class SingleVarStatement implements Statement {
    private String name;
    private boolean value;

    public SingleVarStatement(String name){
        this.name = name;
    }

    public static SingleVarStatement of(String name){
        return new SingleVarStatement(name);
    }
}
