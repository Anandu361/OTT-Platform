import React from 'react'
import { Route, Routes } from 'react-router-dom'
import LandingPage from '../pages/LandingPage.jsx'
import Login from '../pages/Login.jsx'
import Signup from '../pages/Signup.jsx'
import HomePage from '../pages/HomePage.jsx'

function routes() {
  return (
    <Routes>
      <Route path='/' element={<LandingPage />} />
      <Route path='/login' element={<Login />} />
      <Route path='/signup' element={<Signup />} />
      <Route path='/home' element={<HomePage />} />
    </Routes>
  )
}

export default routes
