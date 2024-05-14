package org.devine.aberrant.background;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.devine.aberrant.character.Character;

@Entity
@Data
public class Background {

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

    @Column(name = "nova_purchased")
    private int novaPurchased;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "character_id")
    private Character character;

}
