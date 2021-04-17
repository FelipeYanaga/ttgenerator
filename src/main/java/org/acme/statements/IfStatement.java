package org.acme.statements;

public class IfStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;
    private String statement;

    public IfStatement(){}

    public static IfStatement of(){return new IfStatement();}

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
            this.statement = String.format("%s if %s", this.leftStatement.getString(), this.rightStatement.getString());
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

    public boolean evaluate(){
        if ((this.rightStatement != null) && (this.leftStatement != null)){
            if (leftStatement.evaluate()){
                return rightStatement.evaluate();
            }
            else {
                return true;
            }
        }
        else {
            throw new RuntimeException("Error in statement construction!");
        }
    }

}

