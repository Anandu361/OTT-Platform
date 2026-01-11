import React from 'react'
import landingpage from '../assets/landingpage.jpg'
import { useNavigate } from 'react-router-dom'

function LandingPage() {

  const containerStyle = {
    backgroundImage: `url(${landingpage})`,
    backgroundSize: 'cover', 
    backgroundRepeat: 'no-repeat', 
    height: '100vh', 
    width: '100vw',
    display: 'flex',
    flexDirection: 'column'
  }

  const headingStyle = {
    fontSize: '4rem',
    fontWeight: 'bold'
  }
  const navigate = useNavigate();

  return (
    <div className='container-fluid' style={containerStyle}>
      <div className='container-fluid justify-content-end text-end py-3'>
        <button className="btn btn-danger me-2" onClick={() => navigate('signup')}>Get Started</button>
        <button className="btn btn-light text-danger" onClick={() => navigate('login')}>Sign In</button>
      </div>
      <h1 className='text-white mt-5' style={headingStyle}>Welcome to the <br></br>Destination of Movies...</h1>
    </div>
  )
}

export default LandingPage
