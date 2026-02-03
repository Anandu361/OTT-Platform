import Navbar from '../components/Navbar'
import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';

function MovieDetails() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [movie, setMovie] = useState(null);
  const token = localStorage.getItem('token');
  useEffect(() => {
    axios.get(`http://localhost:8080/api/movies/${id}`)
      .then(response => {
        setMovie(response.data);
      })
      .catch(error => {
        console.error('Error fetching movie details:', error);
      });
  }, [id]);
    function addToWatchlist() {
      if (!token) {
        alert("Please login first");
        return;
      }

      axios.post(
        `http://localhost:8080/api/watchlist/add?movieId=${movie.movieId}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      )
      .then(res => {
        alert(res.data);
      })
      .catch(err => {
        alert(err.response?.data || "Something went wrong");
      });
    }

    function watchNow() {
      if (!token) {
        alert("Please login first");
        return;
      }

      axios.get(
        `http://localhost:8080/api/watch/now?movieId=${movie.movieId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      )
      .then(() => {
        // navigate to video page
        navigate(`/watch/${movie.movieId}`);
      })
      .catch(err => {
        alert(err.response?.data || "Unable to play movie");
      });
    }

  if (!movie) {
    return <div>Loading...</div>;
  }
  return (
    <div>
      <Navbar />
      <button className='m-3 btn btn-link link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover' onClick={() => navigate(-1)}><i className="bi bi-arrow-left"></i> Back</button>
      <div className="container row text-center border rounded-3 p-4 shadow-lg mx-auto">
        <div className="m-4 text-start col ">
          <h1 >{movie.movie_name}</h1>
          <p >{movie.description}</p>
          
        </div>
        <div className="mt-4 col">
          <img
            src={`http://localhost:8080${movie.poster}`}
            alt={movie.movie_name}
            className="img-fluid mb-4"
          />
        </div>
        <div>
          <button className="btn btn-danger" onClick={watchNow}>Watch Now</button>
          <button className="btn btn-outline-danger ms-2" onClick={addToWatchlist}>Add to Watchlist</button>
        </div>
      </div>
    </div>
  )
}

export default MovieDetails
