package org.devine.aberrant.ability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/abilities")
public class AbilityController {

    @Autowired
    private AbilityService abilityService;

    @GetMapping
    public ResponseEntity<List<Ability>> getAllAbilities() {
        List<Ability> abilities = abilityService.findAll();
        return ResponseEntity.ok(abilities);
    }

    @GetMapping("/{abilityId}")
    public ResponseEntity<Ability> getAbilityById(@PathVariable Long abilityId) {
        Ability ability = abilityService.findById(abilityId);
        return ResponseEntity.ok(ability);
    }

}
