import React, { useState } from 'react';
import { Form, Button, Container, Row, Col, Alert, Modal, Spinner } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import { authAPIs, endpoints } from '../../configs/APIs'; 
import styles from '../Style';

const Register = () => {
  const [fullName, setFullName] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [email, setEmail] = useState('');
  const [avatar, setAvatar] = useState(null);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');
  const [loading, setLoading] = useState(false); // State for loading
  const navigate = useNavigate(); // Initialize navigate

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Basic validation
    if (!fullName || !username || !password || !confirmPassword || !email || !avatar) {
      setError('Please fill in all fields');
      console.error('Error: Please fill in all fields');
      return;
    }

    if (password !== confirmPassword) {
      setError('Passwords do not match');
      console.error('Error: Passwords do not match');
      return;
    }

    // Email regex for validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError('Invalid email format');
      console.error('Error: Invalid email format');
      return;
    }

    const formData = new FormData();
    formData.append('fullName', fullName);
    formData.append('username', username);
    formData.append('password', password);
    formData.append('email', email);
    formData.append('file', avatar);

    try {
      setLoading(true); // Show spinner
      const response = await authAPIs().post(endpoints.register, formData);

      if (response.status === 201) {
        // Handle success
        setSuccess('Registration successful!');
        setModalMessage('Bạn đã tạo tài khoản thành công');
        setError('');
        // Copy username and password to clipboard
        copyToClipboard(username, password);
        // Navigate to login page with username and password
        navigate('/login', { state: { username, password } });
      } else {
        // Handle unexpected success response
        setSuccess('');
        setError('Có lỗi xảy ra xin vui lòng thử lại sau.');
        setModalMessage('Có lỗi xảy ra xin vui lòng thử lại sau.');
        console.error(`Unexpected response status: ${response.status}`);
      }
      setShowModal(true);
    } catch (err) {
      if (err.response && err.response.status === 400) {
        setError('Có lỗi xảy ra xin vui lòng thử lại sau.');
        console.error('Validation error:', err.response.data);
      } else if (err.response && err.response.status === 409) {
        setError('Username or email already exists.');
        console.error('Conflict error:', err.response.data);
      } else {
        setError('Network error');
        console.error('Network error:', err);
      }
      setModalMessage('Có lỗi xảy ra xin vui lòng thử lại sau.');
      setShowModal(true);
    } finally {
      setLoading(false); // Hide spinner
    }
  };

  const copyToClipboard = (username, password) => {
    const textToCopy = `Username: ${username}\nPassword: ${password}`;
    navigator.clipboard.writeText(textToCopy).then(
      () => console.log('Username and password copied to clipboard'),
      (err) => console.error('Failed to copy text: ', err)
    );
  };

  const handleCloseModal = () => setShowModal(false);

  return (
    <Container className="mt-5">
      <Row className="justify-content-md-center">
        <Col md={6}>
          <div style={styles.formContainer}>
            <h1 className="text-center">Đăng ký</h1>
            {error && <Alert variant="danger">{error}</Alert>}
            {loading && (
              <div className="text-center mb-3">
                <Spinner animation="border" variant="primary" />
              </div>
            )}
            <Form onSubmit={handleSubmit}>
              <Form.Group controlId="formFullName" className="mb-3">
                <Form.Label>Họ và tên:</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Họ và tên"
                  value={fullName}
                  onChange={(e) => setFullName(e.target.value)}
                />
              </Form.Group>

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

              <Form.Group controlId="formConfirmPassword" className="mb-3">
                <Form.Label>Xác nhận mật khẩu:</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Xác nhận mật khẩu"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                />
              </Form.Group>

              <Form.Group controlId="formEmail" className="mb-3">
                <Form.Label>Email:</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                />
              </Form.Group>

              <Form.Group controlId="formAvatar" className="mb-3">
                <Form.Label>Avatar:</Form.Label>
                <Form.Control
                  type="file"
                  accept="image/png, image/jpeg"
                  onChange={(e) => setAvatar(e.target.files[0])}
                />
              </Form.Group>

              <Button variant="primary" type="submit" className="w-100" disabled={loading}>
                Đăng ký
              </Button>
            </Form>
          </div>
        </Col>
      </Row>

      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>{error ? 'Error' : 'Success'}</Modal.Title>
        </Modal.Header>
        <Modal.Body>{modalMessage}</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default Register;
