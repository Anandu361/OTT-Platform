import React, { useEffect, useState } from 'react'
import Navbar from '../components/Navbar'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'


function Profile() {
  const [user, setUser] = useState(null);
  const token = localStorage.getItem('token');
  const navigate = useNavigate();
  useEffect(() => {
    axios.get("http://localhost:8080/api/user/me", {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    .then(res => setUser(res.data))
    .catch(err => console.error(err))
  }, [token])
  if (!user) {
    return <p className="text-center mt-5">Loading profile...</p>
  }

  return (
    <div>
      <Navbar></Navbar>
      <div className="card text-center mb-3 w-25 mx-auto mt-5 shadow-lg">
        <img src="https://media.istockphoto.com/id/2151669184/vector/vector-flat-illustration-in-grayscale-avatar-user-profile-person-icon-gender-neutral.jpg?s=612x612&w=0&k=20&c=UEa7oHoOL30ynvmJzSCIPrwwopJdfqzBs0q69ezQoM8=" className="card-img-top rounded-circle" alt="user"></img>
        <div className="card-body">
          <h5 className="card-title">{user.fullname}</h5>
          <p className="card-text">Email: {user.email}</p>
          <button className="btn btn-danger text-white fw-bold" onClick={() => navigate('/change-password')}>Change password</button>
        </div>
      </div>
    </div>
  )
}

export default Profile
