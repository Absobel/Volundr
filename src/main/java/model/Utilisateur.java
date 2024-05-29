package model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
//import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Utilisateur implements Serializable {

    @Id
    private String mail;

    private String nom;

    private String prenom;

    private String password; /* probablement à changer */

    private boolean isAdmin;

    /**
     * Groupes auxquels l'usager appartient.
     */
    @ManyToMany(mappedBy = "Utilisateurs", fetch = FetchType.EAGER)
    private List<Groupe> groupesU;

    /**
     * Choix faits et à faire.
     */
    @OneToMany(mappedBy = "utilisateurCh")
    private List<Choix> choix;

    /**
     * Cases accessibles à l'usager.
     */
    @ManyToMany(mappedBy = "utilisateursC")
    private List<MaCase> cases;

    /**
     * Les cases des différents événements auquels appartient l'utilisateur et
     * qui lui ont été affecté
     */
    @OneToMany(mappedBy = "usagerChoisi", fetch = FetchType.EAGER)
    private List<MaCase> casesAffectation;

    public List<Groupe> getGroupesU() {
        return groupesU;
    }

    public void setGroupesU(List<Groupe> groupesU) {
        this.groupesU = groupesU;
    }

    public List<Choix> getChoix() {
        return choix;
    }

    public void setChoix(List<Choix> choix) {
        this.choix = choix;
    }

    public List<MaCase> getCases() {
        return cases;
    }

    public void setCases(List<MaCase> cases) {
        this.cases = cases;
    }

    @ManyToOne
    private Etablissement etablissement;

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

    public List<MaCase> getCasesAffectation() {
        return casesAffectation;
    }

    public void setCasesAffectation(List<MaCase> casesAffectation) {
        this.casesAffectation = casesAffectation;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
