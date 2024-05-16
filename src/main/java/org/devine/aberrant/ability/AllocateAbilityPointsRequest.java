package org.devine.aberrant.ability;

import lombok.Data;

import java.util.Map;

@Data
public class AllocateAbilityPointsRequest {

    private Map<String, Integer> abilityValues;
}
