import { useState } from "react";
import { Link } from "react-router-dom";
import "./../styles/register.css";

function Register() {

    const [name,setName]=useState("");

    const [email,setEmail]=useState("");

    const [password,setPassword]=useState("");

    const register=(e)=>{

        e.preventDefault();

        console.log(name,email,password);

    }

    return(

<div className="register-container">

<div className="glass-card">

<h1>Create Account</h1>

<p>Enterprise Knowledge Assistant</p>

<form onSubmit={register}>

<input
type="text"
placeholder="Full Name"
value={name}
onChange={(e)=>setName(e.target.value)}
/>

<input
type="email"
placeholder="Email"
value={email}
onChange={(e)=>setEmail(e.target.value)}
/>

<input
type="password"
placeholder="Password"
value={password}
onChange={(e)=>setPassword(e.target.value)}
/>

<button>

Register

</button>

</form>

<p className="login-link">

Already have an account?

<Link to="/"> Login</Link>

</p>

</div>

</div>

    );

}

export default Register;