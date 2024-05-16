package org.devine.aberrant.character;

import org.devine.aberrant.ability.AllocateAbilityPointsRequest;
import org.devine.aberrant.attribute.AllocateAttributePointsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // Endpoint to retrieve a character by ID
    @GetMapping("/{characterId}")
    public ResponseEntity<Character> getCharacterById(@PathVariable Long characterId) {
        Character character = characterService.findById(characterId);
        return ResponseEntity.ok(character);
    }

    @PostMapping("/create")
    public ResponseEntity<Character> createCharacter(@RequestBody CharacterCreationRequest request) {
        Character character = characterService.createCharacter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }

    // Endpoint to allocate attribute points
    @PostMapping("/{characterId}/allocateAttributePoints")
    public ResponseEntity<Character> allocateAttributePoints(
            @PathVariable Long characterId,
            @RequestBody AllocateAttributePointsRequest request) {
        Character character = characterService.allocateAttributePoints(characterService.findById(characterId), request);
        return ResponseEntity.ok(character);
    }

    // Endpoint to allocate ability points
    @PostMapping("/{characterId}/allocateAbilityPoints")
    public ResponseEntity<Character> allocateAbilityPoints(
            @PathVariable Long characterId,
            @RequestBody AllocateAbilityPointsRequest request) {
        Character character = characterService.allocateAbilityPoints(characterService.findById(characterId), request);
        return ResponseEntity.ok(character);
    }

    // Endpoint to allocate background points
    @PostMapping("/{characterId}/allocateBackgroundPoints")
    public ResponseEntity<Character> allocateBackgroundPoints(
            @PathVariable Long characterId,
            @RequestBody AllocateBackgroundPointsRequest request) {
        Character character = characterService.allocateBackgroundPoints(characterService.findById(characterId), request);
        return ResponseEntity.ok(character);
    }

}
