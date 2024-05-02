package modele;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Liste des usagers du groupe.
     */
    @ManyToMany(mappedBy = "groupesU")
    private List<Usager> usagers;

    /**
     * Liste des évènements auxquels le groupe participe.
     */
    @OneToMany(mappedBy = "groupeE", fetch = FetchType.EAGER)
    private List<Evenement> evenements;

    /**
     * Nom du groupe.
     */
    private String nom;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Usager> getUsagers() {
        return usagers;
    }

    public void setUsagers(List<Usager> usagers) {
        this.usagers = usagers;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}
