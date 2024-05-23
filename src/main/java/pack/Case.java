package pack;

import java.sql.Time;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Salle du créneau.

    @ManyToOne
    private Salle salleC;

    // Liste de choix faits/à faire des utilisateurs ayant accès à cette case.
    @OneToMany(mappedBy = "caseCh", fetch = FetchType.EAGER)
    private List<Choix> choix;

    /**
     * Liste des utilisateurs ayant accès à cette case.
     */
    @ManyToMany
    private List<Utilisateur> utilisateursC;

    /**
     * Évènement dont la case fait partie.
     */
    @ManyToOne
    private Evenement evenementC;

    /**
     * Début du créneau de la case.
     */
    private Time debutCreaneau;

    /**
     * Fin du créneau de la case.
     */
    private Time finCreneau;

    /**
     * Usager choisi par l'algorithme de décision.
     */
    private Utilisateur usagerChoisi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Salle getSalleC() {
        return salleC;
    }

    public void setSalleC(Salle salleC) {
        this.salleC = salleC;
    }

    public List<Choix> getChoix() {
        return choix;
    }

    public void setChoix(List<Choix> choix) {
        this.choix = choix;
    }

    public List<Utilisateur> getutilisateursC() {
        return utilisateursC;
    }

    public void setutilisateursC(List<Utilisateur> utilisateursC) {
        this.utilisateursC = utilisateursC;
    }

    public Evenement getEvenementC() {
        return evenementC;
    }

    public void setEvenementC(Evenement evenementC) {
        this.evenementC = evenementC;
    }

    public Time getDebutCreaneau() {
        return debutCreaneau;
    }

    public void setDebutCreaneau(Time debutCreaneau) {
        this.debutCreaneau = debutCreaneau;
    }

    public Time getFinCreneau() {
        return finCreneau;
    }

    public void setFinCreneau(Time finCreneau) {
        this.finCreneau = finCreneau;
    }

    public Utilisateur getUsagerChoisi() {
        return usagerChoisi;
    }

    public void setUsagerChoisi(Utilisateur usagerChoisi) {
        this.usagerChoisi = usagerChoisi;
    }

}
