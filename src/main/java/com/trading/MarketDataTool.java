package com.trading;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class MarketDataTool {

    @Inject
    @org.eclipse.microprofile.rest.client.inject.RestClient
    FinnhubClient finnhubClient;

    /**
     * Stream real-time price updates for a symbol
     * This demonstrates MCP streaming capability using Quarkus Multi
     */
    public Multi<String> streamPriceUpdates(String symbol) {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(5))
            .onItem().transformToUni(tick -> getCurrentPrice(symbol))
            .concatenate()
            .onFailure().recoverWithItem("❌ Market data unavailable")
            .select().where(price -> price != null && !price.contains("❌"));
    }

    /**
     * Get current price for a symbol
     */
    public Uni<String> getCurrentPrice(String symbol) {
        return finnhubClient.getQuote(symbol)
            .map(quote -> formatPriceUpdate(symbol, quote))
            .onFailure().recoverWithItem("❌ Failed to fetch price for " + symbol);
    }

    private String formatPriceUpdate(String symbol, FinnhubQuote quote) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        double changePercent = ((quote.c - quote.pc) / quote.pc) * 100;
        String changeDirection = changePercent >= 0 ? "📈" : "📉";
        
        return String.format("📊 **%s** - %s\n" +
                           "💰 **$%.2f** (%+.2f%%)\n" +
                           "🔄 High: $%.2f | Low: $%.2f\n" +
                           "⏰ Updated: %s\n",
                           symbol, changeDirection,
                           quote.c, changePercent,
                           quote.h, quote.l,
                           timestamp);
    }
}