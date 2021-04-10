package org.acme.pda;

import java.util.EnumSet;

public enum State {
    START,
    ID,
    OPERATOR;

    private static final EnumSet<State> FINAL_STATES = EnumSet.of(State.ID, State.START);
}
