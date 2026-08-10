import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./../styles/login.css";

function Login() {

    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const login = (e) => {

        e.preventDefault();

        console.log(email, password);

        navigate("/chat");
    };

    return (
        <div className="login-container">

            <form
                className="glass-card"
                onSubmit={login}
            >

                <h1>
                    Welcome Back
                </h1>

                <p>
                    Login to your Enterprise RAG Assistant
                </p>

                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) =>
                        setEmail(e.target.value)
                    }
                    required
                />

                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) =>
                        setPassword(e.target.value)
                    }
                    required
                />

                <button type="submit">
                    Login
                </button>

                <p className="register-text">
                    Don't have an account?{" "}

                    <Link to="/register">
                        Register
                    </Link>
                </p>

            </form>

        </div>
    );
}

export default Login;