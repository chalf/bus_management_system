import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NavBar from "./components/NavBar";
import GoogleMapsScript from "./components/GoogleMapsScript";
import UserInfo from "./components/User/UserInfo";
import Register from "./components/User/Register";
import Login from "./components/User/Login";
import HomePage from "./components/HomePage";
import RoutePage from "./components/Routes";
import "bootstrap/dist/css/bootstrap.min.css";
import { AuthProvider } from "./components/AuthContext";

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <NavBar />
        <div className="App">
          <GoogleMapsScript />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/user-info" element={<UserInfo />} />
            <Route path="/register" element={<Register />} />
            <Route path="/login" element={<Login />} />
            <Route path="/routes" element={<RoutePage />} />
          </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
};

export default App;
