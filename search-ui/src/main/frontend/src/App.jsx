import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

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
    }
  };
  return (
    <div style={{ padding: '2rem', maxWidth: 600, margin: 'auto' }}>
      <h1>Search App</h1>
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
        {results && (
          <pre>{JSON.stringify(results, null, 2)}</pre>
        )}
      </div>
    </div>
  );
}

export default App
