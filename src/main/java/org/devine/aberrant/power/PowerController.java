package org.devine.aberrant.power;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/powers")
public class PowerController {

    @Autowired PowerService powerService;

    @GetMapping
    public ResponseEntity<List<Power>> getAllPowers() {
        List<Power> powers = powerService.findAll();
        return ResponseEntity.ok(powers);
    }

    @GetMapping("/{powerId}")
    public ResponseEntity<Power> getPowerById(@PathVariable Long powerId) {
        Power power = powerService.findById(powerId);
        return ResponseEntity.ok(power);
    }
}
