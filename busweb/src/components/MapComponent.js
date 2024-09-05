import React, { useEffect, useRef, useState } from 'react';

const MapComponent = ({ location, routeDetails }) => {
  const mapRef = useRef(null);
  const mapInstanceRef = useRef(null);
  const [markers, setMarkers] = useState([]);

  useEffect(() => {
    if (window.google && location.lat && location.lng) {
      const map = new window.google.maps.Map(mapRef.current, {
        center: { lat: location.lat, lng: location.lng },
        zoom: 12,
      });
      mapInstanceRef.current = map;
    }
  }, [location]);

  useEffect(() => {
    const geocodeAndAddMarker = async (address, title, icon) => {
      if (!address) return;

      const geocoder = new window.google.maps.Geocoder();
      try {
        const result = await new Promise((resolve, reject) => {
          geocoder.geocode({ address: address }, (results, status) => {
            if (status === 'OK') {
              resolve(results[0].geometry.location);
            } else {
              console.error('Geocode was not successful: ' + status);
              reject(new Error('Geocode was not successful: ' + status));
            }
          });
        });

        console.log(`Geocoded ${title}:`, result.lat(), result.lng());

        const marker = new window.google.maps.Marker({
          position: result,
          map: mapInstanceRef.current,
          title: title,
          icon: icon,
        });

        setMarkers(prev => [...prev, marker]);
        return marker;
      } catch (error) {
        console.error('Error geocoding address:', address, error);
      }
    };

    const addRouteMarkers = async () => {
      if (mapInstanceRef.current && routeDetails) {
        // Clear existing markers
        markers.forEach(marker => marker.setMap(null));
        setMarkers([]);

        const newMarkers = [];

        // Add markers for each point
        if (routeDetails.startPoint) {
          const marker = await geocodeAndAddMarker(routeDetails.startPoint, routeDetails.startPoint, 'http://maps.google.com/mapfiles/ms/icons/green-dot.png');
          if (marker) newMarkers.push(marker);
        }
        if (routeDetails.endPoint) {
          const marker = await geocodeAndAddMarker(routeDetails.endPoint, routeDetails.endPoint, 'http://maps.google.com/mapfiles/ms/icons/red-dot.png');
          if (marker) newMarkers.push(marker);
        }
        if (routeDetails.startStop) {
          const marker = await geocodeAndAddMarker(routeDetails.startStop.address, routeDetails.startStop.address, 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
          if (marker) newMarkers.push(marker);
        }
        if (routeDetails.endStop) {
          const marker = await geocodeAndAddMarker(routeDetails.endStop.address, routeDetails.endStop.address, 'http://maps.google.com/mapfiles/ms/icons/yellow-dot.png');
          if (marker) newMarkers.push(marker);
        }
        if (routeDetails.transferStop) {
          const marker = await geocodeAndAddMarker(routeDetails.transferStop.address, routeDetails.transferStop.address, 'http://maps.google.com/mapfiles/ms/icons/purple-dot.png');
          if (marker) newMarkers.push(marker);
        }

        // Fit bounds to show all markers
        if (newMarkers.length > 0) {
          const bounds = new window.google.maps.LatLngBounds();
          newMarkers.forEach(marker => bounds.extend(marker.getPosition()));
          mapInstanceRef.current.fitBounds(bounds);
        }
      }
    };

    addRouteMarkers();
  }, [routeDetails]);

  return <div ref={mapRef} style={{ width: '100%', height: '100%' }} />;
};

export default MapComponent;
