package org.devine.aberrant.service;

import jakarta.persistence.EntityNotFoundException;
import org.devine.aberrant.model.GameCharacter;
import org.devine.aberrant.repository.GameCharacterRepository;
import org.devine.aberrant.request.GameCharacterCreationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameCharacterServiceImpl implements GameCharacterService {

    private GameCharacterRepository gameCharacterRepository;

    public GameCharacterServiceImpl(GameCharacterRepository gameCharacterRepository) {
        this.gameCharacterRepository = gameCharacterRepository;
    }

    @Override
    public GameCharacter createCharacter(GameCharacterCreationRequest request) {
//      Create a new character entity using the data from the request
        GameCharacter gameCharacter = new GameCharacter();
        gameCharacter.setName(request.getName());
        gameCharacter.setPlayer(request.getPlayer());
        gameCharacter.setNovaName(request.getNovaName());
        gameCharacter.setConcept(request.getConcept());
        gameCharacter.setNature(request.getNature());
        gameCharacter.setAllegiance(request.getAllegiance());
        gameCharacter.setDescription(request.getDescription());
        gameCharacter.setAttributePoints(request.getAttributePoints());
        gameCharacter.setAbilityPoints(request.getAbilityPoints());
        gameCharacter.setBackgroundPoints(request.getBackgroundPoints());
        gameCharacter.setBonusPoints(request.getBonusPoints());
        gameCharacter.setNovaPoints(request.getNovaPoints());
        gameCharacter.setExperiencePoints(request.getExperiencePoints());

//      Save the character entity in the database
        return gameCharacterRepository.save(gameCharacter);
    }

    @Override
    public List<GameCharacter> findAll() {
        return gameCharacterRepository.findAll();
    }

    @Override
    public GameCharacter findById(Long gameCharacterId) {
        // Retrieve the character from the database by ID
        Optional<GameCharacter> gameCharacterOptional = gameCharacterRepository.findById(gameCharacterId);

        // If the character is found, return it; otherwise, throw an exception
        return gameCharacterOptional.orElseThrow(() -> new EntityNotFoundException("Character not found with id: " + gameCharacterId));
    }

    @Override
    public GameCharacter saveCharacter(GameCharacter gameCharacter) {
        return gameCharacterRepository.save(gameCharacter);
    }

}
