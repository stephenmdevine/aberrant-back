package org.devine.aberrant.request;

import lombok.Data;
import org.devine.aberrant.model.AttributeSet;

import java.util.Map;

@Data
public class AllocateAttributePointsRequest {

    private AttributeSet attributeSet; // Represents the set of attributes being allocated points to
    private Map<String, Integer> attributeValues; // Maps attribute names to their allocated values
    private Map<String, String> qualityDetails; // Maps attribute names to any associated quality details

}
