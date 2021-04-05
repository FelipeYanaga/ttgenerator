package org.acme;

import org.acme.pda.*;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        String s = "A and (B or C)";
//        String ab = "(A and B) iff (C or D)";
//        String sb = "(id and id)";
//        String[] split = sb.split("(?<=\\()|(?=\\))");
//
//        String format = "";
//        for (String a : split) {
//            format = format + " " + a;
//        }
//        System.out.println(format);
//
//        Scanner scanner = new Scanner(format);
//        while (scanner.hasNext()){
//            System.out.println(scanner.next());
//        }
//
        PushDown pda = new PushDown();
        SingleVarStatement.setVars(pda.getVars("A and B"));
        pda.createStatements("A and B");
        System.out.println(pda.getPlaceHolder().getClass().toString());

        Scanner scanner = new Scanner("A and B ( )");
        String p = "\\(";
        System.out.println(scanner.hasNext(p));

    }
}


