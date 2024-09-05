import React, { useState, useEffect, useRef } from "react";
import { Container, Row, Col, Spinner } from "react-bootstrap";
import styles from "./Style";
import { authAPIs, endpoints } from "../configs/APIs";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import PinDropIcon from "@mui/icons-material/PinDrop";
import WhereToVoteIcon from "@mui/icons-material/WhereToVote";
import { Tooltip, IconButton } from "@mui/material";
import MapComponent from "./MapComponent";

const HomePage = () => {
  const [location, setLocation] = useState({ lat: null, lng: null });
  const [error, setError] = useState(null);
  const [isOpen, setIsOpen] = useState(false);
  const [searchInput1, setSearchInput1] = useState("");
  const [searchInput2, setSearchInput2] = useState("");
  const [routeDetails, setRouteDetails] = useState(null); // State to store route details
  const searchInput1Ref = useRef(null);
  const searchInput2Ref = useRef(null);
  const [mapsLoaded, setMapsLoaded] = useState(false);
  const [loading, setLoading] = useState(false); // State for loading spinner

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

      console.log("Search result:", response.data);

      // Format the route details
      const formattedRouteDetails = {
        ...response.data[0],
        startPoint: response.data[0].startPoint,
        endPoint: response.data[0].endPoint,
        startStop: {
          ...response.data[0].startStop,
          address: response.data[0].startStop.address,
        },
        endStop: {
          ...response.data[0].endStop,
          address: response.data[0].endStop.address,
        },
        transferStop: response.data[0].transferStop
          ? {
              ...response.data[0].transferStop,
              address: response.data[0].transferStop.address,
            }
          : null,
      };

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

  const handleMoreVertClick = () => {
    console.log("Xem lộ trình");
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

        {/* Display loading spinner when fetching data */}
        {loading && (
          <div className="d-flex justify-content-center my-4">
            <Spinner animation="border" variant="primary" />
          </div>
        )}

        {/* Route Details Box */}
        {!loading && routeDetails && (
          <Container>
            <Row className="my-2">
              <Col
                xs={1}
                className="d-flex align-items-center justify-content-center"
              >
                <LocationOnIcon />
              </Col>
              <Col xs={10}>{routeDetails.startPoint}</Col>
            </Row>
            <Row className="my-2">
              <Col
                xs={1}
                className="d-flex align-items-center justify-content-center"
              >
                <Tooltip title="Xem lộ trình">
                  <IconButton onClick={handleMoreVertClick}>
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
                  {routeDetails.startStop.address}
                  <br />
                  Xe Buýt:{" "}
                  {routeDetails.busesForStartRoute
                    .map((bus) => bus.busNumber)
                    .join(", ")}
                </div>
              </Col>
            </Row>
            <Row className="my-2">
              <Col
                xs={1}
                className="d-flex align-items-center justify-content-center"
              >
                <Tooltip title="Xem lộ trình">
                  <IconButton onClick={handleMoreVertClick}>
                    <MoreVertIcon />
                  </IconButton>
                </Tooltip>
              </Col>
              <Col xs={10}></Col>
            </Row>

            {routeDetails.transferStop &&
              routeDetails.busesForTransferRoute && (
                <>
                  <Row className="my-2">
                    <Col
                      xs={1}
                      className="d-flex align-items-center justify-content-center"
                    >
                      <WhereToVoteIcon />
                    </Col>
                    <Col xs={10}>
                      <div>
                        {routeDetails.transferStop.address}
                        <br />
                        Xe Buýt:{" "}
                        {routeDetails.busesForTransferRoute
                          .map((bus) => bus.busNumber)
                          .join(", ")}
                      </div>
                    </Col>
                  </Row>
                  <Row className="my-2">
                    <Col
                      xs={1}
                      className="d-flex align-items-center justify-content-center"
                    >
                      <Tooltip title="Xem lộ trình">
                        <IconButton onClick={handleMoreVertClick}>
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
              <Col xs={10}>{routeDetails.endStop.address}</Col>
            </Row>
            <Row className="my-2">
              <Col
                xs={1}
                className="d-flex align-items-center justify-content-center"
              >
                <Tooltip title="Xem lộ trình">
                  <IconButton onClick={handleMoreVertClick}>
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
              <Col xs={10}>{routeDetails.endPoint}</Col>
            </Row>
            <Row className="my-2">
              <Col
                xs={1}
                className="d-flex align-items-center justify-content-center"
              ></Col>
              <Col xs={10}>Khoảng cách: {routeDetails.distanceTotal} km</Col>
            </Row>
          </Container>
        )}
      </div>

      {error ? (
        <p>{error}</p>
      ) : location.lat && location.lng ? (
        <MapComponent location={location} routeDetails={routeDetails} />
      ) : (
        <p>Đang lấy vị trí của bạn...</p>
      )}
    </div>
  );
};

export default HomePage;
