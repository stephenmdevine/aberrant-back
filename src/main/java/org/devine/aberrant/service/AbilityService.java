package org.devine.aberrant.service;

import org.devine.aberrant.model.Ability;
import org.devine.aberrant.repository.AbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AbilityService {

    @Autowired
    private AbilityRepository abilityRepository;

    public List<Ability> findAll() {
        return abilityRepository.findAll();
    }

    public Ability findById(Long abilityId) {
        return abilityRepository.findById(abilityId)
                .orElseThrow(() -> new IllegalArgumentException("Ability not found"));
    }

}
