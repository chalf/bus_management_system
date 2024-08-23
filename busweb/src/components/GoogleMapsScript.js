import { useEffect } from "react";
import React from "react";

const GoogleMapsScript = () => {
  useEffect(() => {
    const apiKey = process.env.REACT_APP_GOOGLE_API_KEY;
    if (!apiKey) {
      console.error("Google API key is missing.");
      return;
    }

    const script = document.createElement("script");
    script.src = `https://maps.googleapis.com/maps/api/js?key=${apiKey}&libraries=places`;
    script.async = true;
    script.defer = true;

    script.onload = () => {
      if (window.google && window.google.maps) {
        window.initMap(); // Call the initMap function you assigned in your HomePage component
      } else {
        console.error("Google Maps API failed to load.");
      }
    };

    document.head.appendChild(script);

    return () => {
      // Cleanup script when the component unmounts
      document.head.removeChild(script);
    };
  }, []);

  return null; // This component does not render anything
};

export default GoogleMapsScript;

