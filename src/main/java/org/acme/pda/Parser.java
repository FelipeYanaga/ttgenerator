package org.acme.pda;

import java.util.Scanner;

public class Parser {

    private final Scanner scanner;

    private Parser(String s){
        this.scanner = new Scanner(splitStatement(s));
    }

    public static Parser of(String s){
        return new Parser(s);
    }

    public String next(){
        return scanner.next();
    }

    public boolean hasNext(){
        return scanner.hasNext();
    }

    public static String splitStatement(String s){
        String[] splitStatement = s.split("(?<=\\()|(?=\\))");

        String splitString = "";
        for (String s1 : splitStatement) {
            splitString = splitString + " " + s1;
        }

        return splitString;
    }

    

}
