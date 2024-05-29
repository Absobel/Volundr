package dataTransfert;

import java.util.Comparator;
import model.Choix;

public class ChoixDTO {

  /**
   * Un Comparator pour trier par notes décroissantes: On peut pas définir
   * une fonction de comparaison directement sur la classe ChoixDTO
   * car on romprait la condition d'égalité
   */
  public static class ChoixDTONotesComparator implements Comparator<ChoixDTO> {

    @Override
    public int compare(ChoixDTO o1, ChoixDTO o2) {
      return -((Integer) o1.getNote()).compareTo(o2.getNote());
    }
  }

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

  /*
   * On considère deux choix égaux si leurs clés primaires sont égales
   * ATTENTION: On doit comparer les clés primaires det ObjetDTO et non les
   * ObjetDTO eux-même car on peut en créer pleins qui représentent
   * la même entitée
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj.getClass() != this.getClass()) {
      return false;
    }

    final ChoixDTO other = (ChoixDTO) obj;
    if (!this.getUtilisateurCh().getMail().equals(other.getUtilisateurCh().getMail())) {
      return false;
    }

    if (this.getCaseCh().getId() != other.getCaseCh().getId()) {
      return false;
    }

    return true;
  }

  /*
   * On considère deux choix égaux si leurs clés primaires sont égales
   * ATTENTION: On doit comparer les clés primaires det ObjetDTO et non les
   * ObjetDTO eux-même car on peut en créer pleins qui représentent
   * la même entitée
   */
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
