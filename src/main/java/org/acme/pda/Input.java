package org.acme.pda;

import java.util.Objects;

public class Input {
    private final State state;
    private final String id;
    private final StackItems item;

    public Input(State state, String id, StackItems item) {
        this.state = state;
        this.id = id;
        this.item = item;
    }

    public static Input formInput(State state, String id, StackItems item) {
        return new Input(state, id, item);
    }

    public StackItems getItem() {
        return this.item;
    }

    public String getId() {
        return this.id;
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
                    Objects.equals(this.id, other.id)
                    && Objects.equals(this.item, other.item);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, id, item);
    }
}
