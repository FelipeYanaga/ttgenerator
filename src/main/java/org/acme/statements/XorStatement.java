package org.acme.statements;

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

    public Statement addStatement(Statement statement) {
        if (this.leftStatement == null){
            setLeftStatement(statement);
            return this;
        }
        else {
            if (this.rightStatement == null) {
                setRightStatement(statement);
                return this;
            }
            else {
                throw new RuntimeException("This statement is already complete.");
            }
        }
    }

    public boolean evaluate(){
        if ((this.rightStatement != null) && (this.leftStatement != null)){
            return rightStatement.evaluate() != leftStatement.evaluate();
        }
        else {
            throw new RuntimeException("Error in statement construction!");
        }
    }

    public static class Builder {
        //Required parameters
        private final Statement leftStatement;

        public Builder(Statement statement){
            this.leftStatement = statement;
        }

        public XorStatement build(){
            return new XorStatement(this);
        }
    }

    private XorStatement(XorStatement.Builder builder) {
        this.leftStatement = builder.leftStatement;
    }
}