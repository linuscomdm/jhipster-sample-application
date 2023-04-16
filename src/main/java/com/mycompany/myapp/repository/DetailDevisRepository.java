package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DetailDevis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetailDevis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailDevisRepository extends JpaRepository<DetailDevis, Long> {}
