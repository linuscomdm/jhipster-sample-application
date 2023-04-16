package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.FormatDocTraduit;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TerjemTheque.
 */
@Entity
@Table(name = "terjem_theque")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TerjemTheque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "lien_download", length = 500, nullable = false)
    private String lienDownload;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "format_doc_traduit", nullable = false)
    private FormatDocTraduit formatDocTraduit;

    @NotNull
    @Size(max = 150)
    @Column(name = "nom_fichier", length = 150, nullable = false)
    private String nomFichier;

    @Lob
    @Column(name = "doc_traduit")
    private byte[] docTraduit;

    @Column(name = "doc_traduit_content_type")
    private String docTraduitContentType;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "etat")
    private Long etat;

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

    public TerjemTheque id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLienDownload() {
        return this.lienDownload;
    }

    public TerjemTheque lienDownload(String lienDownload) {
        this.setLienDownload(lienDownload);
        return this;
    }

    public void setLienDownload(String lienDownload) {
        this.lienDownload = lienDownload;
    }

    public FormatDocTraduit getFormatDocTraduit() {
        return this.formatDocTraduit;
    }

    public TerjemTheque formatDocTraduit(FormatDocTraduit formatDocTraduit) {
        this.setFormatDocTraduit(formatDocTraduit);
        return this;
    }

    public void setFormatDocTraduit(FormatDocTraduit formatDocTraduit) {
        this.formatDocTraduit = formatDocTraduit;
    }

    public String getNomFichier() {
        return this.nomFichier;
    }

    public TerjemTheque nomFichier(String nomFichier) {
        this.setNomFichier(nomFichier);
        return this;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public byte[] getDocTraduit() {
        return this.docTraduit;
    }

    public TerjemTheque docTraduit(byte[] docTraduit) {
        this.setDocTraduit(docTraduit);
        return this;
    }

    public void setDocTraduit(byte[] docTraduit) {
        this.docTraduit = docTraduit;
    }

    public String getDocTraduitContentType() {
        return this.docTraduitContentType;
    }

    public TerjemTheque docTraduitContentType(String docTraduitContentType) {
        this.docTraduitContentType = docTraduitContentType;
        return this;
    }

    public void setDocTraduitContentType(String docTraduitContentType) {
        this.docTraduitContentType = docTraduitContentType;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public TerjemTheque dateCreation(LocalDate dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getEtat() {
        return this.etat;
    }

    public TerjemTheque etat(Long etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(Long etat) {
        this.etat = etat;
    }

    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public TerjemTheque prestataire(Prestataire prestataire) {
        this.setPrestataire(prestataire);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public TerjemTheque demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TerjemTheque)) {
            return false;
        }
        return id != null && id.equals(((TerjemTheque) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TerjemTheque{" +
            "id=" + getId() +
            ", lienDownload='" + getLienDownload() + "'" +
            ", formatDocTraduit='" + getFormatDocTraduit() + "'" +
            ", nomFichier='" + getNomFichier() + "'" +
            ", docTraduit='" + getDocTraduit() + "'" +
            ", docTraduitContentType='" + getDocTraduitContentType() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", etat=" + getEtat() +
            "}";
    }
}
