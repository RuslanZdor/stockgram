package com.stocker.yahoo.data.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object for lambda step functions to pass data for market update
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketUpdate {
    private Market market;
    private long timestamp;
}
