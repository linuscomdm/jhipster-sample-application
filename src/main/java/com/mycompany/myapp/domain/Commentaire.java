package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Commentaire.
 */
@Entity
@Table(name = "commentaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "texte")
    private String texte;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "traductions", "traductionsPjs", "traductionsPrestas", "devis", "commentaires", "ville", "demandeurService" },
        allowSetters = true
    )
    private DemandeDeTraduction demandeDeTraduction;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "user",
            "pieceJointes",
            "devis",
            "commentaires",
            "terjemTheques",
            "notations",
            "banque",
            "ville",
            "prestaDdeTraductions",
            "agenceBanque",
        },
        allowSetters = true
    )
    private Prestataire prestataire;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "user", "demandes", "commentaires", "terjemTheques", "notations", "ville", "banque", "agenceBanque" },
        allowSetters = true
    )
    private Demandeur demandeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Commentaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexte() {
        return this.texte;
    }

    public Commentaire texte(String texte) {
        this.setTexte(texte);
        return this;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public DemandeDeTraduction getDemandeDeTraduction() {
        return this.demandeDeTraduction;
    }

    public void setDemandeDeTraduction(DemandeDeTraduction demandeDeTraduction) {
        this.demandeDeTraduction = demandeDeTraduction;
    }

    public Commentaire demandeDeTraduction(DemandeDeTraduction demandeDeTraduction) {
        this.setDemandeDeTraduction(demandeDeTraduction);
        return this;
    }

    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public Commentaire prestataire(Prestataire prestataire) {
        this.setPrestataire(prestataire);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public Commentaire demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commentaire)) {
            return false;
        }
        return id != null && id.equals(((Commentaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commentaire{" +
            "id=" + getId() +
            ", texte='" + getTexte() + "'" +
            "}";
    }
}
