package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TerjemTheque;
import com.mycompany.myapp.repository.TerjemThequeRepository;
import com.mycompany.myapp.service.TerjemThequeService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TerjemTheque}.
 */
@RestController
@RequestMapping("/api")
public class TerjemThequeResource {

    private final Logger log = LoggerFactory.getLogger(TerjemThequeResource.class);

    private static final String ENTITY_NAME = "terjemTheque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TerjemThequeService terjemThequeService;

    private final TerjemThequeRepository terjemThequeRepository;

    public TerjemThequeResource(TerjemThequeService terjemThequeService, TerjemThequeRepository terjemThequeRepository) {
        this.terjemThequeService = terjemThequeService;
        this.terjemThequeRepository = terjemThequeRepository;
    }

    /**
     * {@code POST  /terjem-theques} : Create a new terjemTheque.
     *
     * @param terjemTheque the terjemTheque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new terjemTheque, or with status {@code 400 (Bad Request)} if the terjemTheque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terjem-theques")
    public ResponseEntity<TerjemTheque> createTerjemTheque(@Valid @RequestBody TerjemTheque terjemTheque) throws URISyntaxException {
        log.debug("REST request to save TerjemTheque : {}", terjemTheque);
        if (terjemTheque.getId() != null) {
            throw new BadRequestAlertException("A new terjemTheque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TerjemTheque result = terjemThequeService.save(terjemTheque);
        return ResponseEntity
            .created(new URI("/api/terjem-theques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terjem-theques/:id} : Updates an existing terjemTheque.
     *
     * @param id the id of the terjemTheque to save.
     * @param terjemTheque the terjemTheque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terjemTheque,
     * or with status {@code 400 (Bad Request)} if the terjemTheque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the terjemTheque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terjem-theques/{id}")
    public ResponseEntity<TerjemTheque> updateTerjemTheque(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TerjemTheque terjemTheque
    ) throws URISyntaxException {
        log.debug("REST request to update TerjemTheque : {}, {}", id, terjemTheque);
        if (terjemTheque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, terjemTheque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!terjemThequeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TerjemTheque result = terjemThequeService.update(terjemTheque);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terjemTheque.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /terjem-theques/:id} : Partial updates given fields of an existing terjemTheque, field will ignore if it is null
     *
     * @param id the id of the terjemTheque to save.
     * @param terjemTheque the terjemTheque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated terjemTheque,
     * or with status {@code 400 (Bad Request)} if the terjemTheque is not valid,
     * or with status {@code 404 (Not Found)} if the terjemTheque is not found,
     * or with status {@code 500 (Internal Server Error)} if the terjemTheque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/terjem-theques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TerjemTheque> partialUpdateTerjemTheque(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TerjemTheque terjemTheque
    ) throws URISyntaxException {
        log.debug("REST request to partial update TerjemTheque partially : {}, {}", id, terjemTheque);
        if (terjemTheque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, terjemTheque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!terjemThequeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TerjemTheque> result = terjemThequeService.partialUpdate(terjemTheque);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, terjemTheque.getId().toString())
        );
    }

    /**
     * {@code GET  /terjem-theques} : get all the terjemTheques.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terjemTheques in body.
     */
    @GetMapping("/terjem-theques")
    public ResponseEntity<List<TerjemTheque>> getAllTerjemTheques(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TerjemTheques");
        Page<TerjemTheque> page = terjemThequeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terjem-theques/:id} : get the "id" terjemTheque.
     *
     * @param id the id of the terjemTheque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the terjemTheque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terjem-theques/{id}")
    public ResponseEntity<TerjemTheque> getTerjemTheque(@PathVariable Long id) {
        log.debug("REST request to get TerjemTheque : {}", id);
        Optional<TerjemTheque> terjemTheque = terjemThequeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(terjemTheque);
    }

    /**
     * {@code DELETE  /terjem-theques/:id} : delete the "id" terjemTheque.
     *
     * @param id the id of the terjemTheque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terjem-theques/{id}")
    public ResponseEntity<Void> deleteTerjemTheque(@PathVariable Long id) {
        log.debug("REST request to delete TerjemTheque : {}", id);
        terjemThequeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
