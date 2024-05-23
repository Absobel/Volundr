package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

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

    public Collection<Utilisateur> listeUsers() {
        return em.createQuery("SELECT u FROM Utilisateur u",
                Utilisateur.class).getResultList();
    }

    public Collection<Etablissement> listeEtablissements() {
        return em.createQuery("SELECT e FROM Etablissement e",
                Etablissement.class).getResultList();
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
