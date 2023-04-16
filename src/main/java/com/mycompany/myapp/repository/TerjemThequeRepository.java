package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TerjemTheque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TerjemTheque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TerjemThequeRepository extends JpaRepository<TerjemTheque, Long> {}
