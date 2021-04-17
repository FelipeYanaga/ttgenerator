package org.acme.statements;

public class AndStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;
    private String statement;

    public AndStatement(){}

    public static AndStatement of(){return new AndStatement();}

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

    /*
    Return the string that this represents
     */
    public String getString() {
        if (this.statement == null){
            setString();
            return statement;
        }
        else {
            return statement;
        }
    }

    private void setString(){
        if (rightStatement != null && leftStatement != null){
            if (leftStatement.isSingleVar() && !rightStatement.isSingleVar()) {
                this.statement = String.format("%s and (%s)", this.leftStatement.getString(), this.rightStatement.getString());
            }
            else if (!leftStatement.isSingleVar() && rightStatement.isSingleVar()){
                this.statement = String.format("(%s) and %s", this.leftStatement.getString(), this.rightStatement.getString());
            }
            else if (!leftStatement.isSingleVar() && !rightStatement.isSingleVar()) {
                this.statement = String.format("(%s) and (%s)", this.leftStatement.getString(), this.rightStatement.getString());
            }
            else {
                this.statement = String.format("%s and %s", this.leftStatement.getString(), this.rightStatement.getString());
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

    public boolean evaluate(){
        if ((this.rightStatement != null) && (this.leftStatement != null)){
            return rightStatement.evaluate() && leftStatement.evaluate();
        }
        else {
            throw new RuntimeException("Error in statement construction!");
        }
    }

    public boolean isSingleVar(){
        return false;
    }

}
