package org.devine.aberrant.request;

import lombok.Data;

import java.util.Map;

@Data
public class AllocateBackgroundPointsRequest {

    private Map<String, Integer> backgroundValues;

}
