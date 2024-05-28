package pack;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Groupe invité à l'évènement.
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Groupe groupeE;

    /**
     * Liste des cases composant l'évènement.
     */
    @OneToMany(mappedBy = "evenementC", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<MaCase> cases;

    /**
     * Nom de l'évènement.
     */
    private String nom;

    private Date debutInscr;

    public Date getDebutInscr() {
        return debutInscr;
    }

    public void setDebutInscr(java.util.Date date) {
        this.debutInscr = date;
    }

    public Date getFinInscr() {
        return finInscr;
    }

    public void setFinInscr(java.util.Date date) {
        this.finInscr = date;
    }

    private Date finInscr;

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

    public Set<MaCase> getCases() {
        return cases;
    }

    public void setCases(Set<MaCase> cases) {
        this.cases = cases;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
