package modele;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Groupe invité à l'évènement.
     */
    @ManyToOne
    private Groupe groupeE;

    /**
     * Liste des cases composant l'évènement.
     */
    @OneToMany(mappedBy = "evenementC", fetch = FetchType.EAGER)
    private List<Case> cases;

    /**
     * Nom de l'évènement.
     */
    private String nom;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Groupe getGroupeE() {
        return groupeE;
    }

    public void setGroupeE(Groupe groupeE) {
        this.groupeE = groupeE;
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
    
}
