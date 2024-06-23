package org.devine.aberrant.repository;

import org.devine.aberrant.model.MegaAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MegaAttributeRepository extends JpaRepository<MegaAttribute, Long> {
}
