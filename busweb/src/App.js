import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import NavBar from "./components/NavBar";
import GoogleMapsScript from "./components/GoogleMapsScript";
import UserInfo from "./components/User/UserInfo";
import Register from "./components/User/Register";
import Login from "./components/User/Login";
import HomePage from "./components/HomePage";
import "bootstrap/dist/css/bootstrap.min.css";

const App = () => {
  return (
    <Router>
      <NavBar />
      <div className="App">
        <GoogleMapsScript />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/user-info" element={<UserInfo />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
