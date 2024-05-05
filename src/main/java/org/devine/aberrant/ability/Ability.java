package org.devine.aberrant.ability;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.devine.aberrant.attribute.Attribute;
import org.devine.aberrant.character.Character;

import java.util.List;

@Entity
@Data
public class Ability {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 32)
    @Column(name = "name")
    private String name;

    @Min(value = 0)
    @Max(value = 5)
    @Column(name = "value")
    private int value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @NotNull
    private Character character;

    @OneToMany(mappedBy = "ability")
    private List<Specialty> specialties;

}
