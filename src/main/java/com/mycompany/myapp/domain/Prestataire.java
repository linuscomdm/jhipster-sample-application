package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Civilite;
import com.mycompany.myapp.domain.enumeration.EtatPrestataire;
import com.mycompany.myapp.domain.enumeration.TypeIdentiteProfessionnelle;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Prestataire.
 */
@Entity
@Table(name = "prestataire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prestataire implements Serializable {

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

    @Size(max = 80)
    @Column(name = "nom_commercial", length = 80)
    private String nomCommercial;

    @Size(max = 30)
    @Column(name = "telephone_travail", length = 30)
    private String telephoneTravail;

    @NotNull
    @Size(max = 30)
    @Column(name = "telephone_mobile", length = 30, nullable = false)
    private String telephoneMobile;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Size(max = 70)
    @Column(name = "adresse", length = 70)
    private String adresse;

    @Size(max = 10)
    @Column(name = "code_postal", length = 10)
    private String codePostal;

    @Lob
    @Column(name = "photo_de_profil")
    private byte[] photoDeProfil;

    @Column(name = "photo_de_profil_content_type")
    private String photoDeProfilContentType;

    @NotNull
    @Size(max = 50)
    @Column(name = "numero_piece_identite", length = 50, nullable = false)
    private String numeroPieceIdentite;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_identite_professionnelle")
    private TypeIdentiteProfessionnelle typeIdentiteProfessionnelle;

    @Lob
    @Column(name = "rattach_identite_pro")
    private byte[] rattachIdentitePro;

    @Column(name = "rattach_identite_pro_content_type")
    private String rattachIdentiteProContentType;

    @Lob
    @Column(name = "coordonnees_bancaires")
    private byte[] coordonneesBancaires;

    @Column(name = "coordonnees_bancaires_content_type")
    private String coordonneesBancairesContentType;

    @Size(max = 80)
    @Column(name = "titulaire_du_compte", length = 80)
    private String titulaireDuCompte;

    @Size(max = 20)
    @Column(name = "rib_ou_rip", length = 20)
    private String ribOuRip;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatPrestataire etat;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "prestataire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pjDdeTraductions", "prestataire" }, allowSetters = true)
    private Set<PieceJointe> pieceJointes = new HashSet<>();

    @OneToMany(mappedBy = "prestataire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "detailDevis", "prestataire", "demandeDeTraduction" }, allowSetters = true)
    private Set<Devis> devis = new HashSet<>();

    @OneToMany(mappedBy = "prestataire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demandeDeTraduction", "prestataire", "demandeur" }, allowSetters = true)
    private Set<Commentaire> commentaires = new HashSet<>();

    @OneToMany(mappedBy = "prestataire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "prestataire", "demandeur" }, allowSetters = true)
    private Set<TerjemTheque> terjemTheques = new HashSet<>();

    @OneToMany(mappedBy = "prestataire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "demandeur", "prestataire" }, allowSetters = true)
    private Set<Notation> notations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "agenceBanques" }, allowSetters = true)
    private Banque banque;

    @ManyToOne
    private Ville ville;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "traductions", "traductionsPjs", "traductionsPrestas", "devis", "commentaires", "ville", "demandeurService" },
        allowSetters = true
    )
    private DemandeDeTraduction prestaDdeTraductions;

    @ManyToOne
    @JsonIgnoreProperties(value = { "demandeurs", "prestataires", "banque" }, allowSetters = true)
    private AgenceBanque agenceBanque;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prestataire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Civilite getCivilite() {
        return this.civilite;
    }

    public Prestataire civilite(Civilite civilite) {
        this.setCivilite(civilite);
        return this;
    }

    public void setCivilite(Civilite civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return this.nom;
    }

    public Prestataire nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Prestataire prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomCommercial() {
        return this.nomCommercial;
    }

    public Prestataire nomCommercial(String nomCommercial) {
        this.setNomCommercial(nomCommercial);
        return this;
    }

    public void setNomCommercial(String nomCommercial) {
        this.nomCommercial = nomCommercial;
    }

    public String getTelephoneTravail() {
        return this.telephoneTravail;
    }

    public Prestataire telephoneTravail(String telephoneTravail) {
        this.setTelephoneTravail(telephoneTravail);
        return this;
    }

    public void setTelephoneTravail(String telephoneTravail) {
        this.telephoneTravail = telephoneTravail;
    }

    public String getTelephoneMobile() {
        return this.telephoneMobile;
    }

    public Prestataire telephoneMobile(String telephoneMobile) {
        this.setTelephoneMobile(telephoneMobile);
        return this;
    }

    public void setTelephoneMobile(String telephoneMobile) {
        this.telephoneMobile = telephoneMobile;
    }

    public String getEmail() {
        return this.email;
    }

    public Prestataire email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Prestataire adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public Prestataire codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public byte[] getPhotoDeProfil() {
        return this.photoDeProfil;
    }

    public Prestataire photoDeProfil(byte[] photoDeProfil) {
        this.setPhotoDeProfil(photoDeProfil);
        return this;
    }

    public void setPhotoDeProfil(byte[] photoDeProfil) {
        this.photoDeProfil = photoDeProfil;
    }

    public String getPhotoDeProfilContentType() {
        return this.photoDeProfilContentType;
    }

    public Prestataire photoDeProfilContentType(String photoDeProfilContentType) {
        this.photoDeProfilContentType = photoDeProfilContentType;
        return this;
    }

    public void setPhotoDeProfilContentType(String photoDeProfilContentType) {
        this.photoDeProfilContentType = photoDeProfilContentType;
    }

    public String getNumeroPieceIdentite() {
        return this.numeroPieceIdentite;
    }

    public Prestataire numeroPieceIdentite(String numeroPieceIdentite) {
        this.setNumeroPieceIdentite(numeroPieceIdentite);
        return this;
    }

    public void setNumeroPieceIdentite(String numeroPieceIdentite) {
        this.numeroPieceIdentite = numeroPieceIdentite;
    }

    public TypeIdentiteProfessionnelle getTypeIdentiteProfessionnelle() {
        return this.typeIdentiteProfessionnelle;
    }

    public Prestataire typeIdentiteProfessionnelle(TypeIdentiteProfessionnelle typeIdentiteProfessionnelle) {
        this.setTypeIdentiteProfessionnelle(typeIdentiteProfessionnelle);
        return this;
    }

    public void setTypeIdentiteProfessionnelle(TypeIdentiteProfessionnelle typeIdentiteProfessionnelle) {
        this.typeIdentiteProfessionnelle = typeIdentiteProfessionnelle;
    }

    public byte[] getRattachIdentitePro() {
        return this.rattachIdentitePro;
    }

    public Prestataire rattachIdentitePro(byte[] rattachIdentitePro) {
        this.setRattachIdentitePro(rattachIdentitePro);
        return this;
    }

    public void setRattachIdentitePro(byte[] rattachIdentitePro) {
        this.rattachIdentitePro = rattachIdentitePro;
    }

    public String getRattachIdentiteProContentType() {
        return this.rattachIdentiteProContentType;
    }

    public Prestataire rattachIdentiteProContentType(String rattachIdentiteProContentType) {
        this.rattachIdentiteProContentType = rattachIdentiteProContentType;
        return this;
    }

    public void setRattachIdentiteProContentType(String rattachIdentiteProContentType) {
        this.rattachIdentiteProContentType = rattachIdentiteProContentType;
    }

    public byte[] getCoordonneesBancaires() {
        return this.coordonneesBancaires;
    }

    public Prestataire coordonneesBancaires(byte[] coordonneesBancaires) {
        this.setCoordonneesBancaires(coordonneesBancaires);
        return this;
    }

    public void setCoordonneesBancaires(byte[] coordonneesBancaires) {
        this.coordonneesBancaires = coordonneesBancaires;
    }

    public String getCoordonneesBancairesContentType() {
        return this.coordonneesBancairesContentType;
    }

    public Prestataire coordonneesBancairesContentType(String coordonneesBancairesContentType) {
        this.coordonneesBancairesContentType = coordonneesBancairesContentType;
        return this;
    }

    public void setCoordonneesBancairesContentType(String coordonneesBancairesContentType) {
        this.coordonneesBancairesContentType = coordonneesBancairesContentType;
    }

    public String getTitulaireDuCompte() {
        return this.titulaireDuCompte;
    }

    public Prestataire titulaireDuCompte(String titulaireDuCompte) {
        this.setTitulaireDuCompte(titulaireDuCompte);
        return this;
    }

    public void setTitulaireDuCompte(String titulaireDuCompte) {
        this.titulaireDuCompte = titulaireDuCompte;
    }

    public String getRibOuRip() {
        return this.ribOuRip;
    }

    public Prestataire ribOuRip(String ribOuRip) {
        this.setRibOuRip(ribOuRip);
        return this;
    }

    public void setRibOuRip(String ribOuRip) {
        this.ribOuRip = ribOuRip;
    }

    public LocalDate getDateCreation() {
        return this.dateCreation;
    }

    public Prestataire dateCreation(LocalDate dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public EtatPrestataire getEtat() {
        return this.etat;
    }

    public Prestataire etat(EtatPrestataire etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(EtatPrestataire etat) {
        this.etat = etat;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Prestataire user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<PieceJointe> getPieceJointes() {
        return this.pieceJointes;
    }

    public void setPieceJointes(Set<PieceJointe> pieceJointes) {
        if (this.pieceJointes != null) {
            this.pieceJointes.forEach(i -> i.setPrestataire(null));
        }
        if (pieceJointes != null) {
            pieceJointes.forEach(i -> i.setPrestataire(this));
        }
        this.pieceJointes = pieceJointes;
    }

    public Prestataire pieceJointes(Set<PieceJointe> pieceJointes) {
        this.setPieceJointes(pieceJointes);
        return this;
    }

    public Prestataire addPieceJointe(PieceJointe pieceJointe) {
        this.pieceJointes.add(pieceJointe);
        pieceJointe.setPrestataire(this);
        return this;
    }

    public Prestataire removePieceJointe(PieceJointe pieceJointe) {
        this.pieceJointes.remove(pieceJointe);
        pieceJointe.setPrestataire(null);
        return this;
    }

    public Set<Devis> getDevis() {
        return this.devis;
    }

    public void setDevis(Set<Devis> devis) {
        if (this.devis != null) {
            this.devis.forEach(i -> i.setPrestataire(null));
        }
        if (devis != null) {
            devis.forEach(i -> i.setPrestataire(this));
        }
        this.devis = devis;
    }

    public Prestataire devis(Set<Devis> devis) {
        this.setDevis(devis);
        return this;
    }

    public Prestataire addDevis(Devis devis) {
        this.devis.add(devis);
        devis.setPrestataire(this);
        return this;
    }

    public Prestataire removeDevis(Devis devis) {
        this.devis.remove(devis);
        devis.setPrestataire(null);
        return this;
    }

    public Set<Commentaire> getCommentaires() {
        return this.commentaires;
    }

    public void setCommentaires(Set<Commentaire> commentaires) {
        if (this.commentaires != null) {
            this.commentaires.forEach(i -> i.setPrestataire(null));
        }
        if (commentaires != null) {
            commentaires.forEach(i -> i.setPrestataire(this));
        }
        this.commentaires = commentaires;
    }

    public Prestataire commentaires(Set<Commentaire> commentaires) {
        this.setCommentaires(commentaires);
        return this;
    }

    public Prestataire addCommentaire(Commentaire commentaire) {
        this.commentaires.add(commentaire);
        commentaire.setPrestataire(this);
        return this;
    }

    public Prestataire removeCommentaire(Commentaire commentaire) {
        this.commentaires.remove(commentaire);
        commentaire.setPrestataire(null);
        return this;
    }

    public Set<TerjemTheque> getTerjemTheques() {
        return this.terjemTheques;
    }

    public void setTerjemTheques(Set<TerjemTheque> terjemTheques) {
        if (this.terjemTheques != null) {
            this.terjemTheques.forEach(i -> i.setPrestataire(null));
        }
        if (terjemTheques != null) {
            terjemTheques.forEach(i -> i.setPrestataire(this));
        }
        this.terjemTheques = terjemTheques;
    }

    public Prestataire terjemTheques(Set<TerjemTheque> terjemTheques) {
        this.setTerjemTheques(terjemTheques);
        return this;
    }

    public Prestataire addTerjemTheque(TerjemTheque terjemTheque) {
        this.terjemTheques.add(terjemTheque);
        terjemTheque.setPrestataire(this);
        return this;
    }

    public Prestataire removeTerjemTheque(TerjemTheque terjemTheque) {
        this.terjemTheques.remove(terjemTheque);
        terjemTheque.setPrestataire(null);
        return this;
    }

    public Set<Notation> getNotations() {
        return this.notations;
    }

    public void setNotations(Set<Notation> notations) {
        if (this.notations != null) {
            this.notations.forEach(i -> i.setPrestataire(null));
        }
        if (notations != null) {
            notations.forEach(i -> i.setPrestataire(this));
        }
        this.notations = notations;
    }

    public Prestataire notations(Set<Notation> notations) {
        this.setNotations(notations);
        return this;
    }

    public Prestataire addNotation(Notation notation) {
        this.notations.add(notation);
        notation.setPrestataire(this);
        return this;
    }

    public Prestataire removeNotation(Notation notation) {
        this.notations.remove(notation);
        notation.setPrestataire(null);
        return this;
    }

    public Banque getBanque() {
        return this.banque;
    }

    public void setBanque(Banque banque) {
        this.banque = banque;
    }

    public Prestataire banque(Banque banque) {
        this.setBanque(banque);
        return this;
    }

    public Ville getVille() {
        return this.ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Prestataire ville(Ville ville) {
        this.setVille(ville);
        return this;
    }

    public DemandeDeTraduction getPrestaDdeTraductions() {
        return this.prestaDdeTraductions;
    }

    public void setPrestaDdeTraductions(DemandeDeTraduction demandeDeTraduction) {
        this.prestaDdeTraductions = demandeDeTraduction;
    }

    public Prestataire prestaDdeTraductions(DemandeDeTraduction demandeDeTraduction) {
        this.setPrestaDdeTraductions(demandeDeTraduction);
        return this;
    }

    public AgenceBanque getAgenceBanque() {
        return this.agenceBanque;
    }

    public void setAgenceBanque(AgenceBanque agenceBanque) {
        this.agenceBanque = agenceBanque;
    }

    public Prestataire agenceBanque(AgenceBanque agenceBanque) {
        this.setAgenceBanque(agenceBanque);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prestataire)) {
            return false;
        }
        return id != null && id.equals(((Prestataire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prestataire{" +
            "id=" + getId() +
            ", civilite='" + getCivilite() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nomCommercial='" + getNomCommercial() + "'" +
            ", telephoneTravail='" + getTelephoneTravail() + "'" +
            ", telephoneMobile='" + getTelephoneMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", photoDeProfil='" + getPhotoDeProfil() + "'" +
            ", photoDeProfilContentType='" + getPhotoDeProfilContentType() + "'" +
            ", numeroPieceIdentite='" + getNumeroPieceIdentite() + "'" +
            ", typeIdentiteProfessionnelle='" + getTypeIdentiteProfessionnelle() + "'" +
            ", rattachIdentitePro='" + getRattachIdentitePro() + "'" +
            ", rattachIdentiteProContentType='" + getRattachIdentiteProContentType() + "'" +
            ", coordonneesBancaires='" + getCoordonneesBancaires() + "'" +
            ", coordonneesBancairesContentType='" + getCoordonneesBancairesContentType() + "'" +
            ", titulaireDuCompte='" + getTitulaireDuCompte() + "'" +
            ", ribOuRip='" + getRibOuRip() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
