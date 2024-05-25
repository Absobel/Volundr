package pack;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Liste des Utilisateurs du groupe.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Utilisateur> Utilisateurs;

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

    public Set<Utilisateur> getUtilisateurs() {
        return Utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> Utilisateurs) {
        this.Utilisateurs = Utilisateurs;
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

    public void addUser(Utilisateur user) {
      this.getUtilisateurs().add(user);
    }

    public void delUser(Utilisateur user) {
      this.getUtilisateurs().remove(user);
    }

}
