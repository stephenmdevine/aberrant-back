package org.devine.aberrant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class GameCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String player;
    private String novaName;
    private String concept;
    private String nature;
    private String allegiance;
    private String description;

// Additional Character Stats
    private int willpower = 3;
    private int quantum = 1;
    private int quantumPool = 22;
    private int initiative = 0;
    private int taint = 0;

// Character Point Info
    private int attributePoints;
    private int abilityPoints;
    private int backgroundPoints;
    private int bonusPoints;
    private int novaPoints;
    private int experiencePoints;

// Relational link to other tables
    @OneToMany(mappedBy = "gameCharacter")
    private List<Ability> abilities = new ArrayList<>();
    @OneToMany(mappedBy = "gameCharacter")
    private List<Attribute> attributes = new ArrayList<>();
    @OneToMany(mappedBy = "gameCharacter")
    private List<AttributeSet> attributeSets = new ArrayList<>();
    @OneToMany(mappedBy = "gameCharacter")
    private List<Background> backgrounds = new ArrayList<>();
    @OneToMany(mappedBy = "gameCharacter")
    private List<MegaAttribute> megaAttributes = new ArrayList<>();
    @OneToMany(mappedBy = "gameCharacter")
    private List<Power> powers = new ArrayList<>();
    @OneToMany(mappedBy = "gameCharacter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Flaw> flaws = new ArrayList<>();
    @OneToMany(mappedBy = "gameCharacter", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Merit> merits = new ArrayList<>();
    @OneToMany(mappedBy = "gameCharacter")
    private List<Enhancement> enhancements = new ArrayList<>();

    public GameCharacter() {}

//  Method to retrieve the value of a specific attribute by name
    public int getAttributeValue(String attributeName) {
//      Iterate through the list of attributes
        for (Attribute attribute : attributes) {
            if (attribute.getName().equalsIgnoreCase(attributeName)) {
                return attribute.getValue();
            }
        }
//      Return a default value or handle the case when the attribute is not found
        return 0; // Default value if attribute not found
    }

//  Method to retrieve the number of times any attribute was purchased with Nova points
    public int getNoOfAttrsBoughtWithNovaPts() {
        int total = 0;
//      Iterate through the list of attributes
        for (Attribute attribute : attributes) {
            total += attribute.getNovaPurchased();
        }
//      Return the total amount of attributes purchased with Nova points
        return total;
    }

//  Method to retrieve the value of a specific ability by name
    public int getAbilityValue(String abilityName) {
//      Iterate through the list of abilities
        for (Ability ability : abilities) {
            if (ability.getName().equalsIgnoreCase(abilityName)) {
                return ability.getValue();
            }
        }
//      Return a default value or handle the case when the ability is not found
        return 0; // Default value if ability not found
    }

//  Method to retrieve the number of times any ability was purchased with Nova points
    public int getNoOfAbilsBoughtWithNovaPts() {
        int total = 0;
        // Iterate through the list of attributes
        for (Ability ability : abilities) {
            total += ability.getNovaPurchased();
        }
        // Return the total amount of attributes purchased with Nova points
        return total;
    }

//  Method to retrieve the value of a specific background by name
    public int getBackgroundValue(String backgroundName) {
//      Iterate through the list of backgrounds
        for (Background background : backgrounds) {
            if (background.getName().equalsIgnoreCase(backgroundName)) {
                return background.getValue();
            }
        }
//      Return a default value or handle the case when the background is not found
        return 0; // Default value if background not found
    }

//     Method to retrieve the number of times any background was purchased with Nova points
    public int getNoOfBkgrsBoughtWithNovaPts() {
        int total = 0;
//      Iterate through the list of backgrounds
        for (Background background : backgrounds) {
            total += background.getNovaPurchased();
        }
//      Return the total amount of backgrounds purchased with Nova points
        return total;
    }

//  Method to retrieve the value of a specific mega attribute by name
    public int getMegaAttributeValue(String megaAttributeName) {
//      Iterate through the list of attributes
        for (MegaAttribute megaAttribute : megaAttributes) {
            if (megaAttribute.getName().equalsIgnoreCase(megaAttributeName)) {
                return megaAttribute.getValue();
            }
        }
//      Return a default value or handle the case when the attribute is not found
        return 0; // Default value if attribute not found
    }

//  Method to retrieve the value of a specific power by name
    public int getPowerValue(String powerName) {
//      Iterate through the list of powers
        for (Power power : powers) {
            if (power.getName().equalsIgnoreCase(powerName)) {
                return power.getValue();
            }
        }
//      Return a default value or handle the case when the attribute is not found
        return 0; // Default value if attribute not found
    }

}
