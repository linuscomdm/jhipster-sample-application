package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DetailDevis;
import com.mycompany.myapp.repository.DetailDevisRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetailDevis}.
 */
@Service
@Transactional
public class DetailDevisService {

    private final Logger log = LoggerFactory.getLogger(DetailDevisService.class);

    private final DetailDevisRepository detailDevisRepository;

    public DetailDevisService(DetailDevisRepository detailDevisRepository) {
        this.detailDevisRepository = detailDevisRepository;
    }

    /**
     * Save a detailDevis.
     *
     * @param detailDevis the entity to save.
     * @return the persisted entity.
     */
    public DetailDevis save(DetailDevis detailDevis) {
        log.debug("Request to save DetailDevis : {}", detailDevis);
        return detailDevisRepository.save(detailDevis);
    }

    /**
     * Update a detailDevis.
     *
     * @param detailDevis the entity to save.
     * @return the persisted entity.
     */
    public DetailDevis update(DetailDevis detailDevis) {
        log.debug("Request to update DetailDevis : {}", detailDevis);
        return detailDevisRepository.save(detailDevis);
    }

    /**
     * Partially update a detailDevis.
     *
     * @param detailDevis the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DetailDevis> partialUpdate(DetailDevis detailDevis) {
        log.debug("Request to partially update DetailDevis : {}", detailDevis);

        return detailDevisRepository
            .findById(detailDevis.getId())
            .map(existingDetailDevis -> {
                if (detailDevis.getQte() != null) {
                    existingDetailDevis.setQte(detailDevis.getQte());
                }
                if (detailDevis.getPrixUnitaire() != null) {
                    existingDetailDevis.setPrixUnitaire(detailDevis.getPrixUnitaire());
                }
                if (detailDevis.getPrixTotal() != null) {
                    existingDetailDevis.setPrixTotal(detailDevis.getPrixTotal());
                }
                if (detailDevis.getEtat() != null) {
                    existingDetailDevis.setEtat(detailDevis.getEtat());
                }

                return existingDetailDevis;
            })
            .map(detailDevisRepository::save);
    }

    /**
     * Get all the detailDevis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetailDevis> findAll(Pageable pageable) {
        log.debug("Request to get all DetailDevis");
        return detailDevisRepository.findAll(pageable);
    }

    /**
     * Get one detailDevis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetailDevis> findOne(Long id) {
        log.debug("Request to get DetailDevis : {}", id);
        return detailDevisRepository.findById(id);
    }

    /**
     * Delete the detailDevis by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetailDevis : {}", id);
        detailDevisRepository.deleteById(id);
    }
}
