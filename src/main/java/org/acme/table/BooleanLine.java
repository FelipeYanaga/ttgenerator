package org.acme.table;

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
}
