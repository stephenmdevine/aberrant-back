package org.devine.aberrant.service;

import org.devine.aberrant.repository.CombatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CombatService {

    @Autowired
    private CombatRepository combatRepository;

}
