import { useState } from 'react';
import ResultList from './components/ResultList';
import './App.css';

function App() {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState(null);
  const [page, setPage] = useState(0);

  const handleSearch = async (newPage = 0) => {
    try {
      const response = await fetch(
        `http://localhost:8080/shows/search/advanced?query=${encodeURIComponent(query)}&page=${newPage}&size=30`
      );
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      setResults(data);
      setPage(newPage);
    } catch (error) {
      console.error('Error fetching search results:', error);
      setResults(null);
    }
  };

  const handlePageChange = (newPage) => {
    handleSearch(newPage);
  };

  return (
    <div style={{ padding: '2rem 0', margin: '0 auto' }}>
      <h1>Netflix Show Search</h1>
      <input
        type="text"
        value={query}
        onChange={e => setQuery(e.target.value)}
        placeholder="Type your query..."
        style={{ width: '500px', padding: '0.5rem' }}
      />
      <button
        onClick={() => handleSearch(0)}
        style={{ padding: '0.5rem 1rem', marginLeft: '1rem' }}
      >
        Search
      </button>
      <div style={{ marginTop: '2rem' }}>
        <ResultList results={results} />
        {/* Pagination Controls */}
        {results && results.totalPages > 1 && (
          <div style={{ marginTop: '2rem', display: 'flex', justifyContent: 'center', gap: '8px' }}>
            <button
              onClick={() => handlePageChange(page - 1)}
              disabled={page === 0}
            >
              Previous
            </button>
            {(() => {
              const total = results.totalPages;
              const maxButtons = 5;
              let start = Math.max(0, page - Math.floor(maxButtons / 2));
              let end = start + maxButtons;
              if (end > total) {
                end = total;
                start = Math.max(0, end - maxButtons);
              }
              return Array.from({ length: end - start }, (_, idx) => {
                const pageNum = start + idx;
                return (
                  <button
                    key={pageNum}
                    onClick={() => handlePageChange(pageNum)}
                    style={{
                      fontWeight: pageNum === page ? 'bold' : 'normal',
                      textDecoration: pageNum === page ? 'underline' : 'none',
                    }}
                  >
                    {pageNum + 1}
                  </button>
                );
              });
            })()}
            <button
              onClick={() => handlePageChange(page + 1)}
              disabled={page === results.totalPages - 1}
            >
              Next
            </button>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
