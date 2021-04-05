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

        public IfStatement build(){
            return new IfStatement(this);
        }
    }

    private IfStatement(IfStatement.Builder builder) {
        this.leftStatement = builder.leftStatement;
    }

}

