package org.acme.statements;

public class NotStatement implements Statement {
    private Statement rightStatement;

    public void setRightStatement(Statement statement) {this.rightStatement = statement;}

    public NotStatement (){
    }

    public static NotStatement of(){return new NotStatement();}

    public Statement addStatement(Statement statement) {
        this.rightStatement = statement;
        return this;
    }

    public static class Builder {

        public Builder(){}

        public Statement build(){
            return new NotStatement(this);
        }
    }

    private NotStatement(Builder builder){
    }

    public boolean evaluate(){
        if ((this.rightStatement != null)) {
            return !rightStatement.evaluate();
        }
        else {
            throw new RuntimeException("Error in statement construction!");
        }
    }
}
