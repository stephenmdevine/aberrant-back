package org.devine.aberrant.repository;

import org.devine.aberrant.model.Enhancement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnhancementRepository extends JpaRepository<Enhancement, Long> {
}
