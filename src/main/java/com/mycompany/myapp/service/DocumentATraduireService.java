package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DocumentATraduire;
import com.mycompany.myapp.repository.DocumentATraduireRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DocumentATraduire}.
 */
@Service
@Transactional
public class DocumentATraduireService {

    private final Logger log = LoggerFactory.getLogger(DocumentATraduireService.class);

    private final DocumentATraduireRepository documentATraduireRepository;

    public DocumentATraduireService(DocumentATraduireRepository documentATraduireRepository) {
        this.documentATraduireRepository = documentATraduireRepository;
    }

    /**
     * Save a documentATraduire.
     *
     * @param documentATraduire the entity to save.
     * @return the persisted entity.
     */
    public DocumentATraduire save(DocumentATraduire documentATraduire) {
        log.debug("Request to save DocumentATraduire : {}", documentATraduire);
        return documentATraduireRepository.save(documentATraduire);
    }

    /**
     * Update a documentATraduire.
     *
     * @param documentATraduire the entity to save.
     * @return the persisted entity.
     */
    public DocumentATraduire update(DocumentATraduire documentATraduire) {
        log.debug("Request to update DocumentATraduire : {}", documentATraduire);
        return documentATraduireRepository.save(documentATraduire);
    }

    /**
     * Partially update a documentATraduire.
     *
     * @param documentATraduire the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentATraduire> partialUpdate(DocumentATraduire documentATraduire) {
        log.debug("Request to partially update DocumentATraduire : {}", documentATraduire);

        return documentATraduireRepository
            .findById(documentATraduire.getId())
            .map(existingDocumentATraduire -> {
                if (documentATraduire.getNombreDePagesATraduire() != null) {
                    existingDocumentATraduire.setNombreDePagesATraduire(documentATraduire.getNombreDePagesATraduire());
                }
                if (documentATraduire.getMentionTraitementParticulier() != null) {
                    existingDocumentATraduire.setMentionTraitementParticulier(documentATraduire.getMentionTraitementParticulier());
                }
                if (documentATraduire.getRemarques() != null) {
                    existingDocumentATraduire.setRemarques(documentATraduire.getRemarques());
                }

                return existingDocumentATraduire;
            })
            .map(documentATraduireRepository::save);
    }

    /**
     * Get all the documentATraduires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentATraduire> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentATraduires");
        return documentATraduireRepository.findAll(pageable);
    }

    /**
     * Get all the documentATraduires with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DocumentATraduire> findAllWithEagerRelationships(Pageable pageable) {
        return documentATraduireRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one documentATraduire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentATraduire> findOne(Long id) {
        log.debug("Request to get DocumentATraduire : {}", id);
        return documentATraduireRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the documentATraduire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentATraduire : {}", id);
        documentATraduireRepository.deleteById(id);
    }
}
