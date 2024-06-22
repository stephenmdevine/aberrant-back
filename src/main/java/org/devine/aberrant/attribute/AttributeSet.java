/*package org.devine.aberrant.attribute;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.devine.aberrant.character.Character;

import java.util.List;

@Entity
@Data
public class AttributeSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "attributeSet")
    private List<Attribute> attributes;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

}
*/