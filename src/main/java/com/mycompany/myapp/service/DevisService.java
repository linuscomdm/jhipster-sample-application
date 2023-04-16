package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Devis;
import com.mycompany.myapp.repository.DevisRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Devis}.
 */
@Service
@Transactional
public class DevisService {

    private final Logger log = LoggerFactory.getLogger(DevisService.class);

    private final DevisRepository devisRepository;

    public DevisService(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    /**
     * Save a devis.
     *
     * @param devis the entity to save.
     * @return the persisted entity.
     */
    public Devis save(Devis devis) {
        log.debug("Request to save Devis : {}", devis);
        return devisRepository.save(devis);
    }

    /**
     * Update a devis.
     *
     * @param devis the entity to save.
     * @return the persisted entity.
     */
    public Devis update(Devis devis) {
        log.debug("Request to update Devis : {}", devis);
        return devisRepository.save(devis);
    }

    /**
     * Partially update a devis.
     *
     * @param devis the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Devis> partialUpdate(Devis devis) {
        log.debug("Request to partially update Devis : {}", devis);

        return devisRepository
            .findById(devis.getId())
            .map(existingDevis -> {
                if (devis.getNumero() != null) {
                    existingDevis.setNumero(devis.getNumero());
                }
                if (devis.getDate() != null) {
                    existingDevis.setDate(devis.getDate());
                }
                if (devis.getPrixTotal() != null) {
                    existingDevis.setPrixTotal(devis.getPrixTotal());
                }
                if (devis.getEtat() != null) {
                    existingDevis.setEtat(devis.getEtat());
                }

                return existingDevis;
            })
            .map(devisRepository::save);
    }

    /**
     * Get all the devis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Devis> findAll(Pageable pageable) {
        log.debug("Request to get all Devis");
        return devisRepository.findAll(pageable);
    }

    /**
     * Get one devis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Devis> findOne(Long id) {
        log.debug("Request to get Devis : {}", id);
        return devisRepository.findById(id);
    }

    /**
     * Delete the devis by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Devis : {}", id);
        devisRepository.deleteById(id);
    }
}
