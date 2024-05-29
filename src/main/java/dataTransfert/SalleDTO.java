package dataTransfert;

import java.io.Serializable;
import model.Salle;

public class SalleDTO implements Serializable {

  private int id;

  /**
   * Id de l'établissement de la salle.
   */
  private int etablissementS;

  /**
   * Bâtiment de la salle.
   */
  private String batiment;

  /**
   * Numéro de la salle;
   */
  private int numero;

  /**
   * Capacité de la salle.
   */
  private int capacite;

  public SalleDTO() {
    /* ne pas oublier le constructeur par defaut pour JBoss */
  }

  public SalleDTO(Salle s) {
    if (s == null)
      this.id = -1;
    else {
      this.id = s.getId();
      this.etablissementS = s.getEtablissementS() == null ? -1 : s.getEtablissementS().getId();
      this.batiment = s.getBatiment();
      this.numero = s.getNumero();
      this.capacite = s.getCapacite();
    }
  }

  public int getId() {
    return id;
  }

  public int getEtablissementS() {
    return etablissementS;
  }

  public String getBatiment() {
    return batiment;
  }

  public int getNumero() {
    return numero;
  }

  public int getCapacite() {
    return capacite;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setEtablissementS(int etablissementS) {
    this.etablissementS = etablissementS;
  }

  public void setBatiment(String batiment) {
    this.batiment = batiment;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public void setCapacite(int capacite) {
    this.capacite = capacite;
  }

}
