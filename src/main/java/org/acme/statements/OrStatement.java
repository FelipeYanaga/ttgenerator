package org.acme.statements;

public class OrStatement implements Statement {
    private Statement rightStatement;
    private Statement leftStatement;
    private String statement;

    public OrStatement(){}

    public static OrStatement of(){return new OrStatement();}

    public void setLeftStatement(Statement leftStatement) {
        this.leftStatement = leftStatement;
    }

    public void setRightStatement(Statement rightStatement) {
        this.rightStatement = rightStatement;
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
            this.statement = String.format("%s or %s", this.leftStatement.getString(), this.rightStatement.getString());
        }
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

    public static class Builder {
        //Required parameters
        private final Statement leftStatement;

        public Builder(Statement statement){
            this.leftStatement = statement;
        }

        public OrStatement build(){
            return new OrStatement(this);
        }
    }

    private OrStatement(Builder builder) {
        this.leftStatement = builder.leftStatement;
    }

    public boolean evaluate(){
        if ((this.rightStatement != null) && (this.leftStatement != null)){
            return rightStatement.evaluate() || leftStatement.evaluate();
        }
        else {
            throw new RuntimeException("Error in statement construction!");
        }
    }

}
