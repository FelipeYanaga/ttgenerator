package org.acme.pda;

public class XorStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;

    public XorStatement(){}

    public static XorStatement of(){return new XorStatement();}

    public void setLeftStatement(Statement leftStatement) {
        this.leftStatement = leftStatement;
    }

    public void setRightStatement(Statement rightStatement) {
        this.rightStatement = rightStatement;
    }
}