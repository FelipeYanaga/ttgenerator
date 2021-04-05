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

    public void addStatement(Statement statement) {
        if (this.leftStatement == null){
            setLeftStatement(statement);
        }
        else {
            if (this.rightStatement == null) {
                setRightStatement(statement);
            }
            else {
                throw new RuntimeException("This statement is already complete.");
            }
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