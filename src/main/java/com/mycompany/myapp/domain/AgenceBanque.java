package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AgenceBanque.
 */
@Entity
@Table(name = "agence_banque")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgenceBanque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "agenceBanque")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "user", "demandes", "commentaires", "terjemTheques", "notations", "ville", "banque", "agenceBanque" },
        allowSetters = true
    )
    private Set<Demandeur> demandeurs = new HashSet<>();

    @OneToMany(mappedBy = "agenceBanque")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Prestataire> prestataires = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "agenceBanques" }, allowSetters = true)
    private Banque banque;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AgenceBanque id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public AgenceBanque code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public AgenceBanque libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Demandeur> getDemandeurs() {
        return this.demandeurs;
    }

    public void setDemandeurs(Set<Demandeur> demandeurs) {
        if (this.demandeurs != null) {
            this.demandeurs.forEach(i -> i.setAgenceBanque(null));
        }
        if (demandeurs != null) {
            demandeurs.forEach(i -> i.setAgenceBanque(this));
        }
        this.demandeurs = demandeurs;
    }

    public AgenceBanque demandeurs(Set<Demandeur> demandeurs) {
        this.setDemandeurs(demandeurs);
        return this;
    }

    public AgenceBanque addDemandeur(Demandeur demandeur) {
        this.demandeurs.add(demandeur);
        demandeur.setAgenceBanque(this);
        return this;
    }

    public AgenceBanque removeDemandeur(Demandeur demandeur) {
        this.demandeurs.remove(demandeur);
        demandeur.setAgenceBanque(null);
        return this;
    }

    public Set<Prestataire> getPrestataires() {
        return this.prestataires;
    }

    public void setPrestataires(Set<Prestataire> prestataires) {
        if (this.prestataires != null) {
            this.prestataires.forEach(i -> i.setAgenceBanque(null));
        }
        if (prestataires != null) {
            prestataires.forEach(i -> i.setAgenceBanque(this));
        }
        this.prestataires = prestataires;
    }

    public AgenceBanque prestataires(Set<Prestataire> prestataires) {
        this.setPrestataires(prestataires);
        return this;
    }

    public AgenceBanque addPrestataire(Prestataire prestataire) {
        this.prestataires.add(prestataire);
        prestataire.setAgenceBanque(this);
        return this;
    }

    public AgenceBanque removePrestataire(Prestataire prestataire) {
        this.prestataires.remove(prestataire);
        prestataire.setAgenceBanque(null);
        return this;
    }

    public Banque getBanque() {
        return this.banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public AgenceBanque banque(Banque banque) {
        this.setBanque(banque);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgenceBanque)) {
            return false;
        }
        return id != null && id.equals(((AgenceBanque) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgenceBanque{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
