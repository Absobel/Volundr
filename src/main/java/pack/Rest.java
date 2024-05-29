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

  /**
   * Fonction essentielle au bon fonctionnement de l'application
   *
   * @return la solution à la faim dans le monde
   */
  @GET
  @Path("hello")
  public String hello() {
    return "bonjour le monde !";
  }

  /**
   * Fonction récupérant la liste des utilisateurs depuis la facade
   *
   * @return la liste des utilisateurs
   */
  @GET
  @Path("getUsers")
  @Produces("application/json")
  public Collection<UtilisateurDTO> getUsers() {
    List<UtilisateurDTO> result = new ArrayList<>();
    for (Utilisateur user : facade.listeUsers())
      result.add(new UtilisateurDTO(user));
    return result;
  }

  /**
   * Fonction récupérant la liste des groupes depuis la facade
   *
   * @return la liste des groupes
   */
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

  /**
   * Fonction récupérant la liste des établlissements depuis la facade
   *
   * @return la liste des établissements
   */
  @GET
  @Path("getEtablissements")
  @Produces("application/json")
  public Collection<EtablissementDTO> getEtablissements() {
    List<EtablissementDTO> result = new ArrayList<>();
    for (Etablissement etablissement : facade.listeEtablissements())
      result.add(new EtablissementDTO(etablissement));
    return result;
  }

  /**
   * Fonction récupérant la liste des salles depuis la facade
   *
   * @return la liste des salles
   */
  @GET
  @Path("getSalles")
  @Produces("application/json")
  public Collection<SalleDTO> getSalles() {
    List<SalleDTO> result = new ArrayList<>();
    for (Salle s : facade.listeSalles())
      result.add(new SalleDTO(s));
    return result;
  }

  /**
   * Fonction retournant la liste des adresses mail des utilisateurs d'un
   * événement donné
   *
   * @param event identifiant de l'événement
   * @return la liste des adresses mail
   */
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

  /**
   * Fonction retournant la liste des adresses mail des utilisateurs d'un groupe
   * donné
   *
   * @param groupe identifiant du groupe
   * @return la liste des adresses mail
   */
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

  /**
   * Fonction retournant une chaîne de caractères décrivant tous les événements
   * Exemple :
   * "noms:Event1(debutCreneau1|finCreneau1)(debutCreneau2|finCreneau2):Event2(debutCreneau3|finCreneau3)
   *
   * @return la chaîne de caractères
   */
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

  /**
   * Fonction retournant les cases d'un événement donné
   *
   * @param event identifiant de l'événement
   * @return la liste des cases
   */
  @GET
  @Path("getCasesEvent/{event}")
  @Produces("application/json")
  public Collection<MaCaseDTO> getCasesEvent(@PathParam("event") int event) {
    Evenement e = facade.trouverEvenement(event);
    Collection<MaCaseDTO> res = new ArrayList<>();
    for (MaCase c : e.getCases())
      res.add(new MaCaseDTO(c));
    return res;
  }

  /**
   * Fonction lançant l'affectation des utilisateurs aux créneaux d'un événement
   * et renvoyant la solution proposée
   *
   * @param event identifiant de l'événement
   * @return liste des cases avec l'utilisateur qui leur a été attribué
   */
  @GET
  @Path("getChoixAffect/{event}/{user}")
  @Produces("application/json")
  public MaCaseDTO getChoixAffect(@PathParam("event") int eventId, @PathParam("user") String userMail) {
    MaCase caseUser = facade.getCaseUser(eventId, userMail);
    MaCaseDTO res = new MaCaseDTO(caseUser);
    return res;
  }

  /**
   * Fonction lançant l'affectation des créneaux d'un événement et retournant les cases remplies avec un utilisateur
   *
   * @param event identifiant de l'événement
   * @return la liste des cases remplies
   */
  @GET
  @Path("runAffectationEvent/{event}")
  @Produces("application/json")
  public Collection<MaCaseDTO> runAffectationEvent(@PathParam("event") int event) {
    Evenement e = facade.trouverEvenement(event);
    Collection<MaCaseDTO> res = new ArrayList<>();
    /* lancer l'affectation */
    facade.AffecterCreneaux(e);
    e = facade.trouverEvenement(event);
    /* envoyer le résultat */
    for (MaCase c : e.getCases())
      res.add(new MaCaseDTO(c));
    return res;
  }

  /**
   * Fonction retournant les choix d'un utilisateurs pour les cases d'un événement
   * donné
   *
   * @param event identifiant de l'événement
   * @param mail  adresse mail de l'utilisateur
   * @return la liste des choix
   */
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

  /**
   * Fonction ajoutant un utilisateur dans la base de données
   *
   * @param user utilisateur à ajouter
   * @return réponse permettant de vérifier si le message a bien été envoyé
   */
  @POST
  @Path("addUser")
  public Response addUser(Utilisateur user) {
    facade.ajoutUtilisateur(user);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
    return Response.status(204).build();
  }

  /**
   * Fonction créant et ajoutant un nouvel événement
   *
   * @param name nom du nouvel événement
   * @return l'événement nouvellement créé
   */
  @POST
  @Path("creerEvent")
  @Produces("application/json")
  public Evenement creerEvent(String name) {
    return facade.ajoutEvenement(name);
  }

  /**
   * Fonction ajoutant une salle dans la base de données
   *
   * @param salle salle à ajouter
   * @return réponse permettant de vérifier si le message a bien été envoyé
   */
  @POST
  @Path("creerSalle")
  @Produces("application/json")
  public Response creerSalle(SalleDTO salle) {
    facade.creerSalle(salle);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
    return Response.status(204).build();
  }

  /**
   * Fonction transmettant un groupe à la facade
   *
   * @param groupeId identifiant du groupe à transmettre
   * @return réponse permettant de vérifier si le message a bien été envoyé
   */
  @POST
  @Path("creerGroupe/{groupe}")
  @Produces("application/json")
  public Response creerGroupe(@PathParam("groupe") int groupeId) {
    Groupe groupe = facade.trouverGroupe(groupeId);
    facade.ajoutGroupe(groupe);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
    return Response.status(204).build();
  }

  /**
   * Fonction créant et ajoutant à la base de données un nouveau groupe vide (0
   * utilisateurs)
   *
   * @param groupeName nom du nouveau groupe
   * @return le groupe nouvellement créé
   */
  @POST
  @Path("creerGroupeVide")
  @Produces("application/json")
  public Groupe creerGroupeVide(String groupeName) {
    return facade.ajoutGroupe(groupeName);
    // https://developer.mozilla.org/fr/docs/Web/HTTP/Status/204
  }

  /**
   * Fonction ajoutant des cases données à un événement
   *
   * @param event identifiant de l'établissement
   * @param cases liste des cases à ajouter
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
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

  /**
   * Fonction assignant une note à une case, pour un utilisateur donné
   *
   * @param mail   adresse mail de l'utilisateur
   * @param caseId identifiant de la case
   * @param note   note attribuée
   * @return réponse permettant de verifier si le message a bien été envoyé
   * @throws PermissionRefuseeException si l'utilisateur n'est pas dans le groupe
   *                                    invité à l'événement contenant la case
   */
  @POST
  @Path("addChoixUserCase/{user}/{case}")
  @Consumes("application/json")
  public Response addChoixUserCase(@PathParam("user") String mail, @PathParam("case") int caseId, int note)
      throws PermissionRefuseeException {
    facade.addChoixToUser(mail, caseId, note);
    return Response.status(200).build();
  }

  /**
   * Fonction supprimant des cases données d'un événement
   *
   * @param event identifiant de l'événement
   * @param cases liste des cases à supprimer
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("delCasesEvent/{event}")
  @Produces("application/json")
  public Response delCasesEvent(@PathParam("event") int event, Collection<MaCase> cases) {
    Evenement realEvent = facade.trouverEvenement(event);
    facade.delCaseToEvent(realEvent, cases);
    return Response.status(200).build();
  }

  /**
   * Fonction ajoutant un utilisateur à un événement
   *
   * @param event identifiant de l'événement
   * @param email adresse mail de l'utilisateur
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("addUserEvent/{event}")
  @Produces("application/json")
  public Response addUserEvent(@PathParam("event") int event, String email) {
    facade.addUserEvent(event, email);
    return Response.status(200).build();
  }

  /**
   * Fonction ajoutant un utilisateur à un groupe
   *
   * @param groupe identifiant du groupe
   * @param email  adresse mail de l'utilisateur
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("addUserGroup/{groupe}")
  @Produces("application/json")
  public Response addUserGroup(@PathParam("groupe") int groupe, String email) {
    facade.addUserToGroup(groupe, email);
    return Response.status(200).build();
  }

  /**
   * Fonction supprimant un utilisateur d'un événement
   *
   * @param event identifiant de l'événement
   * @param email adresse mail de l'utilisateur
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("delUserEvent/{event}")
  @Produces("application/json")
  public Response delUserEvent(@PathParam("event") int event, String email) {
    facade.delUserEvent(event, email);
    return Response.status(200).build();
  }

  /**
   * Fonction supprimant un utilisateur d'un groupe
   *
   * @param groupe identifiant du groupe
   * @param email  adresse mail de l'utilisateur
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("delUserGroup/{groupe}")
  @Produces("application/json")
  public Response delUserGroupe(@PathParam("groupe") int groupe, String email) {
    facade.delUserOfGroup(groupe, email);
    return Response.status(200).build();
  }

  /**
   * Fonction supprimant tous les utilisateurs d'un groupe donné d'un événement
   *
   * @param event   identifiant de l'événement
   * @param groupId identifiant du groupe
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("delUsersFromGroupEvent/{event}")
  @Produces("application/json")
  public Response delUsersFromGroupEvent(@PathParam("event") int event, int groupId) {
    facade.delUsersFromGroupEvent(event, groupId);
    return Response.status(200).build();
  }

  /**
   * Fonction supprimant les utilisateurs d'un groupe s'ils font partie d'un autre
   * groupe
   *
   * @param groupe  groupe cible de la suppression
   * @param groupId groupe dont les utilisateurs seront supprimés de l'autre
   *                groupe
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("delUsersFromGroupOfGroup/{groupe}")
  @Produces("application/json")
  public Response delUsersFromGroupOfGroup(@PathParam("groupe") int groupe, int groupId) {
    facade.delUsersFromGroupOfGroup(groupe, groupId);
    return Response.status(200).build();
  }

  /**
   * Fonction ajoutant tous les utilisateurs d'un groupe à un événement
   *
   * @param event    identifiant de l'événement
   * @param groupeId identifiant du groupe
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("addUserFromGroupEvent/{event}")
  @Produces("application/json")
  public Response addUserFromGroupEvent(@PathParam("event") int event, int groupeId) {
    facade.addUserFromGroup(event, groupeId);
    return Response.status(200).build();
  }

  /**
   * Fonction ajoutant tous les utilisateurs d'un groupe dans un autre groupe
   *
   * @param groupe   identifiant du groupe cible de l'ajout
   * @param groupeId identifiant du groupe dont les utilisateurs sont copiés
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("addUsersFromGroupToGroup/{groupe}")
  @Produces("application/json")
  public Response addUsersFromGroupToGroup(@PathParam("groupe") int groupe, int groupeId) {
    facade.addUsersFromGroupToGroup(groupe, groupeId);
    return Response.status(200).build();
  }

  /**
   * Fonction enregistrant la date de début d'un événement
   *
   * @param idEvent identifiant de l'événement
   * @param help    date de début de l'événement
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("setDebutDate/{idEvent}")
  @Consumes("application/json")
  public Response setDebutDate(@PathParam("idEvent") int idEvent, String help) {
    facade.setDebutDate(idEvent, help);
    return Response.status(200).build();
  }

  /**
   * Fonction enregistrant la date de fin d'un événement
   *
   * @param idEvent identifiant de l'événement
   * @param help    date de fin de l'événement
   * @return réponse permettant de verifier si le message a bien été envoyé
   */
  @POST
  @Path("setFinDate/{idEvent}")
  @Consumes("application/json")
  public Response setFinDate(@PathParam("idEvent") int idEvent, String help) {
    facade.setFinDate(idEvent, help);
    return Response.status(200).build();
  }

}
