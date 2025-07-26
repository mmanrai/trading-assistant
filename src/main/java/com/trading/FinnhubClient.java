package com.trading;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://finnhub.io/api/v1")
public interface FinnhubClient {

    @GET
    @Path("/quote")
    Uni<FinnhubQuote> getQuote(
        @QueryParam("symbol") String symbol,
        @QueryParam("token") String token
    );

    default Uni<FinnhubQuote> getQuote(String symbol) {
        // Using demo token - you can get free API key from finnhub.io
        return getQuote(symbol, "demo");
    }
}