package com.trading;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinnhubQuote {
    
    @JsonProperty("c")
    public double c; // Current price
    
    @JsonProperty("h")
    public double h; // High price of the day
    
    @JsonProperty("l")
    public double l; // Low price of the day
    
    @JsonProperty("o")
    public double o; // Open price of the day
    
    @JsonProperty("pc")
    public double pc; // Previous close price
    
    @JsonProperty("t")
    public long t; // Timestamp
}