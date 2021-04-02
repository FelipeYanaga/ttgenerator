package org.acme.pda;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SingleVarStatement implements Statement {
    private String name;
    private boolean value;

    public SingleVarStatement(String name){
        this.name = name;
    }

    public static SingleVarStatement of(String name){
        return new SingleVarStatement(name);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o instanceof SingleVarStatement) {
            SingleVarStatement other = (SingleVarStatement) o;
            return Objects.equals(this.name, other.name);
        }
        return false;
    }

    @Override
    public int hashCode() { return Objects.hash(name);}

}
