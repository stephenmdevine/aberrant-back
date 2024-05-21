package org.devine.aberrant.megaAttribute;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.devine.aberrant.attribute.AttributeSet;
import org.devine.aberrant.attribute.Quality;
import org.devine.aberrant.character.Character;

import java.util.List;

@Entity
@Data
public class MegaAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    Name should take the form of "MegaAttribute" with no spaces
    @NotBlank
    @Size(max = 32)
    @Column(name = "name")
    private String name;

    @Min(value = 0)
    @Max(value = 10)
    @Column(name = "value")
    private int value;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @OneToMany(mappedBy = "megaAttribute", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Enhancement> enhancements;

}
