package org.acme.table;

import org.acme.pda.AllVars;
import org.acme.pda.PushDown;
import org.acme.statements.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TruthTable {
    private final Statement[] statements; //for this to work you're going to have to create a name var in each stat
    private final AllVars vars;
    private final List<BooleanLine> booleanLines; // going to have to add result in this to
    private final HeaderLine headerLine;

    public TruthTable(String s){
        this.vars = AllVars.of(s);
        this.statements = setAllStatements(s);
        this.headerLine = setHeaderLine();
        this.booleanLines = setLines();
    }

    private Statement[] setAllStatements(String s){
        String[] splitStrings = Arrays.stream(s.split(",")).map(String::trim).toArray(String[]::new);
        List<Statement> statements = new ArrayList<>();

        for (String statementString : splitStrings) {
            PushDown pda = new PushDown(statementString, this.vars);
            statements.add(pda.getMainStatement());
        }

        return statements.toArray(new Statement[splitStrings.length]);
    }

    public HeaderLine setHeaderLine(){
        String [] header = new String[vars.size() + statements.length]; //+ 1 because we are adding a statement at the end
        int i = 0;
        for (SingleVarStatement var : vars.getVars()){
            header[i] = var.getString().trim();
            i += 1;
        }
        for (Statement statement : statements) {
            header[i] = statement.getString();
            i += 1;
        }
        return HeaderLine.of(header);
    }

    public AllVars getAllVars(){
        return this.vars;
    }

    public Statement[] getStatements(){
        return this.statements;
    }

    public HeaderLine getHeaderLine(){
        return this.headerLine;
    }

    public List<BooleanLine> getBooleanLines(){
        return this.booleanLines;
    }

    public List<BooleanLine> setLines(){
        Evaluator evaluator = Evaluator.of(this);
        return evaluator.getLines();
    }

}
