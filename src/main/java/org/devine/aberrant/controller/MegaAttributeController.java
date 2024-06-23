package org.devine.aberrant.controller;

import org.devine.aberrant.model.MegaAttribute;
import org.devine.aberrant.service.MegaAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/megaAttribute")
public class MegaAttributeController {

    @Autowired
    private MegaAttributeService megaAttributeService;

    @GetMapping
    public ResponseEntity<List<MegaAttribute>> getAllMegaAttributes() {
        List<MegaAttribute> megaAttributes = megaAttributeService.findAll();
        return ResponseEntity.ok(megaAttributes);
    }

    @GetMapping("/{megaAttributeId}")
    public ResponseEntity<MegaAttribute> getMegaAttributeById(@PathVariable Long megaAttributeId) {
        MegaAttribute megaAttribute = megaAttributeService.findById(megaAttributeId);
        return ResponseEntity.ok(megaAttribute);
    }

}
