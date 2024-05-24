package pack;

import java.util.List;

import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Etablissement de la salle.
     */
    @ManyToOne
    private Etablissement etablissementS;

    /**
     * Liste des cases pour cette salle.
     */
    @OneToMany(mappedBy = "salleC")
    private List<MaCase> cases;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Etablissement getEtablissementS() {
        return etablissementS;
    }

    public void setEtablissementS(Etablissement etablissementS) {
        this.etablissementS = etablissementS;
    }

    public List<MaCase> getCases() {
        return cases;
    }

    public void setCases(List<MaCase> cases) {
        this.cases = cases;
    }

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

}
