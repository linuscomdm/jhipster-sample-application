package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AgenceBanque;
import com.mycompany.myapp.repository.AgenceBanqueRepository;
import com.mycompany.myapp.service.AgenceBanqueService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.AgenceBanque}.
 */
@RestController
@RequestMapping("/api")
public class AgenceBanqueResource {

    private final Logger log = LoggerFactory.getLogger(AgenceBanqueResource.class);

    private static final String ENTITY_NAME = "agenceBanque";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgenceBanqueService agenceBanqueService;

    private final AgenceBanqueRepository agenceBanqueRepository;

    public AgenceBanqueResource(AgenceBanqueService agenceBanqueService, AgenceBanqueRepository agenceBanqueRepository) {
        this.agenceBanqueService = agenceBanqueService;
        this.agenceBanqueRepository = agenceBanqueRepository;
    }

    /**
     * {@code POST  /agence-banques} : Create a new agenceBanque.
     *
     * @param agenceBanque the agenceBanque to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agenceBanque, or with status {@code 400 (Bad Request)} if the agenceBanque has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agence-banques")
    public ResponseEntity<AgenceBanque> createAgenceBanque(@Valid @RequestBody AgenceBanque agenceBanque) throws URISyntaxException {
        log.debug("REST request to save AgenceBanque : {}", agenceBanque);
        if (agenceBanque.getId() != null) {
            throw new BadRequestAlertException("A new agenceBanque cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgenceBanque result = agenceBanqueService.save(agenceBanque);
        return ResponseEntity
            .created(new URI("/api/agence-banques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agence-banques/:id} : Updates an existing agenceBanque.
     *
     * @param id the id of the agenceBanque to save.
     * @param agenceBanque the agenceBanque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenceBanque,
     * or with status {@code 400 (Bad Request)} if the agenceBanque is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agenceBanque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agence-banques/{id}")
    public ResponseEntity<AgenceBanque> updateAgenceBanque(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgenceBanque agenceBanque
    ) throws URISyntaxException {
        log.debug("REST request to update AgenceBanque : {}, {}", id, agenceBanque);
        if (agenceBanque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agenceBanque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agenceBanqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AgenceBanque result = agenceBanqueService.update(agenceBanque);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agenceBanque.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /agence-banques/:id} : Partial updates given fields of an existing agenceBanque, field will ignore if it is null
     *
     * @param id the id of the agenceBanque to save.
     * @param agenceBanque the agenceBanque to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenceBanque,
     * or with status {@code 400 (Bad Request)} if the agenceBanque is not valid,
     * or with status {@code 404 (Not Found)} if the agenceBanque is not found,
     * or with status {@code 500 (Internal Server Error)} if the agenceBanque couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/agence-banques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgenceBanque> partialUpdateAgenceBanque(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgenceBanque agenceBanque
    ) throws URISyntaxException {
        log.debug("REST request to partial update AgenceBanque partially : {}, {}", id, agenceBanque);
        if (agenceBanque.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agenceBanque.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agenceBanqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgenceBanque> result = agenceBanqueService.partialUpdate(agenceBanque);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agenceBanque.getId().toString())
        );
    }

    /**
     * {@code GET  /agence-banques} : get all the agenceBanques.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agenceBanques in body.
     */
    @GetMapping("/agence-banques")
    public ResponseEntity<List<AgenceBanque>> getAllAgenceBanques(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AgenceBanques");
        Page<AgenceBanque> page = agenceBanqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agence-banques/:id} : get the "id" agenceBanque.
     *
     * @param id the id of the agenceBanque to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agenceBanque, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agence-banques/{id}")
    public ResponseEntity<AgenceBanque> getAgenceBanque(@PathVariable Long id) {
        log.debug("REST request to get AgenceBanque : {}", id);
        Optional<AgenceBanque> agenceBanque = agenceBanqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agenceBanque);
    }

    /**
     * {@code DELETE  /agence-banques/:id} : delete the "id" agenceBanque.
     *
     * @param id the id of the agenceBanque to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agence-banques/{id}")
    public ResponseEntity<Void> deleteAgenceBanque(@PathVariable Long id) {
        log.debug("REST request to delete AgenceBanque : {}", id);
        agenceBanqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
