import React from 'react'
import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Login() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  function loginUser(e) {
    e.preventDefault();
    const user = {
      fullname : username,
      email : email,
      password : password
    }
    console.log(user);
    axios.post('http://localhost:8080/api/login', user)
    .then(response => {
      const token = response.data.token; // backend returns { token: "..." }

      localStorage.setItem("token", token); // ✅ THIS IS IMPORTANT

      console.log("Token saved:", token);
      navigate('/home');
    })

    .catch(error => {
      console.error('There was an error logging in the user!', error);
    });
  }
  return (
    <div className='container'>
      <div className='w-50 mx-auto mt-5 border p-4 rounded-3 shadow'>
        <form action="">
        <h2 className='text-center mb-3'>Login</h2>
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
        <div className='text-center mb-3'>
          <button type="submit" className='btn btn-danger fw-bold' onClick={loginUser}>Login</button>
        </div>
      </form>
      <p className='text-center'>New user? <a href="/signup" className='link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'>Register here</a></p>
    </div>
  </div>
  )
}

export default Login
