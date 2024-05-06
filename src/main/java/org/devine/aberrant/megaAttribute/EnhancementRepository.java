package org.devine.aberrant.megaAttribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnhancementRepository extends JpaRepository<Enhancement, Long> {
}
