package org.devine.aberrant.attribute;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.devine.aberrant.character.Character;

@Entity
@Data
public class Attribute {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    @Size(max = 16)
    @Column(name = "name")
    private String name;

    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "value")
    private int value;

    @Size(max = 32)
    @Column(name = "quality")
    private String quality;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @NotNull
    private Character character;

}
