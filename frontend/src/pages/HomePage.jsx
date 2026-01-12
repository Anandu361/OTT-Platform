import React from 'react'
import Cards from '../components/Cards'
import Navbar from '../components/Navbar'

function HomePage() {
  return (
    <div>
      <Navbar />
      <div className="position-relative">
        {/* Greeting overlay */}
        <div
          className="position-absolute top-0 start-0 text-white p-4"
          style={{ zIndex: 10 }}
        >
          <h1 className="fw-bold ">
            Hello User!
          </h1>
          <p className="mb-0">
            Glad to have you here
          </p>
        </div>


        {/* Carousel */}
        <div
          id="homeCarousel"
          className="carousel slide carousel-fade"
          data-bs-ride="carousel"
          data-bs-interval="1000"
        >
          <div className="carousel-inner">
            <div className="carousel-item active">
              <img
                src="https://images.unsplash.com/photo-1500530855697-b586d89ba3ee"
                className="d-block w-100"
                alt="Slide 1"
                style={{ height: "80vh", objectFit: "cover" }}
              />
              <div className="position-absolute top-0 start-0 w-100 h-100 fade-overlay"></div>
            </div>

            <div className="carousel-item">
              <img
                src="https://images.unsplash.com/photo-1496307042754-b4aa456c4a2d"
                className="d-block w-100"
                alt="Slide 2"
                style={{ height: "80vh", objectFit: "cover" }}
              />
              <div className="position-absolute top-0 start-0 w-100 h-100 fade-overlay"></div>
            </div>

            <div className="carousel-item">
              <img
                src="https://4kwallpapers.com/images/walls/thumbs_3t/24974.jpg"
                className="d-block w-100"
                alt="Slide 3"
                style={{ height: "80vh", objectFit: "cover" }}
              />
              <div className="position-absolute top-0 start-0 w-100 h-100 fade-overlay"></div>
            </div>
          </div>
        </div>
        <div className="container my-5">
          {/* Section header */}
          <div className="d-flex align-items-center justify-content-between mb-4">
            <h3 className="fw-bold">Top movies this week</h3>
            <span className="text-danger fw-semibold">
              View all →
            </span>
          </div>

          {/* Cards grid */}
          <div className="row g-4">
            <div className="col-lg-3 col-md-4 col-sm-6">
              <Cards
                image="https://images.unsplash.com/photo-1606112219348-204d7d8b94ee"
                title="Movie One"
                description="Action | Thriller"
              />
            </div>

            <div className="col-lg-3 col-md-4 col-sm-6">
              <Cards
                image="https://images.unsplash.com/photo-1524985069026-dd778a71c7b4"
                title="Movie Two"
                description="Drama | Romance"
              />
            </div>

            <div className="col-lg-3 col-md-4 col-sm-6">
              <Cards
                image="https://images.unsplash.com/photo-1517602302552-471fe67acf66"
                title="Movie Three"
                description="Sci-Fi | Adventure"
              />
            </div>

            <div className="col-lg-3 col-md-4 col-sm-6">
              <Cards
                image="https://images.unsplash.com/photo-1497032628192-86f99bcd76bc"
                title="Movie Four"
                description="Comedy | Family"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default HomePage
