import React from 'react'

function Login() {
  return (
    <div className='container'>
      <div className='w-50 mx-auto mt-5 border p-4 rounded-3 shadow'>
        <form action="">
        <h2 className='text-center mb-3'>Login</h2>
        <div className="mb-3">
          <label className='form-label' htmlFor="username">Username:</label>
          <input type="text" id="username" name="username" className='form-control'required/>
        </div>
        <div className="mb-3">
          <label className='form-label' htmlFor="email">Email:</label>
          <input type="email" id="email" name="email" className='form-control'required/>
        </div>
        <div className="mb-3">
          <label className='form-label' htmlFor="password">Password:</label>
          <input type="password" id="password" name="password" className='form-control' required/>
        </div>
        <div className='text-center mb-3'>
          <button type="submit" className='btn btn-danger fw-bold'>Login</button>
        </div>
      </form>
      <p className='text-center'>New user? <a href="/signup" className='link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'>Register here</a></p>
    </div>
  </div>
  )
}

export default Login
