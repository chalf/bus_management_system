// AuthContext.js
import React, { createContext, useState, useEffect } from 'react';
import cookie from 'react-cookies';
import { authAPIs, endpoints } from '../configs/APIs';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [userInfo, setUserInfo] = useState(null);

  const fetchUserInfo = async () => {
    try {
      const token = cookie.load('authToken'); 
      if (token) {
        const response = await authAPIs().get(endpoints['current-user'], {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUserInfo(response.data);
      } else {
        setUserInfo(null);
      }
    } catch (err) {
      console.error('Error fetching user info:', err);
    }
  };

  useEffect(() => {
    fetchUserInfo();
  }, []);

  return (
    <AuthContext.Provider value={{ userInfo, fetchUserInfo }}>
      {children}
    </AuthContext.Provider>
  );
};
