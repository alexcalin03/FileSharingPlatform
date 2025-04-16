import React, { useState } from 'react';
import axios from 'axios';

const Auth = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [token, setToken] = useState('');
    const [isLogin, setIsLogin] = useState(true);
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const endpoint = isLogin ? '/api/login' : '/api/register';
        try{
            const response = await axios.post(endpoint, { username, password });
            if(isLogin)
            {
                localStorage.setItem('token', response.data.token);
                setToken(response.data.token);
                setMessage('Login successful');
            }else{
                setMessage('Registration successful. Please login');
                setIsLogin(true);
            }
        } catch (error) {
            if(error.response && error.response.data && error.response.data.error)
            {
                setError(error.response.data.error);
            }else{
                setError('An unknown error occurred');
            }
        }
    };


    const toggleForm = () => {
        setIsLogin(!isLogin);
        setError('');
        setMessage('');
    };

    return (
        <div style={{ maxWidth: "300px", margin: "auto" }}>
      <h2>{isLogin ? "Login" : "Register"}</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
            required
          />
        </div>
        <div>
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            style={{ width: "100%", marginBottom: "10px", padding: "8px" }}
            required
          />
        </div>
        <div>
          <button type="submit" style={{ width: "100%", padding: "8px" }}>
            {isLogin ? "Login" : "Register"}
          </button>
        </div>
      </form>
      {message && <p style={{ textAlign: "center" }}>{message}</p>}
      <button onClick={toggleForm} style={{ width: "100%", padding: "8px" }}>
        {isLogin ? "Need to register?" : "Already have an account? Login"}
      </button>
    </div>
    );

}

export default Auth;
    

    