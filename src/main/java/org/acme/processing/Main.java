package org.acme.processing;

import org.acme.pda.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println(Parser.splitStatement("A and (B and C)"));

    }
}


