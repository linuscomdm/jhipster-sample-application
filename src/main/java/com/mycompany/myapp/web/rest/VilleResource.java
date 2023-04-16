package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Ville;
import com.mycompany.myapp.repository.VilleRepository;
import com.mycompany.myapp.service.VilleService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Ville}.
 */
@RestController
@RequestMapping("/api")
public class VilleResource {

    private final Logger log = LoggerFactory.getLogger(VilleResource.class);

    private static final String ENTITY_NAME = "ville";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VilleService villeService;

    private final VilleRepository villeRepository;

    public VilleResource(VilleService villeService, VilleRepository villeRepository) {
        this.villeService = villeService;
        this.villeRepository = villeRepository;
    }

    /**
     * {@code POST  /villes} : Create a new ville.
     *
     * @param ville the ville to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ville, or with status {@code 400 (Bad Request)} if the ville has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/villes")
    public ResponseEntity<Ville> createVille(@RequestBody Ville ville) throws URISyntaxException {
        log.debug("REST request to save Ville : {}", ville);
        if (ville.getId() != null) {
            throw new BadRequestAlertException("A new ville cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ville result = villeService.save(ville);
        return ResponseEntity
            .created(new URI("/api/villes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /villes/:id} : Updates an existing ville.
     *
     * @param id the id of the ville to save.
     * @param ville the ville to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ville,
     * or with status {@code 400 (Bad Request)} if the ville is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ville couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/villes/{id}")
    public ResponseEntity<Ville> updateVille(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ville ville)
        throws URISyntaxException {
        log.debug("REST request to update Ville : {}, {}", id, ville);
        if (ville.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ville.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!villeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ville result = villeService.update(ville);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ville.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /villes/:id} : Partial updates given fields of an existing ville, field will ignore if it is null
     *
     * @param id the id of the ville to save.
     * @param ville the ville to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ville,
     * or with status {@code 400 (Bad Request)} if the ville is not valid,
     * or with status {@code 404 (Not Found)} if the ville is not found,
     * or with status {@code 500 (Internal Server Error)} if the ville couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/villes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ville> partialUpdateVille(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ville ville)
        throws URISyntaxException {
        log.debug("REST request to partial update Ville partially : {}, {}", id, ville);
        if (ville.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ville.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!villeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ville> result = villeService.partialUpdate(ville);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ville.getId().toString())
        );
    }

    /**
     * {@code GET  /villes} : get all the villes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of villes in body.
     */
    @GetMapping("/villes")
    public ResponseEntity<List<Ville>> getAllVilles(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Villes");
        Page<Ville> page = villeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /villes/:id} : get the "id" ville.
     *
     * @param id the id of the ville to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ville, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/villes/{id}")
    public ResponseEntity<Ville> getVille(@PathVariable Long id) {
        log.debug("REST request to get Ville : {}", id);
        Optional<Ville> ville = villeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ville);
    }

    /**
     * {@code DELETE  /villes/:id} : delete the "id" ville.
     *
     * @param id the id of the ville to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/villes/{id}")
    public ResponseEntity<Void> deleteVille(@PathVariable Long id) {
        log.debug("REST request to delete Ville : {}", id);
        villeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
