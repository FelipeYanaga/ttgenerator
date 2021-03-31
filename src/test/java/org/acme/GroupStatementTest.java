package org.acme;

import org.acme.pda.Operator;
import org.junit.jupiter.api.Test;

public class GroupStatementTest {



    @Test
    public void enumContains(){
        assert(Operator.contains("or"));
    }

}
