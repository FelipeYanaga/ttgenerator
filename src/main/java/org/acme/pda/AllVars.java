package org.acme.pda;

import org.acme.statements.SingleVarStatement;

import java.util.*;

public class AllVars implements SetOfVars {
    //move everything over to this code and create one for every time
    private Set<SingleVarStatement> VARS;
    private final Map<String,SingleVarStatement> VAR_MAP; //probably going to have this set to static

    private AllVars(String s){
        this.VAR_MAP = this.createAllVars(s);
    }

    public static AllVars of(String s){
        return new AllVars(s);
    }

    public int size(){
        return VAR_MAP.size();
    }

    public SingleVarStatement getVar(String s){
        return VAR_MAP.get(s);
    }

    public boolean contains(String s){
        return VAR_MAP.containsKey(s);
    }

    public Set<SingleVarStatement> getVars(){
        return new LinkedHashSet<SingleVarStatement>(VAR_MAP.values());
    }

    public void setValues(boolean [] line){
        // The creation of vars starts at index 0 and goes to the end of the array
        int i = 0;
        for (SingleVarStatement var : new LinkedHashSet<SingleVarStatement>(VAR_MAP.values())){
            var.setValue(line[i]);
            i += 1; // move pointer to the right after setting one value.
        }
    }

    public Map<String, SingleVarStatement> createAllVars(String s){
        Map<String, SingleVarStatement> vars = new LinkedHashMap<>();

        String[] splitStrings = Arrays.stream(s.split(",")).map(String::trim).toArray(String[]::new);

        for (String string : splitStrings){
            Parser parser = Parser.of(Parser.splitStatement(string));
            while (parser.hasNext()) {
                String currentString = parser.next();
                if (!Operator.contains(currentString) && !Parenthesis.contains(currentString)) {
                    vars.put(currentString, SingleVarStatement.of(currentString));
                }
            }
        }

        return vars;
    }






}
