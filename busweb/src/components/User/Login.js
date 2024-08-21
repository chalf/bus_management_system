import React, { useState, useEffect } from 'react';
import { Form, Button, Container, Row, Col, Alert, Modal } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom'; // Import useNavigate
import { authAPIs, endpoints } from '../../configs/APIs';
import { Cookies } from 'react-cookie';
import styles from '../Style';

const cookies = new Cookies(); // Initialize Cookies

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');
  const [token, setToken] = useState('');
  const location = useLocation(); // Access location object
  const navigate = useNavigate(); // Initialize navigate

  useEffect(() => {
    if (location.state) {
      setUsername(location.state.username || '');
      setPassword(location.state.password || '');
    }
  }, [location.state]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Create FormData object
    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

    try {
      const response = await authAPIs().post(endpoints.login, formData, {
        headers: {
          'Content-Type': 'multipart/form-data' // Set header to indicate form-data
        }
      });

      if (response.status === 200) {
        // Token is the response data directly
        const token = response.data;
        // Save token in cookies
        cookies.set('authToken', token, { path: '/' });
        setToken(token);
        setModalMessage('Đăng nhập thành công!');
        setShowModal(true);
      } else {
        setError('Đăng nhập không thành công. Vui lòng thử lại.');
        console.error(`Login failed with status: ${response.status}`);
      }
    } catch (err) {
      setError('Có lỗi xảy ra, vui lòng thử lại.');
      console.error('Login error:', err);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    // Optionally redirect after closing the modal
    navigate('/');
  };

  return (
    <Container className="mt-5">
      <Row className="justify-content-md-center">
        <Col md={4}>
          <div style={styles.formContainer}>
            <h1 className="text-center">Đăng nhập</h1>
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleSubmit}>
              <Form.Group controlId="formUsername" className="mb-3">
                <Form.Label>Tài khoản:</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Tài khoản"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </Form.Group>

              <Form.Group controlId="formPassword" className="mb-3">
                <Form.Label>Mật khẩu:</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Mật khẩu"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </Form.Group>

              <Button variant="primary" type="submit" className="w-100">
                Đăng nhập
              </Button>
            </Form>
          </div>
        </Col>
      </Row>

      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Thông báo</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {modalMessage}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default Login;
