import React, { useEffect, useState } from "react";

const HomePage = () => {
    const [location, setLocation] = useState({ lat: null, lng: null });
    const [error, setError] = useState(null);
  
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
    }, []);

    const apikey = process.env.REACT_APP_GOOGLE_API_KEY
  
    return (
      <div style={{ width: "100vw", height: "100vh" }}>
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
