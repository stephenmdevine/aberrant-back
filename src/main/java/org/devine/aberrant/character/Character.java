package org.devine.aberrant.character;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.devine.aberrant.ability.Ability;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.attribute.AttributeSet;
import org.devine.aberrant.background.Background;
import org.devine.aberrant.megaAttribute.Enhancement;
import org.devine.aberrant.megaAttribute.MegaAttribute;
import org.devine.aberrant.power.Power;

import java.util.List;

@Entity
@Data
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64)
    @Column(name = "name")
    private String name;

// Additional Character Info
    @NotBlank
    @Size(max = 64)
    @Column(name = "player")
    private String player;

    @Size(max = 64)
    @Column(name = "nova_name")
    private String novaName;

    @Size(max = 32)
    @Column(name = "concept")
    private String concept;

    @Size(max = 32)
    @Column(name = "nature")
    private String nature;

    @Size(max = 64)
    @Column(name = "allegiance")
    private String allegiance;

    @Size(max = 256)
    @Column(name = "description")
    private String description;

// Additional Character Stats
    @Column(name = "willpower")
    private int willpower = 3;

    @Column(name = "quantum")
    private int quantum = 1;

    @Column(name = "quantum_pool")
    private int quantumPool;

    @Column(name = "initiative")
    private int initiative;

// Character Point Info
    @Column(name = "attribute_points")
    private int attributePoints;

    @Column(name = "ability_points")
    private int abilityPoints;

    @Column(name = "background_points")
    private int backgroundPoints;

    @Column(name = "bonus_points")
    private int bonusPoints;

    @Column(name = "nova_points")
    private int novaPoints;

    @Column(name = "experience_points")
    private int experiencePoints;

// Relational link to other tables
    @OneToMany(mappedBy = "character")
    private List<Ability> abilities;

    @OneToMany(mappedBy = "character")
    private List<Attribute> attributes;

    @OneToMany(mappedBy = "character")
    private List<AttributeSet> attributeSets;

    @OneToMany(mappedBy = "character")
    private List<Background> backgrounds;

    @OneToMany(mappedBy = "character")
    private List<MegaAttribute> megaAttributes;

    @OneToMany(mappedBy = "character")
    private List<Power> powers;

    @OneToMany(mappedBy = "character")
    private List<Flaw> flaws;

    @OneToMany(mappedBy = "character")
    private List<Merit> merits;

    @OneToMany(mappedBy = "character")
    private List<Enhancement> enhancements;

    public Character() {
//        Calculate initial initiative based on Dex and Wits
        this.initiative = this.getAttributeValue("Dexterity") + this.getAttributeValue("Wits");
    }

//     Method to retrieve the value of a specific attribute by name
    public int getAttributeValue(String attributeName) {
        // Iterate through the list of attributes
        for (Attribute attribute : attributes) {
            if (attribute.getName().equalsIgnoreCase(attributeName)) {
                return attribute.getValue();
            }
        }
        // Return a default value or handle the case when the attribute is not found
        return 0; // Default value if attribute not found
    }

//     Method to retrieve the value of a specific mega attribute by name
    public int getMegaAttributeValue(String megaAttributeName) {
        // Iterate through the list of attributes
        for (MegaAttribute megaAttribute : megaAttributes) {
            if (megaAttribute.getName().equalsIgnoreCase(megaAttributeName)) {
                return megaAttribute.getValue();
            }
        }
        // Return a default value or handle the case when the attribute is not found
        return 0; // Default value if attribute not found
    }

}
