package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Demandeur;
import com.mycompany.myapp.repository.DemandeurRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Demandeur}.
 */
@Service
@Transactional
public class DemandeurService {

    private final Logger log = LoggerFactory.getLogger(DemandeurService.class);

    private final DemandeurRepository demandeurRepository;

    public DemandeurService(DemandeurRepository demandeurRepository) {
        this.demandeurRepository = demandeurRepository;
    }

    /**
     * Save a demandeur.
     *
     * @param demandeur the entity to save.
     * @return the persisted entity.
     */
    public Demandeur save(Demandeur demandeur) {
        log.debug("Request to save Demandeur : {}", demandeur);
        return demandeurRepository.save(demandeur);
    }

    /**
     * Update a demandeur.
     *
     * @param demandeur the entity to save.
     * @return the persisted entity.
     */
    public Demandeur update(Demandeur demandeur) {
        log.debug("Request to update Demandeur : {}", demandeur);
        return demandeurRepository.save(demandeur);
    }

    /**
     * Partially update a demandeur.
     *
     * @param demandeur the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Demandeur> partialUpdate(Demandeur demandeur) {
        log.debug("Request to partially update Demandeur : {}", demandeur);

        return demandeurRepository
            .findById(demandeur.getId())
            .map(existingDemandeur -> {
                if (demandeur.getCivilite() != null) {
                    existingDemandeur.setCivilite(demandeur.getCivilite());
                }
                if (demandeur.getNom() != null) {
                    existingDemandeur.setNom(demandeur.getNom());
                }
                if (demandeur.getPrenom() != null) {
                    existingDemandeur.setPrenom(demandeur.getPrenom());
                }
                if (demandeur.getDateDeNaissance() != null) {
                    existingDemandeur.setDateDeNaissance(demandeur.getDateDeNaissance());
                }
                if (demandeur.getTelephone() != null) {
                    existingDemandeur.setTelephone(demandeur.getTelephone());
                }
                if (demandeur.getEmail() != null) {
                    existingDemandeur.setEmail(demandeur.getEmail());
                }
                if (demandeur.getAdresse() != null) {
                    existingDemandeur.setAdresse(demandeur.getAdresse());
                }
                if (demandeur.getDateCreation() != null) {
                    existingDemandeur.setDateCreation(demandeur.getDateCreation());
                }
                if (demandeur.getEtat() != null) {
                    existingDemandeur.setEtat(demandeur.getEtat());
                }

                return existingDemandeur;
            })
            .map(demandeurRepository::save);
    }

    /**
     * Get all the demandeurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Demandeur> findAll(Pageable pageable) {
        log.debug("Request to get all Demandeurs");
        return demandeurRepository.findAll(pageable);
    }

    /**
     * Get all the demandeurs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Demandeur> findAllWithEagerRelationships(Pageable pageable) {
        return demandeurRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one demandeur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Demandeur> findOne(Long id) {
        log.debug("Request to get Demandeur : {}", id);
        return demandeurRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the demandeur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Demandeur : {}", id);
        demandeurRepository.deleteById(id);
    }
}
