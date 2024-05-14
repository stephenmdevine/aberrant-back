package org.devine.aberrant.attribute;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.devine.aberrant.character.Character;

import java.util.List;

@Entity
@Data
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 16)
    @Column(name = "name")
    private String name;

    @Min(value = 1)
    @Max(value = 10)
    @Column(name = "value")
    private int value;

    @Column(name = "nova_purchased")
    private int novaPurchased;

    @ManyToOne
    @JoinColumn(name = "attribute_set_id")
    private AttributeSet attributeSet;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @OneToMany(mappedBy = "attribute")
    private List<Quality> qualities;

}
