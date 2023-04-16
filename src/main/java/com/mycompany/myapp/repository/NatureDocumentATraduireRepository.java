package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NatureDocumentATraduire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NatureDocumentATraduire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureDocumentATraduireRepository extends JpaRepository<NatureDocumentATraduire, Long> {}
