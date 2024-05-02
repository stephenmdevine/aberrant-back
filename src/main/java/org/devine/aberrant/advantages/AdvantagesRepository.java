package org.devine.aberrant.advantages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvantagesRepository extends JpaRepository<Advantages, Integer> {
}
