package org.acme.pda;

import java.util.*;
import java.util.stream.Collectors;

public class SingleVarStatement implements Statement {
    public static Set<SingleVarStatement> VARS = new HashSet<>();
    private String name;
    private boolean value;

    public SingleVarStatement(String name){
        this.name = name;
    }

    public static SingleVarStatement of(String name){
        return new SingleVarStatement(name);
    }

//    private static final Map<String, SingleVarStatement> VALUES = //create map;

    public void addStatement(Statement statement){

    }

    public static boolean contains(String name) {
        for (SingleVarStatement var : VARS) {
            if (var.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static void setVars(Set<SingleVarStatement> vars) {
        VARS = vars;
    }

    public static void addVar(SingleVarStatement var) {
        VARS.add(var);
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


    public static class Builder {
        //Required parameters
        private final String name;

        public Builder(String name){
            this.name = name;
        }

        public SingleVarStatement build(){
            return new SingleVarStatement(this);
        }
    }

    private SingleVarStatement(Builder builder) {
        this.name = builder.name;
    }

    public static SingleVarStatement getStatement(String name){
        for (SingleVarStatement var : VARS){
            if (var.getName().equalsIgnoreCase(name)){
                return var;
            }
        }
        return null;
    }

}
