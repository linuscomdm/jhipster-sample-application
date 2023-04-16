package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AgenceBanque;
import com.mycompany.myapp.repository.AgenceBanqueRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AgenceBanque}.
 */
@Service
@Transactional
public class AgenceBanqueService {

    private final Logger log = LoggerFactory.getLogger(AgenceBanqueService.class);

    private final AgenceBanqueRepository agenceBanqueRepository;

    public AgenceBanqueService(AgenceBanqueRepository agenceBanqueRepository) {
        this.agenceBanqueRepository = agenceBanqueRepository;
    }

    /**
     * Save a agenceBanque.
     *
     * @param agenceBanque the entity to save.
     * @return the persisted entity.
     */
    public AgenceBanque save(AgenceBanque agenceBanque) {
        log.debug("Request to save AgenceBanque : {}", agenceBanque);
        return agenceBanqueRepository.save(agenceBanque);
    }

    /**
     * Update a agenceBanque.
     *
     * @param agenceBanque the entity to save.
     * @return the persisted entity.
     */
    public AgenceBanque update(AgenceBanque agenceBanque) {
        log.debug("Request to update AgenceBanque : {}", agenceBanque);
        return agenceBanqueRepository.save(agenceBanque);
    }

    /**
     * Partially update a agenceBanque.
     *
     * @param agenceBanque the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AgenceBanque> partialUpdate(AgenceBanque agenceBanque) {
        log.debug("Request to partially update AgenceBanque : {}", agenceBanque);

        return agenceBanqueRepository
            .findById(agenceBanque.getId())
            .map(existingAgenceBanque -> {
                if (agenceBanque.getCode() != null) {
                    existingAgenceBanque.setCode(agenceBanque.getCode());
                }
                if (agenceBanque.getLibelle() != null) {
                    existingAgenceBanque.setLibelle(agenceBanque.getLibelle());
                }

                return existingAgenceBanque;
            })
            .map(agenceBanqueRepository::save);
    }

    /**
     * Get all the agenceBanques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AgenceBanque> findAll(Pageable pageable) {
        log.debug("Request to get all AgenceBanques");
        return agenceBanqueRepository.findAll(pageable);
    }

    /**
     * Get one agenceBanque by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgenceBanque> findOne(Long id) {
        log.debug("Request to get AgenceBanque : {}", id);
        return agenceBanqueRepository.findById(id);
    }

    /**
     * Delete the agenceBanque by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgenceBanque : {}", id);
        agenceBanqueRepository.deleteById(id);
    }
}
