package org.acme.pda;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Parenthesis implements Potato{
    START_PARENTHESIS("("),
    END_PARENTHESIS(")");


    private static final Map<String, Parenthesis> VALUES = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(o -> o.value, o -> o));

    private final String value;

    Parenthesis(String value) { this.value = value;}

}
