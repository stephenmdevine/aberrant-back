package org.devine.aberrant.character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaintRepository extends JpaRepository<Taint, Long> {
}
