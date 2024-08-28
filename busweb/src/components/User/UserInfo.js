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
import cookie from "react-cookies";
import { authAPIs, endpoints } from "../../configs/APIs";
import styles from "../Style"; // Import styles from the style.js

const UserInfo = () => {
  const [userInfo, setUserInfo] = useState(null);
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false); // State for modal visibility
  const [showAvatarModal, setShowAvatarModal] = useState(false); // State for avatar modal
  const [newFullName, setNewFullName] = useState(""); // State for new full name
  const [newEmail, setNewEmail] = useState(""); // State for new email
  const [newAvatar, setNewAvatar] = useState(null); // State for new avatar file
  const [editField, setEditField] = useState(""); // State to track which field is being edited
  const [isLoading, setIsLoading] = useState(false); // State for loading indicator
  const [isUpdating, setIsUpdating] = useState(false); // State for update loading indicator

  useEffect(() => {
    const fetchUserInfo = async () => {
      setIsLoading(true); // Set loading state to true
      try {
        const token = cookie.load("authToken"); // Load token from cookies
        const response = await authAPIs().get(endpoints["current-user"], {
          headers: {
            Authorization: `${token}`,
          },
        });
        setUserInfo(response.data);
        setNewFullName(response.data.fullName); // Initialize with current data
        setNewEmail(response.data.email); // Initialize with current data
      } catch (err) {
        setError("Unable to fetch user information.");
        console.error("Error fetching user info:", err);
      } finally {
        setIsLoading(false); // Set loading state to false
      }
    };

    fetchUserInfo();
  }, []);

  const handleShowModal = (field) => {
    setEditField(field);
    setShowModal(true);
  };

  const handleCloseModal = () => setShowModal(false);

  const handleShowAvatarModal = () => setShowAvatarModal(true);

  const handleCloseAvatarModal = () => setShowAvatarModal(false);

  const handleAvatarChange = (e) => setNewAvatar(e.target.files[0]);

  const handleSaveChanges = async () => {
    setIsUpdating(true); // Set updating state to true
    try {
      const token = cookie.load("authToken");
      const formData = new FormData();
      if (editField === "fullName") {
        formData.append("fullName", newFullName);
      } else if (editField === "email") {
        formData.append("email", newEmail);
      } else if (newAvatar) {
        formData.append("file", newAvatar); // Append the file with key "file"
      }

      // Update user information
      await authAPIs().post(endpoints["update"], formData, {
        headers: {
          Authorization: `${token}`,
        },
      });

      // Refetch user information
      const response = await authAPIs().get(endpoints["current-user"], {
        headers: {
          Authorization: `${token}`,
        },
      });

      setUserInfo(response.data);
      setNewFullName(response.data.fullName); // Update state with new data
      setNewEmail(response.data.email); // Update state with new data
      setNewAvatar(null); // Clear selected avatar file
      setShowModal(false);
      setShowAvatarModal(false);
    } catch (err) {
      if (err.response) {
        console.error("Error response data:", err.response.data);
        console.error("Error response status:", err.response.status);
        console.error("Error response headers:", err.response.headers);
        setError(`Error: ${err.response.status} - ${err.response.data.message}`);
      } else {
        console.error("Error:", err.message);
        setError("Error: Unable to save changes.");
      }
    } finally {
      setIsUpdating(false); // Set updating state to false
    }
  };

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
