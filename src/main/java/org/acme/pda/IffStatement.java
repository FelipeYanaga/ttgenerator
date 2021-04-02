package org.acme.pda;

public class IffStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;

    public IffStatement(){}

    public static IffStatement of(){return new IffStatement();}

    public void setLeftStatement(Statement leftStatement) {
        this.leftStatement = leftStatement;
    }

    public void setRightStatement(Statement rightStatement) {
        this.rightStatement = rightStatement;
    }
}