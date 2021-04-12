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

    public PeachResource(){
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<boolean []> list(){
        return values;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<boolean []> add(Peach peach){
        PushDown pda = new PushDown();
        SingleVarStatement.setVars(pda.getVars(peach.input));
        values = Evaluator.getPerm();
        return values;
    }

}
