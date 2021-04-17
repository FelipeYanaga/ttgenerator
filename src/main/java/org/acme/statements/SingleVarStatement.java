package org.acme.statements;

import java.util.*;
import java.util.stream.Collectors;

public class SingleVarStatement implements Statement {

    //Note that the vars are held in the order in which they are declared.
    //Create Instances of this, instead of static methods
    private final String name;
    private boolean value;

    /*
    Created the map to facilitate getting objects
    Could probably delete the Set functionality then
     */
    public SingleVarStatement(String name){
        this.name = name.trim();
    }

    /*
    @param word associated with the var
     */
    public static SingleVarStatement of(String name){
        return new SingleVarStatement(name);
    }

    public String getString(){
        return this.name;
    }

    /*
    This method is never actually used, because a Statement is never added to this
    However, it is a requirement of the interface
     */
    public Statement addStatement(Statement statement){
        return this; // never actually used
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


    public static class Builder {
        //Required parameters
        private final String name;

        public Builder(String name){
            this.name = name.trim();
        }

        public SingleVarStatement build(){
            return new SingleVarStatement(this);
        }
    }

    private SingleVarStatement(Builder builder) {
        this.name = builder.name;
    }

    /*
    @return boolean value of this var
     */
    public boolean evaluate(){
        return this.value;
    }

    public void setValue(boolean value){
        this.value = value;
    }

    public boolean isSingleVar(){
        return true;
    }
}
