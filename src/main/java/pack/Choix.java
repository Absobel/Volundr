package pack;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Choix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Utilisateur concerné par le choix.
     */
    @ManyToOne
    private Utilisateur utilisateurCh;

    /**
     * Case visée par le choix.
     */
    @ManyToOne
    private Case caseCh;

    /**
     * Note attribuée à la case.
     */
    private int note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateurCh() {
        return utilisateurCh;
    }

    public void setUtilisateurCh(Utilisateur utilisateurCh) {
        this.utilisateurCh = utilisateurCh;
    }

    public Case getCaseCh() {
        return caseCh;
    }

    public void setCaseCh(Case caseCh) {
        this.caseCh = caseCh;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

}
