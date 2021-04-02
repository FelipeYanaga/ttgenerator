package org.acme.pda;

public class AndStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;

    public AndStatement(){}

    public static AndStatement of(){return new AndStatement();}

    public void setLeftStatement(Statement leftStatement) {
        this.leftStatement = leftStatement;
    }

    public void setRightStatement(Statement rightStatement) {
        this.rightStatement = rightStatement;
    }
}
