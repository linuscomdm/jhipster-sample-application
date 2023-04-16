package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Notation.
 */
@Entity
@Table(name = "notation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "notetation", nullable = false)
    private Integer notetation;

    @Size(max = 150)
    @Column(name = "commentaire", length = 150)
    private String commentaire;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "user", "demandes", "commentaires", "terjemTheques", "notations", "ville", "banque", "agenceBanque" },
        allowSetters = true
    )
    private Demandeur demandeur;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNotetation() {
        return this.notetation;
    }

    public Notation notetation(Integer notetation) {
        this.setNotetation(notetation);
        return this;
    }

    public void setNotetation(Integer notetation) {
        this.notetation = notetation;
    }

    public String getCommentaire() {
        return this.commentaire;
    }

    public Notation commentaire(String commentaire) {
        this.setCommentaire(commentaire);
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public Notation dateCreation(LocalDate dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public Notation demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public Notation prestataire(Prestataire prestataire) {
        this.setPrestataire(prestataire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notation)) {
            return false;
        }
        return id != null && id.equals(((Notation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notation{" +
            "id=" + getId() +
            ", notetation=" + getNotetation() +
            ", commentaire='" + getCommentaire() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            "}";
    }
}
