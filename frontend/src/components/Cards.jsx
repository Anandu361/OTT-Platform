import React from 'react'
import { useNavigate } from 'react-router-dom';


function Cards({id, image, title, description }) {
  const navigate = useNavigate();
  console.log("Card ID:", id);
  return (
    <div className="card h-100 text-bg-dark">
      <img src={image} className="card-img-top" alt={title} />
      <div className="card-body card-img-overlay">
        
        <button className="btn btn-danger stretched-link" onClick={() => navigate(`/movie/${id}`)}>View</button>
      </div>
    </div>
  );
}

export default Cards
