package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public void ajoutUtilisateur(Utilisateur user) {
        // user = em.merge(user);
        em.persist(user);
    }

    public Evenement ajoutEvenement(String name) {
        Evenement event = new Evenement();
        event.setNom(name);
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

    public List<Etablissement> listeEtablissements() {
        return em.createQuery("SELECT e FROM Etablissement e",
                Etablissement.class).getResultList();
    }

    public List<Utilisateur> getUsersGroup(Groupe groupe) {
        return groupe.getUtilisateurs();
    }

    public void addCaseToEvent(Evenement event, Set<Case> cases) {
        event.getCases().addAll(cases);
        // em.merge(event);
    }

    public void addSalleToCase(Case creneau, Salle salle) {
        creneau.setSalleC(salle);
        // em.merge(creneau);
    }

    public void delUserFromGroup(Groupe groupe, Utilisateur user) {
        if (groupe.getUtilisateurs().contains(user)) {
            groupe.getUtilisateurs().remove(user);
            // em.merge(groupe); ?
        }
    }

    public void delCaseFromEvent(Evenement event, Case creneau) {
        if (event.getCases().contains(creneau)) {
            event.getCases().remove(creneau);
            // em.merge(event); ?
        }
    }

    public void delSalleFromCase(Case creneau) {
        creneau.setSalleC(null);

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

    public void loader() {
        if (em.find(Etablissement.class, 1) == null) {
            userLoader();
            etablissementLoader();
            salleLoader();
        }
    }
}
