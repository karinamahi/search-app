import React from 'react';
import ResultItem from './ResultItem';

function ResultList({ results }) {
  if (!results || !results.content || results.content.length === 0) {
    return <p>No results found.</p>;
  }

  return (
    <div
      style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(5, 1fr)',
        gap: '16px',
      }}
    >
      {results.content.map(show => (
        <ResultItem key={show.id} show={show} />
      ))}
    </div>
  );
}

export default ResultList;