package com.trading;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestStreamElementType;

@Path("/mcp")
@Produces(MediaType.APPLICATION_JSON)
public class MCPServerResource {

    @Inject
    MarketDataTool marketDataTool;

    /**
     * MCP Tool: Get current stock price
     */
    @GET
    @Path("/price/{symbol}")
    public Uni<String> getCurrentPrice(@PathParam("symbol") String symbol) {
        return marketDataTool.getCurrentPrice(symbol);
    }

    /**
     * MCP Tool: Stream real-time price updates
     * This is the key feature - streaming updates to Claude Desktop
     */
    @GET
    @Path("/stream/{symbol}")
    @Produces(MediaType.TEXT_PLAIN)
    @RestStreamElementType(MediaType.TEXT_PLAIN)
    public Multi<String> streamPriceUpdates(@PathParam("symbol") String symbol) {
        return marketDataTool.streamPriceUpdates(symbol)
            .onItem().transform(update -> update + "\n---\n"); // Add separator for readability
    }

    /**
     * Health check for MCP server
     */
    @GET
    @Path("/health")
    public String health() {
        return "🚀 Market Data MCP Server is running!";
    }
}