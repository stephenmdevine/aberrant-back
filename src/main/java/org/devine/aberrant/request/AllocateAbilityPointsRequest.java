package org.devine.aberrant.request;

import lombok.Data;

import java.util.Map;

@Data
public class AllocateAbilityPointsRequest {

    private Map<String, Integer> abilityValues;

}
