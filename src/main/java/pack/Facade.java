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

  private Utilisateur userSession;

  public Utilisateur getUserSession() {
    return userSession;
  }

  public void setUserSession(Utilisateur userSession) {
    this.userSession = userSession;
  }

  public void ajoutUtilisateur(String nom, String prenom, String mail, String password) {
    Utilisateur user = new Utilisateur();
    user.setNom(nom);
    user.setPrenom(prenom);
    user.setMail(mail);
    user.setPassword(password);
    user = em.merge(user);
    em.persist(user);
  }

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

  public void ajoutGroupe(Groupe groupe) {
    em.merge(groupe);
  }

  public Groupe ajoutGroupe(String groupeName) {
    Groupe groupe = new Groupe();
    groupe.setNom(groupeName);
    // em.merge(groupe);
    em.persist(groupe);
    return groupe;
  }

  public void ajoutGroupe(String groupeName, Set<Utilisateur> users) {
    Groupe groupe = new Groupe();
    groupe.setNom(groupeName);
    groupe.setUtilisateurs(users);
    groupe.setIsNotEventGroup(true);
    groupe = em.merge(groupe);
    em.persist(groupe);
  }

  public void ajoutUtilisateur(Utilisateur user) {
    // user = em.merge(user);
    em.persist(user);
  }

  public Utilisateur updateUserSession() {
    String mail = this.getUserSession().getMail();
    Utilisateur updatedUser = trouverUtilisateur(mail);
    setUserSession(updatedUser);
    return updatedUser;
  }

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

  public void creerSalle(Salle s) {
    em.merge(s);
  }

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

  public boolean verifierUtilisateur(String mail, String password) {
    Utilisateur user = em.find(Utilisateur.class, mail);
    if (user != null) {
      if (user.getPassword().equals(password)) {
        setUserSession(user);
        return true;
      }
    }
    return false;
  }

  public boolean verifierMail(String mail) {
    Utilisateur user = em.find(Utilisateur.class, mail);
    if (user != null) {
      return (user.getMail().equals(mail));
    } else {
      return false;
    }
  }

  public void ajoutEtablissement(String name) {
    Etablissement etab = new Etablissement();
    etab.setNom(name);
    etab = em.merge(etab);
    em.persist(etab);
  }

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

  public List<Utilisateur> listeUsers() {
    return em.createQuery("SELECT u FROM Utilisateur u",
        Utilisateur.class).getResultList();
  }

  public List<Groupe> listeGroupes() {
    return em.createQuery("SELECT g FROM Groupe g",
        Groupe.class).getResultList();
  }

  public List<Salle> listeSalles() {
    return em.createQuery("SELECT s FROM Salle s",
        Salle.class).getResultList();
  }

  public List<Etablissement> listeEtablissements() {
    return em.createQuery("SELECT e FROM Etablissement e",
        Etablissement.class).getResultList();
  }

  public List<Evenement> listeEvenements() {
    return em.createQuery("SELECT e FROM Evenement e",
        Evenement.class).getResultList();
  }

  public List<Choix> listeChoixEventUser(int eventId, String mail) {
    return em.createQuery(
        "SELECT c FROM Choix c JOIN c.utilisateurCh user JOIN c.caseCh caseselect JOIN caseselect.evenementC ev WHERE user.mail=:usermail AND ev.id=:eventid",
        Choix.class).setParameter("usermail", mail).setParameter("eventid", eventId).getResultList();
  }

  public Evenement trouverEvenement(int eventid) {
    return em.find(Evenement.class, eventid);
  }

  public Etablissement trouverEtablissement(int id) {
    return em.find(Etablissement.class, id);
  }

  public Utilisateur trouverUtilisateur(String email) {
    return em.find(Utilisateur.class, email);
  }

  public Salle trouverSalle(int salleId) {
    return em.find(Salle.class, salleId);
  }

  public MaCase trouverMaCase(int caseId) {
    return em.find(MaCase.class, caseId);
  }

  public Groupe trouverGroupe(int groupeId) {
    return em.find(Groupe.class, groupeId);
  }

  public Set<Utilisateur> getUsersGroup(Groupe groupe) {
    return groupe.getUtilisateurs();
  }

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
