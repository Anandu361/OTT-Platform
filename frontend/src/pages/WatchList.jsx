import React from 'react'
import Navbar from '../components/Navbar'

function WatchList() {
  return (
    <div>
      <Navbar />
      <div className="container text-center mt-5">
        <h2>Your Watch List is currently empty.</h2>
        <p className="mt-3">Browse our collection and add movies to your watch list!</p>
      </div>
    </div>
  )
}

export default WatchList
