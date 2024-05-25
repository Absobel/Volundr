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
  @Path("getGroups")
  @Produces("application/json")
  public Collection<GroupeDTO> getGroups() {
    List<GroupeDTO> result = new ArrayList<>();
    for (Groupe groupe : facade.listeGroupes()) {
      if (groupe.getIsNotEventGroup()) {
        result.add(new GroupeDTO(groupe));
      }
    }
    return result;
  }

  @GET
  @Path("getSalles")
  @Produces("application/json")
  public Collection<SalleDTO> getSalles() {
    List<SalleDTO> result = new ArrayList<>();
    for (Salle s : facade.listeSalles())
      result.add(new SalleDTO(s));
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
  @Path("creerSalle")
  @Produces("application/json")
  public Response creerSalle(Salle salle) {
    facade.creerSalle(salle);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
    return Response.status(204).build();
  }

  @POST
  @Path("addCasesEvent/{event}")
  @Produces("application/json")
  public Response addCasesEvent(@PathParam("event") int event, Collection<MaCase> cases) {
    Evenement realEvent = facade.trouverEvenement(event);
    // /* on récupère les vraies cases */
    // Set<MaCase> realCases = new HashSet<>();
    // for (MaCaseDTO c : cases) {
    // realCases.add(facade.toMaCase(c));
    // }
    facade.addCaseToEvent(realEvent, cases);
    return Response.status(200).build();
  }

  @POST
  @Path("delCasesEvent/{event}")
  @Produces("application/json")
  public Response delCasesEvent(@PathParam("event") int event, Collection<MaCase> cases) {
    Evenement realEvent = facade.trouverEvenement(event);
    facade.delCaseToEvent(realEvent, cases);
    return Response.status(200).build();
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

  @POST
  @Path("delUsersFromGroupEvent/{event}")
  @Produces("application/json")
  public Response delUsersFromGroupEvent(@PathParam("event") int event, int groupId) {
    facade.delUsersFromGroupEvent(event, groupId);
    return Response.status(200).build();
  }

  @POST
  @Path("addUserFromGroupEvent/{event}")
  @Produces("application/json")
  public Response addUserFromGroupEvent(@PathParam("event") int event, int groupeId) {
    facade.addUserFromGroup(event, groupeId);
    return Response.status(200).build();
  }
}
