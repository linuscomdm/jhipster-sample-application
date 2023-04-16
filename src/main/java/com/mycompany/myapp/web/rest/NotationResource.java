package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Notation;
import com.mycompany.myapp.repository.NotationRepository;
import com.mycompany.myapp.service.NotationService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Notation}.
 */
@RestController
@RequestMapping("/api")
public class NotationResource {

    private final Logger log = LoggerFactory.getLogger(NotationResource.class);

    private static final String ENTITY_NAME = "notation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotationService notationService;

    private final NotationRepository notationRepository;

    public NotationResource(NotationService notationService, NotationRepository notationRepository) {
        this.notationService = notationService;
        this.notationRepository = notationRepository;
    }

    /**
     * {@code POST  /notations} : Create a new notation.
     *
     * @param notation the notation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notation, or with status {@code 400 (Bad Request)} if the notation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notations")
    public ResponseEntity<Notation> createNotation(@Valid @RequestBody Notation notation) throws URISyntaxException {
        log.debug("REST request to save Notation : {}", notation);
        if (notation.getId() != null) {
            throw new BadRequestAlertException("A new notation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Notation result = notationService.save(notation);
        return ResponseEntity
            .created(new URI("/api/notations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notations/:id} : Updates an existing notation.
     *
     * @param id the id of the notation to save.
     * @param notation the notation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notation,
     * or with status {@code 400 (Bad Request)} if the notation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notations/{id}")
    public ResponseEntity<Notation> updateNotation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Notation notation
    ) throws URISyntaxException {
        log.debug("REST request to update Notation : {}, {}", id, notation);
        if (notation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Notation result = notationService.update(notation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notations/:id} : Partial updates given fields of an existing notation, field will ignore if it is null
     *
     * @param id the id of the notation to save.
     * @param notation the notation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notation,
     * or with status {@code 400 (Bad Request)} if the notation is not valid,
     * or with status {@code 404 (Not Found)} if the notation is not found,
     * or with status {@code 500 (Internal Server Error)} if the notation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Notation> partialUpdateNotation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Notation notation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Notation partially : {}, {}", id, notation);
        if (notation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Notation> result = notationService.partialUpdate(notation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notation.getId().toString())
        );
    }

    /**
     * {@code GET  /notations} : get all the notations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notations in body.
     */
    @GetMapping("/notations")
    public ResponseEntity<List<Notation>> getAllNotations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Notations");
        Page<Notation> page = notationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notations/:id} : get the "id" notation.
     *
     * @param id the id of the notation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notations/{id}")
    public ResponseEntity<Notation> getNotation(@PathVariable Long id) {
        log.debug("REST request to get Notation : {}", id);
        Optional<Notation> notation = notationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notation);
    }

    /**
     * {@code DELETE  /notations/:id} : delete the "id" notation.
     *
     * @param id the id of the notation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notations/{id}")
    public ResponseEntity<Void> deleteNotation(@PathVariable Long id) {
        log.debug("REST request to delete Notation : {}", id);
        notationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
