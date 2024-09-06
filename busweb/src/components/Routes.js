import React, { useEffect, useState } from "react";
import { endpoints, authAPIs } from "../configs/APIs";
import { Spinner, Table, OverlayTrigger, Tooltip, Modal, Button } from "react-bootstrap";
import { FaHeart } from "react-icons/fa";

const RoutePage = () => {
    const [routes, setRoutes] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [modalOpen, setModalOpen] = useState(false);
    const [modalMessage, setModalMessage] = useState("");
    const [modalTitle, setModalTitle] = useState("");

    useEffect(() => {
        const fetchRoutes = async () => {
            try {
                setIsLoading(true);
                const response = await authAPIs().get(endpoints["list-route"]);
                setRoutes(response.data);
            } catch (err) {
                console.error("Error fetching routes:", err);
                setError("Failed to fetch routes. Please try again later.");
            } finally {
                setIsLoading(false);
            }
        };

        fetchRoutes();
    }, []);

    const likeRoute = async (routeId) => {
        try {
            if (!routeId) {
                console.error("No route ID provided");
                setModalTitle("Lỗi");
                setModalMessage("Có lỗi xảy ra!");
                setModalOpen(true);
                return;
            }

            const likeRouteEndpoint = endpoints["like-route"](routeId);
            await authAPIs().post(likeRouteEndpoint);
            setModalTitle("Thông báo");
            setModalMessage("Đã thêm tuyến đường yêu thích");
            setModalOpen(true);
        } catch (error) {
            console.error("Error liking route:", error);
            setModalTitle("Lỗi");
            setModalMessage("Bạn phải đăng nhập mới lưu tuyến được!");
            setModalOpen(true);
        }
    };

    const handleLikeClick = (routeId) => {
        likeRoute(routeId);
    };

    const handleCloseModal = () => {
        setModalOpen(false);
    };

    return (
        <div className="container mt-5">
            <h1>Danh sách các tuyến đường</h1>
            {isLoading ? (
                <Spinner animation="border" role="status">
                    <span className="sr-only">Loading...</span>
                </Spinner>
            ) : error ? (
                <p className="text-danger">{error}</p>
            ) : (
                <Table striped bordered hover>
                    <thead className="thead-dark">
                        <tr>
                            <th>Tên tuyến</th>
                            <th className="text-center"></th>
                        </tr>
                    </thead>
                    <tbody>
                        {routes.map((route) => (
                            <tr key={route.routeId}>
                                <td>{route.routeName}</td>
                                <td className="text-center">
                                    <OverlayTrigger
                                        placement="top"
                                        overlay={
                                            <Tooltip id={`tooltip-${route.routeId}`}>Yêu thích</Tooltip>
                                        }
                                    >
                                        <span>
                                            <FaHeart
                                                className="text-danger"
                                                style={{ cursor: "pointer" }}
                                                onClick={() => handleLikeClick(route.routeId)}
                                            />
                                        </span>
                                    </OverlayTrigger>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            )}

            {/* Modal */}
            <Modal show={modalOpen} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>{modalTitle}</Modal.Title>
                </Modal.Header>
                <Modal.Body>{modalMessage}</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>
                        Đóng
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default RoutePage;
