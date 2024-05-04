package org.devine.aberrant.character;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.devine.aberrant.ability.Ability;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.attribute.AttributeSet;
import org.devine.aberrant.background.Background;
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

}
