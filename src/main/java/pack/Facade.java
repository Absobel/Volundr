package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
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
    em.persist(s);
  }

  public Salle creerSalle(SalleDTO salleDTO) {
    Salle s = new Salle();
    s.setId(salleDTO.getId());
    s.setEtablissementS(trouverEtablissement(salleDTO.getEtablissementS()));
    s.setBatiment(salleDTO.getBatiment());
    s.setNumero(salleDTO.getNumero());
    s.setCapacite(salleDTO.getCapacite());

    System.out.println("num" + salleDTO.getNumero());
    System.out.println("num2" + s.getNumero());

    em.persist(s);
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

    MaCase c = new MaCase();
    c.setDebutCreneau(0);
    c.setFinCreneau(1);
    c.setEvenementC(event);
    em.persist(c);

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

  public void addChoixToUser(String mail, int caseId, int note) {
    Choix newChoix = new Choix();
    newChoix.setUtilisateurCh(trouverUtilisateur(mail));
    newChoix.setCaseCh(trouverMaCase(caseId));
    newChoix.setNote(note);
    em.merge(newChoix);
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

  public void userLoader() {
    try (BufferedReader userReader = new BufferedReader(new FileReader("Volundr/src/main/webapp/user.txt"))) {
      String ligne;

      while ((ligne = userReader.readLine()) != null) {
        String[] mots = ligne.split(" ");
        String prenom = mots[0];
        String nom = mots[1];
        String mail = mots[2];
        String password = mots[3];
        ajoutUtilisateur(nom, prenom, mail, password);
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void etablissementLoader() {
    try (BufferedReader etablissementReader = new BufferedReader(
        new FileReader("Volundr/src/main/webapp/etablissement.txt"))) {
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
        new FileReader("Volundr/src/main/webapp/salle.txt"))) {
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
        new FileReader("Volundr/src/main/webapp/groupe.txt"))) {
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
}
