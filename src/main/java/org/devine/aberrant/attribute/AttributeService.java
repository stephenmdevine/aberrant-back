/*package org.devine.aberrant.attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    public Attribute findById(Long attributeId) {
        return attributeRepository.findById(attributeId)
                .orElseThrow(() -> new IllegalArgumentException("Attribute not found"));
    }

}
*/