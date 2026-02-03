import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import Navbar from '../components/Navbar'
import Cards from '../components/Cards'
import Pagination from '../components/Pagination'
import axios from 'axios'

function MovieList() {
  const [movies, setMovies] = useState([])
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)

  const location = useLocation()
  const searchQuery = new URLSearchParams(location.search).get("search")

  useEffect(() => {
    const url = searchQuery
      ? `http://localhost:8080/api/movies/search?query=${searchQuery}`
      : `http://localhost:8080/api/movies?page=${page}&size=8`

    axios.get(url)
      .then(res => {
        if (searchQuery) {
          // search = no pagination
          setMovies(res.data)
          setTotalPages(0)
        } else {
          // normal list = paginated
          setMovies(res.data.content)
          setTotalPages(res.data.totalPages)
        }
      })
      .catch(err => console.error("Error fetching movies:", err))
  }, [searchQuery, page])

  return (
    <div>
      <Navbar />

      <div className="container mt-5">
        <h2 className="mb-4">All Movies</h2>

        {movies.length === 0 ? (
          <p>No movies available.</p>
        ) : (
          <>
            <div className="row g-4">
              {movies.map(movie => (
                <div
                  className="col-lg-3 col-md-4 col-sm-6"
                  key={movie.movieId}
                >
                  <Cards
                    id={movie.movieId}
                    image={`http://localhost:8080${movie.poster}`}
                    title={movie.movieName}
                    description={movie.description}
                  />
                </div>
              ))}
            </div>

            {/* Pagination only when not searching */}
            {!searchQuery && totalPages > 1 && (
              <Pagination
                currentPage={page}
                totalPages={totalPages}
                onPageChange={setPage}
              />
            )}
          </>
        )}
      </div>
    </div>
  )
}

export default MovieList
