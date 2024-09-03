import React, { useState, useEffect } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  Alert,
  Image,
  Dropdown,
  Button,
  Modal,
  Form,
  Table,
  Spinner,
  Tooltip,
  OverlayTrigger
} from "react-bootstrap";
import { authAPIs, endpoints } from "../../configs/APIs";
import styles from "../Style"; // Import styles from the style.js
import { useAuth } from '../AuthContext';

const UserInfo = () => {
  const { userInfo, fetchUserInfo } = useAuth();
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [showAvatarModal, setShowAvatarModal] = useState(false);
  const [newFullName, setNewFullName] = useState("");
  const [newEmail, setNewEmail] = useState("");
  const [newAvatar, setNewAvatar] = useState(null);
  const [editField, setEditField] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);

  useEffect(() => {
    if (userInfo) {
      setNewFullName(userInfo.fullName);
      setNewEmail(userInfo.email);
    }
  }, [userInfo]);

  const handleShowModal = (field) => {
    setEditField(field);
    setShowModal(true);
  };

  const handleCloseModal = () => setShowModal(false);

  const handleShowAvatarModal = () => setShowAvatarModal(true);

  const handleCloseAvatarModal = () => setShowAvatarModal(false);

  const handleAvatarChange = (e) => setNewAvatar(e.target.files[0]);

  const handleSaveChanges = async () => {
    setIsUpdating(true);
    try {
      const formData = new FormData();
      if (editField === "fullName") {
        formData.append("fullName", newFullName);
      } else if (editField === "email") {
        formData.append("email", newEmail);
      } else if (newAvatar) {
        formData.append("file", newAvatar);
      }

      await authAPIs().post(endpoints["update"], formData);

      await fetchUserInfo();

      setNewAvatar(null);
      setShowModal(false);
      setShowAvatarModal(false);
    } catch (err) {
      console.error("Error:", err);
      setError("Error: Unable to save changes.");
    } finally {
      setIsUpdating(false);
    }
  };

  if (isLoading) {
    return (
      <Container className="mt-5">
        <Spinner animation="border" role="status">
          <span className="sr-only">Loading...</span>
        </Spinner>
      </Container>
    );
  }

  if (!userInfo) {
    return (
      <Container className="mt-5">
        <Alert variant="info">Hãy đăng nhập để xem cập nhật</Alert>
      </Container>
    );
  }
  

  return (
    <Container className="mt-5">
      <Row className="justify-content-md-center">
        <Col md={8}>
          {error && <Alert variant="danger">{error}</Alert>}
          {isLoading ? (
            <Spinner animation="border" role="status">
              <span className="sr-only">Loading...</span>
            </Spinner>
          ) : userInfo ? (
            <Card className="text-center">
              <Card.Header>
                <h1>Thông tin cá nhân</h1>
              </Card.Header>
              <Card.Body>
                <OverlayTrigger
                  placement="top"
                  overlay={<Tooltip id="avatar-tooltip">Click to change avatar</Tooltip>}
                >
                  <Image
                    src={userInfo.avatarUrl}
                    roundedCircle
                    style={{
                      width: "150px",
                      height: "150px",
                      marginBottom: "20px",
                      cursor: "pointer",
                    }}
                    onClick={handleShowAvatarModal}
                  />
                </OverlayTrigger>
                <Card.Title>{userInfo.fullName}</Card.Title>
                <Table style={styles.customTable}>
                  <tbody>
                    <tr style={styles.customTableRow}>
                      <td><strong>Họ và Tên:</strong></td>
                      <td>{userInfo.fullName}</td>
                      <td>
                        <Button
                          variant="primary"
                          onClick={() => handleShowModal("fullName")}
                        >
                          Thay đổi
                        </Button>
                      </td>
                    </tr>
                    <tr>
                      <td><strong>Email:</strong></td>
                      <td>{userInfo.email}</td>
                      <td>
                        <Button
                          variant="primary"
                          onClick={() => handleShowModal("email")}
                        >
                          Thay đổi
                        </Button>
                      </td>
                    </tr>
                  </tbody>
                </Table>
                <Dropdown className="mt-3">
                  <Dropdown.Toggle variant="secondary" id="dropdown-basic">
                    Các tuyến đường yêu thích
                  </Dropdown.Toggle>
                  <Dropdown.Menu>
                    <Dropdown.Item href="#/route1">
                      Tuyến 1: A đến B
                    </Dropdown.Item>
                    <Dropdown.Item href="#/route2">
                      Tuyến 2: C đến D
                    </Dropdown.Item>
                    <Dropdown.Item href="#/route3">
                      Tuyến 3: E đến F
                    </Dropdown.Item>
                  </Dropdown.Menu>
                </Dropdown>
              </Card.Body>
            </Card>
          ) : (
            <p>Loading user information...</p>
          )}
        </Col>
      </Row>

      {/* Modal for editing user information */}
      <Modal show={showModal} onHide={handleCloseModal}>
        <Modal.Header closeButton>
          <Modal.Title>Chỉnh sửa thông tin</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            {editField === "fullName" && (
              <Form.Group controlId="formFullName">
                <Form.Label>
                  <strong>Họ và Tên:</strong>
                </Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Nhập họ và tên mới"
                  value={newFullName}
                  onChange={(e) => setNewFullName(e.target.value)}
                />
              </Form.Group>
            )}
            {editField === "email" && (
              <Form.Group controlId="formEmail">
                <Form.Label>
                  <strong>Email:</strong>
                </Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Nhập email mới"
                  value={newEmail}
                  onChange={(e) => setNewEmail(e.target.value)}
                />
              </Form.Group>
            )}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Hủy
          </Button>
          <Button
            variant="primary"
            onClick={handleSaveChanges}
            disabled={isUpdating} // Disable button while updating
          >
            {isUpdating ? (
              <Spinner animation="border" size="sm" />
            ) : (
              "Lưu thay đổi"
            )}
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Modal for changing avatar */}
      <Modal show={showAvatarModal} onHide={handleCloseAvatarModal}>
        <Modal.Header closeButton>
          <Modal.Title>Thay đổi ảnh đại diện</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group controlId="formFile">
              <Form.Label>
                <strong>Chọn ảnh đại diện mới:</strong>
              </Form.Label>
              <Form.Control
                type="file"
                accept="image/png, image/jpeg"
                onChange={handleAvatarChange}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseAvatarModal}>
            Hủy
          </Button>
          <Button
            variant="primary"
            onClick={handleSaveChanges}
            disabled={isUpdating} // Disable button while updating
          >
            {isUpdating ? (
              <Spinner animation="border" size="sm" />
            ) : (
              "Lưu thay đổi"
            )}
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default UserInfo;
