import React, { useEffect, useState } from 'react'
import Navbar from '../components/Navbar'
import Cards from '../components/Cards'
import axios from 'axios'

function WatchHistory() {
  const [history, setHistory] = useState([])
  const token = localStorage.getItem("token")

  useEffect(() => {
    axios.get("http://localhost:8080/api/watch/history", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(res => {
      setHistory(res.data)
    })
    .catch(err => {
      console.error("Error fetching watch history:", err)
    })
  }, [token])

  return (
    <div>
      <Navbar />

      <div className="container mt-5">
        <h2 className="mb-4">Watch History</h2>

        {history.length === 0 ? (
          <p>You haven’t watched any movies yet.</p>
        ) : (
          <div className="row g-4">
            {history.map(item => (
              <div
                className="col-lg-3 col-md-4 col-sm-6"
                key={item.id}
              >
                <Cards
                  id={item.movie.movie_id}
                  image={`http://localhost:8080${item.movie.poster}`}
                  title={item.movie.movie_name}
                  description={item.movie.description}
                />

                <p className="text-muted text-center mt-2">
                  Watched on{" "}
                  {new Date(item.watchedAt).toLocaleString()}
                </p>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default WatchHistory
