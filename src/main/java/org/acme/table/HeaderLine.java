package org.acme.table;

public class HeaderLine {
    private String[] header;

    private HeaderLine(String [] header){
        this.header = header;
    }

    public static HeaderLine of(String [] header){
        return new HeaderLine(header);
    }

    public String [] getHeader(){
        return this.header;
    }
}
