package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Banque;
import com.mycompany.myapp.repository.BanqueRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Banque}.
 */
@Service
@Transactional
public class BanqueService {

    private final Logger log = LoggerFactory.getLogger(BanqueService.class);

    private final BanqueRepository banqueRepository;

    public BanqueService(BanqueRepository banqueRepository) {
        this.banqueRepository = banqueRepository;
    }

    /**
     * Save a banque.
     *
     * @param banque the entity to save.
     * @return the persisted entity.
     */
    public Banque save(Banque banque) {
        log.debug("Request to save Banque : {}", banque);
        return banqueRepository.save(banque);
    }

    /**
     * Update a banque.
     *
     * @param banque the entity to save.
     * @return the persisted entity.
     */
    public Banque update(Banque banque) {
        log.debug("Request to update Banque : {}", banque);
        return banqueRepository.save(banque);
    }

    /**
     * Partially update a banque.
     *
     * @param banque the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Banque> partialUpdate(Banque banque) {
        log.debug("Request to partially update Banque : {}", banque);

        return banqueRepository
            .findById(banque.getId())
            .map(existingBanque -> {
                if (banque.getCode() != null) {
                    existingBanque.setCode(banque.getCode());
                }
                if (banque.getLibelle() != null) {
                    existingBanque.setLibelle(banque.getLibelle());
                }

                return existingBanque;
            })
            .map(banqueRepository::save);
    }

    /**
     * Get all the banques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Banque> findAll(Pageable pageable) {
        log.debug("Request to get all Banques");
        return banqueRepository.findAll(pageable);
    }

    /**
     * Get one banque by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Banque> findOne(Long id) {
        log.debug("Request to get Banque : {}", id);
        return banqueRepository.findById(id);
    }

    /**
     * Delete the banque by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Banque : {}", id);
        banqueRepository.deleteById(id);
    }
}
