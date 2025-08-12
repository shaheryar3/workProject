import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './index.css';

const API_BASE_URL = 'http://localhost:8080/api/crypto';

function App() {
  const [cryptos, setCryptos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchCryptos = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await axios.get(`${API_BASE_URL}/top?limit=12`);
      setCryptos(response.data);
    } catch (err) {
      setError('Failed to fetch cryptocurrency data. Make sure the backend server is running.');
      console.error('Error fetching cryptos:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCryptos();
    // Refresh data every 30 seconds
    const interval = setInterval(fetchCryptos, 30000);
    return () => clearInterval(interval);
  }, []);

  const formatPrice = (price) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD',
      minimumFractionDigits: 2,
      maximumFractionDigits: price > 1 ? 2 : 6
    }).format(price);
  };

  const formatMarketCap = (marketCap) => {
    if (marketCap >= 1e12) {
      return `$${(marketCap / 1e12).toFixed(2)}T`;
    } else if (marketCap >= 1e9) {
      return `$${(marketCap / 1e9).toFixed(2)}B`;
    } else if (marketCap >= 1e6) {
      return `$${(marketCap / 1e6).toFixed(2)}M`;
    }
    return `$${marketCap?.toLocaleString()}`;
  };

  const formatPercentageChange = (change) => {
    if (change === null || change === undefined) return 'N/A';
    const sign = change >= 0 ? '+' : '';
    return `${sign}${change.toFixed(2)}%`;
  };

  if (loading && cryptos.length === 0) {
    return (
      <div className="App">
        <div className="header">
          <h1>Crypto Price Tracker</h1>
          <p>Real-time cryptocurrency prices</p>
        </div>
        <div className="loading">Loading cryptocurrency data...</div>
      </div>
    );
  }

  return (
    <div className="App">
      <div className="header">
        <h1>Crypto Price Tracker</h1>
        <p>Real-time cryptocurrency prices powered by CoinGecko</p>
        <button 
          className="refresh-button" 
          onClick={fetchCryptos}
          disabled={loading}
        >
          {loading ? 'Refreshing...' : 'Refresh Prices'}
        </button>
      </div>

      {error && (
        <div className="error">
          {error}
        </div>
      )}

      <div className="crypto-grid">
        {cryptos.map((crypto) => (
          <div key={crypto.id} className="crypto-card">
            <div className="crypto-header">
              <div className="crypto-info">
                <img 
                  src={crypto.image} 
                  alt={crypto.name}
                  className="crypto-logo"
                />
                <div className="crypto-name">
                  <h3>{crypto.name}</h3>
                  <span>{crypto.symbol}</span>
                </div>
              </div>
              <div className="crypto-rank">
                #{crypto.marketCapRank}
              </div>
            </div>
            
            <div className="crypto-price">
              {formatPrice(crypto.currentPrice)}
            </div>
            
            <div className={`crypto-change ${crypto.priceChangePercentage24h >= 0 ? 'positive' : 'negative'}`}>
              {formatPercentageChange(crypto.priceChangePercentage24h)} (24h)
            </div>
            
            <div className="crypto-market-cap">
              Market Cap: {formatMarketCap(crypto.marketCap)}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;