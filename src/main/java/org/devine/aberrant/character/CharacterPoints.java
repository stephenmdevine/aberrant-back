package org.devine.aberrant.character;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "character")
@EqualsAndHashCode(exclude = "character")
public class CharacterPoints {

    @Id
    @Column(name = "character")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "character", referencedColumnName = "id")
    private Character character;

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

}
