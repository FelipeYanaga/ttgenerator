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

        public AndStatement build(){
            return new AndStatement(this);
        }
    }

    private AndStatement(AndStatement.Builder builder) {
        this.leftStatement = builder.leftStatement;
    }

}
