package pack;

import java.io.Serializable;

public class UtilisateurDTO implements Serializable {

    private String mail;

    private String nom;

    private String prenom;

    private String password; /* probablement à changer */

    private Etablissement etablissement;

    public UtilisateurDTO(Utilisateur user) {
      this.mail = user.getMail();
      this.nom = user.getNom();
      this.prenom = user.getPrenom();
      this.password = user.getPassword();
      this.etablissement = user.getEtablissement();
    }

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

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

}