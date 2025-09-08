import { useState } from 'react';
import ResultList from './components/ResultList';
import './App.css';

function App() {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState(null);

  const handleSearch = async () => {
    try {
      const response = await fetch(`http://localhost:8080/shows?title=${encodeURIComponent(query)}&page=0&size=30`);
      if (!response.ok) {
        throw new Error('Network response was not ok');
      } 
      const data = await response.json();
      setResults(data);
    } catch (error) {
      console.error('Error fetching search results:', error);
      setResults(null);
    }
  };

  return (
    <div style={{ padding: '2rem', maxWidth: 600, margin: 'auto' }}>
      <h1>Netflix Show Search</h1>
      <input
        type="text"
        value={query}
        onChange={e => setQuery(e.target.value)}
        placeholder="Type your query..."
        style={{ width: '70%', padding: '0.5rem' }}
      />
      <button onClick={handleSearch} style={{ padding: '0.5rem 1rem', marginLeft: '1rem' }}>
        Search
      </button>
      <div style={{ marginTop: '2rem' }}>
        <ResultList results={results} />
      </div>
    </div>
  );
}

export default App;
