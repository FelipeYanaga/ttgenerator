package org.acme.processing;

import org.acme.pda.*;
import org.acme.statements.SingleVarStatement;
import org.acme.table.BooleanLine;
import org.acme.table.TruthTable;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        TruthTable table = new TruthTable("A and B and C");
        for (BooleanLine line : table.getBooleanLines()){
            System.out.println(Arrays.toString(line.getValues()));
        }
    }
}


