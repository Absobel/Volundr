package pack;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(ChoixId.class)
public class Choix {

    /**
     * Utilisateur concerné par le choix.
     */
    @Id
    @ManyToOne
    private Utilisateur utilisateurCh;

    /**
     * Case visée par le choix.
     */
    @Id
    @ManyToOne
    private MaCase caseCh;

    /**
     * Note attribuée à la case.
     */
    private int note;

    public Utilisateur getUtilisateurCh() {
        return utilisateurCh;
    }

    public void setUtilisateurCh(Utilisateur utilisateurCh) {
        this.utilisateurCh = utilisateurCh;
    }

    public MaCase getCaseCh() {
        return caseCh;
    }

    public void setCaseCh(MaCase caseCh) {
        this.caseCh = caseCh;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

}
