package rest;

import brugerautorisation.transport.rmi.Brugeradmin;
import java.net.MalformedURLException;
import org.json.JSONObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("email")
public class email {

//send email to according to student id
    @POST
    @Produces("application/json")
    public Response email(String ID) throws java.rmi.RemoteException {
        JSONObject studentID = new JSONObject(ID);

        Brugeradmin ba = null;

        try {
            ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
        }

        JSONObject valid = new JSONObject();
        
        try {
            ba.sendGlemtAdgangskodeEmail(studentID.getString("student"), "Dette er en test, husk at skifte kode");
            valid.put("Valid", true);
            
                            String hyper = "{"
                        + " \"Links\":["
                        + "{ \"Rel\": \"login\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/login\","
                        + " \"Method\": \"POST\"},"
                        + "{ \"Rel\": \"scores\","
                        + " \"href\": \"http://ubuntu4.saluton.dk:20002/s144211_testbuild/rest/score\","
                        + " \"Method\": \"GET\"}"
                        + "]"
                        + "}";
            
            return Response.ok(hyper, MediaType.APPLICATION_JSON).build();
        } catch (IllegalStateException e) {
            valid.put("Valid", false);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error: " + e.toString()).build();
        }
    }
}
