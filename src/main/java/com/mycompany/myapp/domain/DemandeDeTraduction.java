package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.EtatDemande;
import com.mycompany.myapp.domain.enumeration.ModeEnvoi;
import com.mycompany.myapp.domain.enumeration.ModeLivraison;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeDeTraduction.
 */
@Entity
@Table(name = "demande_de_traduction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandeDeTraduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_envoi_preconise")
    private ModeEnvoi modeEnvoiPreconise;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_livraison_exige")
    private ModeLivraison modeLivraisonExige;

    @Column(name = "delai_de_traitemen_souhaite")
    private Integer delaiDeTraitemenSouhaite;

    @Size(max = 150)
    @Column(name = "adresse_livraison", length = 150)
    private String adresseLivraison;

    @Column(name = "delai_de_traitemen_prestataire")
    private Integer delaiDeTraitemenPrestataire;

    @Size(max = 500)
    @Column(name = "observation", length = 500)
    private String observation;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "date_cloture")
    private LocalDate dateCloture;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatDemande etat;

    @OneToMany(mappedBy = "demandeTraductions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documens", "langueDestination", "typeDocument", "demandeTraductions" }, allowSetters = true)
    private Set<DocumentATraduire> traductions = new HashSet<>();

    @OneToMany(mappedBy = "pjDdeTraductions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pjDdeTraductions", "prestataire" }, allowSetters = true)
    private Set<PieceJointe> traductionsPjs = new HashSet<>();

    @OneToMany(mappedBy = "prestaDdeTraductions")
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
    private Set<Prestataire> traductionsPrestas = new HashSet<>();

    @OneToMany(mappedBy = "demandeDeTraduction")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "detailDevis", "prestataire", "demandeDeTraduction" }, allowSetters = true)
    private Set<Devis> devis = new HashSet<>();

    @OneToMany(mappedBy = "demandeDeTraduction")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demandeDeTraduction", "prestataire", "demandeur" }, allowSetters = true)
    private Set<Commentaire> commentaires = new HashSet<>();

    @ManyToOne
    private Ville ville;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "user", "demandes", "commentaires", "terjemTheques", "notations", "ville", "banque", "agenceBanque" },
        allowSetters = true
    )
    private Demandeur demandeurService;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeDeTraduction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModeEnvoi getModeEnvoiPreconise() {
        return this.modeEnvoiPreconise;
    }

    public DemandeDeTraduction modeEnvoiPreconise(ModeEnvoi modeEnvoiPreconise) {
        this.setModeEnvoiPreconise(modeEnvoiPreconise);
        return this;
    }

    public void setModeEnvoiPreconise(ModeEnvoi modeEnvoiPreconise) {
        this.modeEnvoiPreconise = modeEnvoiPreconise;
    }

    public ModeLivraison getModeLivraisonExige() {
        return this.modeLivraisonExige;
    }

    public DemandeDeTraduction modeLivraisonExige(ModeLivraison modeLivraisonExige) {
        this.setModeLivraisonExige(modeLivraisonExige);
        return this;
    }

    public void setModeLivraisonExige(ModeLivraison modeLivraisonExige) {
        this.modeLivraisonExige = modeLivraisonExige;
    }

    public Integer getDelaiDeTraitemenSouhaite() {
        return this.delaiDeTraitemenSouhaite;
    }

    public DemandeDeTraduction delaiDeTraitemenSouhaite(Integer delaiDeTraitemenSouhaite) {
        this.setDelaiDeTraitemenSouhaite(delaiDeTraitemenSouhaite);
        return this;
    }

    public void setDelaiDeTraitemenSouhaite(Integer delaiDeTraitemenSouhaite) {
        this.delaiDeTraitemenSouhaite = delaiDeTraitemenSouhaite;
    }

    public String getAdresseLivraison() {
        return this.adresseLivraison;
    }

    public DemandeDeTraduction adresseLivraison(String adresseLivraison) {
        this.setAdresseLivraison(adresseLivraison);
        return this;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public Integer getDelaiDeTraitemenPrestataire() {
        return this.delaiDeTraitemenPrestataire;
    }

    public DemandeDeTraduction delaiDeTraitemenPrestataire(Integer delaiDeTraitemenPrestataire) {
        this.setDelaiDeTraitemenPrestataire(delaiDeTraitemenPrestataire);
        return this;
    }

    public void setDelaiDeTraitemenPrestataire(Integer delaiDeTraitemenPrestataire) {
        this.delaiDeTraitemenPrestataire = delaiDeTraitemenPrestataire;
    }

    public String getObservation() {
        return this.observation;
    }

    public DemandeDeTraduction observation(String observation) {
        this.setObservation(observation);
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public DemandeDeTraduction dateCreation(LocalDate dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateCloture() {
        return this.dateCloture;
    }

    public DemandeDeTraduction dateCloture(LocalDate dateCloture) {
        this.setDateCloture(dateCloture);
        return this;
    }

    public void setDateCloture(LocalDate dateCloture) {
        this.dateCloture = dateCloture;
    }

    public EtatDemande getEtat() {
        return this.etat;
    }

    public DemandeDeTraduction etat(EtatDemande etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(EtatDemande etat) {
        this.etat = etat;
    }

    public Set<DocumentATraduire> getTraductions() {
        return this.traductions;
    }

    public void setTraductions(Set<DocumentATraduire> documentATraduires) {
        if (this.traductions != null) {
            this.traductions.forEach(i -> i.setDemandeTraductions(null));
        }
        if (documentATraduires != null) {
            documentATraduires.forEach(i -> i.setDemandeTraductions(this));
        }
        this.traductions = documentATraduires;
    }

    public DemandeDeTraduction traductions(Set<DocumentATraduire> documentATraduires) {
        this.setTraductions(documentATraduires);
        return this;
    }

    public DemandeDeTraduction addTraductions(DocumentATraduire documentATraduire) {
        this.traductions.add(documentATraduire);
        documentATraduire.setDemandeTraductions(this);
        return this;
    }

    public DemandeDeTraduction removeTraductions(DocumentATraduire documentATraduire) {
        this.traductions.remove(documentATraduire);
        documentATraduire.setDemandeTraductions(null);
        return this;
    }

    public Set<PieceJointe> getTraductionsPjs() {
        return this.traductionsPjs;
    }

    public void setTraductionsPjs(Set<PieceJointe> pieceJointes) {
        if (this.traductionsPjs != null) {
            this.traductionsPjs.forEach(i -> i.setPjDdeTraductions(null));
        }
        if (pieceJointes != null) {
            pieceJointes.forEach(i -> i.setPjDdeTraductions(this));
        }
        this.traductionsPjs = pieceJointes;
    }

    public DemandeDeTraduction traductionsPjs(Set<PieceJointe> pieceJointes) {
        this.setTraductionsPjs(pieceJointes);
        return this;
    }

    public DemandeDeTraduction addTraductionsPj(PieceJointe pieceJointe) {
        this.traductionsPjs.add(pieceJointe);
        pieceJointe.setPjDdeTraductions(this);
        return this;
    }

    public DemandeDeTraduction removeTraductionsPj(PieceJointe pieceJointe) {
        this.traductionsPjs.remove(pieceJointe);
        pieceJointe.setPjDdeTraductions(null);
        return this;
    }

    public Set<Prestataire> getTraductionsPrestas() {
        return this.traductionsPrestas;
    }

    public void setTraductionsPrestas(Set<Prestataire> prestataires) {
        if (this.traductionsPrestas != null) {
            this.traductionsPrestas.forEach(i -> i.setPrestaDdeTraductions(null));
        }
        if (prestataires != null) {
            prestataires.forEach(i -> i.setPrestaDdeTraductions(this));
        }
        this.traductionsPrestas = prestataires;
    }

    public DemandeDeTraduction traductionsPrestas(Set<Prestataire> prestataires) {
        this.setTraductionsPrestas(prestataires);
        return this;
    }

    public DemandeDeTraduction addTraductionsPresta(Prestataire prestataire) {
        this.traductionsPrestas.add(prestataire);
        prestataire.setPrestaDdeTraductions(this);
        return this;
    }

    public DemandeDeTraduction removeTraductionsPresta(Prestataire prestataire) {
        this.traductionsPrestas.remove(prestataire);
        prestataire.setPrestaDdeTraductions(null);
        return this;
    }

    public Set<Devis> getDevis() {
        return this.devis;
    }

    public void setDevis(Set<Devis> devis) {
        if (this.devis != null) {
            this.devis.forEach(i -> i.setDemandeDeTraduction(null));
        }
        if (devis != null) {
            devis.forEach(i -> i.setDemandeDeTraduction(this));
        }
        this.devis = devis;
    }

    public DemandeDeTraduction devis(Set<Devis> devis) {
        this.setDevis(devis);
        return this;
    }

    public DemandeDeTraduction addDevis(Devis devis) {
        this.devis.add(devis);
        devis.setDemandeDeTraduction(this);
        return this;
    }

    public DemandeDeTraduction removeDevis(Devis devis) {
        this.devis.remove(devis);
        devis.setDemandeDeTraduction(null);
        return this;
    }

    public Set<Commentaire> getCommentaires() {
        return this.commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        if (this.commentaires != null) {
            this.commentaires.forEach(i -> i.setDemandeDeTraduction(null));
        }
        if (commentaires != null) {
            commentaires.forEach(i -> i.setDemandeDeTraduction(this));
        }
        this.commentaires = commentaires;
    }

    public DemandeDeTraduction commentaires(Set<Commentaire> commentaires) {
        this.setCommentaires(commentaires);
        return this;
    }

    public DemandeDeTraduction addCommentaire(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setDemandeDeTraduction(this);
        return this;
    }

    public DemandeDeTraduction removeCommentaire(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setDemandeDeTraduction(null);
        return this;
    }

    public Ville getVille() {
        return this.ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public DemandeDeTraduction ville(Ville ville) {
        this.setVille(ville);
        return this;
    }

    public Demandeur getDemandeurService() {
        return this.demandeurService;
    }

    public void setDemandeurService(Demandeur demandeur) {
        this.demandeurService = demandeur;
    }

    public DemandeDeTraduction demandeurService(Demandeur demandeur) {
        this.setDemandeurService(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeDeTraduction)) {
            return false;
        }
        return id != null && id.equals(((DemandeDeTraduction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeDeTraduction{" +
            "id=" + getId() +
            ", modeEnvoiPreconise='" + getModeEnvoiPreconise() + "'" +
            ", modeLivraisonExige='" + getModeLivraisonExige() + "'" +
            ", delaiDeTraitemenSouhaite=" + getDelaiDeTraitemenSouhaite() +
            ", adresseLivraison='" + getAdresseLivraison() + "'" +
            ", delaiDeTraitemenPrestataire=" + getDelaiDeTraitemenPrestataire() +
            ", observation='" + getObservation() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
