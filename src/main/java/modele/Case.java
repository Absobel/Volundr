package modele;

import java.util.*;
import java.sql.*;
import jakarta.persistence.*;

@Entity
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Salle du créneau.
     */
    @ManyToOne
    private Salle salleC;

    /**
     * Liste de choix faits/à faire des usagers ayant accès à cette case.
     */
    @OneToMany(mappedBy = "caseCh", fetch = FetchType.EAGER)
    private List<Choix> choix;

    /**
     * Liste des usagers ayant accès à cette case.
     */
    @ManyToMany
    private List<Usager> usagersC;

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
    private Usager usagerChoisi;


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

    public List<Usager> getUsagersC() {
        return usagersC;
    }

    public void setUsagersC(List<Usager> usagersC) {
        this.usagersC = usagersC;
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

    public Usager getUsagerChoisi() {
        return usagerChoisi;
    }

    public void setUsagerChoisi(Usager usagerChoisi) {
        this.usagerChoisi = usagerChoisi;
    }

}
