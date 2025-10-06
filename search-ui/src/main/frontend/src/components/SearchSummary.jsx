import React from 'react';

function SearchSummary({ page, results, query }) {
  if (!results || results.totalItems === 0) return null;

  const start = page * results.size + 1;
  const end = Math.min((page + 1) * results.size, results.totalItems);

  return (
    <div style={{ marginTop: '1rem', fontSize: '1rem', fontWeight: 'bold' }}>
      {`${start}-${end} of ${results.totalItems} results for `}
      <span style={{ color: 'yellow' }}>"{query}"</span>
    </div>
  );
}

export default SearchSummary;