package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Notation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotationRepository extends JpaRepository<Notation, Long> {}
