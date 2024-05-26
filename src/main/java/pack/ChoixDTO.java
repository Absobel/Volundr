package pack;

public class ChoixDTO {

    /**
     * Utilisateur concerné par le choix.
     */
    private UtilisateurDTO utilisateurCh;

    /**
     * Case visée par le choix.
     */
    private MaCaseDTO caseCh;

    /**
     * Note attribuée à la case.
     */
    private int note;

    public ChoixDTO(Choix choix) {
      this.utilisateurCh = new UtilisateurDTO(choix.getUtilisateurCh());
      this.caseCh = new MaCaseDTO(choix.getCaseCh());
      this.note = choix.getNote();
    }

    public UtilisateurDTO getUtilisateurCh() {
        return utilisateurCh;
    }

    public MaCaseDTO getCaseCh() {
        return caseCh;
    }

    public int getNote() {
        return note;
    }

}
