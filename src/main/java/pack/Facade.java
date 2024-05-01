package pack;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
public class Facade {

    @PersistenceContext
    private EntityManager em;

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
            return (user.getPassword().equals(password));
        }
        return false;
    }

    public boolean verifierMail(String mail){
        Utilisateur user = em.find(Utilisateur.class,mail);
        if (user != null) {
            return (user.getMail().equals(mail));
        } else {
            return false;
        }
    }
}
