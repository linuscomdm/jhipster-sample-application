package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DemandeDeTraduction;
import com.mycompany.myapp.repository.DemandeDeTraductionRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandeDeTraduction}.
 */
@Service
@Transactional
public class DemandeDeTraductionService {

    private final Logger log = LoggerFactory.getLogger(DemandeDeTraductionService.class);

    private final DemandeDeTraductionRepository demandeDeTraductionRepository;

    public DemandeDeTraductionService(DemandeDeTraductionRepository demandeDeTraductionRepository) {
        this.demandeDeTraductionRepository = demandeDeTraductionRepository;
    }

    /**
     * Save a demandeDeTraduction.
     *
     * @param demandeDeTraduction the entity to save.
     * @return the persisted entity.
     */
    public DemandeDeTraduction save(DemandeDeTraduction demandeDeTraduction) {
        log.debug("Request to save DemandeDeTraduction : {}", demandeDeTraduction);
        return demandeDeTraductionRepository.save(demandeDeTraduction);
    }

    /**
     * Update a demandeDeTraduction.
     *
     * @param demandeDeTraduction the entity to save.
     * @return the persisted entity.
     */
    public DemandeDeTraduction update(DemandeDeTraduction demandeDeTraduction) {
        log.debug("Request to update DemandeDeTraduction : {}", demandeDeTraduction);
        return demandeDeTraductionRepository.save(demandeDeTraduction);
    }

    /**
     * Partially update a demandeDeTraduction.
     *
     * @param demandeDeTraduction the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemandeDeTraduction> partialUpdate(DemandeDeTraduction demandeDeTraduction) {
        log.debug("Request to partially update DemandeDeTraduction : {}", demandeDeTraduction);

        return demandeDeTraductionRepository
            .findById(demandeDeTraduction.getId())
            .map(existingDemandeDeTraduction -> {
                if (demandeDeTraduction.getModeEnvoiPreconise() != null) {
                    existingDemandeDeTraduction.setModeEnvoiPreconise(demandeDeTraduction.getModeEnvoiPreconise());
                }
                if (demandeDeTraduction.getModeLivraisonExige() != null) {
                    existingDemandeDeTraduction.setModeLivraisonExige(demandeDeTraduction.getModeLivraisonExige());
                }
                if (demandeDeTraduction.getDelaiDeTraitemenSouhaite() != null) {
                    existingDemandeDeTraduction.setDelaiDeTraitemenSouhaite(demandeDeTraduction.getDelaiDeTraitemenSouhaite());
                }
                if (demandeDeTraduction.getAdresseLivraison() != null) {
                    existingDemandeDeTraduction.setAdresseLivraison(demandeDeTraduction.getAdresseLivraison());
                }
                if (demandeDeTraduction.getDelaiDeTraitemenPrestataire() != null) {
                    existingDemandeDeTraduction.setDelaiDeTraitemenPrestataire(demandeDeTraduction.getDelaiDeTraitemenPrestataire());
                }
                if (demandeDeTraduction.getObservation() != null) {
                    existingDemandeDeTraduction.setObservation(demandeDeTraduction.getObservation());
                }
                if (demandeDeTraduction.getDateCreation() != null) {
                    existingDemandeDeTraduction.setDateCreation(demandeDeTraduction.getDateCreation());
                }
                if (demandeDeTraduction.getDateCloture() != null) {
                    existingDemandeDeTraduction.setDateCloture(demandeDeTraduction.getDateCloture());
                }
                if (demandeDeTraduction.getEtat() != null) {
                    existingDemandeDeTraduction.setEtat(demandeDeTraduction.getEtat());
                }

                return existingDemandeDeTraduction;
            })
            .map(demandeDeTraductionRepository::save);
    }

    /**
     * Get all the demandeDeTraductions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeDeTraduction> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeDeTraductions");
        return demandeDeTraductionRepository.findAll(pageable);
    }

    /**
     * Get all the demandeDeTraductions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DemandeDeTraduction> findAllWithEagerRelationships(Pageable pageable) {
        return demandeDeTraductionRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one demandeDeTraduction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DemandeDeTraduction> findOne(Long id) {
        log.debug("Request to get DemandeDeTraduction : {}", id);
        return demandeDeTraductionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the demandeDeTraduction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DemandeDeTraduction : {}", id);
        demandeDeTraductionRepository.deleteById(id);
    }
}
