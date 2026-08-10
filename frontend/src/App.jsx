import { Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import ChatPage from "./pages/ChatPage";
import AdminPage from "./pages/AdminPage";
import ConflictResolution from "./pages/ConflictResolution";

function App() {

    return (
        <Routes>

            <Route
                path="/"
                element={<Login />}
            />

            <Route
                path="/register"
                element={<Register />}
            />

            <Route
                path="/chat"
                element={<ChatPage />}
            />

            <Route
                path="/admin"
                element={<AdminPage />}
            />

            <Route
                path="/conflicts"
                element={<ConflictResolution />}
            />

        </Routes>
    );
}

export default App;