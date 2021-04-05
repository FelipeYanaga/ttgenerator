package org.acme.pda;

public class NotStatement implements Statement {
    private Statement rightStatement;

    public void setRightStatement(Statement statement) {this.rightStatement = statement;}

    public NotStatement (){
    }

    public static NotStatement of(){return new NotStatement();}

    public void addStatement(Statement statement) {this.rightStatement = statement; }

    public static class Builder {

        public Builder(){}

        public Statement build(){
            return new NotStatement(this);
        }
    }

    private NotStatement(Builder builder){
    }
}
