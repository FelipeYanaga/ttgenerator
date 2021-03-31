package org.acme.pda;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public enum Operator implements Potato {
    AND("and"),
    OR("or"),
    XOR("xor"),
    IFF("iff"),
    IF("if"),
    NOT("not");

    private static final Map<String, Operator> VALUES = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(o -> o.value, o -> o));

    private static final EnumSet<Operator> OPERATORS = EnumSet.allOf(Operator.class);

    private final String value;

    Operator(String value) {
        this.value = value;
    }

    public static Operator fromString(String s) {
        return VALUES.get(s);
    }

    public static boolean contains(String s) {
        return VALUES.containsKey(s);
    }
}
