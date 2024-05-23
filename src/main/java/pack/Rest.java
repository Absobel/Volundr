package pack;

import java.util.Collection;
import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * Rest
 */
@Path("tutorial")
public class Rest {

  @EJB
  Facade facade;

  @GET
  @Path("hello")
  public String hello() {
    return "bonjour le monde !";
  }

  @GET
  @Path("getGroup")
  @Produces("application/json")
  public Collection<Utilisateur> getGroup() {
    return facade.listeUsers();
  }

  @POST
  @Path("addUserGroup")
  public Response addUserGroup(Utilisateur user) {
    facade.ajoutUtilisateur(user);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
    return Response.status(204).build();
  }

  @POST
  @Path("creerEvent")
  @Produces("application/json")
  public Evenement creerEvent(String name) {
    return facade.ajoutEvenement(name);
  }
}
