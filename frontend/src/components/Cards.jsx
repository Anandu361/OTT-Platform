import React from 'react'
import { useNavigate } from 'react-router-dom';

function Cards({ image, title, description }) {
  const navigate = useNavigate();
  return (
    <div className="card h-100 text-bg-dark">
      <img src={image} className="card-img-top" alt={title} />
      <div className="card-body card-img-overlay">
        <h5 className="card-title">{title}</h5>
        <p className="card-text">{description}</p>
        <button className="btn btn-danger stretched-link" onClick={() => navigate(`/movie/${title}`)}>View</button>
      </div>
    </div>
  );
}

export default Cards
