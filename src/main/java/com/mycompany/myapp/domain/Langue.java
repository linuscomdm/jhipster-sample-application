package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Langue.
 */
@Entity
@Table(name = "langue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Langue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code_langue")
    private String codeLangue;

    @Column(name = "nom_langue")
    private String nomLangue;

    @ManyToOne
    @JsonIgnoreProperties(value = { "documens", "langueDestination", "typeDocument", "demandeTraductions" }, allowSetters = true)
    private DocumentATraduire docTraductions;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Langue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeLangue() {
        return this.codeLangue;
    }

    public Langue codeLangue(String codeLangue) {
        this.setCodeLangue(codeLangue);
        return this;
    }

    public void setCodeLangue(String codeLangue) {
        this.codeLangue = codeLangue;
    }

    public String getNomLangue() {
        return this.nomLangue;
    }

    public Langue nomLangue(String nomLangue) {
        this.setNomLangue(nomLangue);
        return this;
    }

    public void setNomLangue(String nomLangue) {
        this.nomLangue = nomLangue;
    }

    public DocumentATraduire getDocTraductions() {
        return this.docTraductions;
    }

    public void setDocTraductions(DocumentATraduire documentATraduire) {
        this.docTraductions = documentATraduire;
    }

    public Langue docTraductions(DocumentATraduire documentATraduire) {
        this.setDocTraductions(documentATraduire);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Langue)) {
            return false;
        }
        return id != null && id.equals(((Langue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Langue{" +
            "id=" + getId() +
            ", codeLangue='" + getCodeLangue() + "'" +
            ", nomLangue='" + getNomLangue() + "'" +
            "}";
    }
}
