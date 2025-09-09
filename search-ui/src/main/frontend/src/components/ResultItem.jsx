import React from 'react';

function ResultItem({ show }) {
  return (
    <div style={{
      border: '1px solid #474646ff',
      borderRadius: 8,
      padding: 12,
      marginBottom: 12,
      boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
      background: '#292929',
      textAlign: 'left',
      fontSize: '11px',
    }}>
      <h2 style={{ margin: '0 0 8px 0' }}>{show.title}</h2>
      <p><strong>Type:</strong> {show.type}</p>
      <p><strong>Directors:</strong> {show.directors?.join(', ')}</p>
      <p><strong>Cast:</strong> {show.cast?.join(', ')}</p>
      <p><strong>Country:</strong> {show.country?.join(', ')}</p>
      <p><strong>Date Added:</strong> {show.dateAdded}</p>
      <p><strong>Release Year:</strong> {show.releaseYear}</p>
      <p><strong>Rating:</strong> {show.rating}</p>
      <p><strong>Duration:</strong> {show.duration}</p>
      <p><strong>Categories:</strong> {show.categories?.join(', ')}</p>
      <p><strong>Description:</strong> {show.description}</p>
    </div>
  );
}

export default ResultItem;