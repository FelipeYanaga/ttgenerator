package org.acme.pda;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public enum Operator {
    AND("and"),
    OR("or"),
    XOR("xor"),
    IFF("iff"),
    IF("if"),
    NOT("not");

    private static final Map<String, Operator> VALUES = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(o -> o.value, o -> o));

    public static final EnumSet<Operator> OPERATORS = EnumSet.allOf(Operator.class);

    public static final EnumSet<Operator> NOT_SET = EnumSet.of(NOT);

    public static final EnumSet<Operator> NON_NOT_OPERATORS = EnumSet.complementOf(NOT_SET);

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
