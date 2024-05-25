package pack;

import java.util.Set;

public class GroupeDTO {

    private int id;

    /**
     * Liste des Utilisateurs du groupe.
     */
    private Set<Utilisateur> Utilisateurs;

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
        this.Utilisateurs = groupe.getUtilisateurs();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsNotEventGroup() {
        return isNotEventGroup;
    }

    public void setIsNotEventGroup(boolean isNotEventGroup) {
        this.isNotEventGroup = isNotEventGroup;
    }

    public Set<Utilisateur> getUtilisateurs() {
        return Utilisateurs;
    }

    public void setUtilisateurs(Set<Utilisateur> Utilisateurs) {
        this.Utilisateurs = Utilisateurs;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void addUser(Utilisateur user) {
        this.getUtilisateurs().add(user);
    }

    public void delUser(Utilisateur user) {
        this.getUtilisateurs().remove(user);
    }

}
