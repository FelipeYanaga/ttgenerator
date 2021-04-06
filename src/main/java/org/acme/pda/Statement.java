package org.acme.pda;

public interface Statement {

    public Statement addStatement(Statement statement);

    public boolean evaluate();

}
