import { useEffect } from "react";

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
        console.log("Google Maps API loaded successfully.");
      } else {
        console.error("Google Maps API failed to load.");
      }
    };

    script.onerror = (error) => {
      console.error("Error loading Google Maps API:", error);
    };

    // Check if the script is already in the document
    const existingScript = document.querySelector(`script[src^="https://maps.googleapis.com/maps/api/js"]`);
    if (existingScript) {
      console.log("Google Maps script already exists. Removing old script.");
      existingScript.remove();
    }

    document.head.appendChild(script);

    return () => {
      // Cleanup script when the component unmounts
      const scriptToRemove = document.querySelector(`script[src^="https://maps.googleapis.com/maps/api/js"]`);
      if (scriptToRemove) {
        document.head.removeChild(scriptToRemove);
      }
    };
  }, []);

  return null; // This component does not render anything
};

export default GoogleMapsScript;