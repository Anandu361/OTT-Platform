import React, { useState, useEffect, use } from 'react'
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

function WatchMovie() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [movie, setMovie] = useState(null);
  useEffect(() => {
    axios.get(`http://localhost:8080/api/movies/${id}`)
      .then(response => {
        setMovie(response.data);
      })
      .catch(error => {
        console.error('Error fetching movie details:', error);
      });
  }
  , [id]);
  if (!movie) {
    return <div>Loading...</div>;
  }
  return (
    <div>
      <div className="container mt-4">
      <button
        className="btn btn-link text-danger mb-3"
        onClick={() => navigate(-1)}
      >
        ← Back
      </button>

      <h2 className="mb-3">{movie.movie_name}</h2>

      <video
        src={`http://localhost:8080${movie.movie}`}
        controls
        autoPlay
        className="w-100 rounded shadow"
        style={{ maxHeight: "75vh", backgroundColor: "black" }}
      />

      <p className="mt-3">{movie.description}</p>
    </div>
  </div>
  )
}

export default WatchMovie
