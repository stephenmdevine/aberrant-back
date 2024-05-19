package org.devine.aberrant.character;

import org.devine.aberrant.ability.AllocateAbilityPointsRequest;
import org.devine.aberrant.attribute.AllocateAttributePointsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
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

    @PostMapping("/{characterId}/spendBonusPoints")
    public ResponseEntity<Character> spendBonusPoints(
            @PathVariable Long characterId,
            @RequestBody SpendPointsRequest request) {
        Character character = characterService.findById(characterId);
        character = handlePointSpending(character, request, true, false);
        return ResponseEntity.ok(character);
    }

    @PostMapping("/{characterId}/spendNovaPoints")
    public ResponseEntity<Character> spendNovaPoints(
            @PathVariable Long characterId,
            @RequestBody SpendPointsRequest request) {
        Character character = characterService.findById(characterId);
        character = handlePointSpending(character, request, false, true);
        return ResponseEntity.ok(character);
    }

    @PostMapping("/{characterId}/spendExperiencePoints")
    public ResponseEntity<Character> spendExperiencePoints(
            @PathVariable Long characterId,
            @RequestBody SpendPointsRequest request) {
        Character character = characterService.findById(characterId);
        character = handlePointSpending(character, request, false, false);
        return ResponseEntity.ok(character);
    }

    private Character handlePointSpending(Character character, SpendPointsRequest request, boolean isBonus, boolean isNova) {
        switch (request.getPointType().toLowerCase()) {
            case "attribute":
                characterService.increaseAttribute(character, request.getName(), request.getQualityName(), isBonus, isNova);
                break;
            case "ability":
                characterService.increaseAbility(character, request.getName(), isBonus, isNova);
                break;
            case "background":
                characterService.increaseBackground(character, request.getName(), isBonus, isNova);
                break;
            case "willpower":
                characterService.increaseWillpower(character, isBonus, isNova);
                break;
            case "quantum":
                characterService.increaseQuantum(character, isBonus, isNova, request.isTainted());
                break;
            case "initiative":
                characterService.increaseInitiative(character, isBonus);
                break;
            case "merit":
                characterService.addMerit(character, request.getName(), request.getValue());
                break;
            case "flaw":
                characterService.addFlaw(character, request.getName(), request.getValue());
                break;
            case "megaattribute":
                characterService.increaseMegaAttribute(character, request.getName(), request.getEnhancementName(), isBonus, request.isTainted());
                break;
            case "enhancement":
                characterService.addEnhancement(character, request.getName(), request.getExtraName(), isBonus, request.isTainted());
                break;
            case "power":
                characterService.increasePower(character, request.getName(), request.getValue(), request.getQuantumMinimum(), request.getAttributeName(), request.isHasExtra(), request.getExtraName(), isBonus, request.isTainted());
                break;
            case "abilityspecialty":
                characterService.addAbilitySpecialty(character, request.getName(), request.getSpecialtyName(), isBonus);
                break;
            case "quantumpool":
                characterService.increaseQuantumPool(character);
                break;
            default:
                throw new IllegalArgumentException("Invalid point type");
        }
        return characterService.saveCharacter(character); // Assuming you have a saveCharacter method to persist changes
    }

}
