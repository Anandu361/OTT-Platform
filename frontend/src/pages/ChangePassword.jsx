import React from 'react'
import { useNavigate } from 'react-router-dom'

function ChangePassword() {
  const navigate = useNavigate();
  return (
    <div className='container'>
      <div className="card text-center mb-3 w-25 mx-auto mt-5 p-3 shadow-lg">
        <div className="card-body">
          <h2 className='text-center mb-3'>Change Password</h2>
          <form action="">
            <div className="mb-3">
              <label className='form-label' htmlFor="currentPassword">Current Password:</label>
              <input type="password" id="currentPassword" name="currentPassword" className='form-control'required/>
            </div>
            <div className="mb-3">
              <label className='form-label' htmlFor="newPassword">New Password:</label>
              <input type="password" id="newPassword" name="newPassword" className='form-control'required/>
            </div>
            <div className="mb-3">
              <label className='form-label' htmlFor="confirmPassword">Confirm New Password:</label>
              <input type="password" id="confirmPassword" name="confirmPassword" className='form-control' required/>
            </div>
            <div className='text-center mb-3'>
              <button type="submit" className='btn btn-danger fw-bold' onClick={() => navigate('/profile')}>Update Password</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}

export default ChangePassword
