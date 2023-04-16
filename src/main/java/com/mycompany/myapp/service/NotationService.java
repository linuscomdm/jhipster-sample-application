package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Notation;
import com.mycompany.myapp.repository.NotationRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Notation}.
 */
@Service
@Transactional
public class NotationService {

    private final Logger log = LoggerFactory.getLogger(NotationService.class);

    private final NotationRepository notationRepository;

    public NotationService(NotationRepository notationRepository) {
        this.notationRepository = notationRepository;
    }

    /**
     * Save a notation.
     *
     * @param notation the entity to save.
     * @return the persisted entity.
     */
    public Notation save(Notation notation) {
        log.debug("Request to save Notation : {}", notation);
        return notationRepository.save(notation);
    }

    /**
     * Update a notation.
     *
     * @param notation the entity to save.
     * @return the persisted entity.
     */
    public Notation update(Notation notation) {
        log.debug("Request to update Notation : {}", notation);
        return notationRepository.save(notation);
    }

    /**
     * Partially update a notation.
     *
     * @param notation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Notation> partialUpdate(Notation notation) {
        log.debug("Request to partially update Notation : {}", notation);

        return notationRepository
            .findById(notation.getId())
            .map(existingNotation -> {
                if (notation.getNotetation() != null) {
                    existingNotation.setNotetation(notation.getNotetation());
                }
                if (notation.getCommentaire() != null) {
                    existingNotation.setCommentaire(notation.getCommentaire());
                }
                if (notation.getDateCreation() != null) {
                    existingNotation.setDateCreation(notation.getDateCreation());
                }

                return existingNotation;
            })
            .map(notationRepository::save);
    }

    /**
     * Get all the notations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Notation> findAll(Pageable pageable) {
        log.debug("Request to get all Notations");
        return notationRepository.findAll(pageable);
    }

    /**
     * Get one notation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Notation> findOne(Long id) {
        log.debug("Request to get Notation : {}", id);
        return notationRepository.findById(id);
    }

    /**
     * Delete the notation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Notation : {}", id);
        notationRepository.deleteById(id);
    }
}
