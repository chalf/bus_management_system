import React, { useState, useEffect, useRef } from "react";
import styles from "./Style"; // Import the styles

const HomePage = () => {
  const [location, setLocation] = useState({ lat: null, lng: null });
  const [error, setError] = useState(null);
  const [isOpen, setIsOpen] = useState(false);
  const [searchInput1, setSearchInput1] = useState("");
  const [searchInput2, setSearchInput2] = useState("");
  const searchInput1Ref = useRef(null);
  const searchInput2Ref = useRef(null);

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

    // Initialize Google Maps Autocomplete
    const initAutocomplete = () => {
      if (window.google && window.google.maps) {
        const options = { types: ["geocode"] };
        
        new window.google.maps.places.Autocomplete(searchInput1Ref.current, options);
        new window.google.maps.places.Autocomplete(searchInput2Ref.current, options);
      }
    };

    // Wait until the script is loaded
    if (window.google) {
      initAutocomplete();
    } else {
      window.initMap = initAutocomplete;
    }
  }, []);

  const handleSearch = () => {
    console.log("Searching with:", searchInput1, searchInput2);
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
            onChange={(e) => setSearchInput1(e.target.value)}
            ref={searchInput1Ref}
            style={styles.searchInput}
          />
          <h3 style={styles.inputTitle}>Điểm đến:</h3>
          <input
            type="text"
            placeholder="Nhập điểm đến"
            value={searchInput2}
            onChange={(e) => setSearchInput2(e.target.value)}
            ref={searchInput2Ref}
            style={styles.searchInput}
          />
          <button onClick={handleSearch} style={styles.searchButton}>
            Tìm kiếm
          </button>
        </div>
      </div>

      {error ? (
        <p>{error}</p>
      ) : location.lat && location.lng ? (
        <iframe
          title="User Location"
          src={`https://www.google.com/maps/embed/v1/view?key=${apikey}&center=${location.lat},${location.lng}&zoom=14&maptype=roadmap`}
          width="100%"
          height="100%"
          style={{ border: 0 }}
          allowFullScreen=""
          loading="lazy"
        ></iframe>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default HomePage;