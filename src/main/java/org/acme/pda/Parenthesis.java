package org.acme.pda;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public enum Parenthesis {
    START_PARENTHESIS("("),
    END_PARENTHESIS(")");


    private static final Map<String, Parenthesis> VALUES = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(o -> o.value, o -> o));

    public static final EnumSet<Parenthesis> OPENING_P = EnumSet.of(START_PARENTHESIS);

    public static final EnumSet<Parenthesis> CLOSING_P = EnumSet.of(END_PARENTHESIS);

    private final String value;

    Parenthesis(String value) { this.value = value;}

    public static Parenthesis fromString(String s) {return VALUES.get(s);}

    public static boolean contains(String s){
        return VALUES.containsKey(s);
    }

}
