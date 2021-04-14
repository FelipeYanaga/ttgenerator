package org.acme.statements;

import java.util.*;
import java.util.stream.Collectors;

public class SingleVarStatement implements Statement {

    //Note that the vars are held in the order in which they are declared.
    //Create Instances of this, instead of static methods
    public static Set<SingleVarStatement> VARS = new LinkedHashSet<>();
    private final String name;
    private boolean value;
    public static Map<String, SingleVarStatement> VAR_MAP;

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

    public static SingleVarStatement getStatement(String name){
        return VAR_MAP.get(name);
    }

    /*
    This method is never actually used, because a Statement is never added to this
    However, it is a requirement of the interface
     */
    public Statement addStatement(Statement statement){
        return this; // never actually used
    }

    /*
    @param the "name" of the variable
    @return if the variable exists or not
     */
    public static boolean contains(String name) {
        return VAR_MAP.containsKey(name);
    }

    /*
    Create the vars, could be improved by taking out the pda.getVars method
     */
    public static void setVars(Set<SingleVarStatement> vars) {
        VARS = vars;
        VAR_MAP = VARS.stream()
                .collect(Collectors.toUnmodifiableMap(SingleVarStatement::getString, o-> o));
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

    /*
    @param the line of boolean values: TTT, FFF ...
    Sets the variable values for the statement.
    I need to pay attention to the order
     */
    public static void setValues(boolean [] line){
        int i = 0; // var used to move from left to the right of the array
        /*
        1st var will be assigned index 0, then 2nd index 1 ...
         */
        for (SingleVarStatement var : VARS) {
            var.setValue(line[i]);
            i += 1; // Move pointer to the right
        }
    }

}
