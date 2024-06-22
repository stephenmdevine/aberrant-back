/*package org.devine.aberrant.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backgrounds")
public class BackgroundController {

    @Autowired
    private BackgroundService backgroundService;

    @GetMapping
    public ResponseEntity<List<Background>> getAllBackgrounds() {
        List<Background> backgrounds = backgroundService.findAll();
        return ResponseEntity.ok(backgrounds);
    }

    @GetMapping("/{backgroundId}")
    public ResponseEntity<Background> getBackgroundById(@PathVariable Long backgroundId) {
        Background background = backgroundService.findById(backgroundId);
        return ResponseEntity.ok(background);
    }

}
*/