package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Ville;
import com.mycompany.myapp.repository.VilleRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ville}.
 */
@Service
@Transactional
public class VilleService {

    private final Logger log = LoggerFactory.getLogger(VilleService.class);

    private final VilleRepository villeRepository;

    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    /**
     * Save a ville.
     *
     * @param ville the entity to save.
     * @return the persisted entity.
     */
    public Ville save(Ville ville) {
        log.debug("Request to save Ville : {}", ville);
        return villeRepository.save(ville);
    }

    /**
     * Update a ville.
     *
     * @param ville the entity to save.
     * @return the persisted entity.
     */
    public Ville update(Ville ville) {
        log.debug("Request to update Ville : {}", ville);
        return villeRepository.save(ville);
    }

    /**
     * Partially update a ville.
     *
     * @param ville the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ville> partialUpdate(Ville ville) {
        log.debug("Request to partially update Ville : {}", ville);

        return villeRepository
            .findById(ville.getId())
            .map(existingVille -> {
                if (ville.getNom() != null) {
                    existingVille.setNom(ville.getNom());
                }

                return existingVille;
            })
            .map(villeRepository::save);
    }

    /**
     * Get all the villes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Ville> findAll(Pageable pageable) {
        log.debug("Request to get all Villes");
        return villeRepository.findAll(pageable);
    }

    /**
     * Get one ville by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Ville> findOne(Long id) {
        log.debug("Request to get Ville : {}", id);
        return villeRepository.findById(id);
    }

    /**
     * Delete the ville by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Ville : {}", id);
        villeRepository.deleteById(id);
    }
}
