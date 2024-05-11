package org.devine.aberrant.character;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

//    Endpoint to spend Nova points
    @PostMapping("{characterId}/spendNovaPointsOnMegaAttribute")
    public ResponseEntity<String> spendNovaPoints(@PathVariable Long characterId, @RequestBody Map<String, Object> novaSpendingRequest) {
        try {
//            Extract necessary parameters from the request
            int novaPointsSpent = (int) novaSpendingRequest.get("novaPointsSpent");
            String megaAttributeName = (String) novaSpendingRequest.get("megaAttributeName");
            String enhancementName = (String) novaSpendingRequest.get("enhancementName");
//            Extract other parameters as needed

            Character character = characterService.findById(characterId);

//            Spend Nova points
            characterService.spendNovaPointsOnMegaAttribute(character, megaAttributeName, enhancementName);
//            Call other service methods for additional actions

//             Return a success response
            return ResponseEntity.ok("Nova points spent successfully.");
        } catch (Exception e) {
            // Return an error response if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to spend Nova points: " + e.getMessage());
        }
    }
}
