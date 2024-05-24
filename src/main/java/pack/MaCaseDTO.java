package pack;

import java.io.Serializable;

public class MaCaseDTO implements Serializable {

    private int id;

    /**
     * Début du créneau de la case.
     */
    private int debutCreneau;

    /**
     * Fin du créneau de la case.
     */
    private int finCreneau;

    public MaCaseDTO(MaCase c) {
      this.id = c.getId();
      this.debutCreneau = c.getDebutCreneau();
      this.finCreneau = c.getFinCreneau();
    }

    public int getId() {
      return id;
    }

    public int getDebutCreneau() {
      return debutCreneau;
    }

    public int getFinCreneau() {
      return finCreneau;
    }

}
