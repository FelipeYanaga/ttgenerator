package org.acme.statements;

public class IffStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;
    private String statement;

    public IffStatement(){}

    public static IffStatement of(){return new IffStatement();}

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
            this.statement = String.format("%s iff %s", this.leftStatement.getString(), this.rightStatement.getString());
        }
    }

    public static class Builder {
        //Required parameters
        private final Statement leftStatement;

        public Builder(Statement statement){
            this.leftStatement = statement;
        }

        public IffStatement build(){
            return new IffStatement(this);
        }
    }

    private IffStatement(IffStatement.Builder builder) {
        this.leftStatement = builder.leftStatement;
    }

    public boolean evaluate(){
        if ((this.rightStatement != null) && (this.leftStatement != null)){
            return rightStatement.evaluate() == leftStatement.evaluate();
        }
        else {
            throw new RuntimeException("Error in statement construction!");
        }
    }
}