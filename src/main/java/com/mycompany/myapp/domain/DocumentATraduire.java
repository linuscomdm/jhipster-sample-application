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
 * A DocumentATraduire.
 */
@Entity
@Table(name = "document_a_traduire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentATraduire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre_de_pages_a_traduire", nullable = false)
    private Integer nombreDePagesATraduire;

    @Size(max = 500)
    @Column(name = "mention_traitement_particulier", length = 500)
    private String mentionTraitementParticulier;

    @Size(max = 500)
    @Column(name = "remarques", length = 500)
    private String remarques;

    @OneToMany(mappedBy = "docTraductions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "docTraductions" }, allowSetters = true)
    private Set<Langue> documens = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "docTraductions" }, allowSetters = true)
    private Langue langueDestination;

    @ManyToOne
    private NatureDocumentATraduire typeDocument;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "traductions", "traductionsPjs", "traductionsPrestas", "devis", "commentaires", "ville", "demandeurService" },
        allowSetters = true
    )
    private DemandeDeTraduction demandeTraductions;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocumentATraduire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNombreDePagesATraduire() {
        return this.nombreDePagesATraduire;
    }

    public DocumentATraduire nombreDePagesATraduire(Integer nombreDePagesATraduire) {
        this.setNombreDePagesATraduire(nombreDePagesATraduire);
        return this;
    }

    public void setNombreDePagesATraduire(Integer nombreDePagesATraduire) {
        this.nombreDePagesATraduire = nombreDePagesATraduire;
    }

    public String getMentionTraitementParticulier() {
        return this.mentionTraitementParticulier;
    }

    public DocumentATraduire mentionTraitementParticulier(String mentionTraitementParticulier) {
        this.setMentionTraitementParticulier(mentionTraitementParticulier);
        return this;
    }

    public void setMentionTraitementParticulier(String mentionTraitementParticulier) {
        this.mentionTraitementParticulier = mentionTraitementParticulier;
    }

    public String getRemarques() {
        return this.remarques;
    }

    public DocumentATraduire remarques(String remarques) {
        this.setRemarques(remarques);
        return this;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public Set<Langue> getDocumens() {
        return this.documens;
    }

    public void setDocumens(Set<Langue> langues) {
        if (this.documens != null) {
            this.documens.forEach(i -> i.setDocTraductions(null));
        }
        if (langues != null) {
            langues.forEach(i -> i.setDocTraductions(this));
        }
        this.documens = langues;
    }

    public DocumentATraduire documens(Set<Langue> langues) {
        this.setDocumens(langues);
        return this;
    }

    public DocumentATraduire addDocumens(Langue langue) {
        this.documens.add(langue);
        langue.setDocTraductions(this);
        return this;
    }

    public DocumentATraduire removeDocumens(Langue langue) {
        this.documens.remove(langue);
        langue.setDocTraductions(null);
        return this;
    }

    public Langue getLangueDestination() {
        return this.langueDestination;
    }

    public void setLangueDestination(Langue langue) {
        this.langueDestination = langue;
    }

    public DocumentATraduire langueDestination(Langue langue) {
        this.setLangueDestination(langue);
        return this;
    }

    public NatureDocumentATraduire getTypeDocument() {
        return this.typeDocument;
    }

    public void setTypeDocument(NatureDocumentATraduire natureDocumentATraduire) {
        this.typeDocument = natureDocumentATraduire;
    }

    public DocumentATraduire typeDocument(NatureDocumentATraduire natureDocumentATraduire) {
        this.setTypeDocument(natureDocumentATraduire);
        return this;
    }

    public DemandeDeTraduction getDemandeTraductions() {
        return this.demandeTraductions;
    }

    public void setDemandeTraductions(DemandeDeTraduction demandeDeTraduction) {
        this.demandeTraductions = demandeDeTraduction;
    }

    public DocumentATraduire demandeTraductions(DemandeDeTraduction demandeDeTraduction) {
        this.setDemandeTraductions(demandeDeTraduction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentATraduire)) {
            return false;
        }
        return id != null && id.equals(((DocumentATraduire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentATraduire{" +
            "id=" + getId() +
            ", nombreDePagesATraduire=" + getNombreDePagesATraduire() +
            ", mentionTraitementParticulier='" + getMentionTraitementParticulier() + "'" +
            ", remarques='" + getRemarques() + "'" +
            "}";
    }
}
