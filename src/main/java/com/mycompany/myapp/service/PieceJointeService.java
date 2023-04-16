package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.PieceJointe;
import com.mycompany.myapp.repository.PieceJointeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PieceJointe}.
 */
@Service
@Transactional
public class PieceJointeService {

    private final Logger log = LoggerFactory.getLogger(PieceJointeService.class);

    private final PieceJointeRepository pieceJointeRepository;

    public PieceJointeService(PieceJointeRepository pieceJointeRepository) {
        this.pieceJointeRepository = pieceJointeRepository;
    }

    /**
     * Save a pieceJointe.
     *
     * @param pieceJointe the entity to save.
     * @return the persisted entity.
     */
    public PieceJointe save(PieceJointe pieceJointe) {
        log.debug("Request to save PieceJointe : {}", pieceJointe);
        return pieceJointeRepository.save(pieceJointe);
    }

    /**
     * Update a pieceJointe.
     *
     * @param pieceJointe the entity to save.
     * @return the persisted entity.
     */
    public PieceJointe update(PieceJointe pieceJointe) {
        log.debug("Request to update PieceJointe : {}", pieceJointe);
        return pieceJointeRepository.save(pieceJointe);
    }

    /**
     * Partially update a pieceJointe.
     *
     * @param pieceJointe the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PieceJointe> partialUpdate(PieceJointe pieceJointe) {
        log.debug("Request to partially update PieceJointe : {}", pieceJointe);

        return pieceJointeRepository
            .findById(pieceJointe.getId())
            .map(existingPieceJointe -> {
                if (pieceJointe.getNomFichier() != null) {
                    existingPieceJointe.setNomFichier(pieceJointe.getNomFichier());
                }
                if (pieceJointe.getChemin() != null) {
                    existingPieceJointe.setChemin(pieceJointe.getChemin());
                }
                if (pieceJointe.getUrlPiece() != null) {
                    existingPieceJointe.setUrlPiece(pieceJointe.getUrlPiece());
                }
                if (pieceJointe.getDescription() != null) {
                    existingPieceJointe.setDescription(pieceJointe.getDescription());
                }
                if (pieceJointe.getCodePiece() != null) {
                    existingPieceJointe.setCodePiece(pieceJointe.getCodePiece());
                }
                if (pieceJointe.getLibellePiece() != null) {
                    existingPieceJointe.setLibellePiece(pieceJointe.getLibellePiece());
                }
                if (pieceJointe.getRattachPj() != null) {
                    existingPieceJointe.setRattachPj(pieceJointe.getRattachPj());
                }
                if (pieceJointe.getRattachPjContentType() != null) {
                    existingPieceJointe.setRattachPjContentType(pieceJointe.getRattachPjContentType());
                }
                if (pieceJointe.getDateCreation() != null) {
                    existingPieceJointe.setDateCreation(pieceJointe.getDateCreation());
                }

                return existingPieceJointe;
            })
            .map(pieceJointeRepository::save);
    }

    /**
     * Get all the pieceJointes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PieceJointe> findAll(Pageable pageable) {
        log.debug("Request to get all PieceJointes");
        return pieceJointeRepository.findAll(pageable);
    }

    /**
     * Get all the pieceJointes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PieceJointe> findAllWithEagerRelationships(Pageable pageable) {
        return pieceJointeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one pieceJointe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PieceJointe> findOne(Long id) {
        log.debug("Request to get PieceJointe : {}", id);
        return pieceJointeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the pieceJointe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PieceJointe : {}", id);
        pieceJointeRepository.deleteById(id);
    }
}
