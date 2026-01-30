import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'

function ChangePassword() {
  const navigate = useNavigate()

  const [currentPassword, setCurrentPassword] = useState('')
  const [newPassword, setNewPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')

  const token = localStorage.getItem("token")

  function handleSubmit(e) {
    e.preventDefault()

    if (newPassword !== confirmPassword) {
      alert("New passwords do not match")
      return
    }

    axios.post(
      "http://localhost:8080/api/update-password",
      {
        oldPassword: currentPassword,
        newPassword: newPassword
      },
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )
    .then(res => {
      alert(res.data)
      navigate('/profile')
    })
    .catch(err => {
      alert(err.response?.data || "Password update failed")
    })
  }

  return (
    <div className='container'>
      <div className="card text-center mb-3 w-25 mx-auto mt-5 p-3 shadow-lg">
        <div className="card-body">
          <h2 className='mb-3'>Change Password</h2>

          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label className='form-label'>Current Password</label>
              <input
                type="password"
                className='form-control'
                required
                value={currentPassword}
                onChange={e => setCurrentPassword(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label className='form-label'>New Password</label>
              <input
                type="password"
                className='form-control'
                required
                value={newPassword}
                onChange={e => setNewPassword(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label className='form-label'>Confirm New Password</label>
              <input
                type="password"
                className='form-control'
                required
                value={confirmPassword}
                onChange={e => setConfirmPassword(e.target.value)}
              />
            </div>

            <button type="submit" className='btn btn-danger fw-bold'>
              Update Password
            </button>
          </form>
        </div>
      </div>
    </div>
  )
}

export default ChangePassword
