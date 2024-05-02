package modele;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Etablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Usagers appartenant à l'établissement.
     */
    @OneToMany(mappedBy = "etablissementU", fetch = FetchType.EAGER)
    private List<Usager> usagers;

    /**
     * Salles de l'étbalissement.
     */
    @OneToMany(mappedBy = "etablissementS", fetch = FetchType.EAGER)
    private List<Salle> salles;

    /**
     * Nom de l'étbalissement.
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

    public List<Salle> getSalles() {
        return salles;
    }

    public void setSalles(List<Salle> salles) {
        this.salles = salles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}
