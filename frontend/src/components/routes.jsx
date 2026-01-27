import React from 'react'
import { Route, Routes } from 'react-router-dom'
import LandingPage from '../pages/LandingPage.jsx'
import Login from '../pages/Login.jsx'
import Signup from '../pages/Signup.jsx'
import HomePage from '../pages/HomePage.jsx'
import Profile from '../pages/Profile.jsx'
import ChangePassword from '../pages/ChangePassword.jsx'
import WatchList from '../pages/WatchList.jsx'
import WatchHistory from '../pages/WatchHistory.jsx'  
import MovieDetails from '../pages/MovieDetails.jsx'

function routes() {
  return (
    <Routes>
      <Route path='/' element={<LandingPage />} />
      <Route path='/login' element={<Login />} />
      <Route path='/signup' element={<Signup />} />
      <Route path='/home' element={<HomePage />} />
      <Route path='/profile' element={<Profile />} />
      <Route path='/change-password' element={<ChangePassword />} />
      <Route path='/watchlist' element={<WatchList />} />
      <Route path='/watchhistory' element={<WatchHistory />} />
      <Route path='/movie/:id' element={<MovieDetails />} />
    </Routes>
  )
}

export default routes
