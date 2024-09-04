import React from 'react';
import { Navbar, Nav, Container, NavDropdown } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';

import { useAuth } from './AuthContext';

const NavBar = () => {
  const { userInfo, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout(); // Use the logout function from AuthContext
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
