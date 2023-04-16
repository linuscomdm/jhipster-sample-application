package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.NatureDocumentATraduire;
import com.mycompany.myapp.repository.NatureDocumentATraduireRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureDocumentATraduire}.
 */
@Service
@Transactional
public class NatureDocumentATraduireService {

    private final Logger log = LoggerFactory.getLogger(NatureDocumentATraduireService.class);

    private final NatureDocumentATraduireRepository natureDocumentATraduireRepository;

    public NatureDocumentATraduireService(NatureDocumentATraduireRepository natureDocumentATraduireRepository) {
        this.natureDocumentATraduireRepository = natureDocumentATraduireRepository;
    }

    /**
     * Save a natureDocumentATraduire.
     *
     * @param natureDocumentATraduire the entity to save.
     * @return the persisted entity.
     */
    public NatureDocumentATraduire save(NatureDocumentATraduire natureDocumentATraduire) {
        log.debug("Request to save NatureDocumentATraduire : {}", natureDocumentATraduire);
        return natureDocumentATraduireRepository.save(natureDocumentATraduire);
    }

    /**
     * Update a natureDocumentATraduire.
     *
     * @param natureDocumentATraduire the entity to save.
     * @return the persisted entity.
     */
    public NatureDocumentATraduire update(NatureDocumentATraduire natureDocumentATraduire) {
        log.debug("Request to update NatureDocumentATraduire : {}", natureDocumentATraduire);
        return natureDocumentATraduireRepository.save(natureDocumentATraduire);
    }

    /**
     * Partially update a natureDocumentATraduire.
     *
     * @param natureDocumentATraduire the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NatureDocumentATraduire> partialUpdate(NatureDocumentATraduire natureDocumentATraduire) {
        log.debug("Request to partially update NatureDocumentATraduire : {}", natureDocumentATraduire);

        return natureDocumentATraduireRepository
            .findById(natureDocumentATraduire.getId())
            .map(existingNatureDocumentATraduire -> {
                if (natureDocumentATraduire.getCodeType() != null) {
                    existingNatureDocumentATraduire.setCodeType(natureDocumentATraduire.getCodeType());
                }
                if (natureDocumentATraduire.getTypeDocument() != null) {
                    existingNatureDocumentATraduire.setTypeDocument(natureDocumentATraduire.getTypeDocument());
                }

                return existingNatureDocumentATraduire;
            })
            .map(natureDocumentATraduireRepository::save);
    }

    /**
     * Get all the natureDocumentATraduires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NatureDocumentATraduire> findAll(Pageable pageable) {
        log.debug("Request to get all NatureDocumentATraduires");
        return natureDocumentATraduireRepository.findAll(pageable);
    }

    /**
     * Get one natureDocumentATraduire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NatureDocumentATraduire> findOne(Long id) {
        log.debug("Request to get NatureDocumentATraduire : {}", id);
        return natureDocumentATraduireRepository.findById(id);
    }

    /**
     * Delete the natureDocumentATraduire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NatureDocumentATraduire : {}", id);
        natureDocumentATraduireRepository.deleteById(id);
    }
}
