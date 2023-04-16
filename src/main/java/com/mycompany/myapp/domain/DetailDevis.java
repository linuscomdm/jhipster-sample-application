package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DetailDevis.
 */
@Entity
@Table(name = "detail_devis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetailDevis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "qte", nullable = false)
    private Long qte;

    @NotNull
    @Column(name = "prix_unitaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal prixUnitaire;

    @NotNull
    @Column(name = "prix_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal prixTotal;

    @NotNull
    @Column(name = "etat", nullable = false)
    private Long etat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "detailDevis", "prestataire", "demandeDeTraduction" }, allowSetters = true)
    private Devis devis;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetailDevis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQte() {
        return this.qte;
    }

    public DetailDevis qte(Long qte) {
        this.setQte(qte);
        return this;
    }

    public void setQte(Long qte) {
        this.qte = qte;
    }

    public BigDecimal getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public DetailDevis prixUnitaire(BigDecimal prixUnitaire) {
        this.setPrixUnitaire(prixUnitaire);
        return this;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getPrixTotal() {
        return this.prixTotal;
    }

    public DetailDevis prixTotal(BigDecimal prixTotal) {
        this.setPrixTotal(prixTotal);
        return this;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Long getEtat() {
        return this.etat;
    }

    public DetailDevis etat(Long etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(Long etat) {
        this.etat = etat;
    }

    public Devis getDevis() {
        return this.devis;
    }

    public void setDevis(Devis devis) {
        this.devis = devis;
    }

    public DetailDevis devis(Devis devis) {
        this.setDevis(devis);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailDevis)) {
            return false;
        }
        return id != null && id.equals(((DetailDevis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailDevis{" +
            "id=" + getId() +
            ", qte=" + getQte() +
            ", prixUnitaire=" + getPrixUnitaire() +
            ", prixTotal=" + getPrixTotal() +
            ", etat=" + getEtat() +
            "}";
    }
}
