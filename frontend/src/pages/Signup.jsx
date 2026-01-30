import React, { use } from 'react'
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';


function Signup() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');

  function registerUser(e) {
    e.preventDefault();
    const user = {
      fullname : username,
      email : email,
      password : password
    }
    console.log(user);
    if (user?.password === confirmPassword){
      console.log("Passwords match");
      axios.post('http://localhost:8080/api/register', user)
      .then(response => {
        console.log('User registered successfully:', response.data);
        navigate('/login');
      })
      .catch(error => {
        console.error('There was an error registering the user!', error);
        setError('Registration failed. Please try again.');
      });
    }}
  return (
    <div className='container'>
      <div className='w-50 mx-auto mt-5 border p-4 rounded-3 shadow'>
        <form action="">
        <h2 className='text-center mb-3'>Signup</h2>
        <div className="mb-3">
          <label className='form-label' htmlFor="username">Username:</label>
          <input type="text" id="username" name="username" className='form-control' required value={username} onChange={(e) => setUsername(e.target.value)}/>
        </div>
        <div className="mb-3">
          <label className='form-label' htmlFor="email">Email:</label>
          <input type="email" id="email" name="email" className='form-control'required value={email} onChange={(e) => setEmail(e.target.value)}/>
        </div>
        <div className="mb-3">
          <label className='form-label' htmlFor="password">Password:</label>
          <input type="password" id="password" name="password" className='form-control' required value={password} onChange={(e) => setPassword(e.target.value)}/>
        </div>
        <div className='mb-3'>
          <label className='form-label' htmlFor="confirmPassword">Confirm Password:</label>
          <input type="password" id="confirmPassword" name="confirmPassword" className='form-control' required value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)}/>
        </div>
        <div className='text-center mb-3'>
          <button type="submit" className='btn btn-danger fw-bold' onClick={registerUser}>Signup</button>
        </div>
      </form>
      <p className='text-center'>Already a user? <a href="/login" className='link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'>Login here</a></p>
    </div>
  </div>
  )
}

export default Signup
