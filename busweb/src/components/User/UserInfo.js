import React, { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Alert, Image } from 'react-bootstrap';
import axios from 'axios';
import cookie from 'react-cookies';
import { authAPIs, endpoints } from '../../configs/APIs'; // Import endpoints

const UserInfo = () => {
  const [userInfo, setUserInfo] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = cookie.load('authToken'); // Load token from cookies
        const response = await authAPIs().get(endpoints['current-user'], {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUserInfo(response.data);
      } catch (err) {
        setError('Unable to fetch user information.');
        console.error('Error fetching user info:', err);
      }
    };

    fetchUserInfo();
  }, []);

  return (
    <Container className="mt-5">
      <Row className="justify-content-md-center">
        <Col md={8}>
          {error && <Alert variant="danger">{error}</Alert>}
          {userInfo ? (
            <Card className="text-center">
              <Card.Header><h1>Thông tin cá nhân</h1></Card.Header>
              <Card.Body>
                <Image src={userInfo.avatarUrl} roundedCircle style={{ width: '150px', height: '150px', marginBottom: '20px' }} />
                <Card.Title>{userInfo.fullName}</Card.Title>
                <Card.Text>
                  <strong>Họ và Tên:</strong> {userInfo.fullName}<br />
                  <strong>Email:</strong> {userInfo.email}<br />
                  <strong>Tên đăng nhập:</strong> {userInfo.username}
                </Card.Text>
              </Card.Body>
            </Card>
          ) : (
            <p>Loading user information...</p>
          )}
        </Col>
      </Row>
    </Container>
  );
};

export default UserInfo;
