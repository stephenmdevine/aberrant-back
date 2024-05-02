package org.devine.aberrant.ability;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.character.Character;

@Entity
@Data
public class Ability {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @Size(max = 32)
    @Column(name = "name")
    private String name;

    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "value")
    private int value;

    @Size(max = 32)
    @Column(name = "specialty_1")
    private String specialty1;

    @Size(max = 32)
    @Column(name = "specialty_2")
    private String specialty2;

    @Size(max = 32)
    @Column(name = "specialty_3")
    private String specialty3;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @NotNull
    private Attribute attribute;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @NotNull
    private Character character;


}
