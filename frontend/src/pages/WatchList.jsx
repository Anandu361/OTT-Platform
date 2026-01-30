import React, { useEffect, useState } from 'react'
import Navbar from '../components/Navbar'
import Cards from '../components/Cards'
import axios from 'axios'

function WatchList() {
  const [watchlist, setWatchlist] = useState([])
  const token = localStorage.getItem("token")

  // Fetch watchlist
  useEffect(() => {
    axios.get("http://localhost:8080/api/watchlist/my", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(res => {
      setWatchlist(res.data)
    })
    .catch(err => {
      console.error("Error fetching watchlist:", err)
    })
  }, [token])

  // Remove movie from watchlist
  const removeFromWatchlist = (movieId) => {
    axios.delete(
      `http://localhost:8080/api/watchlist/remove?movieId=${movieId}`,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )
    .then(() => {
      // Update UI instantly
      setWatchlist(prev =>
        prev.filter(wl => wl.movie.movie_id !== movieId)
      )
    })
    .catch(err => {
      console.error("Error removing from watchlist:", err)
    })
  }

  return (
    <div>
      <Navbar />

      <div className="container mt-5">
        <h2 className="mb-4">My Watchlist</h2>

        {watchlist.length === 0 ? (
          <p>Your watchlist is empty.</p>
        ) : (
          <div className="row g-4">
            {watchlist.map(wl => (
              <div className="col-lg-3 col-md-4 col-sm-6" key={wl.id}>
                
                {/* Reused Cards component */}
                <Cards
                  id={wl.movie.movie_id}
                  image={`http://localhost:8080${wl.movie.poster}`}
                  title={wl.movie.movie_name}
                  description={wl.movie.description}
                />

                {/* Remove button */}
                <button
                  className="btn btn-outline-danger w-100 mt-2"
                  onClick={() => removeFromWatchlist(wl.movie.movie_id)}
                >
                  Remove from Watchlist
                </button>

              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default WatchList
