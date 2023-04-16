package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Prestataire;
import com.mycompany.myapp.repository.PrestataireRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Prestataire}.
 */
@Service
@Transactional
public class PrestataireService {

    private final Logger log = LoggerFactory.getLogger(PrestataireService.class);

    private final PrestataireRepository prestataireRepository;

    public PrestataireService(PrestataireRepository prestataireRepository) {
        this.prestataireRepository = prestataireRepository;
    }

    /**
     * Save a prestataire.
     *
     * @param prestataire the entity to save.
     * @return the persisted entity.
     */
    public Prestataire save(Prestataire prestataire) {
        log.debug("Request to save Prestataire : {}", prestataire);
        return prestataireRepository.save(prestataire);
    }

    /**
     * Update a prestataire.
     *
     * @param prestataire the entity to save.
     * @return the persisted entity.
     */
    public Prestataire update(Prestataire prestataire) {
        log.debug("Request to update Prestataire : {}", prestataire);
        return prestataireRepository.save(prestataire);
    }

    /**
     * Partially update a prestataire.
     *
     * @param prestataire the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Prestataire> partialUpdate(Prestataire prestataire) {
        log.debug("Request to partially update Prestataire : {}", prestataire);

        return prestataireRepository
            .findById(prestataire.getId())
            .map(existingPrestataire -> {
                if (prestataire.getCivilite() != null) {
                    existingPrestataire.setCivilite(prestataire.getCivilite());
                }
                if (prestataire.getNom() != null) {
                    existingPrestataire.setNom(prestataire.getNom());
                }
                if (prestataire.getPrenom() != null) {
                    existingPrestataire.setPrenom(prestataire.getPrenom());
                }
                if (prestataire.getNomCommercial() != null) {
                    existingPrestataire.setNomCommercial(prestataire.getNomCommercial());
                }
                if (prestataire.getTelephoneTravail() != null) {
                    existingPrestataire.setTelephoneTravail(prestataire.getTelephoneTravail());
                }
                if (prestataire.getTelephoneMobile() != null) {
                    existingPrestataire.setTelephoneMobile(prestataire.getTelephoneMobile());
                }
                if (prestataire.getEmail() != null) {
                    existingPrestataire.setEmail(prestataire.getEmail());
                }
                if (prestataire.getAdresse() != null) {
                    existingPrestataire.setAdresse(prestataire.getAdresse());
                }
                if (prestataire.getCodePostal() != null) {
                    existingPrestataire.setCodePostal(prestataire.getCodePostal());
                }
                if (prestataire.getPhotoDeProfil() != null) {
                    existingPrestataire.setPhotoDeProfil(prestataire.getPhotoDeProfil());
                }
                if (prestataire.getPhotoDeProfilContentType() != null) {
                    existingPrestataire.setPhotoDeProfilContentType(prestataire.getPhotoDeProfilContentType());
                }
                if (prestataire.getNumeroPieceIdentite() != null) {
                    existingPrestataire.setNumeroPieceIdentite(prestataire.getNumeroPieceIdentite());
                }
                if (prestataire.getTypeIdentiteProfessionnelle() != null) {
                    existingPrestataire.setTypeIdentiteProfessionnelle(prestataire.getTypeIdentiteProfessionnelle());
                }
                if (prestataire.getRattachIdentitePro() != null) {
                    existingPrestataire.setRattachIdentitePro(prestataire.getRattachIdentitePro());
                }
                if (prestataire.getRattachIdentiteProContentType() != null) {
                    existingPrestataire.setRattachIdentiteProContentType(prestataire.getRattachIdentiteProContentType());
                }
                if (prestataire.getCoordonneesBancaires() != null) {
                    existingPrestataire.setCoordonneesBancaires(prestataire.getCoordonneesBancaires());
                }
                if (prestataire.getCoordonneesBancairesContentType() != null) {
                    existingPrestataire.setCoordonneesBancairesContentType(prestataire.getCoordonneesBancairesContentType());
                }
                if (prestataire.getTitulaireDuCompte() != null) {
                    existingPrestataire.setTitulaireDuCompte(prestataire.getTitulaireDuCompte());
                }
                if (prestataire.getRibOuRip() != null) {
                    existingPrestataire.setRibOuRip(prestataire.getRibOuRip());
                }
                if (prestataire.getDateCreation() != null) {
                    existingPrestataire.setDateCreation(prestataire.getDateCreation());
                }
                if (prestataire.getEtat() != null) {
                    existingPrestataire.setEtat(prestataire.getEtat());
                }

                return existingPrestataire;
            })
            .map(prestataireRepository::save);
    }

    /**
     * Get all the prestataires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Prestataire> findAll(Pageable pageable) {
        log.debug("Request to get all Prestataires");
        return prestataireRepository.findAll(pageable);
    }

    /**
     * Get all the prestataires with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Prestataire> findAllWithEagerRelationships(Pageable pageable) {
        return prestataireRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one prestataire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Prestataire> findOne(Long id) {
        log.debug("Request to get Prestataire : {}", id);
        return prestataireRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the prestataire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Prestataire : {}", id);
        prestataireRepository.deleteById(id);
    }
}
