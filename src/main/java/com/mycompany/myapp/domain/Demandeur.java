package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Civilite;
import com.mycompany.myapp.domain.enumeration.EtatDemandeur;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Demandeur.
 */
@Entity
@Table(name = "demandeur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Demandeur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "civilite")
    private Civilite civilite;

    @NotNull
    @Size(max = 80)
    @Column(name = "nom", length = 80, nullable = false)
    private String nom;

    @NotNull
    @Size(max = 80)
    @Column(name = "prenom", length = 80, nullable = false)
    private String prenom;

    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    @NotNull
    @Size(max = 30)
    @Column(name = "telephone", length = 30, nullable = false)
    private String telephone;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Size(max = 50)
    @Column(name = "adresse", length = 50)
    private String adresse;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatDemandeur etat;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "demandeurService")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "traductions", "traductionsPjs", "traductionsPrestas", "devis", "commentaires", "ville", "demandeurService" },
        allowSetters = true
    )
    private Set<DemandeDeTraduction> demandes = new HashSet<>();

    @OneToMany(mappedBy = "demandeur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demandeDeTraduction", "prestataire", "demandeur" }, allowSetters = true)
    private Set<Commentaire> commentaires = new HashSet<>();

    @OneToMany(mappedBy = "demandeur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prestataire", "demandeur" }, allowSetters = true)
    private Set<TerjemTheque> terjemTheques = new HashSet<>();

    @OneToMany(mappedBy = "demandeur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demandeur", "prestataire" }, allowSetters = true)
    private Set<Notation> notations = new HashSet<>();

    @ManyToOne
    private Ville ville;

    @ManyToOne
    @JsonIgnoreProperties(value = { "agenceBanques" }, allowSetters = true)
    private Banque banque;

    @ManyToOne
    @JsonIgnoreProperties(value = { "demandeurs", "prestataires", "banque" }, allowSetters = true)
    private AgenceBanque agenceBanque;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Demandeur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Civilite getCivilite() {
        return this.civilite;
    }

    public Demandeur civilite(Civilite civilite) {
        this.setCivilite(civilite);
        return this;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return this.nom;
    }

    public Demandeur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Demandeur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public Demandeur dateDeNaissance(LocalDate dateDeNaissance) {
        this.setDateDeNaissance(dateDeNaissance);
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Demandeur telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Demandeur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Demandeur adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public Demandeur dateCreation(LocalDate dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public EtatDemandeur getEtat() {
        return this.etat;
    }

    public Demandeur etat(EtatDemandeur etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(EtatDemandeur etat) {
        this.etat = etat;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Demandeur user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<DemandeDeTraduction> getDemandes() {
        return this.demandes;
    }

    public void setDemandes(Set<DemandeDeTraduction> demandeDeTraductions) {
        if (this.demandes != null) {
            this.demandes.forEach(i -> i.setDemandeurService(null));
        }
        if (demandeDeTraductions != null) {
            demandeDeTraductions.forEach(i -> i.setDemandeurService(this));
        }
        this.demandes = demandeDeTraductions;
    }

    public Demandeur demandes(Set<DemandeDeTraduction> demandeDeTraductions) {
        this.setDemandes(demandeDeTraductions);
        return this;
    }

    public Demandeur addDemandes(DemandeDeTraduction demandeDeTraduction) {
        this.demandes.add(demandeDeTraduction);
        demandeDeTraduction.setDemandeurService(this);
        return this;
    }

    public Demandeur removeDemandes(DemandeDeTraduction demandeDeTraduction) {
        this.demandes.remove(demandeDeTraduction);
        demandeDeTraduction.setDemandeurService(null);
        return this;
    }

    public Set<Commentaire> getCommentaires() {
        return this.commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        if (this.commentaires != null) {
            this.commentaires.forEach(i -> i.setDemandeur(null));
        }
        if (commentaires != null) {
            commentaires.forEach(i -> i.setDemandeur(this));
        }
        this.commentaires = commentaires;
    }

    public Demandeur commentaires(Set<Commentaire> commentaires) {
        this.setCommentaires(commentaires);
        return this;
    }

    public Demandeur addCommentaire(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setDemandeur(this);
        return this;
    }

    public Demandeur removeCommentaire(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setDemandeur(null);
        return this;
    }

    public Set<TerjemTheque> getTerjemTheques() {
        return this.terjemTheques;
    }

    public void setTerjemTheques(Set<TerjemTheque> terjemTheques) {
        if (this.terjemTheques != null) {
            this.terjemTheques.forEach(i -> i.setDemandeur(null));
        }
        if (terjemTheques != null) {
            terjemTheques.forEach(i -> i.setDemandeur(this));
        }
        this.terjemTheques = terjemTheques;
    }

    public Demandeur terjemTheques(Set<TerjemTheque> terjemTheques) {
        this.setTerjemTheques(terjemTheques);
        return this;
    }

    public Demandeur addTerjemTheque(TerjemTheque terjemTheque) {
        this.terjemTheques.add(terjemTheque);
        terjemTheque.setDemandeur(this);
        return this;
    }

    public Demandeur removeTerjemTheque(TerjemTheque terjemTheque) {
        this.terjemTheques.remove(terjemTheque);
        terjemTheque.setDemandeur(null);
        return this;
    }

    public Set<Notation> getNotations() {
        return this.notations;
    }

    public void setNotations(Set<Notation> notations) {
        if (this.notations != null) {
            this.notations.forEach(i -> i.setDemandeur(null));
        }
        if (notations != null) {
            notations.forEach(i -> i.setDemandeur(this));
        }
        this.notations = notations;
    }

    public Demandeur notations(Set<Notation> notations) {
        this.setNotations(notations);
        return this;
    }

    public Demandeur addNotation(Notation notation) {
        this.notations.add(notation);
        notation.setDemandeur(this);
        return this;
    }

    public Demandeur removeNotation(Notation notation) {
        this.notations.remove(notation);
        notation.setDemandeur(null);
        return this;
    }

    public Ville getVille() {
        return this.ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Demandeur ville(Ville ville) {
        this.setVille(ville);
        return this;
    }

    public Banque getBanque() {
        return this.banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public Demandeur banque(Banque banque) {
        this.setBanque(banque);
        return this;
    }

    public AgenceBanque getAgenceBanque() {
        return this.agenceBanque;
    }

    public void setAgenceBanque(AgenceBanque agenceBanque) {
        this.agenceBanque = agenceBanque;
    }

    public Demandeur agenceBanque(AgenceBanque agenceBanque) {
        this.setAgenceBanque(agenceBanque);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demandeur)) {
            return false;
        }
        return id != null && id.equals(((Demandeur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Demandeur{" +
            "id=" + getId() +
            ", civilite='" + getCivilite() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
