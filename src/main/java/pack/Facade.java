package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
public class Facade {

  @PersistenceContext
  private EntityManager em;

  /**
   * Ajout d'un utilisateur dans la base de données
   *
   * @param nom      nom de l'utilisateur
   * @param prenom   prenom de l'utilisateur
   * @param mail     mail de l'utilisateur
   * @param password mot de passe de l'utilisateur
   */
  public void ajoutUtilisateur(String nom, String prenom, String mail, String password) {
    Utilisateur user = new Utilisateur();
    user.setNom(nom);
    user.setPrenom(prenom);
    user.setMail(mail);
    user.setPassword(password);
    user = em.merge(user);
    em.persist(user);
  }

  /**
   * Ajout d'un utilisateur dans la base de données avec la possibilité de
   * préciser s'il est admin
   *
   * @param nom      nom de l'utilisateur
   * @param prenom   prenom de l'utilisateur
   * @param mail     mail de l'utilisateur
   * @param password mot de passe de l'utilisateur
   * @param isAdmin  true si l'utilisateur créé est admin, false sinon
   */
  public void ajoutUtilisateur(String nom, String prenom, String mail, String password, boolean isAdmin) {
    Utilisateur user = new Utilisateur();
    user.setNom(nom);
    user.setPrenom(prenom);
    user.setMail(mail);
    user.setPassword(password);
    user.setIsAdmin(isAdmin);
    user = em.merge(user);
    em.persist(user);
  }

  /**
   * Ajout d'un groupe dans la base de données
   *
   * @param groupe groupe à ajouter à la base de données
   */
  public void ajoutGroupe(Groupe groupe) {
    em.merge(groupe);
  }

  /**
   * Ajout d'un groupe vide dans la base de données
   *
   * @param groupName nom du nouveau groupe à ajouter à la base de données
   */
  public Groupe ajoutGroupe(String groupeName) {
    Groupe groupe = new Groupe();
    groupe.setNom(groupeName);
    // em.merge(groupe);
    em.persist(groupe);
    return groupe;
  }

  /**
   * Ajout d'un groupe contenant des utilisateurs précis dans la base de données
   *
   * @param groupName nom du nouveau groupe à ajouter à la base de données
   * @param users     liste d'utilisateurs composant le groupe
   */
  public void ajoutGroupe(String groupeName, Set<Utilisateur> users) {
    Groupe groupe = new Groupe();
    groupe.setNom(groupeName);
    groupe.setUtilisateurs(users);
    groupe.setIsNotEventGroup(true);
    groupe = em.merge(groupe);
    em.persist(groupe);
  }

  /**
   * Ajout d'un utilisateur dans la base de données
   *
   * @param user utilisateur à ajouter à la base de données
   */
  public void ajoutUtilisateur(Utilisateur user) {
    // user = em.merge(user);
    em.persist(user);
  }

  /**
   * Ajout à la base de données et renvoi d'une case
   *
   * @param maCaseDTO variable contenant les informations que l'on veut
   *                  transmettre à la nouvelle case
   * @return la case avec les informations transmises
   */
  public MaCase creerMaCase(MaCaseDTO maCaseDTO) {
    MaCase c = new MaCase();
    c.setId(maCaseDTO.getId());
    c.setDebutCreneau(maCaseDTO.getDebutCreneau());
    c.setFinCreneau(maCaseDTO.getFinCreneau());
    c.setSalleC(trouverSalle(maCaseDTO.getSalleC().getId()));

    em.persist(c);
    return c;
  }

  /**
   * Transforme l'objet MaCaseDTO en un entity bean correspondant MaCase
   *
   * @return l'entity MaCase associé ou crée un nouvel entity s'il n'existe pas
   */
  public MaCase toMaCase(MaCaseDTO c) {
    MaCase result = trouverMaCase(c.getId());
    if (result == null)
      return creerMaCase(c);
    else {
      result.setDebutCreneau(c.getDebutCreneau());
      result.setFinCreneau(c.getFinCreneau());
      result.setSalleC(trouverSalle(c.getSalleC().getId()));

      return result;
    }
  }

  /**
   * Ajout d'une salle dans la base de données
   *
   * @param s salle à ajouter à la base de données
   */
  public void creerSalle(Salle s) {
    em.merge(s);
  }

  /**
   * Ajout à la base de données et renvoi d'une salle
   *
   * @param salleDTO variable contenant les informations que l'on veut transmettre
   *                 à la nouvelle salle
   * @return la salle avec les informations transmises
   */
  public Salle creerSalle(SalleDTO salleDTO) {
    Salle s = new Salle();
    s.setId(salleDTO.getId());
    s.setEtablissementS(trouverEtablissement(salleDTO.getEtablissementS()));
    s.setBatiment(salleDTO.getBatiment());
    s.setNumero(salleDTO.getNumero());
    s.setCapacite(salleDTO.getCapacite());

    em.merge(s);
    return s;
  }

  /**
   * Transforme l'objet DTO en un entity bean correspondant
   *
   * @return l'entity bean associé ou crée un nouvel entity s'il n'existe pas
   */
  public Salle toSalle(SalleDTO s) {
    Salle result = trouverSalle(s.getId());
    if (result == null)
      return creerSalle(s);
    else {
      result.setId(s.getId());
      result.setEtablissementS(trouverEtablissement(s.getEtablissementS()));
      result.setBatiment(s.getBatiment());
      result.setNumero(s.getNumero());
      result.setCapacite(s.getCapacite());

      return result;
    }
  }

  /**
   * Ajout à la base de données et renvoi d'un événement
   *
   * @param name nom de l'événement à créer
   * @return l'événement nouvellement créé
   */
  public Evenement ajoutEvenement(String name) {
    Groupe g = new Groupe();
    g.setNom(name);
    g.setIsNotEventGroup(false);
    em.persist(g);

    Evenement event = new Evenement();
    event.setNom(name);
    event.setGroupeE(g);
    em.persist(event);

    return event;
  }

  /**
   * Fonction vérifiant si l'utilisateur a bien rentré le mdp correspondant à
   * l'adresse mail
   *
   * @param mail     adresse mail entrée
   * @param password mot de passe entré
   * @return l'utilisateur si le mdp et l'adresse correspondent, null sinon
   */
  public Utilisateur verifierUtilisateur(String mail, String password) {
    Utilisateur user = em.find(Utilisateur.class, mail);
    if (user != null) {
      if (user.getPassword().equals(password)) {
        return user;
      }
    }
    return null;
  }

  /**
   * Fonction vérifiant si le mail entré correspond à celui d'un utilisateur
   * inscrit dans la base de données
   *
   * @param mail adresse mail entrée
   * @return true si le mail existe, false sinon
   */
  public boolean verifierMail(String mail) {
    Utilisateur user = em.find(Utilisateur.class, mail);
    if (user != null) {
      return (user.getMail().equals(mail));
    } else {
      return false;
    }
  }

  /**
   * Ajout à la base de données un nouvel événement
   *
   * @param name nom du nouvel événement
   */
  public void ajoutEtablissement(String name) {
    Etablissement etab = new Etablissement();
    etab.setNom(name);
    etab = em.merge(etab);
    em.persist(etab);
  }

  /**
   * Ajout d'une nouvelle salle dans la base de données
   *
   * @param bat    bâtiment de la salle
   * @param num    numéro de la salle
   * @param cap    capacité de la salle
   * @param idEtab identifiant de l'établissement contenant la salle
   */
  public void ajoutSalle(String bat, int num, int cap, int idEtab) {
    Salle salle = new Salle();
    salle.setBatiment(bat);
    salle.setNumero(num);
    salle.setCapacite(cap);
    Etablissement etab = em.find(Etablissement.class, idEtab);
    salle.setEtablissementS(etab);
    salle = em.merge(salle);
    em.persist(salle);
  }

  /**
   * Fonction de récupération de la liste des utilisateurs
   *
   * @return liste des utilisateurs
   */
  public List<Utilisateur> listeUsers() {
    return em.createQuery("SELECT u FROM Utilisateur u",
        Utilisateur.class).getResultList();
  }

  /**
   * Fonction de récupération de la liste des groupes
   *
   * @return liste des groupes
   */
  public List<Groupe> listeGroupes() {
    return em.createQuery("SELECT g FROM Groupe g",
        Groupe.class).getResultList();
  }

  /**
   * Fonction de récupération de la liste des salles
   *
   * @return liste des salles
   */
  public List<Salle> listeSalles() {
    return em.createQuery("SELECT s FROM Salle s",
        Salle.class).getResultList();
  }

  /**
   * Fonction de récupération de la liste des établissements
   *
   * @return liste des établissements
   */
  public List<Etablissement> listeEtablissements() {
    return em.createQuery("SELECT e FROM Etablissement e",
        Etablissement.class).getResultList();
  }

  /**
   * Fonction de récupération de la liste des événements
   *
   * @return liste des événements
   */
  public List<Evenement> listeEvenements() {
    return em.createQuery("SELECT e FROM Evenement e",
        Evenement.class).getResultList();
  }

  /**
   * Fonction de récupération de la liste des choix d'un utilisateur donné pour un
   * événement
   *
   * @param eventId événement pour lequel ont veut récupérer les choix
   * @param mail    utilisateur ayant réalisé les choix
   * @return liste des choix
   */
  public List<Choix> listeChoixEventUser(int eventId, String mail) {
    return em.createQuery(
        "SELECT c FROM Choix c JOIN c.utilisateurCh user JOIN c.caseCh caseselect JOIN caseselect.evenementC ev WHERE user.mail=:usermail AND ev.id=:eventid",
        Choix.class).setParameter("usermail", mail).setParameter("eventid", eventId).getResultList();
  }

  /**
   * Fonction de récupération d'un événement à partir de son identifiant
   *
   * @param eventid identifiant de l'événement
   * @return l'événement correspondant à l'id passé en paramètre
   */
  public Evenement trouverEvenement(int eventid) {
    return em.find(Evenement.class, eventid);
  }

  /**
   * Fonction de récupération d'un établissement à partir de son identifiant
   *
   * @param id identifiant de l'établissement
   * @return l'établissement correspondant à l'id passé en paramètre
   */
  public Etablissement trouverEtablissement(int id) {
    return em.find(Etablissement.class, id);
  }

  /**
   * Fonction de récupération d'un utilisateur à partir de son adresse mail
   *
   * @param mail adresse mail de l'utilisateur
   * @return l'utilisateur correspondant à l'adresse mail passée en paramètres
   */
  public Utilisateur trouverUtilisateur(String email) {
    return em.find(Utilisateur.class, email);
  }

  /**
   * Fonction de récupération d'une salle à partir de son identifiant
   *
   * @param salleId identifiant de la salle
   * @return la salle correspondant à l'id passé en paramètres
   */
  public Salle trouverSalle(int salleId) {
    return em.find(Salle.class, salleId);
  }

  /**
   * Fonction de récupération d'une case à partir de son identifiant
   *
   * @param caseId identifiant de la case
   * @return la case correspondant à l'id passé en paramètres
   */
  public MaCase trouverMaCase(int caseId) {
    return em.find(MaCase.class, caseId);
  }

  /**
   * Fonction de récupération d'un groupe à partir de son identifiant
   *
   * @param groupeId identifiant du groupe
   * @return le groupe correspondant à l'id passé en paramètres
   */
  public Groupe trouverGroupe(int groupeId) {
    return em.find(Groupe.class, groupeId);
  }

  /**
   * Fonction récupérant la liste des utilisateurs d'un groupe donné
   *
   * @param groupe groupe dont on veut récupérer la liste des utilisateurs
   * @return la liste des utilisateurs du groupe passé en paramètre
   */
  public Set<Utilisateur> getUsersGroup(Groupe groupe) {
    return groupe.getUtilisateurs();
  }

  /**
   * Fonction ajoutant une liste de cases à un événement (tous deux existants)
   *
   * @param event événement cible de l'ajout de cases
   * @param cases cases à ajouter
   */
  public void addCaseToEvent(Evenement event, Collection<MaCase> cases) {
    /*
     * attention : le sens de la relation impose de modifier
     * en passant par les cases et non par l'event
     */
    for (MaCase c : cases) {
      c.setEvenementC(event);
      em.merge(c);
    }
  }

  /**
   * Fonction permettant à un utilisateur de faire un nouveau choix
   *
   * @param mail   adresse mail de l'utilisateur
   * @param caseId case pour laquelle l'utilisateur pourra exprimer un choix
   * @param note   note attribuée
   * @throws PermissionRefuseeException si l'utilisateur n'est pas dans le groupe
   *                                    invité à l'événement
   */
  public void addChoixToUser(String mail, int caseId, int note) throws PermissionRefuseeException {
    MaCase c = trouverMaCase(caseId);
    Utilisateur u = trouverUtilisateur(mail);

    /*
     * on verifie d'abord si l'utilisateur appartient bien au groupe
     * de l'événement pour avoir le droit de faire cette opération
     */
    Groupe groupeEvent = c.getEvenementC().getGroupeE();
    if (groupeEvent.getUtilisateurs().contains(u)) {
      Choix newChoix = new Choix();
      newChoix.setUtilisateurCh(u);
      newChoix.setCaseCh(c);
      newChoix.setNote(note);
      em.merge(newChoix);
    } else {
      throw new PermissionRefuseeException("ERROR: Permission denied : not in event group");
    }
  }

  public void delCaseToEvent(Evenement event, Collection<MaCase> cases) {
    /*
     * attention : le sens de la relation impose de modifier
     * en passant par les cases et non par l'event
     */
    for (MaCase c : cases) {
      c = em.merge(c);
      em.remove(c);
    }
  }

  public void addSalleToCase(MaCase creneau, Salle salle) {
    creneau.setSalleC(salle);
    // em.merge(creneau);
  }

  public Collection<Utilisateur> addUserEvent(int event, String email) {
    Evenement e = this.trouverEvenement(event);
    Utilisateur u = this.trouverUtilisateur(email);
    Groupe g = e.getGroupeE();
    g.addUser(u);
    return g.getUtilisateurs();
  }

  public void addUserFromGroup(int eventId, int groupeId) {
    Evenement event = em.find(Evenement.class, eventId);
    Groupe groupe = em.find(Groupe.class, groupeId);
    if (!(groupe == null)) {
      if (!(event == null)) {
        for (Utilisateur u : groupe.getUtilisateurs()) {
          event.getGroupeE().addUser(u);
        }
      }
    }
  }

  public void addUsersFromGroupToGroup(int newGroupeId, int groupeId) {
    Groupe newGroupe = trouverGroupe(newGroupeId);
    Groupe groupe = trouverGroupe(groupeId);
    if (!(newGroupe == null)) {
      if (!(groupe == null)) {
        for (Utilisateur u : groupe.getUtilisateurs()) {
          newGroupe.addUser(u);
        }
      }
    }
  }

  public void addUserToGroup(int groupeId, String mail) {
    Groupe groupe = em.find(Groupe.class, groupeId);
    Utilisateur user = em.find(Utilisateur.class, mail);
    if (!(groupe == null)) {
      if (!(user == null)) {
        groupe.getUtilisateurs().add(user);
      }
    }
  }

  public void delUsersFromGroupEvent(int eventId, int groupeId) {
    Evenement event = em.find(Evenement.class, eventId);
    Groupe groupe = em.find(Groupe.class, groupeId);
    if (!(groupe == null)) {
      if (!(event == null)) {
        for (Utilisateur u : groupe.getUtilisateurs()) {
          if (event.getGroupeE().getUtilisateurs().contains(u)) {
            event.getGroupeE().delUser(u);
          }
        }
      }
    }
  }

  public void delUsersFromGroupOfGroup(int newGroupeId, int groupeId) {
    Groupe newGroupe = trouverGroupe(newGroupeId);
    Groupe groupe = trouverGroupe(groupeId);
    if (!(groupe == null)) {
      if (!(newGroupe == null)) {
        for (Utilisateur u : groupe.getUtilisateurs()) {
          if (newGroupe.getUtilisateurs().contains(u)) {
            newGroupe.delUser(u);
          }
        }
      }
    }
  }

  public Collection<Utilisateur> delUserEvent(int event, String email) {
    Evenement e = this.trouverEvenement(event);
    Utilisateur u = this.trouverUtilisateur(email);
    Groupe g = e.getGroupeE();
    g.delUser(u);
    return g.getUtilisateurs();
  }

  public Collection<Utilisateur> delUserOfGroup(int groupeId, String email) {
    Groupe g = this.trouverGroupe(groupeId);
    Utilisateur u = this.trouverUtilisateur(email);
    g.delUser(u);
    return g.getUtilisateurs();
  }

  public void delUserFromGroup(Groupe groupe, Utilisateur user) {
    if (groupe.getUtilisateurs().contains(user)) {
      groupe.getUtilisateurs().remove(user);
      // em.merge(groupe); ?
    }
  }

  public void delCaseFromEvent(Evenement event, MaCase creneau) {
    if (event.getCases().contains(creneau)) {
      event.getCases().remove(creneau);
      // em.merge(event); ?
    }
  }

  /**
   * Affecter un créneau à chaque membre de l'événement
   *
   * @param event l'événement sur lequel on lance l'algorithme
   */
  public void AffecterCreneaux(Evenement event) {

    /* On efface les affectations existantes */
    for (MaCase creneau : event.getCases()) {
      creneau.setUsagerChoisi(null);
      em.merge(creneau); /* pour valider la modif */
    }

    /*
     * On va affecter à chaque utilisateur le premier créneau libre
     * dans l'ordre de ses choix
     */
    for (Utilisateur u : event.getGroupeE().getUtilisateurs()) {
      /*
       * On ne peut pas créer de SortedSet sur des Choix mais uniquement
       * sur des ChoixDTO.
       * Explication: J'ai pas voulu override les méthodes de Choix: compareTo,
       * hashcode et equals pour pas créer de bug avec les JPA
       */
      Set<ChoixDTO> choixTotauxUser = new HashSet<>();

      /*
       * ratacher l'user au PersistenceContext
       * sinon on peut pas faire getChoix
       */
      Utilisateur user = em.merge(u);

      /* On ajoute les cases que l'utilisateur a noté */
      for (Choix c : user.getChoix())
        choixTotauxUser.add(new ChoixDTO(c));

      /*
       * On ré-ajoute toutes les cases en créant des choix fictifs au cas
       * où l'utilisateur a oublié de noter certaines.
       * Le SortedSet assure que si on a déjà mis le choix, il ne sera pas
       * re-ajouté
       */
      for (MaCase creneau : event.getCases()) {
        /*
         * On crée virtuellement un choix qu'on convertit ensuite en ChoixDTO.
         * Cette entitée ne sera pas ajoutée à la base de données
         */
        Choix c = new Choix();
        c.setUtilisateurCh(user);
        c.setCaseCh(creneau);
        c.setNote(Choix.NOTE_DEFAUT); /* note par défaut */
        choixTotauxUser.add(new ChoixDTO(c)); /*
                                               * le set ajoute uniquement
                                               * si n'existe pas déjà
                                               */
      }

      /* On va maintenant trier par ordre décroissant de notes */
      List<ChoixDTO> choixTries = new ArrayList<>();
      choixTries.addAll(choixTotauxUser);
      Collections.sort(choixTries, new ChoixDTO.ChoixDTONotesComparator());

      /*
       * On parcourt maintenant les ChoixDTO (triés par ordre décroissant de
       * notes grace au SortedSet)
       */
      for (ChoixDTO c : choixTries) {
        MaCase realCase = trouverMaCase(c.getCaseCh().getId());
        if (realCase.getUsagerChoisi() == null) {
          realCase.setUsagerChoisi(user);
          em.merge(realCase); /* pas sur que ce soit nécessaire */
          break; /* passer à l'user suivant */
        }
      }

    }
  }

  public void userLoader() {
    try (BufferedReader userReader = new BufferedReader(
        new FileReader("Volundr/src/main/webapp/loaderFiles/user.txt"))) {
      String ligne;

      while ((ligne = userReader.readLine()) != null) {
        String[] mots = ligne.split(" ");
        String prenom = mots[0];
        String nom = mots[1];
        String mail = mots[2];
        String password = mots[3];
        boolean isAdmin = false;
        if (mots.length > 4) {
          isAdmin = Boolean.parseBoolean(mots[4]);
        }
        ajoutUtilisateur(nom, prenom, mail, password, isAdmin);
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void etablissementLoader() {
    try (BufferedReader etablissementReader = new BufferedReader(
        new FileReader("Volundr/src/main/webapp/loaderFiles/etablissement.txt"))) {
      String ligne;

      while ((ligne = etablissementReader.readLine()) != null) {
        ajoutEtablissement(ligne);
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void salleLoader() {
    try (BufferedReader etablissementReader = new BufferedReader(
        new FileReader("Volundr/src/main/webapp/loaderFiles/salle.txt"))) {
      String ligne;

      while ((ligne = etablissementReader.readLine()) != null) {
        String[] mots = ligne.split(" ");
        int etab = Integer.parseInt(mots[0]);
        String bat = mots[1];
        int num = Integer.parseInt(mots[2]);
        int cap = Integer.parseInt(mots[3]);
        ajoutSalle(bat, num, cap, etab);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void groupLoader() {
    try (BufferedReader etablissementReader = new BufferedReader(
        new FileReader("Volundr/src/main/webapp/loaderFiles/groupe.txt"))) {
      String ligne;

      while ((ligne = etablissementReader.readLine()) != null) {
        String[] mots1 = ligne.split("\\|");
        String groupeName = mots1[0];
        String[] mots2 = mots1[1].split(" ");
        Set<Utilisateur> users = new HashSet<>();
        for (String mail : mots2) {
          Utilisateur user = trouverUtilisateur(mail);
          users.add(user);
        }
        ajoutGroupe(groupeName, users);
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void loader() {
    if (em.find(Etablissement.class, 1) == null) {
      userLoader();
      etablissementLoader();
      salleLoader();
      groupLoader();
    }
  }

  public void setDebutDate(int id, String help) {
    Evenement event = trouverEvenement(id);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      event.setDebutInscr(dateFormat.parse(help));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      event.setNom(e.getMessage());
      e.printStackTrace();
    }
  }

  public void setFinDate(int id, String help) {
    Evenement event = trouverEvenement(id);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      event.setFinInscr(dateFormat.parse(help));

    } catch (ParseException e) {
      // TODO Auto-generated catch block
      event.setNom(e.getMessage());
      e.printStackTrace();
    }
  }
}
