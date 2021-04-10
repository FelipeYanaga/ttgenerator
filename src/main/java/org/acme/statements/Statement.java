package org.acme.statements;

public interface Statement {

    public Statement addStatement(Statement statement);

    public boolean evaluate();

}
