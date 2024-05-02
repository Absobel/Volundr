package modele;

import jakarta.persistence.*;

@Entity
public class Choix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Usager concerné par le choix.
     */
    @ManyToOne
    private Usager usagerCh;

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

    public Usager getUsagerCh() {
        return usagerCh;
    }

    public void setUsagerCh(Usager usagerCh) {
        this.usagerCh = usagerCh;
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
