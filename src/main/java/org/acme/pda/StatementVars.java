package org.acme.pda;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.acme.statements.SingleVarStatement;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatementVars implements SetOfVars {
    private Map<String, SingleVarStatement> VAR_MAP;

    private StatementVars(String s, AllVars vars){
        this.VAR_MAP = this.creteAllVars(s, vars);
    }

    public static StatementVars ofStatement(String s, AllVars vars){
        return new StatementVars(s,vars);
    }

    public Map<String, SingleVarStatement> creteAllVars(String s, AllVars vars){
        Map<String, SingleVarStatement> varMap = new LinkedHashMap<>();

        Parser parser = Parser.of(Parser.splitStatement(s));
        while (parser.hasNext()) {
            String currentString = parser.next();
            if (!Operator.contains(currentString) && !Parenthesis.contains(currentString)) {
                SingleVarStatement var = vars.getVar(currentString);
                varMap.put(var.getString(),var);
            }
        }

        return varMap;
    }

    public SingleVarStatement getVar(String name){
        return VAR_MAP.get(name);
    }

    public boolean contains(String s){
        return VAR_MAP.containsKey(s);
    }

}
