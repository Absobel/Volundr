package pack;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Utilisateur {

    @Id
    private String mail;

    private String nom;

    private String prenom;

    private String password; /* probablement à changer */

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
