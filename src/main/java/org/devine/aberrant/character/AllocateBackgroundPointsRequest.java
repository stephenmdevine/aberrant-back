package org.devine.aberrant.character;

import lombok.Data;

import java.util.Map;

@Data
public class AllocateBackgroundPointsRequest {

    private Map<String, Integer> backgroundValues;
}
