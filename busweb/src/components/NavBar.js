import React, { useState, useEffect } from 'react';
import { Navbar, Nav, Container, NavDropdown } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import cookie from 'react-cookies';
import axios from 'axios';
import { authAPIs, endpoints } from '../configs/APIs'; 

const NavBar = () => {
  const [userInfo, setUserInfo] = useState(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = cookie.load('authToken'); 
        if (token) {
          const response = await authAPIs().get(endpoints['current-user'], {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          setUserInfo(response.data);
        } else {
          setUserInfo(null); // Clear user info if no token is present
        }
      } catch (err) {
        setError('Unable to fetch user information.');
        console.error('Error fetching user info:', err);
      }
    };

    fetchUserInfo();

    // Watch for changes in the authToken cookie and refetch user info
    const cookieChangeListener = () => fetchUserInfo();
    window.addEventListener('cookiechange', cookieChangeListener);

    return () => {
      window.removeEventListener('cookiechange', cookieChangeListener);
    };
  }, []);

  const handleLogout = () => {
    cookie.remove('authToken'); 
    setUserInfo(null); 
    navigate('/login'); 
  };

  return (
    <Navbar style={{ backgroundColor: '#6666FF' }} variant="dark" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/">Giao thông công cộng</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link as={Link} to="/">Trang chủ</Nav.Link>
            <Nav.Link as={Link} to="/docs">Tài liệu</Nav.Link>
          </Nav>
          <Nav>
            <NavDropdown
              title={
                userInfo ? (
                  <>
                    <img
                      src={userInfo.avatarUrl}
                      alt="avatar"
                      style={{ borderRadius: '50%', width: '30px', height: '30px', marginRight: '8px' }}
                    />
                    Hi, {userInfo.fullName}
                  </>
                ) : (
                  <FaUser />
                )
              }
              id="basic-nav-dropdown"
              align="end"
            >
              {userInfo ? (
                <>
                  <NavDropdown.Item as={Link} to="/user-info">Xem trang cá nhân</NavDropdown.Item>
                  <NavDropdown.Item onClick={handleLogout}>Đăng xuất</NavDropdown.Item>
                </>
              ) : (
                <>
                  <NavDropdown.Item as={Link} to="/login">Đăng nhập</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/register">Đăng ký</NavDropdown.Item>
                </>
              )}
            </NavDropdown>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavBar;
