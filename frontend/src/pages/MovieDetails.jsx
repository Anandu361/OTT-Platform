import React from 'react'
import Navbar from '../components/Navbar'
import { useNavigate } from 'react-router-dom';

function MovieDetails() {
  const navigate = useNavigate();
  return (
    <div>
      <Navbar />
      <button className='m-3 btn btn-link link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover' onClick={() => navigate(-1)}><i className="bi bi-arrow-left"></i> Back</button>
      <div className="container row text-center mt-5 border rounded-3 p-4 shadow-lg mx-auto">
        <div className="m-4 text-start col ">
          <h1 >Movie Name</h1>
          <p >This is a detailed description of the movie. It provides insights into the plot, characters, and other interesting aspects of the film.</p>
          <h3>Cast:</h3>
          <ul className='list-unstyled'>
            <li>Actor 1 as Character A</li>
            <li>Actor 2 as Character B</li>
            <li>Actor 3 as Character C</li>
          </ul> 
        </div>
        <div className="mt-4 col">
          <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQMXQPShZr-RvIoikjIn2G-SoJd93tXp0JiHg&s" alt="Movie Poster" className="img-fluid mb-4" />
        </div>
      </div>
    </div>
  )
}

export default MovieDetails
