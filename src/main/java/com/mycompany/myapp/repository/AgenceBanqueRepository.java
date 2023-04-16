package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AgenceBanque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AgenceBanque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenceBanqueRepository extends JpaRepository<AgenceBanque, Long> {}
