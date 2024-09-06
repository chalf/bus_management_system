import React, { useState, useEffect, useRef } from "react";
import { Container, Row, Col, Spinner, Nav, Tab } from "react-bootstrap";
import styles from "./Style";
import { authAPIs, endpoints } from "../configs/APIs";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import PinDropIcon from "@mui/icons-material/PinDrop";
import { Hail, Favorite } from "@mui/icons-material";
import { Tooltip, IconButton } from "@mui/material";
import MapComponent from "./MapComponent";
import { Button, Modal } from "react-bootstrap";
import { useAuth } from "./AuthContext";

const HomePage = () => {
  const { userInfo } = useAuth();
  const [location, setLocation] = useState({ lat: null, lng: null });
  const [error, setError] = useState(null);
  const [isOpen, setIsOpen] = useState(false);
  const [searchInput1, setSearchInput1] = useState("");
  const [searchInput2, setSearchInput2] = useState("");
  const [routeDetails, setRouteDetails] = useState(null);
  const searchInput1Ref = useRef(null);
  const searchInput2Ref = useRef(null);
  const [mapsLoaded, setMapsLoaded] = useState(false);
  const [loading, setLoading] = useState(false);
  const [selectedRoute, setSelectedRoute] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMessage, setModalMessage] = useState("");
  const [modalTitle, setModalTitle] = useState("");
  const [activeTabIndex, setActiveTabIndex] = useState(0);

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setLocation({
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          });
        },
        (err) => {
          setError(err.message);
        }
      );
    } else {
      setError("Geolocation is not supported by this browser.");
    }

    // Check if Google Maps API is loaded
    const checkGoogleMapsLoaded = () => {
      if (window.google && window.google.maps) {
        setMapsLoaded(true);
      } else {
        setTimeout(checkGoogleMapsLoaded, 100); // Check again after 100ms
      }
    };

    checkGoogleMapsLoaded();
  }, []);

  useEffect(() => {
    if (mapsLoaded) {
      initAutocomplete();
    }
  }, [mapsLoaded]);

  const initAutocomplete = () => {
    if (window.google && window.google.maps) {
      const options = { types: ["geocode"] };

      const autocomplete1 = new window.google.maps.places.Autocomplete(
        searchInput1Ref.current,
        options
      );
      const autocomplete2 = new window.google.maps.places.Autocomplete(
        searchInput2Ref.current,
        options
      );

      autocomplete1.addListener("place_changed", () => {
        const place = autocomplete1.getPlace();
        if (place && place.formatted_address) {
          setSearchInput1(place.formatted_address);
        }
      });

      autocomplete2.addListener("place_changed", () => {
        const place = autocomplete2.getPlace();
        if (place && place.formatted_address) {
          setSearchInput2(place.formatted_address);
        }
      });
    }
  };

  const handleInputChange = (setter) => (event) => {
    setter(event.target.value);
  };

  const handleSearch = async () => {
    setLoading(true);
    try {
      const origin = searchInput1.trim();
      const destination = searchInput2.trim();

      const response = await authAPIs().get(endpoints.find, {
        params: {
          origin: origin,
          destination: destination,
        },
      });

      const formattedRouteDetails = response.data.slice(0, 5).map((route) => ({
        ...route,
        startPoint: route.startPoint,
        endPoint: route.endPoint,
        startStop: {
          ...route.startStop,
          address: route.startStop.address,
        },
        endStop: {
          ...route.endStop,
          address: route.endStop.address,
        },
        transferStop: route.transferStop
          ? {
              ...route.transferStop,
              address: route.transferStop.address,
            }
          : null,
        startRoute: route.startRoute || {}, // Provide a default empty object
        transferRoute: route.transferRoute || {}, // Provide a default empty object
      }));

      setRouteDetails(formattedRouteDetails);
    } catch (error) {
      console.error("Error fetching route:", error);
    } finally {
      setLoading(false);
    }
  };

  // Add this helper function to convert addresses to LatLng
  const getLatLng = async (address) => {
    return new Promise((resolve, reject) => {
      const geocoder = new window.google.maps.Geocoder();
      geocoder.geocode({ address: address }, (results, status) => {
        if (status === "OK") {
          resolve({
            lat: results[0].geometry.location.lat(),
            lng: results[0].geometry.location.lng(),
          });
        } else {
          reject(
            new Error(
              "Geocode was not successful for the following reason: " + status
            )
          );
        }
      });
    });
  };

  const handleMoreVertClick = (routeIndex, segmentIndex) => {
    if (routeDetails && routeDetails[routeIndex]) {
      const route = routeDetails[routeIndex];
      let start, end;

      switch (segmentIndex) {
        case 0:
          start = route.startPoint;
          end = route.startStop.address;
          break;
        case 1:
          start = route.startStop.address;
          end = route.transferStop
            ? route.transferStop.address
            : route.endStop.address;
          break;
        case 2:
          if (route.transferStop) {
            start = route.transferStop.address;
            end = route.endStop.address;
          } else {
            start = route.endStop.address;
            end = route.endPoint;
          }
          break;
        case 3:
          start = route.endStop.address;
          end = route.endPoint;
          break;
        default:
          console.error("Invalid segment index");
          return;
      }

      setSelectedRoute({ start, end });
    }
  };

  const handleDoubleClick = (routeId) => {
    console.log("Id: ", routeId);
    if (routeId) {
      likeRoute(routeId);
    } else {
      console.error("Route ID is not defined");
    }
  };

  const handleCloseModal = () => {
    setModalOpen(false);
  };

  const likeRoute = async (routeId) => {
    try {
      if (!routeId) {
        console.error("No route ID provided");
        setModalTitle("Lỗi");
        setModalMessage("Có lỗi xảy ra!");
        setModalOpen(true);
        return;
      }
  
      // Use the function to get the URL
      const likeRouteEndpoint = endpoints['like-route'](routeId);
      
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

  const apikey = process.env.REACT_APP_GOOGLE_API_KEY;

  return (
    <div style={{ width: "100vw", height: "90vh", position: "relative" }}>
      {/* Half-round button */}
      <div
        style={{
          ...styles.halfRoundButton,
          ...(isOpen ? styles.halfRoundButtonOpen : {}),
        }}
        onClick={() => setIsOpen(!isOpen)}
      >
        {isOpen ? "<" : ">"}
      </div>

      {/* Off-canvas menu */}
      <div
        style={{
          ...styles.offCanvasMenu,
          ...(isOpen ? styles.offCanvasMenuOpen : {}),
        }}
      >
        <h2 style={styles.title}>Tìm kiếm</h2>
        <div style={styles.searchContainer}>
          <h3 style={styles.inputTitle}>Điểm bắt đầu:</h3>
          <input
            type="text"
            placeholder="Nhập điểm bắt đầu"
            value={searchInput1}
            onChange={handleInputChange(setSearchInput1)}
            ref={searchInput1Ref}
            style={styles.searchInput}
          />
          <h3 style={styles.inputTitle}>Điểm đến:</h3>
          <input
            type="text"
            placeholder="Nhập điểm đến"
            value={searchInput2}
            onChange={handleInputChange(setSearchInput2)}
            ref={searchInput2Ref}
            style={styles.searchInput}
          />
          <button onClick={handleSearch} className="btn btn-primary">
            Tìm kiếm
          </button>
        </div>
        {loading ? (
  <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100%" }}>
    <Spinner animation="border" role="status">
      <span className="visually-hidden">Loading...</span>
    </Spinner>
  </div>
) : (
  // Display loading spinner when fetching data
  !loading &&
  routeDetails &&
  routeDetails.length > 0 && (
    <Tab.Container
      defaultActiveKey="0"
      onSelect={(k) => setActiveTabIndex(parseInt(k))}
    >
      <Nav variant="tabs">
        {routeDetails.map((_, index) => (
          <Nav.Item key={index}>
            <Nav.Link eventKey={index.toString()}>{`Tuyến số ${
              index + 1
            }`}</Nav.Link>
          </Nav.Item>
        ))}
      </Nav>
      <Tab.Content>
        {routeDetails.map((route, index) => (
          <Tab.Pane eventKey={index.toString()} key={index}>
            <Container>
                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      >
                        <LocationOnIcon />
                      </Col>
                      <Col xs={10}>
                        <strong>Điểm đi:</strong>
                        <br />
                        {route.startPoint}
                      </Col>
                    </Row>
                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      >
                        <Tooltip title="Xem lộ trình">
                          <IconButton
                            onClick={() => handleMoreVertClick(index, 0)}
                          >
                            <MoreVertIcon />
                          </IconButton>
                        </Tooltip>
                      </Col>
                      <Col xs={10}></Col>
                    </Row>
                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      >
                        <PinDropIcon />
                      </Col>
                      <Col xs={10}>
                        <div>
                          <strong>Lên tại trạm:</strong>
                          <br />
                          {route.startStop.address}
                          <br />
                          <strong>Lên xe Bus số:</strong>{" "}
                          {route.busesForStartRoute
                            .map((bus) => bus.busNumber)
                            .join(", ")}
                        </div>
                        <Tooltip title="Nhấp đúp chuột vào để lưu tuyến">
                          <div
                            className="mt-1"
                            onDoubleClick={() =>
                              handleDoubleClick(route.startRoute.routeId)
                            }
                            style={{ cursor: "pointer" }}
                          >
                            <strong>Tuyến: </strong> 
                            {route.startRoute.routeName}  <Favorite />
                          </div>
                        </Tooltip>
                      </Col>
                    </Row>
                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      >
                        <Tooltip title="Xem lộ trình">
                          <IconButton
                            onClick={() => handleMoreVertClick(index, 1)}
                          >
                            <MoreVertIcon />
                          </IconButton>
                        </Tooltip>
                      </Col>
                      <Col xs={10}></Col>
                    </Row>

                    {route.transferStop && route.busesForTransferRoute && (
                      <>
                        <Row className="my-2">
                          <Col
                            xs={1}
                            className="d-flex align-items-center justify-content-center"
                          >
                            <Hail />
                          </Col>
                          <Col xs={10}>
                            <div>
                              <strong>Xuống tại trạm:</strong>
                              <br />
                              {route.transferStop.address}
                              <br />
                              <strong>Lên xe Bus số:</strong>{" "}
                              {route.busesForTransferRoute
                                .map((bus) => bus.busNumber)
                                .join(", ")}
                            </div>
                            <Tooltip title="Nhấp đúp chuột vào để lưu tuyến">
                              <div
                                className="mt-1"
                                onDoubleClick={() =>
                                  handleDoubleClick(route.transferRoute.routeId)
                                }
                                style={{ cursor: "pointer" }}
                              >
                                <strong>Tuyến: </strong>
                                {route.transferRoute.routeName}  <Favorite />
                              </div>
                            </Tooltip>
                          </Col>
                        </Row>
                        <Row className="my-2">
                          <Col
                            xs={1}
                            className="d-flex align-items-center justify-content-center"
                          >
                            <Tooltip title="Xem lộ trình">
                              <IconButton
                                onClick={() => handleMoreVertClick(index, 2)}
                              >
                                <MoreVertIcon />
                              </IconButton>
                            </Tooltip>
                          </Col>
                          <Col xs={10}></Col>
                        </Row>
                      </>
                    )}

                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      >
                        <PinDropIcon />
                      </Col>
                      <Col xs={10}>
                        <strong>Xuống tại trạm:</strong>
                        <br />
                        {route.endStop.address}
                      </Col>
                    </Row>
                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      >
                        <Tooltip title="Xem lộ trình">
                          <IconButton
                            onClick={() => handleMoreVertClick(index, 3)}
                          >
                            <MoreVertIcon />
                          </IconButton>
                        </Tooltip>
                      </Col>
                      <Col xs={10}></Col>
                    </Row>
                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      >
                        <LocationOnIcon />
                      </Col>
                      <Col xs={10}>
                        <strong>Điểm đến:</strong>
                        <br />
                        {route.endPoint}
                      </Col>
                    </Row>
                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      ></Col>
                      <Col xs={10}>
                        <strong>Khoảng cách: </strong>{" "}
                        {parseFloat(route.distanceTotal).toFixed(2)} km
                      </Col>
                    </Row>
                    <Row className="my-2">
                      <Col
                        xs={1}
                        className="d-flex align-items-center justify-content-center"
                      ></Col>
                      <Col xs={10}>
                        <strong>Ước tính: </strong>
                        {parseFloat(route.durationTotal).toFixed(2)} phút
                      </Col>
                    </Row>
                  </Container>
          </Tab.Pane>
        ))}
      </Tab.Content>
    </Tab.Container>
  )
)}
      </div>

      {error ? (
        <p>{error}</p>
      ) : location.lat && location.lng ? (
        <MapComponent
          location={location}
          routeDetails={routeDetails ? [routeDetails[activeTabIndex]] : []}
          selectedRoute={selectedRoute}
        />
      ) : (
        <p>Đang lấy vị trí của bạn...</p>
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
          {!userInfo && (
            <Button
              variant="primary"
              onClick={() => (window.location.href = "/login")}
            >
              Đăng nhập
            </Button>
          )}
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default HomePage;
