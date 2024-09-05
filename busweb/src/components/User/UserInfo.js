import React, { useState, useEffect } from "react";
import {
  Container,
  Row,
  Col,
  Card,
  Alert,
  Image,
  Button,
  Modal,
  Form,
  Table,
  Spinner,
  Tooltip,
  OverlayTrigger,
} from "react-bootstrap";
import { authAPIs, endpoints } from "../../configs/APIs";
import styles from "../Style"; // Import styles from the style.js
import { useAuth } from "../AuthContext";

const UserInfo = () => {
  const { userInfo, fetchUserInfo } = useAuth();
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [showAvatarModal, setShowAvatarModal] = useState(false);
  const [newFullName, setNewFullName] = useState("");
  const [newEmail, setNewEmail] = useState("");
  const [newAvatar, setNewAvatar] = useState(null);
  const [newPassword, setNewPassword] = useState(""); // State for new password
  const [editField, setEditField] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);
  const [likedRoutes, setLikedRoutes] = useState([]);
  const [showLikedRoutes, setShowLikedRoutes] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);

  useEffect(() => {
    if (userInfo) {
      setNewFullName(userInfo.fullName);
      setNewEmail(userInfo.email);
      fetchLikedRoutes();
    }
  }, [userInfo]);

  const fetchLikedRoutes = async () => {
    try {
      const response = await authAPIs().get(endpoints["liked-routes"]);
      setLikedRoutes(response.data);
    } catch (err) {
      console.error("Error fetching liked routes:", err);
      setError("Error: Unable to fetch liked routes.");
    }
  };

  const handleShowModal = (field) => {
    setEditField(field);
    setShowModal(true);
  };

  const handleDeleteRoute = async (favoriteId) => {
    if (window.confirm("Bạn chắc chắn muốn xóa?")) {
      try {
        await authAPIs().delete(endpoints["unlike-route"](favoriteId));
        setShowSuccessModal(true); // Show success modal on successful deletion
        fetchLikedRoutes(); // Refresh the list of liked routes
      } catch (err) {
        console.error("Error deleting route:", err);
        setError("Error: Unable to delete the route.");
      }
    }
  };

  const handleCloseModal = () => setShowModal(false);

  const handleShowAvatarModal = () => setShowAvatarModal(true);

  const handleCloseAvatarModal = () => setShowAvatarModal(false);

  const handleAvatarChange = (e) => setNewAvatar(e.target.files[0]);

  const handleCloseSuccessModal = () => setShowSuccessModal(false);

  const handleSaveChanges = async () => {
    setIsUpdating(true);
    try {
      const formData = new FormData();
      if (editField === "fullName") {
        formData.append("fullName", newFullName);
      } else if (editField === "email") {
        formData.append("email", newEmail);
      } else if (editField === "password") {
        formData.append("password", newPassword); // Add new password to form data
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
                  overlay={
                    <Tooltip id="avatar-tooltip">Thay đổi Avatar</Tooltip>
                  }
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
                      <td>
                        <strong>Họ và Tên:</strong>
                      </td>
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
                      <td>
                        <strong>Email:</strong>
                      </td>
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
                    <tr>
                      <td></td>
                      <td></td>
                      <td>
                        <Button
                          variant="primary"
                          onClick={() => handleShowModal("password")}
                        >
                          Thay đổi mật khẩu
                        </Button>
                      </td>
                    </tr>
                  </tbody>
                </Table>
                <Button
                  variant="secondary"
                  onClick={() => setShowLikedRoutes(!showLikedRoutes)}
                >
                  {showLikedRoutes
                    ? "Ẩn tuyến đường yêu thích"
                    : "Xem tuyến đường yêu thích"}
                </Button>
                {showLikedRoutes && (
                  <Table className="mt-3" striped bordered hover>
                    <thead>
                      <tr>
                        <th>Tên tuyến đường</th>
                        <th>Điểm bắt đầu</th>
                        <th>Điểm kết thúc</th>
                        <th></th>
                      </tr>
                    </thead>
                    <tbody>
                      {likedRoutes.map((route) => (
                        <tr key={route.favoriteId}>
                          <td>{route.routeId.routeName}</td>
                          <td>{route.routeId.startPoint}</td>
                          <td>{route.routeId.endPoint}</td>
                          <td>
                            <Button
                              variant="danger"
                              onClick={() =>
                                handleDeleteRoute(route.favoriteId)
                              }
                            >
                              Xóa
                            </Button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </Table>
                )}
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
            {editField === "password" && (
              <Form.Group controlId="formPassword">
                <Form.Label>
                  <strong>Mật khẩu mới:</strong>
                </Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Nhập mật khẩu mới"
                  value={newPassword}
                  onChange={(e) => setNewPassword(e.target.value)}
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
            disabled={isUpdating}
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
            disabled={isUpdating}
          >
            {isUpdating ? (
              <Spinner animation="border" size="sm" />
            ) : (
              "Lưu thay đổi"
            )}
          </Button>
        </Modal.Footer>
      </Modal>
      <Modal show={showSuccessModal} onHide={handleCloseSuccessModal}>
        <Modal.Header closeButton>
          <Modal.Title>Thông báo</Modal.Title>
        </Modal.Header>
        <Modal.Body>Đã xóa tuyến đường yêu thích thành công</Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleCloseSuccessModal}>
            OK
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default UserInfo;
