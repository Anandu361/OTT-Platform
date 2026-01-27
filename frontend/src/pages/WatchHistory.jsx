import React from 'react'
import Navbar from '../components/Navbar'

function WatchHistory() {
  return (
    <div>
      <Navbar />
      <div className="container text-center mt-5">
        <h2>Your Watch History is currently empty.</h2>
        <p className="mt-3">Browse our collection and add movies to your watch history!</p>
      </div>
    </div>
  )
}

export default WatchHistory
