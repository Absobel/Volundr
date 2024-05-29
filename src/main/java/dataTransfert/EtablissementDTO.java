package dataTransfert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import model.Etablissement;
import model.Salle;
import model.Utilisateur;

public class EtablissementDTO {

    private int id;

    private String nom;

    private Collection<UtilisateurDTO> utilisateurs;

    /**
     * Salles de l'étbalissement.
     */
    private List<SalleDTO> salles;

    public EtablissementDTO(Etablissement etablissement) {
        this.id = etablissement.getId();
        this.nom = etablissement.getNom();
        this.utilisateurs = new ArrayList<>();
        for (Utilisateur utilisateur : etablissement.getUtilisateurs()) {
            this.utilisateurs.add(new UtilisateurDTO(utilisateur));
        }
        this.salles = new ArrayList<>();
        for (Salle salle : etablissement.getSalles()) {
            this.salles.add(new SalleDTO(salle));
        }
    }

    public List<SalleDTO> getSalles() {
        return salles;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Collection<UtilisateurDTO> getUtilisateurs() {
        return utilisateurs;
    }
}
