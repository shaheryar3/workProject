# Cryptocurrency Price Tracker

A full-stack web application that displays real-time cryptocurrency prices using React frontend and Spring Boot backend.

## Architecture

- **Frontend**: React.js SPA running on port 3000
- **Backend**: Spring Boot REST API running on port 8080  
- **External API**: CoinGecko API for cryptocurrency data (with fallback dummy data)

## Features

- 📈 Real-time cryptocurrency price tracking
- 💰 Display of market cap and 24h price changes
- 🔄 Auto-refresh every 30 seconds
- 🎨 Responsive and modern UI design
- 🛡️ Error handling with fallback data
- 🔗 CORS-enabled backend for frontend integration

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- npm or yarn

## Setup Instructions

### Backend (Spring Boot)

1. Navigate to the backend directory:
   ```bash
   cd crypto-backend
   ```

2. Compile and run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

3. The backend API will be available at `http://localhost:8080`

### Frontend (React)

1. Navigate to the frontend directory:
   ```bash
   cd crypto-frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. The frontend will be available at `http://localhost:3000`

## API Endpoints

- `GET /api/crypto/health` - Health check endpoint
- `GET /api/crypto/top?limit={n}` - Get top N cryptocurrencies by market cap
- `GET /api/crypto/{id}` - Get specific cryptocurrency by ID

## Project Structure

```
├── crypto-backend/                 # Spring Boot backend
│   ├── src/main/java/com/crypto/backend/
│   │   ├── CryptoBackendApplication.java
│   │   ├── controller/CryptoController.java
│   │   ├── service/CryptoService.java
│   │   └── model/CryptoCurrency.java
│   └── pom.xml
├── crypto-frontend/                # React frontend
│   ├── src/
│   │   ├── App.js
│   │   ├── index.js
│   │   └── index.css
│   ├── public/index.html
│   └── package.json
└── README.md
```

## Technologies Used

### Backend
- Spring Boot 3.2.0
- Spring WebFlux (for reactive HTTP client)
- Maven
- Java 17

### Frontend  
- React 18
- Axios (for HTTP requests)
- CSS3 with Grid and Flexbox
- Modern ES6+ JavaScript

## Data Flow

1. React frontend makes HTTP requests to Spring Boot backend
2. Spring Boot backend calls CoinGecko API for cryptocurrency data
3. If external API fails, fallback dummy data is returned
4. Data is formatted and displayed in responsive card layout
5. Auto-refresh keeps data current

## Error Handling

- Network timeouts with 5-second limit
- Fallback dummy data when external API is unavailable
- Graceful degradation with "N/A" values for missing data
- CORS configuration for cross-origin requests

## Future Enhancements

- Real-time WebSocket updates
- Historical price charts
- Portfolio tracking
- Price alerts
- Additional cryptocurrency exchanges
- User authentication
- Favorites and watchlists