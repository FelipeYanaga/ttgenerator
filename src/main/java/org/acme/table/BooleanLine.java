package org.acme.table;

import java.util.Arrays;
import java.util.Objects;

public class BooleanLine {
    private boolean [] values;

    private BooleanLine(boolean[] line){
        this.values = line;
    }

    public static BooleanLine of(boolean[] line){
        return new BooleanLine(line);
    }

    public boolean[] getValues(){
        return this.values;
    }

    public int size(){
        return this.values.length;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return  true;
        if (o instanceof BooleanLine){
            BooleanLine other = (BooleanLine) o;
            return Arrays.equals(this.values, ((BooleanLine) o).getValues());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(values);
    }

}
