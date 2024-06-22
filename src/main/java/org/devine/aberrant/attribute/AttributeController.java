/*package org.devine.aberrant.attribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @GetMapping
    public ResponseEntity<List<Attribute>> getAllAttributes() {
        List<Attribute> attributes = attributeService.findAll();
        return ResponseEntity.ok(attributes);
    }

    @GetMapping("/{attributeId}")
    public ResponseEntity<Attribute> getAttributeById(@PathVariable Long attributeId) {
        Attribute attribute = attributeService.findById(attributeId);
        return ResponseEntity.ok(attribute);
    }

}
*/