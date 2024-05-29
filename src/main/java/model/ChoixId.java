package model;

import java.io.Serializable;

/**
 * ChoixId: la clé composite de la classe Choix
 * l'avantage d'utiliser une clé composite, c'est qu'on va pouvoir éviter
 * d'avoir des doublons.
 * si on fait un nouveau choix avec le même user et la même case, Java va
 * automatiquement remplacer l'ancien choix au lieu de créer une nouvelle entrée
 */
public class ChoixId implements Serializable {

  private Utilisateur utilisateurCh;

  private MaCase caseCh;

  public ChoixId() {
    /* Ne pas oublier le constructeur vide pour JBoss */
  }

  public ChoixId(Utilisateur utilisateurCh, MaCase caseCh) {
    this.utilisateurCh = utilisateurCh;
    this.caseCh = caseCh;
  }

  public Utilisateur getUtilisateurCh() {
    return utilisateurCh;
  }

  public MaCase getCaseCh() {
    return caseCh;
  }
}
