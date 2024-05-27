package pack;

public class ChoixDTO implements Comparable<ChoixDTO> {

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

    /** Permet de trier dans l'ordre décroissant des notes pour avoir
     * les créneaux les mieux notés en premier */
    @Override
    public int compareTo(ChoixDTO c) {
        float delta = this.note - c.note;
        if (delta > 0) {
            return -1;
        } else if (delta < 0) {
            return 1;
        } else {
          int ordreUsers = this.getUtilisateurCh().getMail().compareTo(c.getUtilisateurCh().getMail());
          if (ordreUsers != 0)
            return ordreUsers;

          return ((Integer) this.getCaseCh().getId()).compareTo(c.getCaseCh().getId());
        }
    }

    /* On considère deux choix égaux si leurs clés primaires sont égales
     * ATTENTION: On doit comparer les clés primaires det ObjetDTO et non les
     * ObjetDTO eux-même car on peut en créer pleins qui représentent
     * la même entitée */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final ChoixDTO other = (ChoixDTO) obj;
        if ((this.getUtilisateurCh() == null) ? (other.getUtilisateurCh() != null) : !this.getUtilisateurCh().equals(other.getUtilisateurCh())) {
            return false;
        }

        if ((this.getCaseCh() == null) ? (other.getCaseCh() != null) : !(this.getCaseCh().getId() == getCaseCh().getId())) {
            return false;
        }

        return true;
    }

    /* On considère deux choix égaux si leurs clés primaires sont égales
     * ATTENTION: On doit comparer les clés primaires det ObjetDTO et non les
     * ObjetDTO eux-même car on peut en créer pleins qui représentent
     * la même entitée */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.utilisateurCh != null ? this.utilisateurCh.getMail() : 0).hashCode();
        hash = 53 * hash + (this.caseCh != null ? this.caseCh.getId() : 0);
        return hash;
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
