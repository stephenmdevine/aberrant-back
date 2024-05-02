package org.devine.aberrant.backgrounds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundsRepository extends JpaRepository<Backgrounds, Integer> {
}
