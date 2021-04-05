package org.acme.pda;

import java.util.Objects;
import java.util.Set;

public class Input {
    private final State state;
    private final Set ids;
    private final StackItems item;

    public Input(State state, Set ids, StackItems item) {
        this.state = state;
        this.ids = ids;
        this.item = item;
    }

    public static Input of(State state, Set id, StackItems item) {
        return new Input(state, id, item);
    }

    public StackItems getItem() {
        return this.item;
    }

    public Set getId() {
        return this.ids;
    }

    public State getState() {
        return this.state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Input) {
            Input other = (Input) o;
            return Objects.equals(this.state, other.state) &&
                    Objects.equals(this.ids, other.ids)
                    && Objects.equals(this.item, other.item);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, ids, item);
    }
}
