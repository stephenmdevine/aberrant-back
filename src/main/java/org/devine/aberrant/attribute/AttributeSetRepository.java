package org.devine.aberrant.attribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeSetRepository extends JpaRepository<AttributeSet, Long> {
}
