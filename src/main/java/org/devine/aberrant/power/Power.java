/*package org.devine.aberrant.power;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.character.Character;

@Entity
@Data
public class Power {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 64)
    @Column(name = "name")
    private String name;

    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "value")
    private int value;

    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "level")
    private int level;

    @Column(name = "quantum_minimum")
    private int quantumMinimum;

    @Column(name = "has_extra")
    private Boolean hasExtra;

    @Column(name = "extra_name")
    private String extraName;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}
*/