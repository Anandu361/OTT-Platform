import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();
  const [query, setQuery] = useState('')
  const [movies, setMovies] = useState([]);
  useEffect(() => {
    axios.get("http://localhost:8080/api/movies", {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    })
    .then(res => setMovies(res.data))
    .catch(err => console.error(err));
  }, []);

  const suggestions =
    query.trim() === ""
      ? []
      : movies
          .filter(m =>
            m.movieName.toLowerCase().includes(query.toLowerCase())
          )
          .slice(0, 5);

  function handleSearch(e) {
    e.preventDefault()
    if (query.trim() !== '') {
      navigate(`/movies?search=${query}`)
    }
  }
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4">
      {/* Website Name */}
      <button className="navbar-brand fw-bold btn btn-link text-decoration-none" onClick={() => navigate('/home')}>
        MovieZone
      </button>

      {/* Mobile Toggle */}
      <button
        className="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarContent"
      >
        <span className="navbar-toggler-icon"></span>
      </button>

      <div className="collapse navbar-collapse" id="navbarContent">
        {/* Left buttons */}
        <ul className="navbar-nav me-4 mb-2 mb-lg-0">
          <li className="nav-item">
            <button className="btn btn-outline-light me-2" onClick={() => navigate('/watchlist')}>
              Watchlist
            </button>
          </li>
          <li className="nav-item">
            <button className="btn btn-outline-light" onClick={() => navigate('/watchhistory')}> 
              Watch History
            </button>
          </li>
        </ul>

        {/* Search bar */}
        <form className="d-flex mx-auto w-50 position-relative">
          <input
            className="form-control me-2 focus-ring focus-ring-danger"
            type="search"
            placeholder="Search movies..."
            aria-label="Search"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
          />
          {suggestions.length > 0 && (
            <ul
              className="list-group position-absolute w-100 shadow"
              style={{
                top: "100%",
                zIndex: 1000,
                maxHeight: "250px",
                overflowY: "auto",
                borderRadius: "0 0 8px 8px"
              }}
            >
              {suggestions.map(movie => (
                <li
                  key={movie.movieId}
                  className="list-group-item list-group-item-action"
                  style={{ cursor: "pointer" }}
                  onClick={() => {
                    navigate(`/movie/${movie.movieId}`);
                    setQuery("");
                  }}
                >
                  {movie.movieName}
                </li>
              ))}
            </ul>
          )}


          <button className="btn btn-danger" type="submit" onClick={handleSearch}>
            Search
          </button>
        </form>

        {/* Profile Icon */}
        <button className="btn btn-link ms-auto p-0" onClick={() => navigate('/profile')} style={{ border: "none" }}>
          <img
            src="https://cdn-icons-png.flaticon.com/512/149/149071.png"
            alt="Profile"
            width="35"
            height="35"
            className="rounded-circle"
            style={{ cursor: "pointer" }}
          />
        </button>
      </div>
    </nav>
  );
}

export default Navbar;
