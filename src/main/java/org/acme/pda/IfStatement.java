package org.acme.pda;

public class IfStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;

    public IfStatement(){}

    public static IfStatement of(){return new IfStatement();}

    public void setLeftStatement(Statement leftStatement) {
        this.leftStatement = leftStatement;
    }

    public void setRightStatement(Statement rightStatement) {
        this.rightStatement = rightStatement;
    }
}