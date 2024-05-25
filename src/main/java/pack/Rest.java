package pack;

import java.util.List;
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
  @Path("getUsers")
  @Produces("application/json")
  public Collection<UtilisateurDTO> getUsers() {
    List<UtilisateurDTO> result = new ArrayList<>();
    for (Utilisateur user : facade.listeUsers())
      result.add(new UtilisateurDTO(user));
    return result;
  }

  @GET
  @Path("getUsersEvent/{event}")
  @Produces("application/json")
  public Collection<String> getUsersEvent(@PathParam("event") int event) {
    // Evenement e = facade.trouverEvenement(event);
    // return e.getGroupeE().getUtilisateurs();

    List<String> test = new ArrayList<>();
    for (Utilisateur u : facade.trouverEvenement(event).getGroupeE().getUtilisateurs())
      test.add(u.getMail());
    return test;
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
  @Path("addUser")
  public Response addUser(Utilisateur user) {
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

  @POST
  @Path("addUserEvent/{event}")
  @Produces("application/json")
  public Response addUserEvent(@PathParam("event") int event, String email) {
    facade.addUserEvent(event, email);
    return Response.status(200).build();
  }

  @POST
  @Path("delUserEvent/{event}")
  @Produces("application/json")
  public Response delUserEvent(@PathParam("event") int event, String email) {
    facade.delUserEvent(event, email);
    return Response.status(200).build();
  }


}
