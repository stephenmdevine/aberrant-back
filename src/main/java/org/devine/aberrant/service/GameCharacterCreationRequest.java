package org.devine.aberrant.service;

import lombok.Data;

@Data
public class GameCharacterCreationRequest {

    private String name;
    private String player;
    private String novaName;
    private String concept;
    private String nature;
    private String allegiance;
    private String description;

// Additional fields for attribute, ability, background, etc., points allocation
    private int attributePoints;
    private int abilityPoints;
    private int backgroundPoints;
    private int bonusPoints;
    private int novaPoints;
    private int experiencePoints;

}
