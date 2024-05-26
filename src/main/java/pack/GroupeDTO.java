package pack;

import java.util.HashSet;
import java.util.Set;

public class GroupeDTO {

  private int id;

  /**
   * Liste des Utilisateurs du groupe.
   */
  private Set<UtilisateurDTO> Utilisateurs;

  /**
   * Liste des évènements auxquels le groupe participe.
   */
  // @OneToMany(mappedBy = "groupeE", fetch = FetchType.EAGER)
  // private List<Evenement> evenements;

  /**
   * Nom du groupe.
   */
  private String nom;

  public boolean isNotEventGroup = false;

  public GroupeDTO(Groupe groupe) {
    this.id = groupe.getId();
    this.nom = groupe.getNom();
    this.isNotEventGroup = groupe.getIsNotEventGroup();
    this.Utilisateurs = new HashSet<>();
    for (Utilisateur g : groupe.getUtilisateurs())
      this.Utilisateurs.add(new UtilisateurDTO(g));
  }

  public int getId() {
    return id;
  }

  public boolean getIsNotEventGroup() {
    return isNotEventGroup;
  }

  public Set<UtilisateurDTO> getUtilisateurs() {
    return Utilisateurs;
  }

  /*
   * public List<Evenement> getEvenements() {
   * return evenements;
   * }
   *
   * public void setEvenements(List<Evenement> evenements) {
   * this.evenements = evenements;
   * }
   */

  public String getNom() {
    return nom;
  }
}
