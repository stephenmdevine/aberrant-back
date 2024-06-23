package org.devine.aberrant.repository;

import org.devine.aberrant.model.AttributeSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeSetRepository extends JpaRepository<AttributeSet, Long> {
}
