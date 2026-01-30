import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import Navbar from '../components/Navbar'
import Cards from '../components/Cards'
import axios from 'axios'

function MovieList() {
  const [movies, setMovies] = useState([])
  const location = useLocation()
  const searchQuery = new URLSearchParams(location.search).get("search")

  useEffect(() => {
    const url = searchQuery
      ? `http://localhost:8080/api/movies/search?query=${searchQuery}`
      : `http://localhost:8080/api/movies`

    axios.get(url)
      .then(res => setMovies(res.data))
      .catch(err => console.error("Error fetching movies:", err))
  }, [searchQuery])


  return (
    <div>
      <Navbar />

      <div className="container mt-5">
        <h2 className="mb-4">All Movies</h2>

        {movies.length === 0 ? (
          <p>No movies available.</p>
        ) : (
          <div className="row g-4">
            {movies.map(movie => (
              <div
                className="col-lg-3 col-md-4 col-sm-6"
                key={movie.movie_id}
              >
                <Cards
                  id={movie.movie_id}
                  image={`http://localhost:8080${movie.poster}`}
                  title={movie.movie_name}
                  description={movie.description}
                />
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default MovieList
