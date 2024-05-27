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

  /** L'identifiant de la salle à laquelle appartient la case */
  private SalleDTO salleC;

  /** L'Utilisateur à qui la case a été affectée */
  private UtilisateurDTO usagerChoisi;

  public MaCaseDTO() {
    /* ne pas oublier le constructeur par defaut pour JBoss */
  }

  public MaCaseDTO(MaCase c) {
    this.id = c.getId();
    this.debutCreneau = c.getDebutCreneau();
    this.finCreneau = c.getFinCreneau();
    this.salleC = new SalleDTO(c.getSalleC());
    this.usagerChoisi = c.getUsagerChoisi() == null ? null : new UtilisateurDTO(c.getUsagerChoisi());
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

  public SalleDTO getSalleC() {
    return salleC;
  }

  public UtilisateurDTO getUsagerChoisi() {
    return usagerChoisi;
  }
}
