import React, { useState } from 'react';

function ResultItem({ item, rank }) {
  const [showExplanation, setShowExplanation] = useState(false);
  const [expanded, setExpanded] = useState(false);

  const show = item.show;
  const score = item.score;
  const scoreExplanation = item.explanation;
  const highlightedTitle = item.highlightFields?.title?.[0] || show.title;

  return (
    <div style={{
      position: 'relative',
      border: '1px solid #474646ff',
      borderRadius: 8,
      padding: 12,
      marginBottom: 12,
      boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
      background: '#292929',
      textAlign: 'left',
      fontSize: '11px',
      minHeight: '180px',
    }}>
      {/* Rank badge */}
      <div style={{
        position: 'absolute',
        top: 10,
        left: 12,
        borderRadius: '12px',
        padding: '2px 8px',
        color: '#61dafb',
        fontWeight: 'bold',
        fontSize: '12px',
        zIndex: 2,
      }}>
        #{rank}
      </div>
      {/* Score badge */}
      <div style={{
        position: 'absolute',
        top: 10,
        right: 12,
        display: 'flex',
        alignItems: 'center',
        gap: '4px',
        background: '#222',
        borderRadius: '12px',
        padding: '2px 8px',
        color: '#fff',
        fontWeight: 'bold',
        fontSize: '12px',
        zIndex: 2,
      }}>
        Score: {score}
        <span
          style={{
            marginLeft: '4px',
            cursor: 'pointer',
            color: '#61dafb',
            fontWeight: 'bold',
            fontSize: '14px',
            borderRadius: '50%',
            border: '1px solid #61dafb',
            width: '18px',
            height: '18px',
            display: 'inline-flex',
            alignItems: 'center',
            justifyContent: 'center',
            background: '#292929',
          }}
          title="Show score explanation"
          onClick={() => setShowExplanation(true)}
        >
          ?
        </span>
      </div>

      <h2
        style={{ margin: '28px 0 8px 0' }}
        dangerouslySetInnerHTML={{ __html: highlightedTitle }}
      />
      <p><strong>Type:</strong> {show.type}</p>
      <p><strong>Categories:</strong> {show.categories?.join(', ')}</p>
      <p><strong>Description:</strong> {show.description}</p>

      {/* Show more / Show less toggle */}
      <button
        style={{
          position: 'absolute',
          right: 12,
          bottom: 12,
          margin: 0,
          padding: '2px 12px',
          fontSize: '11px',
          borderRadius: '6px',
          border: 'none',
          background: '#444',
          color: '#fff',
          cursor: 'pointer',
          zIndex: 1,
        }}
        onClick={() => setExpanded(!expanded)}
      >
        {expanded ? 'Show less' : 'Show more'}
      </button>

      {expanded && (
        <div style={{ marginTop: '8px' }}>
          <p><strong>Directors:</strong> {show.directors?.join(', ')}</p>
          <p><strong>Cast:</strong> {show.cast?.join(', ')}</p>
          <p><strong>Country:</strong> {show.country?.join(', ')}</p>
          <p><strong>Date Added:</strong> {show.dateAdded}</p>
          <p><strong>Release Year:</strong> {show.releaseYear}</p>
          <p><strong>Rating:</strong> {show.rating}</p>
          <p><strong>Duration:</strong> {show.duration}</p>
        </div>
      )}

      {/* Popup for explanation */}
      {showExplanation && (
        <div style={{
          position: 'fixed',
          top: 0, left: 0, right: 0, bottom: 0,
          background: 'rgba(0,0,0,0.5)',
          zIndex: 9999,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}>
          <div style={{
            background: '#fff',
            color: '#222',
            padding: '24px',
            borderRadius: '8px',
            maxWidth: '80vw',
            maxHeight: '80vh',
            overflow: 'auto',
            boxShadow: '0 4px 24px rgba(0,0,0,0.2)',
            position: 'relative',
          }}>
            <button
              style={{
                position: 'absolute',
                top: 8,
                right: 12,
                background: 'transparent',
                border: 'none',
                fontSize: '18px',
                cursor: 'pointer',
                color: '#888',
              }}
              onClick={() => setShowExplanation(false)}
              aria-label="Close"
            >
              &times;
            </button>
            <h3>Score Explanation</h3>
            <pre style={{
              background: '#f6f6f6',
              padding: '12px',
              borderRadius: '6px',
              fontSize: '12px',
              overflowX: 'auto',
            }}>
              {JSON.stringify(scoreExplanation, null, 2)}
            </pre>
          </div>
        </div>
      )}
    </div>
  );
}

export default ResultItem;