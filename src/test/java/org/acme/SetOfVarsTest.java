package org.acme;

import org.acme.pda.AllVars;
import org.junit.jupiter.api.Test;

public class SetOfVarsTest {

    @Test
    public void setVars(){
        String s = "A and B, B or C, C and D";
        AllVars setOfVars = AllVars.of(s);
        assert(setOfVars.size() == 4);
    }

    @Test
    public void getVar(){
        String s = "A and B, B or C, C and D";
        AllVars setOfVars = AllVars.of(s);
        assert(setOfVars.contains("A"));
    }

}
