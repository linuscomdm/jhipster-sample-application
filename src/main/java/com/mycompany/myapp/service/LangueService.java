package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Langue;
import com.mycompany.myapp.repository.LangueRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Langue}.
 */
@Service
@Transactional
public class LangueService {

    private final Logger log = LoggerFactory.getLogger(LangueService.class);

    private final LangueRepository langueRepository;

    public LangueService(LangueRepository langueRepository) {
        this.langueRepository = langueRepository;
    }

    /**
     * Save a langue.
     *
     * @param langue the entity to save.
     * @return the persisted entity.
     */
    public Langue save(Langue langue) {
        log.debug("Request to save Langue : {}", langue);
        return langueRepository.save(langue);
    }

    /**
     * Update a langue.
     *
     * @param langue the entity to save.
     * @return the persisted entity.
     */
    public Langue update(Langue langue) {
        log.debug("Request to update Langue : {}", langue);
        return langueRepository.save(langue);
    }

    /**
     * Partially update a langue.
     *
     * @param langue the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Langue> partialUpdate(Langue langue) {
        log.debug("Request to partially update Langue : {}", langue);

        return langueRepository
            .findById(langue.getId())
            .map(existingLangue -> {
                if (langue.getCodeLangue() != null) {
                    existingLangue.setCodeLangue(langue.getCodeLangue());
                }
                if (langue.getNomLangue() != null) {
                    existingLangue.setNomLangue(langue.getNomLangue());
                }

                return existingLangue;
            })
            .map(langueRepository::save);
    }

    /**
     * Get all the langues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Langue> findAll(Pageable pageable) {
        log.debug("Request to get all Langues");
        return langueRepository.findAll(pageable);
    }

    /**
     * Get all the langues with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Langue> findAllWithEagerRelationships(Pageable pageable) {
        return langueRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one langue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Langue> findOne(Long id) {
        log.debug("Request to get Langue : {}", id);
        return langueRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the langue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Langue : {}", id);
        langueRepository.deleteById(id);
    }
}
