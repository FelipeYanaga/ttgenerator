package org.acme.pda;

public class OrStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;

    public OrStatement(){}

    public static OrStatement of(){return new OrStatement();}

    public void setLeftStatement(Statement leftStatement) {
        this.leftStatement = leftStatement;
    }

    public void setRightStatement(Statement rightStatement) {
        this.rightStatement = rightStatement;
    }
}
