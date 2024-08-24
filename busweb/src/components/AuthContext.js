import React, { createContext, useState, useContext, useEffect } from 'react';
import cookie from 'react-cookies';
import { authAPIs, endpoints } from '../configs/APIs';

const AuthContext = createContext(null);

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
      setUserInfo(null);
    }
  };

  useEffect(() => {
    fetchUserInfo();
  }, []);

  const login = (userData) => {
    setUserInfo(userData);
  };

  const logout = () => {
    cookie.remove('authToken');
    setUserInfo(null);
  };

  return (
    <AuthContext.Provider value={{ userInfo, login, logout, fetchUserInfo }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);