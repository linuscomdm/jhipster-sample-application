package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Commentaire;
import com.mycompany.myapp.repository.CommentaireRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Commentaire}.
 */
@Service
@Transactional
public class CommentaireService {

    private final Logger log = LoggerFactory.getLogger(CommentaireService.class);

    private final CommentaireRepository commentaireRepository;

    public CommentaireService(CommentaireRepository commentaireRepository) {
        this.commentaireRepository = commentaireRepository;
    }

    /**
     * Save a commentaire.
     *
     * @param commentaire the entity to save.
     * @return the persisted entity.
     */
    public Commentaire save(Commentaire commentaire) {
        log.debug("Request to save Commentaire : {}", commentaire);
        return commentaireRepository.save(commentaire);
    }

    /**
     * Update a commentaire.
     *
     * @param commentaire the entity to save.
     * @return the persisted entity.
     */
    public Commentaire update(Commentaire commentaire) {
        log.debug("Request to update Commentaire : {}", commentaire);
        return commentaireRepository.save(commentaire);
    }

    /**
     * Partially update a commentaire.
     *
     * @param commentaire the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Commentaire> partialUpdate(Commentaire commentaire) {
        log.debug("Request to partially update Commentaire : {}", commentaire);

        return commentaireRepository
            .findById(commentaire.getId())
            .map(existingCommentaire -> {
                if (commentaire.getTexte() != null) {
                    existingCommentaire.setTexte(commentaire.getTexte());
                }

                return existingCommentaire;
            })
            .map(commentaireRepository::save);
    }

    /**
     * Get all the commentaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Commentaire> findAll(Pageable pageable) {
        log.debug("Request to get all Commentaires");
        return commentaireRepository.findAll(pageable);
    }

    /**
     * Get one commentaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Commentaire> findOne(Long id) {
        log.debug("Request to get Commentaire : {}", id);
        return commentaireRepository.findById(id);
    }

    /**
     * Delete the commentaire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Commentaire : {}", id);
        commentaireRepository.deleteById(id);
    }
}
