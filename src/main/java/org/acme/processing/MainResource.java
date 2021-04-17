package org.acme.processing;

import org.acme.table.TruthTable;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MainResource {

    private TruthTable table;

    /*
    Notes:

    1) Fix the lines produced, as sometimes it produces more than it needs to
     */

    public MainResource(){
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TruthTable list(){
        return table;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public TruthTable add(Main peach){
        this.table = new TruthTable(peach.input);
        return table;
    }

}
