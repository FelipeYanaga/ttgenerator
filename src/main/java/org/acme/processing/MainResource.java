package org.acme.processing;

import org.acme.table.TruthTable;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class MainResource {

    private TruthTable table;

    public MainResource(){
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TruthTable list(){
        return table;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public TruthTable add(Main main){
        this.table = new TruthTable(main.input);
        return table;
    }

}
