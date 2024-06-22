/*package org.devine.aberrant.attribute;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Quality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}
*/