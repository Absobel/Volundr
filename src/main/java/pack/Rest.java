package pack;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.ejb.EJB;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
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
  @Path("getEtablissements")
  @Produces("application/json")
  public Collection<EtablissementDTO> getEtablissements() {
    List<EtablissementDTO> result = new ArrayList<>();
    for (Etablissement etablissement : facade.listeEtablissements())
      result.add(new EtablissementDTO(etablissement));
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
  @Path("getUsersGroup/{groupe}")
  @Produces("application/json")
  public Collection<String> getUsersGroup(@PathParam("groupe") int groupe) {
    // Evenement e = facade.trouverEvenement(event);
    // return e.getGroupeE().getUtilisateurs();

    List<String> users = new ArrayList<>();
    for (Utilisateur u : facade.trouverGroupe(groupe).getUtilisateurs())
      users.add(u.getMail());
    return users;
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

  @GET
  @Path("getChoixEvent/{event}/{user}")
  @Produces("application/json")
  public Collection<ChoixDTO> getChoixEventUser(@PathParam("event") int event, @PathParam("user") String mail) {
    Collection<Choix> choix = facade.listeChoixEventUser(event, mail);
    List<ChoixDTO> res = new ArrayList<>();
    for (Choix c : choix)
      res.add(new ChoixDTO(c));
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
  public Response creerSalle(SalleDTO salle) {
    facade.creerSalle(salle);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
    return Response.status(204).build();
  }

  @POST
  @Path("creerGroupe/{groupe}")
  @Produces("application/json")
  public Response creerGroupe(@PathParam("groupe") int groupeId) {
    Groupe groupe = facade.trouverGroupe(groupeId);
    facade.ajoutGroupe(groupe);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
    return Response.status(204).build();
  }

  @POST
  @Path("creerGroupeVide")
  @Produces("application/json")
  public Groupe creerGroupeVide(String groupeName) {
    return facade.ajoutGroupe(groupeName);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
  }

  @POST
  @Path("addCasesEvent/{event}")
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
  @Path("addChoixUserCase/{user}/{case}")
  @Consumes("application/json")
  public Response addChoixUserCase(@PathParam("user") String mail, @PathParam("case") int caseId, int note) throws PermissionRefuseeException {
    facade.addChoixToUser(mail, caseId, note);
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
  @Path("addUserGroup/{groupe}")
  @Produces("application/json")
  public Response addUserGroup(@PathParam("groupe") int groupe, String email) {
    facade.addUserToGroup(groupe, email);
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
  @Path("delUserGroup/{groupe}")
  @Produces("application/json")
  public Response delUserGroupe(@PathParam("groupe") int groupe, String email) {
    facade.delUserOfGroup(groupe, email);
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
  @Path("delUsersFromGroupOfGroup/{groupe}")
  @Produces("application/json")
  public Response delUsersFromGroupOfGroup(@PathParam("groupe") int groupe, int groupId) {
    facade.delUsersFromGroupEvent(groupe, groupId);
    return Response.status(200).build();
  }

  @POST
  @Path("addUserFromGroupEvent/{event}")
  @Produces("application/json")
  public Response addUserFromGroupEvent(@PathParam("event") int event, int groupeId) {
    facade.addUserFromGroup(event, groupeId);
    return Response.status(200).build();
  }

  @POST
  @Path("addUsersFromGroupToGroup/{groupe}")
  @Produces("application/json")
  public Response addUsersFromGroupToGroup(@PathParam("groupe") int groupe, int groupeId) {
    facade.addUsersFromGroupToGroup(groupe, groupeId);
    return Response.status(200).build();
  }
}
