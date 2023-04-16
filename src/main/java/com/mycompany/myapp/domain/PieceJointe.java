package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PieceJointe.
 */
@Entity
@Table(name = "piece_jointe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PieceJointe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nom_fichier", length = 150, nullable = false)
    private String nomFichier;

    @NotNull
    @Size(max = 500)
    @Column(name = "chemin", length = 500, nullable = false)
    private String chemin;

    @NotNull
    @Size(max = 500)
    @Column(name = "url_piece", length = 500, nullable = false)
    private String urlPiece;

    @Column(name = "description")
    private String description;

    @Column(name = "code_piece")
    private String codePiece;

    @Column(name = "libelle_piece")
    private String libellePiece;

    @Lob
    @Column(name = "rattach_pj")
    private byte[] rattachPj;

    @Column(name = "rattach_pj_content_type")
    private String rattachPjContentType;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "traductions", "traductionsPjs", "traductionsPrestas", "devis", "commentaires", "ville", "demandeurService" },
        allowSetters = true
    )
    private DemandeDeTraduction pjDdeTraductions;

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

    public PieceJointe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return this.nomFichier;
    }

    public PieceJointe nomFichier(String nomFichier) {
        this.setNomFichier(nomFichier);
        return this;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getChemin() {
        return this.chemin;
    }

    public PieceJointe chemin(String chemin) {
        this.setChemin(chemin);
        return this;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public String getUrlPiece() {
        return this.urlPiece;
    }

    public PieceJointe urlPiece(String urlPiece) {
        this.setUrlPiece(urlPiece);
        return this;
    }

    public void setUrlPiece(String urlPiece) {
        this.urlPiece = urlPiece;
    }

    public String getDescription() {
        return this.description;
    }

    public PieceJointe description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodePiece() {
        return this.codePiece;
    }

    public PieceJointe codePiece(String codePiece) {
        this.setCodePiece(codePiece);
        return this;
    }

    public void setCodePiece(String codePiece) {
        this.codePiece = codePiece;
    }

    public String getLibellePiece() {
        return this.libellePiece;
    }

    public PieceJointe libellePiece(String libellePiece) {
        this.setLibellePiece(libellePiece);
        return this;
    }

    public void setLibellePiece(String libellePiece) {
        this.libellePiece = libellePiece;
    }

    public byte[] getRattachPj() {
        return this.rattachPj;
    }

    public PieceJointe rattachPj(byte[] rattachPj) {
        this.setRattachPj(rattachPj);
        return this;
    }

    public void setRattachPj(byte[] rattachPj) {
        this.rattachPj = rattachPj;
    }

    public String getRattachPjContentType() {
        return this.rattachPjContentType;
    }

    public PieceJointe rattachPjContentType(String rattachPjContentType) {
        this.rattachPjContentType = rattachPjContentType;
        return this;
    }

    public void setRattachPjContentType(String rattachPjContentType) {
        this.rattachPjContentType = rattachPjContentType;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public PieceJointe dateCreation(LocalDate dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public DemandeDeTraduction getPjDdeTraductions() {
        return this.pjDdeTraductions;
    }

    public void setPjDdeTraductions(DemandeDeTraduction demandeDeTraduction) {
        this.pjDdeTraductions = demandeDeTraduction;
    }

    public PieceJointe pjDdeTraductions(DemandeDeTraduction demandeDeTraduction) {
        this.setPjDdeTraductions(demandeDeTraduction);
        return this;
    }

    public Prestataire getPrestataire() {
        return this.prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public PieceJointe prestataire(Prestataire prestataire) {
        this.setPrestataire(prestataire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PieceJointe)) {
            return false;
        }
        return id != null && id.equals(((PieceJointe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PieceJointe{" +
            "id=" + getId() +
            ", nomFichier='" + getNomFichier() + "'" +
            ", chemin='" + getChemin() + "'" +
            ", urlPiece='" + getUrlPiece() + "'" +
            ", description='" + getDescription() + "'" +
            ", codePiece='" + getCodePiece() + "'" +
            ", libellePiece='" + getLibellePiece() + "'" +
            ", rattachPj='" + getRattachPj() + "'" +
            ", rattachPjContentType='" + getRattachPjContentType() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            "}";
    }
}
