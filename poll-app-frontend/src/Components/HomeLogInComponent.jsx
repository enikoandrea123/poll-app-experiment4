import { useState } from "react";
import "./HomeLogIn.css";

export default function HomeLogInComponent({ onLoginSuccess, onEnterPublicVote }) {
    const [users, setUsers] = useState([]);
    const [form, setForm] = useState({ username: "", email: "", password: "" });
    const [mode, setMode] = useState("login");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    const handleRegister = (e) => {
        e.preventDefault();
        if (users.some((u) => u.username === form.username)) {
            alert("Username already exists!");
            return;
        }
        const newUser = { ...form, id: users.length + 1 };
        setUsers([...users, newUser]);
        setForm({ username: "", email: "", password: "" });
        onLoginSuccess(newUser);
    };

    const handleLogin = (e) => {
        e.preventDefault();
        const found = users.find(
            (u) => u.username === form.username && u.password === form.password
        );
        if (found) {
            setForm({ username: "", email: "", password: "" });
            onLoginSuccess(found);
        } else {
            alert("Invalid credentials");
        }
    };

    return (
        <div className="home-container">
            <h1 className="title">Welcome to Poll App</h1>

            <form
                className="auth-form"
                onSubmit={mode === "login" ? handleLogin : handleRegister}
            >
                <input
                    type="text"
                    name="username"
                    value={form.username}
                    onChange={handleChange}
                    placeholder="Username"
                    required
                    className="auth-input"
                />
                {mode === "register" && (
                    <input
                        type="email"
                        name="email"
                        value={form.email}
                        onChange={handleChange}
                        placeholder="Email"
                        required
                        className="auth-input"
                    />
                )}
                <input
                    type="password"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="Password"
                    required
                    className="auth-input"
                />

                <button type="submit" className="btn btn-primary">
                    {mode === "login" ? "Login" : "Register"}
                </button>
            </form>

            <p className="toggle">
                {mode === "login" ? (
                    <>
                        Don’t have an account?{" "}
                        <button className="link" onClick={() => setMode("register")}>
                            Register
                        </button>
                    </>
                ) : (
                    <>
                        Already registered?{" "}
                        <button className="link" onClick={() => setMode("login")}>
                            Login
                        </button>
                    </>
                )}
            </p>

            <hr className="divider" />

            <button className="btn btn-secondary" onClick={onEnterPublicVote}>
                Enter Public Polls
            </button>
        </div>
    );
}