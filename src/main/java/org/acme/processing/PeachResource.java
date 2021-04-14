package org.acme.processing;

import org.acme.pda.Evaluator;
import org.acme.pda.PushDown;
import org.acme.statements.SingleVarStatement;

import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;
import java.util.Set;

@Path("/peach")
public class PeachResource {

    private List<boolean []> values;
    private TruthTable table;

    public PeachResource(){
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TruthTable list(){
        return table;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public TruthTable add(Peach peach){
        this.table = new TruthTable();
        PushDown pda = new PushDown(peach.input);
        table.statements = Evaluator.header(pda);
        table.potato = Evaluator.makeTable(pda);
        return table;
    }

}
