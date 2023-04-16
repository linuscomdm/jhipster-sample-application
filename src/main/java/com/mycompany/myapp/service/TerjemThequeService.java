package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TerjemTheque;
import com.mycompany.myapp.repository.TerjemThequeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TerjemTheque}.
 */
@Service
@Transactional
public class TerjemThequeService {

    private final Logger log = LoggerFactory.getLogger(TerjemThequeService.class);

    private final TerjemThequeRepository terjemThequeRepository;

    public TerjemThequeService(TerjemThequeRepository terjemThequeRepository) {
        this.terjemThequeRepository = terjemThequeRepository;
    }

    /**
     * Save a terjemTheque.
     *
     * @param terjemTheque the entity to save.
     * @return the persisted entity.
     */
    public TerjemTheque save(TerjemTheque terjemTheque) {
        log.debug("Request to save TerjemTheque : {}", terjemTheque);
        return terjemThequeRepository.save(terjemTheque);
    }

    /**
     * Update a terjemTheque.
     *
     * @param terjemTheque the entity to save.
     * @return the persisted entity.
     */
    public TerjemTheque update(TerjemTheque terjemTheque) {
        log.debug("Request to update TerjemTheque : {}", terjemTheque);
        return terjemThequeRepository.save(terjemTheque);
    }

    /**
     * Partially update a terjemTheque.
     *
     * @param terjemTheque the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TerjemTheque> partialUpdate(TerjemTheque terjemTheque) {
        log.debug("Request to partially update TerjemTheque : {}", terjemTheque);

        return terjemThequeRepository
            .findById(terjemTheque.getId())
            .map(existingTerjemTheque -> {
                if (terjemTheque.getLienDownload() != null) {
                    existingTerjemTheque.setLienDownload(terjemTheque.getLienDownload());
                }
                if (terjemTheque.getFormatDocTraduit() != null) {
                    existingTerjemTheque.setFormatDocTraduit(terjemTheque.getFormatDocTraduit());
                }
                if (terjemTheque.getNomFichier() != null) {
                    existingTerjemTheque.setNomFichier(terjemTheque.getNomFichier());
                }
                if (terjemTheque.getDocTraduit() != null) {
                    existingTerjemTheque.setDocTraduit(terjemTheque.getDocTraduit());
                }
                if (terjemTheque.getDocTraduitContentType() != null) {
                    existingTerjemTheque.setDocTraduitContentType(terjemTheque.getDocTraduitContentType());
                }
                if (terjemTheque.getDateCreation() != null) {
                    existingTerjemTheque.setDateCreation(terjemTheque.getDateCreation());
                }
                if (terjemTheque.getEtat() != null) {
                    existingTerjemTheque.setEtat(terjemTheque.getEtat());
                }

                return existingTerjemTheque;
            })
            .map(terjemThequeRepository::save);
    }

    /**
     * Get all the terjemTheques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TerjemTheque> findAll(Pageable pageable) {
        log.debug("Request to get all TerjemTheques");
        return terjemThequeRepository.findAll(pageable);
    }

    /**
     * Get one terjemTheque by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TerjemTheque> findOne(Long id) {
        log.debug("Request to get TerjemTheque : {}", id);
        return terjemThequeRepository.findById(id);
    }

    /**
     * Delete the terjemTheque by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TerjemTheque : {}", id);
        terjemThequeRepository.deleteById(id);
    }
}
