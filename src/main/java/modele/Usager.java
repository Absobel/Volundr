package modele;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Usager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Etablissement auquel l'usager appartient.
     */
    @ManyToOne
    private Etablissement etablissementU;

    /**
     * Groupes auxquels l'usager appartient.
     */
    @ManyToMany
    private List<Groupe> groupesU;

    /**
     * Choix faits et à faire.
     */
    @OneToMany(mappedBy = "usagerCh", fetch = FetchType.EAGER)
    private List<Choix> choix;

    /**
     * Cases accessibles à l'usager.
     */
    @ManyToMany(mappedBy = "usagersC")
    private List<Case> cases;

    /** 
     * Nom de l'usager.
     */
    private String nom;

    /**
     * Prenom de l'usager.
     */
    private String prenom;

    /**
     * Adresse mail de l'usager.
     */
    private String mail;

    /**
     * Mot de passe de l'usager.
     */
    private String motDePasse;

    /**
     * Vaut true si l'utilisateur est un admin.
     */
    private boolean isAdmin;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Etablissement getEtablissementU() {
        return etablissementU;
    }

    public void setEtablissementU(Etablissement etablissementU) {
        this.etablissementU = etablissementU;
    }

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

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

}
