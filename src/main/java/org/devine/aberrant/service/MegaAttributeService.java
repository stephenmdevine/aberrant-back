package org.devine.aberrant.service;

import org.devine.aberrant.model.MegaAttribute;
import org.devine.aberrant.repository.MegaAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MegaAttributeService {

    @Autowired
    private MegaAttributeRepository megaAttributeRepository;

    public List<MegaAttribute> findAll() {
        return megaAttributeRepository.findAll();
    }

    public MegaAttribute findById(Long megaAttributeId) {
        return megaAttributeRepository.findById(megaAttributeId)
                .orElseThrow(() -> new IllegalArgumentException("Mega Attribute not found"));
    }

}
