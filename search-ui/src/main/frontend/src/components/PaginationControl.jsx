import React from 'react';

function PaginationControl({ page, totalPages, onPageChange }) {
  if (totalPages <= 1) return null;

  const maxButtons = 5;
  let start = Math.max(0, page - Math.floor(maxButtons / 2));
  let end = start + maxButtons;
  if (end > totalPages) {
    end = totalPages;
    start = Math.max(0, end - maxButtons);
  }

  return (
    <div style={{ marginTop: '2rem', display: 'flex', justifyContent: 'center', gap: '8px' }}>
      <button
        onClick={() => onPageChange(page - 1)}
        disabled={page === 0}
      >
        Previous
      </button>
      {Array.from({ length: end - start }, (_, idx) => {
        const pageNum = start + idx;
        return (
          <button
            key={pageNum}
            onClick={() => onPageChange(pageNum)}
            style={{
              fontWeight: pageNum === page ? 'bold' : 'normal',
              textDecoration: pageNum === page ? 'underline' : 'none',
            }}
          >
            {pageNum + 1}
          </button>
        );
      })}
      <button
        onClick={() => onPageChange(page + 1)}
        disabled={page === totalPages - 1}
      >
        Next
      </button>
    </div>
  );
}

export default PaginationControl;