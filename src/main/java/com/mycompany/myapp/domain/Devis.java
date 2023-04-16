package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Devis.
 */
@Entity
@Table(name = "devis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Devis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "numero", length = 30, nullable = false)
    private String numero;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "prix_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal prixTotal;

    @NotNull
    @Column(name = "etat", nullable = false)
    private Long etat;

    @OneToMany(mappedBy = "devis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "devis" }, allowSetters = true)
    private Set<DetailDevis> detailDevis = new HashSet<>();

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
        value = { "traductions", "traductionsPjs", "traductionsPrestas", "devis", "commentaires", "ville", "demandeurService" },
        allowSetters = true
    )
    private DemandeDeTraduction demandeDeTraduction;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Devis id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return this.numero;
    }

    public Devis numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Devis date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrixTotal() {
        return this.prixTotal;
    }

    public Devis prixTotal(BigDecimal prixTotal) {
        this.setPrixTotal(prixTotal);
        return this;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Long getEtat() {
        return this.etat;
    }

    public Devis etat(Long etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(Long etat) {
        this.etat = etat;
    }

    public Set<DetailDevis> getDetailDevis() {
        return this.detailDevis;
    }

    public void setDetailDevis(Set<DetailDevis> detailDevis) {
        if (this.detailDevis != null) {
            this.detailDevis.forEach(i -> i.setDevis(null));
        }
        if (detailDevis != null) {
            detailDevis.forEach(i -> i.setDevis(this));
        }
        this.detailDevis = detailDevis;
    }

    public Devis detailDevis(Set<DetailDevis> detailDevis) {
        this.setDetailDevis(detailDevis);
        return this;
    }

    public Devis addDetailDevis(DetailDevis detailDevis) {
        this.detailDevis.add(detailDevis);
        detailDevis.setDevis(this);
        return this;
    }

    public Devis removeDetailDevis(DetailDevis detailDevis) {
        this.detailDevis.remove(detailDevis);
        detailDevis.setDevis(null);
        return this;
    }

    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public Devis prestataire(Prestataire prestataire) {
        this.setPrestataire(prestataire);
        return this;
    }

    public DemandeDeTraduction getDemandeDeTraduction() {
        return this.demandeDeTraduction;
    }

    public void setDemandeDeTraduction(DemandeDeTraduction demandeDeTraduction) {
        this.demandeDeTraduction = demandeDeTraduction;
    }

    public Devis demandeDeTraduction(DemandeDeTraduction demandeDeTraduction) {
        this.setDemandeDeTraduction(demandeDeTraduction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Devis)) {
            return false;
        }
        return id != null && id.equals(((Devis) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Devis{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", date='" + getDate() + "'" +
            ", prixTotal=" + getPrixTotal() +
            ", etat=" + getEtat() +
            "}";
    }
}
