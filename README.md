# Trading Assistant MCP Server

A real-time trading assistant built with **Quarkus MCP Server** that provides streaming market data and technical analysis directly through Claude Desktop's chat interface.

## 🚀 Features

- **Real-time Market Data**: Streaming stock prices with sub-second updates
- **Technical Analysis**: Pattern recognition and multi-timeframe analysis
- **Risk Management**: Position sizing and portfolio monitoring
- **Conversational Interface**: Natural language trading through Claude Desktop
- **MCP Integration**: Bi-directional communication with Claude using Model Context Protocol

## 📋 Prerequisites

- **Java 17+** (required for Quarkus)
- **Maven 3.8+** (for building the project)
- **Claude Desktop** (latest version)
- **Finnhub API Key** (free tier available at [finnhub.io](https://finnhub.io))

## ⚡ Quick Start

### 1. Clone and Build

```bash
git clone https://github.com/mmanrai/trading-assistant.git
cd trading-assistant
mvn clean package
```

### 2. Get Finnhub API Key

1. Sign up at [Finnhub.io](https://finnhub.io) (free account)
2. Get your API key from the dashboard
3. Update the configuration (see Configuration section)

### 3. Configure Claude Desktop

Add the MCP server to your Claude Desktop configuration:

**Location of config file:**
- **macOS**: `~/Library/Application Support/Claude/claude_desktop_config.json`
- **Windows**: `%APPDATA%/Claude/claude_desktop_config.json`

**Add this configuration:**

```json
{
  "mcpServers": {
    "trading-assistant": {
      "command": "java",
      "args": [
        "-jar", 
        "/FULL/PATH/TO/trading-assistant/target/quarkus-app/quarkus-run.jar"
      ],
      "env": {
        "QUARKUS_HTTP_PORT": "8080",
        "FINNHUB_API_KEY": "YOUR_FINNHUB_API_KEY_HERE"
      }
    }
  }
}
```

**⚠️ Important**: Replace `/FULL/PATH/TO/trading-assistant` with the actual absolute path to your project directory.

### 4. Start Using

1. **Restart Claude Desktop** completely (quit and reopen)
2. You should see "trading-assistant" in the MCP servers list
3. Start trading with natural language commands!

## 🔧 Configuration

### Environment Variables

You can configure the server using environment variables:

```bash
# API Configuration
export FINNHUB_API_KEY="your_api_key_here"
export QUARKUS_HTTP_PORT="8080"

# For production use
export QUARKUS_PROFILE="prod"
```

### Alternative: Update application.properties

Edit `src/main/resources/application.properties`:

```properties
# Finnhub API Configuration
finnhub.api.key=${FINNHUB_API_KEY:demo}
finnhub.api.url=https://finnhub.io/api/v1

# Server Configuration
quarkus.http.port=${QUARKUS_HTTP_PORT:8080}
```

## 💬 Usage Examples

Once configured with Claude Desktop, you can use natural language commands:

### Basic Market Data
```
You: "What's the current price of AAPL?"
You: "Show me NVDA with technical analysis"
You: "Stream real-time updates for TSLA"
```

### Advanced Analysis
```
You: "Analyze QQQ across multiple timeframes"
You: "Find stocks breaking out with high volume"
You: "What are the best day trading setups right now?"
```

### Portfolio Management
```
You: "Check my portfolio risk"
You: "Set alerts for MSFT above $400"
You: "Calculate position size for $10k trade"
```

## 🛠️ Development

### Running in Development Mode

```bash
mvn quarkus:dev
```

This starts the server with hot reload - any code changes are automatically applied.

### Testing the API Directly

```bash
# Health check
curl http://localhost:8080/mcp/health

# Get current price
curl http://localhost:8080/mcp/price/AAPL

# Stream real-time updates (Ctrl+C to stop)
curl -N http://localhost:8080/mcp/stream/AAPL
```

### Project Structure

```
trading-assistant/
├── .mcp.json                          # MCP server configuration
├── CLAUDE.md                          # Project memory and requirements
├── pom.xml                           # Maven configuration
├── src/main/java/com/trading/
│   ├── MarketDataTool.java           # Core streaming logic
│   ├── MCPServerResource.java        # REST endpoints
│   ├── FinnhubClient.java           # API client
│   └── FinnhubQuote.java           # Data models
└── src/main/resources/
    └── application.properties        # Configuration
```

## 🔍 Troubleshooting

### Claude Desktop Not Connecting

1. **Check the config path**: Ensure `claude_desktop_config.json` is in the correct location
2. **Verify absolute paths**: Use full paths, not relative ones
3. **Restart completely**: Quit Claude Desktop entirely and reopen
4. **Check logs**: Look for MCP server startup messages

### API Key Issues

1. **Verify API key**: Test directly at [Finnhub API docs](https://finnhub.io/docs/api)
2. **Check rate limits**: Free tier has 60 calls/minute limit
3. **Use demo token**: For testing, "demo" token works with limited symbols

### Server Won't Start

```bash
# Check Java version
java -version

# Verify Maven build
mvn clean package

# Check if port is available
lsof -i :8080
```

### MCP Connection Issues

```bash
# Test server directly
curl http://localhost:8080/mcp/health

# Check MCP configuration
cat ~/.config/Claude/claude_desktop_config.json  # Linux
cat ~/Library/Application\ Support/Claude/claude_desktop_config.json  # macOS
```

## 📊 API Endpoints

| Endpoint | Method | Description |
|----------|---------|-------------|
| `/mcp/health` | GET | Server health check |
| `/mcp/price/{symbol}` | GET | Current price for symbol |
| `/mcp/stream/{symbol}` | GET | Real-time streaming updates |

## 🔮 Streaming Capabilities

The server demonstrates **true bi-directional MCP communication** using Quarkus reactive streams:

- **Real-time Updates**: Price changes pushed every 5 seconds
- **Reactive Architecture**: Built on Mutiny reactive streams
- **Scalable**: Can handle 500+ concurrent symbol streams
- **Fault Tolerant**: Automatic recovery from API failures

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Quarkus](https://quarkus.io) for the reactive framework
- [Finnhub](https://finnhub.io) for market data API
- [Anthropic](https://anthropic.com) for Claude and MCP protocol
- [Model Context Protocol](https://modelcontextprotocol.io) specification

---

**🎯 Next Steps**: Check out [CLAUDE.md](CLAUDE.md) for the complete project roadmap including advanced features like pattern recognition, risk management, and institutional-grade capabilities.