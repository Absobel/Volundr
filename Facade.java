3/* Facade.java */

/* La spécification ci-dessous est une première ébauche. Sentez-vous libre
 * de la modifier, et je suis totalement ouvert à la discussion pour
 * changer tout ou partie */

/* Juste, pensez à prévenir tout le monde sur discord et à mettre à jour les
 * javadoc si vous faites des modifs */

import java.util.List;

/* Liste des exceptions dont on risque d'avoir besoin :
 * - ComptePasCree (vérifiée) : l'identifiant donné ne possède pas de compte
 * - UserPasConnecte (vérifiée, mais jsuis pas totalement convaincu) :
 *                 l'utilisateur essaie d'accéder à une page sans être connecté
 * - NonAutorise (vérifiée) : l'utilisateur essaie d'accéder à une ressource
 *      qui le concerne pas ou de modifier sans être administrateur de l'event…
 */

public interface Facade {

  /** Verifier si un couple identifiant/mot de passe 
   * est correct pour se connecter
   * @param user l'identifiant utilisé pour se connecter
   * @param password le mot de passe utilisé pour se connecter
   * @return true si la connexion a réussie ou false si l'un des deux
   *         est incorrect
   * @throws ComptePasCree l'identifiant ne possède pas de compte associé
   * @throws ... il y aura probablement des erreurs server ou réseau à traiter ?
   */
  bool connexion(userId, String password)
    throws ComptePasCree, …;
  /* -> une autre version que j'envisageais est de renvoyer l'objet User
   * si la connexion a réussie ou le cookie de connexion
   * et de lever une exception "MotPasseIncorrect" si le mot de passe est faux
   * => j'aimerais bien avoir votre avis */


  /* Fonction utilitaire qui pourrait servir mais pas sûr */
  /** Vérifier si l'utilisateur est connecté
   * @param cookie le cookie de connexion
   * @return true si connecté, false sinon
   * @throws ... à voir
   */
  bool isConnected(cookie) throws …; /* <- en fait je sais pas comment on
                                      *   gère les connexions en java */

  /** Obtenir la liste des créneaux d'un événement, peut être appelée par les
   * administrateurs de l'événement ou les participants pour voir les créneaux
   * @param event l'événement en question
   * @return la liste des créneaux
   * @throws UserPasConnecte si l'utilisateur n'est pas connecté
   * @throws NonAutorise si l'utilisateur n'est pas concerné par l'événement
   * @throws ... il y aura probablement des erreurs server ou réseau à traiter ?
   */
  List<Creneau> getCreneauxEvent (Event event)
      throws UserPasConnecte, NonAutorise, …;
  /* -> les événements récupérés seront détachés de la base de donnée,
   *    c'est du read only */

  /** Obtenir la liste des choix d'un utilisateur pour un événement
   * @param event l'événement en question
   * @param user la personne dont on récupère les choix
   * @return la liste des choix
   * @throws UserPasConnecte si l'utilisateur n'est pas connecté
   * @throws NonAutorise si l'utilisateur n'est pas concerné par l'événement
   * @throws ... il y aura probablement des erreurs server ou réseau à traiter ?
   */
  List<choix> getChoixEventUser (Event event, User user)
      throws UserPasConnecte, NonAutorise, …;

  /** Modifier un choix de l'utilisateur sur un créneau de l'événement :
   * si l'utilisateur avait déjà fait un choix, on efface l'ancien, sinon on lui
   * ajoute le nouveau choix
   * @param event l'événement en question
   * @param user la personne qui fait le choix
   * @param choix le choix effectué (qui contient le créneau concerné et
   *                                 la note affectée par l'utilisateur)
   * @throws UserPasConnecte si l'utilisateur n'est pas connecté
   * @throws NonAutorise si c'est pas le bon utilisateur qui fait la modif
   *         (attention: un admin de l'événement à le droit de modifier à la
   *          place d'un utilisateur)
   * @throws ... il y aura probablement des erreurs server ou réseau à traiter ?
   *         attention: c'est important de bien gérer les erreurs réseau/serveur
   *         sur cette fonction car les requêtes de modifications seront
   *         probablement faites de façon asynchrone sur le client, 
   *         => risque de désynchronisation
   */
  void setChoixEventUser (Event event, User user, Choix choix)
    throws UserPasConnecte, NonAutorise, …;


  /* attention on rentre dans la partie chiante: la gestion par les admins */
  
  /* je vais écrire les specs, mais en gros il va falloir les fonctions:
   * - créerEvent
   * - getEventSettings
   * - setEventSettings
   * - getUsersGroup (obtenir les users d'un groupe)
   * - addGroupUser (ajouter un user à un groupe)
   * - addEventCreneau (ajouter un créneau à un event)
   * - addEventSalle (ajouter une salle à un event)
   * - delGroupUser (même chose mais pour supprimer)
   * - delEventCreneau
   * - delEventSalle
   */
}
