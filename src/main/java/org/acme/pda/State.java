package org.acme.pda;

import java.util.EnumSet;

public enum State implements Potato {
    START,
    ID,
    NOT_STATE;

    private static final EnumSet<State> FINAL_STATES = EnumSet.of(State.ID, State.START);

    private static final EnumSet<State> NON_FINAL_STATES = EnumSet.of(State.NOT_STATE);
}
