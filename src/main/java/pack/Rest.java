package pack;

import java.util.ArrayList;
import java.util.Collection;
import jakarta.ejb.EJB;
import jakarta.ws.rs.PathParam;
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

  @GET
  @Path("getEvent")
  @Produces("application/json")
  public String getEvent() {
    String s = "noms";
    for (Evenement e : facade.listeEvenements()) {
      s = s + ":" + e.getNom();
      for (MaCase c : e.getCases())
        s = s + "(" + c.getDebutCreneau() + "|" + c.getFinCreneau() + ")";
    }
    return s;
  }

  @GET
  @Path("getCasesEvent/{event}")
  @Produces("application/json")
  public Collection<MaCaseDTO> getEvents(@PathParam("event") int event) {
    Evenement e = facade.trouverEvenement(event);
    Collection<MaCaseDTO> res = new ArrayList<>();
    for (MaCase c : e.getCases())
      res.add(new MaCaseDTO(c));
    return res;
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
