package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NatureDocumentATraduire.
 */
@Entity
@Table(name = "nature_document_a_traduire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NatureDocumentATraduire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code_type")
    private String codeType;

    @Column(name = "type_document")
    private String typeDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NatureDocumentATraduire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeType() {
        return this.codeType;
    }

    public NatureDocumentATraduire codeType(String codeType) {
        this.setCodeType(codeType);
        return this;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getTypeDocument() {
        return this.typeDocument;
    }

    public NatureDocumentATraduire typeDocument(String typeDocument) {
        this.setTypeDocument(typeDocument);
        return this;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NatureDocumentATraduire)) {
            return false;
        }
        return id != null && id.equals(((NatureDocumentATraduire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatureDocumentATraduire{" +
            "id=" + getId() +
            ", codeType='" + getCodeType() + "'" +
            ", typeDocument='" + getTypeDocument() + "'" +
            "}";
    }
}
